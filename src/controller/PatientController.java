package controller;

import com.jfoenix.controls.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import model.ImageLoader;
import model.InvalidNodeException;
import model.astar;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.*;

import java.util.ArrayList;


public class PatientController extends Controller {

    @FXML
    private JFXTogglePane textTogglePane;

    @FXML
    private JFXToggleButton textToggle;

    @FXML
    private JFXSlider zoomBar;

    @FXML
    private ImageView img_Map;

    @FXML
    private JFXButton btn_map03;

    @FXML
    private JFXButton btn_map02;

    @FXML
    private JFXButton btn_map01;

    @FXML
    private JFXButton btn_mapG;

    @FXML
    private JFXButton btn_mapL1;

    @FXML
    private JFXButton btn_mapL2;

    @FXML
    private Canvas gc;

    @FXML
    private ImageView homeScreen;

    @FXML
    public ImageView currentMap;

    @FXML
    private JFXTextArea toggleTextArea;

    @FXML
    private Label floor3Label;

    @FXML
    private Label floor2Label;

    @FXML
    private Label floor1Label;

    @FXML
    private Label floorGLabel;

    @FXML
    private Label floorL1Label;

    @FXML
    private Label floorL2Label;

    private GraphicsContext gc1 = null;
    public static TextDirections textDirections = new TextDirections();
    private int XTrans = 0;
    private int YTrans = 0;
    private double Zoom = 1;
    ArrayList<NodeObj> currPath = null;
    NodeObj goal = null;
    private PathingContainer currentAlgorithm = new PathingContainer();
    double mapWidth;
    double mapHeight;
    ImageLoader mapImage = new ImageLoader();
    ArrayList<String> Floors;
    double startX;
    double startY;

    public void initialize() {
        Image m1 = mapImage.getLoadedMap("btn_map01");
        currentMap.setImage(m1);
        btn_map01.setOpacity(1);
        currentAlgorithm.setPathAlg(new astar());
        mapWidth = currentMap.getFitWidth();
        mapHeight = currentMap.getFitHeight();
        setKioskLoc(2460, 910);
        redraw();
    }

    private void redraw(){
        if (gc1 == null)
            gc1 = gc.getGraphicsContext2D();
        gc1.clearRect(0, 0, currentMap.getFitWidth(), currentMap.getFitHeight());
    }

    @FXML
    void changeMap(Event e){
        Main.controllers.updateAllMaps(e);
        if(currPath != null){
            DrawCurrentFloorPath();
        }
    }

    void getMap(Event e) {
        String clickedID = ((JFXButton) e.getSource()).getId();
        clearChosenFloor();
        switch (clickedID) {
            case "btn_mapL2":
                Main.getNodeMap().setCurrentFloor("L2");
                btn_mapL2.setOpacity(1);
                break;
            case "btn_mapL1":
                Main.getNodeMap().setCurrentFloor("L1");
                btn_mapL1.setOpacity(1);
                break;
            case "btn_mapG":
                Main.getNodeMap().setCurrentFloor("G");
                btn_mapG.setOpacity(1);
                break;
            case "btn_map01":
                Main.getNodeMap().setCurrentFloor("1");
                btn_map01.setOpacity(1);
                break;
            case "btn_map02":
                Main.getNodeMap().setCurrentFloor("2");
                btn_map02.setOpacity(1);
                break;
            case "btn_map03":
                Main.getNodeMap().setCurrentFloor("3");
                btn_map03.setOpacity(1);
                break;
        }
        gc1.clearRect(0, 0, currentMap.getFitWidth(), currentMap.getFitHeight());
        Image map = mapImage.getLoadedMap(clickedID);
        this.currentMap.setImage(map);
        redraw();
    }

