package controller;

import javafx.event.Event;
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
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminController extends Controller{

    private enum CurrentStatus {
       ADMIN, ADDNODE, REMOVENODE, MODIFYBORDERS, SERVICEREQUEST,
    }

    private CurrentStatus currentState = CurrentStatus.ADMIN;

    private GraphicsContext gc1 = null;

    private int XTrans = 0;

    private int YTrans = 0;

    private double Zoom = 1;

    private int counterForEdges = 0;

    private PathingContainer currentAlgorithm = new PathingContainer();


    @FXML
    private MenuButton PFM;

    @FXML
    private TextField serviceNodeID;

    @FXML
    private AnchorPane mapContainer;

    @FXML
    public ImageView currentMap;

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
    private MenuItem MapL2;

    @FXML
    private MenuItem MapL1;

    @FXML
    private MenuItem MapG;

    @FXML
    private MenuItem Map3;

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
    private TextField removeNode;

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

    public ImageLoader mapImage = new ImageLoader();

    public void initialize(){
        Image m1 = mapImage.getLoadedMap("Map1");
        currentMap.setImage(m1);
        currentAlgorithm.setPathAlg(new astar());
        mapWidth = currentMap.getFitWidth();
        mapHeight = currentMap.getFitHeight();
        switchTab2();
    }

    @FXML


    /**
     * StateHandler is called when the map is clicked. Depending on the state, it either pathfinds,
     * or provides some map editing functionality.
     */
    void stateHandler(MouseEvent event) {
        //get the mouses location and convert that to the corrected map coordinates for the original image
        // System.out.println(mapWidth + " " + mapHeight);

        double mousex = (5000 * event.getX()) / mapWidth;
        double mousey = (3400 * event.getY()) / mapHeight;

        switch (currentState){
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
        }

        //Print to confirm

        //convert click resolution to map ratio
        //far left stair node
    }


    /**
     * This method deals with selecting a node when editing the map
     * @param mousex This is the x coordinate of the mouse.
     * @param mousey This is the y coordinate of the mouse.
     * @param currentState This is the current state of the state machine in stateHandler.
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
            }
        }catch(InvalidNodeException e){
            e.printStackTrace();
        }

    }


    /**
     * This deals with selecting an edge when editing the map.
     * @param mousex This is the x coordinate of the mouse.
     * @param mousey This is the y coordinate of the mouse.
     * @param counterForEdges This is an integer that keeps track of an edge is selected.
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

    /**
     * This is called when adding or editing. It updates the
     * node map, and redraws the screen.
     */
    void UpdateBorderButton() {
        String NIDA = edgeNodeA.getText();
        String NIDB = edgeNodeB.getText();
        int eWeight = Integer.parseInt(edgeWeight.getText());
        EdgeObj edgeAB = Main.getNodeMap().addEditEdge(NIDA, NIDB, eWeight);
        switchTab2();
        gc1.setStroke(Color.RED);
        gc1.strokeLine(edgeAB.getNodeA().node.getxLoc()*mapWidth/5000,
                edgeAB.getNodeA().node.getyLoc()*mapHeight/3400,
                edgeAB.getNodeB().node.getxLoc()*mapWidth/5000,
                edgeAB.getNodeB().node.getyLoc()*mapHeight/3400);
    }

    /**
     * DeleteBorderButton is called when deleting an edge. Removes edge from map, and redraws screen
     */
    @FXML
    void DeleteBorderButton() {
        String nodeA = edgeNodeA.getText();
        String nodeB = edgeNodeB.getText();
        Main.getNodeMap().deleteEdge(nodeA,nodeB);
        switchTab2();
    }

    /**
     * This method highlights the floors and setting the rest of them transparent.
     * @deprecated This function is never used.
     */
    public void highlightFloors(){
        Map1.setStyle("-fx-background-color: transparent;");
        Map2.setStyle("-fx-background-color: transparent;");
        Map3.setStyle("-fx-background-color: transparent;");
        MapL1.setStyle("-fx-background-color: transparent;");
        MapL2.setStyle("-fx-background-color: transparent;");
        MapG.setStyle("-fx-background-color: transparent;");
    }

    /**
     * This function accepts a floor and returns the map of that floor.
     * @param Nfloor This is the number of the floor.
     * @return MenuItem returns the map of the floor
     * @throws InvalidNodeException Throws invalid node exception.
     * @deprecated This function is never used.
     */
    public MenuItem GetMapDropdownFromFloor(String Nfloor) throws InvalidNodeException{
        switch(Nfloor){
            case "1":
                return Map1;

            case "2":
                return Map2;

            case "3":
                return Map3;

            case "L1":
                return MapL1;

            case "L2":
                return MapL2;

            case "G":
                return MapG;

            default:
                throw new InvalidNodeException("Node does not have a correct floor ID");
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
        Main.getCurrStage().setScene(Main.getService());
    }

   //--------------------------------------------------------------------------------

    /**
     * switchTab2 is called when admin tab is selected, draws admin map to screen.
     */
    @FXML
    public void switchTab2(){
        if(gc1 == null)
            gc1 = gc.getGraphicsContext2D();
        gc1.clearRect(0, 0, currentMap.getFitWidth(), currentMap.getFitHeight());
        gc1.setLineWidth(2);
        gc1.setFill(Color.BLACK);
        currentState = CurrentStatus.ADMIN;
        for(NodeObj n: Main.getNodeMap().getFilteredNodes()){
            for(EdgeObj e: n.getListOfEdgeObjs()){
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
            gc1.setFill(Color.BLACK);
            gc1.fillOval(n.node.getxLoc()*mapWidth/5000 - 5,
                    n.node.getyLoc()*mapHeight/3400 - 5,
                    10,
                    10);
            gc1.setFill(Color.LIGHTBLUE);
            gc1.fillOval(n.node.getxLoc()*mapWidth/5000 - 4,
                    n.node.getyLoc()*mapHeight/3400 - 4,
                    8,
                    8);
        }
        gc1.setFill(Color.BLUE);
    }

    /**
     * switchTab1 is called when Patient tab is clicked. Prepares screen for pathfinding.
     */
    @FXML
    public void switchTab1(){
        //switch to other controller
        Main.getCurrStage().setScene(Main.getPatientScene());
    }

    /**
     * sendServiceRequest sends the service request to the set email in JanitorService.sendEmailServiceRequest
     * @@deprecated This function is never used.
     */
    @FXML
    void sendServiceRequest(){
        String requestedNode = serviceNodeID.getText();
        String message = MessageTestBox.getText();
        Main.getJanitorService().setLocation(requestedNode);
        Main.getJanitorService().setMessageText(message);
        Main.getJanitorService().sendEmailServiceRequest();
    }

    /**
     * This function sets the current pathfinding algorithm (DepthFirst, BreadthFirst, Dijkstras or astar)
     * @param e This the event that triggers the change of the algorithm.
     */
    @FXML
    void setCurrentAlgorithm(Event e){
        String clickedID = ((MenuItem)e.getSource()).getId();

        switch(clickedID){
            case "depthFirst":
                this.currentAlgorithm.setPathAlg(new DepthFirst());
                break;
            case "breadthFirst":
                this.currentAlgorithm.setPathAlg(new BreadthFirst());
                break;
            case "dijkstras":
                this.currentAlgorithm.setPathAlg(new Dijkstras());
                break;
            case "aStar":
                this.currentAlgorithm.setPathAlg(new astar());
                break;
        }
        PFM.setText(clickedID);
    }

    /**
     * This function resets the kiosk location.
     */
    @FXML
    void resetKioskLoc(){
        setKioskLoc(2460, 910);
    }

    /**
     * This function updates the selected map.
     * @param e This is an event that determines when the maps should refresh.
     */
    @FXML
    void mapSelected(Event e){
        Main.getControllers().updateAllMaps(e);
    }

    /**
     * Displays the selected map.
     * @param e Event e is used to trigger this method.
     */
    @FXML
    void GetMap(Event e){
        String clickedID = ((MenuItem)e.getSource()).getId();

        switch(clickedID){
            case "MapL2":
                MapDropDown.setText("Floor L2");
                Main.getNodeMap().setCurrentFloor("L2");
                break;
            case "MapL1":
                MapDropDown.setText("Floor L1");
                Main.getNodeMap().setCurrentFloor("L1");
                break;
            case "MapG":
                MapDropDown.setText("Ground Floor");
                Main.getNodeMap().setCurrentFloor("G");
                break;
            case "Map1":
                MapDropDown.setText("Floor 1");
                Main.getNodeMap().setCurrentFloor("1");
                break;
            case "Map2":
                MapDropDown.setText("Floor 2");
                Main.getNodeMap().setCurrentFloor("2");
                break;
            case "Map3":
                MapDropDown.setText("Floor 3");
                Main.getNodeMap().setCurrentFloor("3");
                break;
        }
        gc1.clearRect(0, 0, currentMap.getFitWidth(), currentMap.getFitHeight());
        Image map = mapImage.getLoadedMap(clickedID);
        this.currentMap.setImage(map);
        //PatientController.currentMap.setImage(map);
        if((currentState == CurrentStatus.ADMIN) || (currentState == CurrentStatus.ADDNODE) ||  (currentState == CurrentStatus.REMOVENODE) ||  (currentState == CurrentStatus.MODIFYBORDERS) || (currentState == CurrentStatus.SERVICEREQUEST)){
            switchTab2();
        }
    }

    /**
     * setKioskLoc sets the default location for the floor.
     */
    void setKioskLoc(int xCoord, int yCoord) {
        try {
            Main.setKiosk(Main.getNodeMap().getNearestNeighbor(xCoord, yCoord));
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
    }

    /**
     * removeNode removes a node from the map.
     */
    @FXML
    void removeNode(){
        String delNodeID = removeNode.getText();
        Node delNode = new Node(delNodeID); //WARNING: THIS CREATES A Node WITH ONLY AN ID, NO OTHER FIELDS POPULATED. ONLY ATTEMPT TO ACCESS nodeID.
        NodeObj delNodeObj = new NodeObj(delNode);
        Main.getNodeMap().removeNode(delNodeObj);
        switchTab2();
    }

    /**
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
        Node modNode = new Node(xLoc, yLoc, floor, building, nodeType, longName, shortName, team, nodeID);
        NodeObj modNodeObj = new NodeObj(modNode);
        try {
            Main.getNodeMap().addEditNode(modNodeObj);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        switchTab2();
    }



    @FXML
    void Zin() {
        Zoom+=.5;
        resize();
    }

    @FXML
    void Zout() {
        if(Zoom>1) {
            Zoom -= .5;
            resize();
        }

    }

    @FXML
    void Tleft() {
        XTrans+=(int) (200.0/Zoom);
        resize();
    }

    @FXML
    void Tright() {
        XTrans-=(int) (200.0/Zoom);
        resize();
    }

    @FXML
    void Tup() {
        YTrans+=(int) (160.0/Zoom);
        resize();
    }

    @FXML
    void Tdown() {
        YTrans-=(int) (160.0/Zoom);
        resize();
    }

    /**
     * This resizes the entire user interface.
     */
    public void resize(){
        if(Zoom<=1){
            XTrans = 0;
            YTrans = 0;
        }
        gc.setScaleX(Zoom);
        gc.setScaleY(Zoom);
        gc.setTranslateX(XTrans);
        gc.setTranslateY(YTrans);
        currentMap.setScaleX(Zoom);
        currentMap.setScaleY(Zoom);
        currentMap.setTranslateX(XTrans);
        currentMap.setTranslateY(YTrans);
        mapWidth = currentMap.getFitWidth();
        mapHeight = currentMap.getFitHeight();
    }

}
