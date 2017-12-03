package controller;

import com.jfoenix.controls.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.*;

import java.util.ArrayList;


public class AdminController extends Controller {

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
    private Pane nodeInfoPane;

    @FXML
    private JFXTextField nodeTypeField;

    @FXML
    private JFXTextField shortNameField;

    @FXML
    private JFXTextField longNameField;

    @FXML
    private JFXTextField teamField;

    @FXML
    private JFXTextField buildingField;

    @FXML
    private JFXTextField floorField;

    @FXML
    private JFXTextField yLocField;

    @FXML
    private JFXTextField xLocField;

    @FXML
    private JFXTextField nodeIDField;

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
    double startX;
    double startY;

    public void initialize(){
        Image m1 = mapImage.getLoadedMap("btn_map01");
        currentMap.setImage(m1);
        currentAlgorithm.setPathAlg(new astar());
        mapWidth = currentMap.getFitWidth();
        mapHeight = currentMap.getFitHeight();
        redraw();
    }


    private void redraw(){
        if(gc1 == null)
            gc1 = gc.getGraphicsContext2D();
        gc1.clearRect(0, 0, currentMap.getFitWidth(), currentMap.getFitHeight());
        gc1.setLineWidth(2);
        gc1.setFill(Color.BLACK);
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

    @FXML
    void changeMap(Event e){
        Main.controllers.updateAllMaps(e);
    }

    void getMap(Event e) {
        String clickedID = ((JFXButton) e.getSource()).getId();
        switch (clickedID) {
            case "btn_mapL2":
                Main.getNodeMap().setCurrentFloor("L2");
                break;
            case "btn_mapL1":
                Main.getNodeMap().setCurrentFloor("L1");
                break;
            case "btn_mapG":
                Main.getNodeMap().setCurrentFloor("G");
                break;
            case "btn_map01":
                Main.getNodeMap().setCurrentFloor("1");
                break;
            case "btn_map02":
                Main.getNodeMap().setCurrentFloor("2");
                break;
            case "btn_map03":
                Main.getNodeMap().setCurrentFloor("3");
                break;
        }
        gc1.clearRect(0, 0, currentMap.getFitWidth(), currentMap.getFitHeight());
        Image map = mapImage.getLoadedMap(clickedID);
        this.currentMap.setImage(map);
        redraw();
    }

    @FXML
    void selectNode(MouseEvent event){
        double mousex = (5000 * event.getX()) / mapWidth;
        double mousey = (3400 * event.getY()) / mapHeight;
        try {
            NodeObj editNode = Main.getNodeMap().getNearestNeighbor((int)mousex, (int)mousey);

            //set each of the fields to their values
            nodeIDField.setText(editNode.getNode().getNodeID());
            xLocField.setText(editNode.getNode().getxLoc() + "");
            yLocField.setText(editNode.getNode().getyLoc() + "");
            floorField.setText(editNode.getNode().getFloor());
            nodeTypeField.setText(editNode.getNode().getNodeType());
            teamField.setText(editNode.getNode().getTeam());
            buildingField.setText(editNode.getNode().getBuilding());
            longNameField.setText(editNode.getNode().getLongName());
            teamField.setText(editNode.getNode().getShortName());

            //now make all fields visible with .setVisibility(true)
            nodeInfoPane.setVisible(true);

        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void adminLogin(){
        Main.getCurrStage().setScene(Main.getPatientScene());
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


}