    void clearChosenFloor(){
        if(Floors == null) {
            btn_mapL2.setOpacity(.5);
            btn_mapL1.setOpacity(.5);
            btn_mapG.setOpacity(.5);
            btn_map01.setOpacity(.5);
            btn_map02.setOpacity(.5);
            btn_map03.setOpacity(.5);
            selectFloor(Main.getNodeMap().currentFloor, 1);
        }else {
            btn_mapL2.setOpacity(.5);
            btn_mapL1.setOpacity(.5);
            btn_mapG.setOpacity(.5);
            btn_map01.setOpacity(.5);
            btn_map02.setOpacity(.5);
            btn_map03.setOpacity(.5);
            for(int i = 0; i < Floors.size(); i++){
                selectFloor(Floors.get(i), i+1);
            }
            selectFloorWithPath(Main.getNodeMap().currentFloor, 1);
        }
    }

    void selectFloor(String selectedFloor, int order) {

        switch (selectedFloor) {
            case "L2":
                btn_mapL2.setOpacity(1);
                if(floorL2Label.getText().equals("")){
                    floorL2Label.setText(order + "");
                }else {
                    floorL2Label.setText(floorL2Label.getText() + ", " + order + "");
                }
                break;
            case "L1":
                btn_mapL1.setOpacity(1);
                if(floorL1Label.getText().equals("")){
                    floorL1Label.setText(order + "");
                }else {
                    floorL1Label.setText(floorL1Label.getText() + ", " + order + "");
                }
                break;
            case "G":
                btn_mapG.setOpacity(1);
                if(floorGLabel.getText().equals("")){
                    floorGLabel.setText(order + "");
                }else {
                    floorGLabel.setText(floorGLabel.getText() + ", " + order + "");
                }
                break;
            case "1":
                btn_map01.setOpacity(1);
                if(floor1Label.getText().equals("")){
                    floor1Label.setText(order + "");
                }else {
                    floor1Label.setText(floor1Label.getText() + ", " + order + "");
                }
                break;
            case "2":
                btn_map02.setOpacity(1);
                if(floor2Label.getText().equals("")){
                    floor2Label.setText(order + "");
                }else {
                    floor2Label.setText(floor2Label.getText() + ", " + order + "");
                }
                break;
            case "3":
                btn_map03.setOpacity(1);
                if(floor3Label.getText().equals("")){
                    floor3Label.setText(order + "");
                }else {
                    floor3Label.setText(floor3Label.getText() + ", " + order + "");
                }
                break;
        }
    }

    void selectFloorWithPath(String selectedFloor, int order) {

        switch (selectedFloor) {
            case "L2":
                btn_mapL2.setOpacity(1);
                break;
            case "L1":
                btn_mapL1.setOpacity(1);
                break;
            case "G":
                btn_mapG.setOpacity(1);
                break;
            case "1":
                btn_map01.setOpacity(1);
                break;
            case "2":
                btn_map02.setOpacity(1);
                break;
            case "3":
                btn_map03.setOpacity(1);
                break;
        }
    }

