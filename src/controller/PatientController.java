package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import model.*;

import java.util.ArrayList;

public class PatientController extends Controller{

    private enum CurrentStatus {
        PATIENT, SETSTARTNODE, SETENDNODE
    };

    private CurrentStatus currentState = CurrentStatus.PATIENT;

    private GraphicsContext gc1 = null;

    private int XTrans = 0;

    private int YTrans = 0;

    private double Zoom = 1;

    ArrayList<NodeObj> currPath = null;

    NodeObj goal = null;

    private PathingContainer currentAlgorithm = new PathingContainer();

    @FXML
    private TextField startNodeID;

    @FXML
    private TextField EndNodeID;

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
    private Button homeScreenButton;

    @FXML
    private ImageView homeScreen;

    @FXML
    private void clearHomeScreen(){
        mapContainer.getChildren().remove(homeScreen);
        mapContainer.getChildren().remove(homeScreenButton);

    }

    ImageLoader mapImage = new ImageLoader();

    public void initialize(){
        Image m1 = mapImage.getLoadedMap("Map1");
        currentMap.setImage(m1);
        currentAlgorithm.setPathAlg(new astar());
        currentState = CurrentStatus.PATIENT;
        mapWidth = currentMap.getFitWidth();
        mapHeight = currentMap.getFitHeight();
        switchTab1();
    }

