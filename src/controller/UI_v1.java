package controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import model.*;
import sun.plugin.javascript.navig4.Layer;

import java.sql.SQLException;
import java.util.ArrayList;

public class UI_v1 {

    private enum CurrentStatus {
        PATIENT, ADMIN, ADDNODE, REMOVENODE, MODIFYBORDERS, SERVICEREQUEST, SETSTARTNODE
    };

    private CurrentStatus currentState = CurrentStatus.PATIENT;

    private GraphicsContext gc1 = null;

    private int counterForEdges = 0;

    @FXML
    private Button startNodeSelector;

    @FXML
    private TextField startNodeID;

    @FXML
    private Button setStartNodeConfirm;

    @FXML
    private TextField serviceNodeID;

    @FXML
    private Button requestServiceSelector;

    @FXML
    private TextArea MessageTextBox;

    @FXML
    private Layer lineLayer;

    @FXML
    private SplitPane AddLine;

    @FXML
    private AnchorPane mapContainer;

    @FXML
    private ImageView currentMap;

    double mapWidth;
    double mapHeight;

    @FXML
    private Canvas gc;

    @FXML
    private MenuButton MapDropDown;

    @FXML
    private MenuItem Map1;

    @FXML
    private MenuItem Map2;

    @FXML
    private Tab patientTab;

    @FXML
    private Tab adminTab;

    @FXML
    private AnchorPane requestconfirm;

    @FXML
    private Button addNodeSelector;

    @FXML
    private Button removeNodeSelector;

    @FXML
    private Button modifyBordersSelector;

    @FXML
    private TextField edgeWeight;

    @FXML
    private TextField edgeNodeA;

    @FXML
    private TextField edgeNodeB;

    @FXML
    private TextField Xloc;

    @FXML
    private TextField Yloc;

    @FXML
    private TextField Floor;

    @FXML
    private TextField Building;

    @FXML
    private TextField NodeType;

    @FXML
    private TextField ShortName;

    @FXML
    private TextField LongName;

    @FXML
    private TextField Team;

    @FXML
    private TextField NodeId;

    @FXML
    private Button addNodeConfirm;

    @FXML
    private TextField removeNode;

    @FXML
    private Button removeNodeConfirm;

    @FXML
    private Button edgeUpdate;

    @FXML
    private Button edgeDelete;

    @FXML
    private Button homeScreenButton;

    @FXML
    private ImageView homeScreen;


    @FXML
    private TextArea MessageTestBox;

    @FXML
    private void clearHomeScreen(){
        mapContainer.getChildren().remove(homeScreen);
        mapContainer.getChildren().remove(homeScreenButton);

    }

    @FXML
    /*
    * stateHandler is called when the map is clicked. Depending on the state, it either pathfinds,
     * or provides some map editing functionality
     */
    void stateHandler(MouseEvent event) {
        //get the mouses location and convert that to the corrected map coordinates for the original image
        Scene currScene = Main.getCurrScene();
        mapWidth = currentMap.getFitWidth();
        mapHeight = currentMap.getFitHeight();
        // System.out.println(mapWidth + " " + mapHeight);

        double mousex = (5000 * event.getX()) / mapWidth;
        double mousey = (3400 * event.getY()) / mapHeight;

        switch (currentState){
            case PATIENT:
                try {
                    findPath((int) Math.floor(mousex), (int) Math.floor(mousey));
                } catch (InvalidNodeException e) {
                    e.printStackTrace();
                }
                break;
            case ADMIN:
                switchTab2();
                break;
            case ADDNODE:
                nodeProcess((int)mousex,(int)mousey,currentState);
                //addEditNode(Xloc.getText(), Yloc.getText(), Floor.getText(), Building.getText(), NodeType.getText(), LongName.getText(), ShortName.getText(), Team.getText(), NodeId.getText());
                break;
            case REMOVENODE:
                //remove node
                //removeNode(removeNode.getText());
                nodeProcess((int)mousex,(int)mousey,currentState);
                break;
            case MODIFYBORDERS:
                if(counterForEdges == 0) {
                    edgeProcess((int) Math.floor(mousex), (int) Math.floor(mousey), counterForEdges);
                    counterForEdges++;
                }
                else if(counterForEdges == 1) {
                    edgeProcess((int) Math.floor(mousex), (int) Math.floor(mousey), counterForEdges);
                    counterForEdges++;
                }
                break;
            case SERVICEREQUEST:
                nodeProcess((int)mousex, (int)mousey, currentState);
                break;
            case SETSTARTNODE:
                nodeProcess((int)mousex, (int)mousey, currentState);
                break;
        }

        //Print to confirm

        //convert click resolution to map ratio
        System.out.println((5000 * event.getX()) / mapWidth + " " + (3400 * event.getY()) / mapHeight);
        //far left stair node
    }