    /*
     * setKioskLoc sets the default location for the floor
     */
    void setKioskLoc(int xCoord, int yCoord) {
        try {
            Main.setKiosk(Main.getNodeMap().getNearestNeighborFilter(xCoord, yCoord));
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
    }

    public void DrawCurrentFloorPath() {
        floorL2Label.setText("");
        floorL1Label.setText("");
        floorGLabel.setText("");
        floor1Label.setText("");
        floor2Label.setText("");
        floor3Label.setText("");
        gc1.setLineWidth(2);
        NodeObj tempDraw = goal;
        Floors = new ArrayList<String>();
        for (NodeObj n : currPath) {
            if (n != goal) {
                if (n.node.getFloor().equals(Main.getNodeMap().currentFloor) &&
                        tempDraw.node.getFloor().equals(Main.getNodeMap().currentFloor)) {
                    gc1.strokeLine(n.node.getxLoc() * mapWidth / 5000,
                            n.node.getyLoc() * mapHeight / 3400,
                            tempDraw.node.getxLoc() * mapWidth / 5000,
                            tempDraw.node.getyLoc() * mapHeight / 3400);
                }
            }
            if(Floors.size() > 0) {
                if (!(Floors.get(Floors.size() - 1).equals(n.getNode().getFloor()) || n.getNode().getNodeType().equals("ELEV"))) {
                    Floors.add(n.getNode().getFloor());
                }
            }else if (!(n.getNode().getNodeType().equals("ELEV"))){
                Floors.add(n.getNode().getFloor());
            }
            tempDraw = n;
        }

        if (goal.node.getFloor().equals(Main.getNodeMap().currentFloor)) {
            gc1.setFill(Color.DARKRED);
            gc1.fillOval(goal.node.getxLoc() * mapWidth / 5000 - 5,
                    goal.node.getyLoc() * mapHeight / 3400 - 5,
                    10,
                    10);
        }
        if (Main.getKiosk().node.getFloor().equals(Main.getNodeMap().currentFloor)) {
            gc1.setFill(Color.DARKGREEN);
            gc1.fillOval(Main.getKiosk().node.getxLoc() * mapWidth / 5000 - 5,
                    Main.getKiosk().node.getyLoc() * mapHeight / 3400 - 5,
                    10,
                    10);
        }
        gc1.setFill(Color.YELLOW);
        clearChosenFloor();
        System.out.println(Floors.toString());

/*


        try {
            for(String s:Floors) {
                GetMapDropdownFromFloor(s).setText(GetMapDropdownFromFloor(s).getText() + " [*]");
            }
            GetMapDropdownFromFloor(Main.getKiosk().node.getFloor()).setText(GetMapDropdownFromFloor(Main.getKiosk().node.getFloor()).getText() + " [Start]");
            if(goal.node.getNodeType().equals("ELEV")){
                GetMapDropdownFromFloor(goal.node.getFloor()).setText
                        (GetMapDropdownFromFloor(goal.node.getFloor()).getText() + " [*]");
            }
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
        */
    }


    /*
     * findPath pathfinds, and draws the route to the screen
     */
    public void findPath(MouseEvent event){
        //create a new astar object

        double mousex = (5000 * event.getX()) / mapWidth;
        double mousey = (3400 * event.getY()) / mapHeight;
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
        ArrayList<NodeObj> path;
        if(!Kiosk.getNode().getNodeID().equals(goal.getNode().getNodeID())) {
            //try a*
            if (currentAlgorithm.getPathAlg().pathfind(Kiosk, goal)) {
                path = currentAlgorithm.getPathAlg().getGenPath();
                currPath = path;
                toggleTextArea.setText(textDirections.getTextDirections(path));
            } else {
                try {
                    throw new InvalidNodeException("this is not accessable with the current map");
                } catch (InvalidNodeException e) {
                    e.printStackTrace();
                }
            }
            DrawCurrentFloorPath();
        }
    }

    @FXML
    void mousePress(MouseEvent event){
        if((event.getButton() == MouseButton.SECONDARY) || ((event.getButton() == MouseButton.PRIMARY) && (event.isControlDown()))){
            setStartNode(event);
        }else if(event.getButton() == MouseButton.PRIMARY){
            findPath(event);
        }
    }


    /*
     * setStartNode sets the start node to the node nearest to the given coords in the current building
     */
    @FXML
    void setStartNode(MouseEvent event){
        double mousex = (5000 * event.getX()) / mapWidth;
        double mousey = (3400 * event.getY()) / mapHeight;
        String newStartNodeID = null;
        try {
            newStartNodeID = Main.getNodeMap().getNearestNeighborFilter((int)mousex,(int)mousey).getNode().getNodeID();
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
        NodeObj newStartNode = Main.getNodeMap().getNodeObjByID(newStartNodeID);
        Main.setKiosk(newStartNode);
        redraw();
    }

    @FXML
    void adminLogin(){
        Main.getCurrStage().setScene(Main.getAdminScene());
    }


    @FXML
    void Zin() {
        System.out.println(zoomBar.getValue());
        Zoom = zoomBar.getValue();
        resize();

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

    @FXML
    public void textToggle() {
        if (textToggle.isSelected()) {
            textTogglePane.setVisible(true);
        }else{
            textTogglePane.setVisible(false);
        }
    }
}