    @FXML
    /*
    * stateHandler is called when the map is clicked. Depending on the state, it either pathfinds,
     * or provides some map editing functionality
     */
    void stateHandler(MouseEvent event) {
        //get the mouses location and convert that to the corrected map coordinates for the original image
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
            case SETSTARTNODE:
                nodeProcess((int)mousex, (int)mousey, currentState);
                break;
            case SETENDNODE:
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
            if (currentState.equals(CurrentStatus.SETSTARTNODE)){
                nearestNode = Main.getNodeMap().getNearestNeighborFilter(mousex,mousey);
                this.startNodeID.setText(nearestNode.getNode().getNodeID());
            }else if (currentState.equals(CurrentStatus.SETENDNODE)){
                nearestNode = Main.getNodeMap().getNearestNeighborFilter(mousex,mousey);
                this.EndNodeID.setText(nearestNode.getNode().getNodeID());
            }
        }catch(InvalidNodeException e){
            e.printStackTrace();
        }

    }

    /*
    * findPath pathfinds, and draws the route to the screen
     */
    public void findPath(int mousex, int mousey) throws InvalidNodeException{
        //create a new astar object
        if(gc1 == null)
            gc1 = gc.getGraphicsContext2D();
        gc1.clearRect(0, 0, currentMap.getFitWidth(), currentMap.getFitHeight());
        gc1.setLineWidth(2);
        gc1.setStroke(Color.BLUE);
        gc1.setFill(Color.RED);

        //get node that corr. to click from ListOfNodeObjects made in main

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
        if (currentAlgorithm.getPathAlg().pathfind(Kiosk, goal)){
            path = currentAlgorithm.getPathAlg().getGenPath();
            currPath = path;
        }
        else {
            try {
                throw new InvalidNodeException("this is not accessable with the current map");
            } catch (InvalidNodeException e) {
                e.printStackTrace();
            }
        }
        DrawCurrentFloorPath();
    }

    public void highlightFloors(){
        Map1.setStyle("-fx-background-color: transparent;");
        Map2.setStyle("-fx-background-color: transparent;");
        Map3.setStyle("-fx-background-color: transparent;");
        MapL1.setStyle("-fx-background-color: transparent;");
        MapL2.setStyle("-fx-background-color: transparent;");
        MapG.setStyle("-fx-background-color: transparent;");
    }

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

    public void DrawCurrentFloorPath(){
        gc1.setLineWidth(2);
        NodeObj tempDraw = goal;
        ArrayList<String> Floors = new ArrayList<String>();
        for(NodeObj n: currPath) {
            if (n != goal) {
                if (n.node.getFloor().equals(Main.getNodeMap().currentFloor) &&
                        tempDraw.node.getFloor().equals(Main.getNodeMap().currentFloor)) {
                    gc1.strokeLine(n.node.getxLoc() * mapWidth / 5000,
                            n.node.getyLoc() * mapHeight / 3400,
                            tempDraw.node.getxLoc() * mapWidth / 5000,
                            tempDraw.node.getyLoc() * mapHeight / 3400);
                }
                if(!(Floors.contains(n.node.getFloor()) || n.node.getNodeType().equals("ELEV")))
                    Floors.add(n.node.getFloor());
            }
            System.out.println(n.node.getFloor());
            System.out.println(tempDraw.node.getFloor());
            tempDraw = n;
        }

        if(goal.node.getFloor().equals(Main.getNodeMap().currentFloor)){
            gc1.setFill(Color.DARKRED);
            gc1.fillOval(goal.node.getxLoc()*mapWidth/5000 - 5,
                    goal.node.getyLoc()*mapHeight/3400 - 5,
                    10,
                    10);
        }
        if(Main.getKiosk().node.getFloor().equals(Main.getNodeMap().currentFloor)){
            gc1.setFill(Color.DARKGREEN);
            gc1.fillOval(Main.getKiosk().node.getxLoc()*mapWidth/5000 - 5,
                    Main.getKiosk().node.getyLoc()*mapHeight/3400 - 5,
                    10,
                    10);
        }
        gc1.setFill(Color.YELLOW);

        highlightFloors();
        try {
            for(String s:Floors) {
                System.out.println(s);
                GetMapDropdownFromFloor(s).setStyle("-fx-background-color: lemonchiffon;");
            }
            GetMapDropdownFromFloor(Main.getKiosk().node.getFloor()).setStyle("-fx-background-color: lightgreen;");
        } catch (InvalidNodeException e) {
            e.printStackTrace();
            System.out.println("fail");
        }
    }

    @FXML
    void setStartNodeFlag(){
        this.currentState = CurrentStatus.SETSTARTNODE;
    }

    @FXML
    void setEndNodeFlag(){
        this.currentState = CurrentStatus.SETENDNODE;
    }

    //--------------------------------------------------------------------------------


    /*
    * switchTab2 is called when admin tab is selected, draws admin map to screen
     */
    @FXML
    public void switchTab2(){
        Main.getCurrStage().setScene(Main.getAdminScene());
    }

    /*
    * switchTab1 is called when Patient tab is clicked. Prepares screen for pathfinding.
     */
    @FXML
    public void switchTab1(){
        if(gc1 == null)
            gc1 = gc.getGraphicsContext2D();
        gc1.clearRect(0, 0, currentMap.getFitWidth(), currentMap.getFitHeight());
        currentState = CurrentStatus.PATIENT;


    }

    @FXML
    void mapSelected(Event e){
        Main.getControllers().updateAllMaps(e);
    }

    @FXML
    void GetMap(Event e){
        String clickedID = ((MenuItem)e.getSource()).getId();
        switch(clickedID){
            case "MapL2":
                setKioskLoc(2460, 910);
                MapDropDown.setText("Floor L2");
                Main.getNodeMap().setCurrentFloor("L2");
                break;
            case "MapL1":
                setKioskLoc(2460, 910);
                MapDropDown.setText("Floor L1");
                Main.getNodeMap().setCurrentFloor("L1");
                break;
            case "MapG":
                setKioskLoc(2460, 910);
                MapDropDown.setText("Ground Floor");
                Main.getNodeMap().setCurrentFloor("G");
                break;
            case "Map1":
                setKioskLoc(2460, 910);
                MapDropDown.setText("Floor 1");
                Main.getNodeMap().setCurrentFloor("1");
                break;
            case "Map2":
                setKioskLoc(2460, 910);
                MapDropDown.setText("Floor 2");
                Main.getNodeMap().setCurrentFloor("2");
                break;
            case "Map3":
                setKioskLoc(2460, 910);
                MapDropDown.setText("Floor 3");
                Main.getNodeMap().setCurrentFloor("3");
                break;
        }
        gc1.clearRect(0, 0, currentMap.getFitWidth(), currentMap.getFitHeight());
        Image map = mapImage.getLoadedMap(clickedID);
        this.currentMap.setImage(map);
        //AdminController.currentMap.setImage(map);

        if(((currentState == CurrentStatus.PATIENT) || (currentState == CurrentStatus.SETSTARTNODE) || (currentState == CurrentStatus.SETENDNODE)) && (currPath!=null)){
            DrawCurrentFloorPath();
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

    @FXML
    void setEndNode(){
        String newEndNodeID = EndNodeID.getText();
        NodeObj newEndNode = Main.getNodeMap().getNodeObjByID(newEndNodeID);
        switchTab1();
        try {
            findPath(newEndNode.node.getxLoc(),newEndNode.node.getyLoc());
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
        currentState = CurrentStatus.PATIENT;
        System.out.println("Did you get here");
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