    /*
    * nodeProcess deals with selecting a node when editing the map
     */
    private void nodeProcess(int mousex, int mousey, CurrentStatus currentState){
        NodeObj nearestNode = null;
        try {
            if (currentState.equals(CurrentStatus.REMOVENODE)) {
                nearestNode = Main.getNodeMap().getNearestNeighborFilter(mousex, mousey);
                this.removeNode.setText(nearestNode.getNode().getNodeID());
            } else if (currentState.equals(CurrentStatus.ADDNODE)) {
                this.Xloc.setText("" + mousex);
                this.Yloc.setText("" + mousey);
                //Do the add field stuff
            } else if (currentState.equals(CurrentStatus.SERVICEREQUEST)) {
                nearestNode = Main.getNodeMap().getNearestNeighborFilter(mousex, mousey);
                this.serviceNodeID.setText(nearestNode.getNode().getNodeID());
            } else if (currentState.equals(CurrentStatus.SETSTARTNODE)){
                nearestNode = Main.getNodeMap().getNearestNeighborFilter(mousex,mousey);
                this.startNodeID.setText(nearestNode.getNode().getNodeID());
            }
        }catch(InvalidNodeException e){
            e.printStackTrace();
        }

    }

    /*
    *  edgeProcess deals with selecting an edge when editing the map
     */
    private void edgeProcess(int mousex, int mousey, int counterForEdges) {
       NodeObj nodeO = null;
        try {
            nodeO = Main.getNodeMap().getNearestNeighborFilter
                    ((int) Math.floor(mousex), (int) Math.floor(mousey));
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }

        if (counterForEdges ==0)
            edgeNodeA.setText(nodeO.node.getNodeID());
        if (counterForEdges ==1)
            edgeNodeB.setText(nodeO.node.getNodeID());
    }

