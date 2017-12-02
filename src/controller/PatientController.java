package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.ImageLoader;
import model.InvalidNodeException;
import model.astar;
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


public class PatientController extends Controller {

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

    private GraphicsContext gc1 = null;

    public static TextDirections textDirections = new TextDirections();

    private int XTrans = 0;

    private int YTrans = 0;

    private double Zoom = 1;

    ArrayList<NodeObj> currPath = null;

    NodeObj goal = null;

    private PathingContainer currentAlgorithm = new PathingContainer();

    @FXML
    private TextArea directionsBox;

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
    private ImageView homeScreen;


    ImageLoader mapImage = new ImageLoader();

    public void initialize() {
        Image m1 = mapImage.getLoadedMap("btn_map01");
        currentMap.setImage(m1);
        currentAlgorithm.setPathAlg(new astar());
        mapWidth = currentMap.getFitWidth();
        mapHeight = currentMap.getFitHeight();
        setKioskLoc(2460, 910);
        if (gc1 == null)
            gc1 = gc.getGraphicsContext2D();
        gc1.clearRect(0, 0, currentMap.getFitWidth(), currentMap.getFitHeight());
    }

    @FXML
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
        //AdminController.currentMap.setImage(map);
        DrawCurrentFloorPath();
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

    public void DrawCurrentFloorPath() {
        gc1.setLineWidth(2);
        NodeObj tempDraw = goal;
        ArrayList<String> Floors = new ArrayList<String>();
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
            if (!(Floors.contains(n.node.getFloor()) || n.node.getNodeType().equals("ELEV")))
                Floors.add(n.node.getFloor());
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
    }


    /*
     * findPath pathfinds, and draws the route to the screen
     */
    public void findPath(MouseEvent event) throws InvalidNodeException{
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
//                directionsBox.setText(textDirections.getTextDirections(path));
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


}