    @FXML
    /*
    *UpdateBorderButton is called when adding or editing. It updates the
    * node map, and redraws the screen
     */
    void UpdateBorderButton() {
        String NIDA = edgeNodeA.getText();
        String NIDB = edgeNodeB.getText();
        int eWeight = Integer.parseInt(edgeWeight.getText());
        boolean flagEdgeFoundA = false;
        boolean flagEdgeFoundB = false;
        NodeObj nodeA = null;
        NodeObj nodeB = null;
        EdgeObj edgeAB = null;
        for(NodeObj n: Main.getNodeMap().getNodes()){
            if(n.node.getNodeID().equals(NIDA)) {
                nodeA = n;
                for (EdgeObj e : n.getListOfEdgeObjs()) {
                    try {
                        if (e.getOtherNodeObj(n).node.getNodeID().equals(NIDB)) {
                            if(eWeight < 0)
                                e.setWeight(eWeight);
                            else
                                e.setWeight(e.genWeightFromDistance());
                            flagEdgeFoundA = true;
                            edgeAB = e;
                        }
                    } catch (InvalidNodeException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            if (n.node.getNodeID().equals(NIDB)) {
                nodeB = n;
                for (EdgeObj e : n.getListOfEdgeObjs()) {
                    try {
                        if (e.getOtherNodeObj(n).node.getNodeID().equals(NIDA)) {
                            if(eWeight < 0)
                                e.setWeight(eWeight);
                            else
                                e.setWeight(e.genWeightFromDistance());
                            flagEdgeFoundB = true;
                            edgeAB = e;
                        }
                    } catch (InvalidNodeException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
            if (!flagEdgeFoundA && !flagEdgeFoundB && eWeight!=0) {
                edgeAB = new EdgeObj(nodeA, nodeB, eWeight);
                nodeA.addEdge(edgeAB);
                nodeB.addEdge(edgeAB);
            }
            else if(!flagEdgeFoundA && !flagEdgeFoundB && eWeight==0) {
                edgeAB = new EdgeObj(nodeA, nodeB);
                nodeA.addEdge(edgeAB);
                nodeB.addEdge(edgeAB);
            }
            else if(!flagEdgeFoundA){
                nodeA.addEdge(edgeAB);
            }
            else if(!flagEdgeFoundB){
                nodeB.addEdge(edgeAB);
            }

            System.out.println("here2");


        switchTab2();
        gc1.setStroke(Color.RED);
        gc1.strokeLine(edgeAB.getNodeA().node.getxLoc()*mapWidth/5000,
                edgeAB.getNodeA().node.getyLoc()*mapHeight/3400,
                edgeAB.getNodeB().node.getxLoc()*mapWidth/5000,
                edgeAB.getNodeB().node.getyLoc()*mapHeight/3400);
    }

    /*
    * DeleteBorderButton is called when deleting an edge. Removes edge from map, and redraws screen
     */
    @FXML
    void DeleteBorderButton() {
        String nodeA = edgeNodeA.getText();
        String nodeB = edgeNodeB.getText();
        Main.getNodeMap().deleteEdge(nodeA,nodeB);
        switchTab2();
    }

    /*
    * findPath pathfinds, and draws the route to the screen
     */
    public void findPath(int mousex, int mousey) throws InvalidNodeException{
        //create a new astar object
        if(gc1 == null)
            gc1 = gc.getGraphicsContext2D();
        gc1.clearRect(0, 0, currentMap.getFitWidth(), currentMap.getFitHeight());
        astar newpathGen = new astar();
        gc1.setLineWidth(5);
        gc1.setStroke(Color.BLUE);
        gc1.setFill(Color.RED);

        //get node that corr. to click from ListOfNodeObjects made in main
        NodeObj goal = null;
        try {
            goal = Main.getNodeMap().getNearestNeighborFilter
                    ((int) Math.floor(mousex), (int) Math.floor(mousey));
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
        //getStart
        NodeObj Kiosk = Main.getKiosk();
        //set the path to null
        ArrayList<NodeObj> path = null;

        //try a*
        if(newpathGen.pathfind(Kiosk,goal,gc1))
            path = newpathGen.getGenPath();
        else
            try {
                throw new InvalidNodeException("this is not accessable with the current map");
            } catch (InvalidNodeException e) {
                e.printStackTrace();
            }

            NodeObj tempDraw = goal;

        for(NodeObj n: path)
            if(n != goal){
                gc1.strokeLine(n.node.getxLoc()*mapWidth/5000,
                        n.node.getyLoc()*mapHeight/3400,
                        tempDraw.node.getxLoc()*mapWidth/5000,
                        tempDraw.node.getyLoc()*mapHeight/3400);
                gc1.setLineWidth(5);
                tempDraw = n;
            }
    }

    /*
    * THESE METHODS SET THE CURRENT STATE OF THE APPLICATION
     */
    @FXML
    void addNodeFlag() {
        this.currentState = CurrentStatus.ADDNODE;
    }

    @FXML
    void modifyBordersFlag() {
        currentState = CurrentStatus.MODIFYBORDERS;
        counterForEdges = 0;
    }

    @FXML
    void removeNodeFlag() {
        this.currentState = CurrentStatus.REMOVENODE;
    }

    @FXML
    void serviceRequestFlag(){
        this.currentState = CurrentStatus.SERVICEREQUEST;
    }

    @FXML
    void setStartNodeFlag(){
        this.currentState = CurrentStatus.SETSTARTNODE;
    }

    //--------------------------------------------------------------------------------


    /*
    * switchTab2 is called when admin tab is selected, draws admin map to screen
     */
    @FXML
    void switchTab2(){
        if(gc1 == null)
            gc1 = gc.getGraphicsContext2D();
        gc1.clearRect(0, 0, currentMap.getFitWidth(), currentMap.getFitHeight());
        gc1.setLineWidth(3);
        gc1.setFill(Color.YELLOW);
        currentState = CurrentStatus.ADMIN;
        for(NodeObj n: Main.getNodeMap().getFilteredNodes()){
            for(EdgeObj e: n.getListOfEdgeObjs()){
                System.out.println(e.edgeID);
                gc1.setStroke(Color.BLUE);
                gc1.strokeLine(e.getNodeA().node.getxLoc()*mapWidth/5000,
                    e.getNodeA().node.getyLoc()*mapHeight/3400,
                    e.getNodeB().node.getxLoc()*mapWidth/5000,
                    e.getNodeB().node.getyLoc()*mapHeight/3400);
                /*gc1.fillText("" + e.getWeight(),
                        (e.getNodeA().node.getxLoc()*mapWidth/5000 +e.getNodeB().node.getxLoc()*mapWidth/5000)/2,
                        (e.getNodeA().node.getyLoc()*mapHeight/3400+ e.getNodeB().node.getyLoc()*mapHeight/3400)/2);*/
            }

        }

        for(NodeObj n: Main.getNodeMap().getFilteredNodes()){
            System.out.println(n.node.getNodeID());
            gc1.setStroke(Color.BLUE);
            gc1.fillOval(n.node.getxLoc()*mapWidth/5000 - 5,
                    n.node.getyLoc()*mapHeight/3400 - 5,
                    10,
                    10);
        }
        gc1.setFill(Color.BLUE);
    }

    /*
    * switchTab1 is called when Patient tab is clicked. Prepares screen for pathfinding.
     */
    @FXML
    void switchTab1(){
        if(gc1 == null)
            gc1 = gc.getGraphicsContext2D();
        gc1.clearRect(0, 0, currentMap.getFitWidth(), currentMap.getFitHeight());
        currentState = CurrentStatus.PATIENT;


    }

    /*
    * sendServiceRequest sends the service request to the set email in JanitorService.sendEmailServiceRequest
     */
    @FXML
    void sendServiceRequest(){
        String requestedNode = serviceNodeID.getText();
        String message = MessageTestBox.getText();
        String finalMessage = "At node " + requestedNode + ": " + message;
        Main.getJanitorService().sendEmailServiceRequest(finalMessage);
    }

    /*
    * GetMap1 loads Floor 1 map into the screen
     */
    @FXML
    void GetMap1() {
        setKioskLoc(2460, 910);
        gc1.clearRect(0, 0, currentMap.getFitWidth(), currentMap.getFitHeight());
        MapDropDown.setText("45 Francis Floor 1 Center");
        Image m1 = new Image("file:src/view/media/basicMap.png");
        currentMap.setImage(m1);
        Main.getNodeMap().setCurrentFloor("1");
        if(currentState == CurrentStatus.ADMIN){
            switchTab2();
        }
        //need to set currentMap of ListOfNodeObjs to "
    }

    /*
    * GetMap2 loads Floor 2 map into the screen
     */
    @FXML
    void GetMap2() {
        setKioskLoc(1580, 1810);
        gc1.clearRect(0, 0, currentMap.getFitWidth(), currentMap.getFitHeight());
        MapDropDown.setText("Shapiro Building Floor 2");
        Image m2 = new Image("file:src/view/media/Shapiro.png");
        currentMap.setImage(m2);
        Main.getNodeMap().setCurrentFloor("2");
        if(currentState == CurrentStatus.ADMIN){
            switchTab2();
        }
    }

    /*
    * setKioskLoc sets the default location for the floor
     */
    void setKioskLoc(int xCoord, int yCoord) {
        try {
            Main.setKiosk(Main.getNodeMap().getNearestNeighbor(xCoord, yCoord));
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
    }

    /*
    * removeNode removes a node from the map
     */
    @FXML
    void removeNode(){
        String delNodeID = removeNode.getText();
        System.out.println("DELETE NODE CLICKED: " + delNodeID);
        Node delNode = new Node(delNodeID); //WARNING: THIS CREATES A Node WITH ONLY AN ID, NO OTHER FIELDS POPULATED. ONLY ATTEMPT TO ACCESS nodeID.
        NodeObj delNodeObj = new NodeObj(delNode);
        Main.getNodeMap().removeNode(delNodeObj);
        switchTab2();
    }

    /*
    * addEditNode adds/modifies a node in the map
     */
    @FXML
    void addEditNode(){
        String xLoc = Xloc.getText();
        String yLoc = Yloc.getText();
        String floor = Floor.getText();
        String building = Building.getText();
        String nodeType = NodeType.getText();
        String longName = LongName.getText();
        String shortName = ShortName.getText();
        String team = Team.getText();
        String nodeID = NodeId.getText();
        System.out.println("ADD/EDIT NODE CLICKED");
        Node modNode = new Node(xLoc, yLoc, floor, building, nodeType, longName, shortName, team, nodeID);
        NodeObj modNodeObj = new NodeObj(modNode);
        try {
            Main.getNodeMap().addEditNode(modNodeObj);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        switchTab2();
    }

    /*
    * setStartNode sets the start node to the node nearest to the given coords in the current building
     */
    @FXML
    void setStartNode(){
        String newStartNodeID = startNodeID.getText();
        NodeObj newStartNode = Main.getNodeMap().getNodeObjByID(newStartNodeID);
        Main.setKiosk(newStartNode);
        switchTab1();
        currentState = CurrentStatus.PATIENT;
        System.out.println("Did you get here");
    }

}
