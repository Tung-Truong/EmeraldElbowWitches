package controller;

//import Healthcare.HealthCareRun;
//import Healthcare.ServiceException;

import com.jfoenix.controls.*;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeTableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.ImageLoader;
import model.InvalidNodeException;
import model.astar;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import model.*;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import javafx.scene.control.Hyperlink;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javax.tools.Tool;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class PatientController extends Controller {

    @FXML
    private AnchorPane window;

    @FXML
    private JFXButton loginButton;

    @FXML
    private JFXButton directionsButton;

    @FXML
    private JFXTogglePane textTogglePane;

    @FXML
    private JFXToggleButton textToggle;

    @FXML
    private JFXSlider zoomBar;

    @FXML
    private JFXButton SearchForNode;

    @FXML
    private JFXComboBox<String> SearchOptions;

    @FXML
    private JFXTextField SearchNodeID;

    @FXML
    private ImageView img_Map;

    @FXML
    private JFXButton btn_map03;

    @FXML
    private JFXButton SearchForStart;

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
    private JFXButton SearchPath;

    @FXML
    public ImageView currentMap;

    @FXML
    private ImageView openclose;

    @FXML
    private ImageView openclose1;

    @FXML
    private ImageView start;

    @FXML
    private ImageView end;

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

    @FXML
    private JFXButton Tleft;

    @FXML
    private JFXButton Tright;

    @FXML
    private JFXButton Tup;

    @FXML
    private JFXButton Tdown;

    @FXML
    private JFXButton toHTML;

    @FXML
    private JFXToggleButton reversePath;

    public static TextDirections textDirections = new TextDirections();
    ArrayList<NodeObj> currPath = null;
    NodeObj goal = null;
    ArrayList<String> Floors;
    double startX;
    double startY;
    ArrayList<NodeObj> strPath;
    Animation oldAnimation;
    SingleController single = SingleController.getController();
    private NodeObj openCloseNode;
    private NodeObj openClose1Node;
    private boolean openCloseFlag = false;
    private boolean openClose1Flag = false;
    private ImageLoader mapImage = new ImageLoader();
    private GraphicsContext gc1 = null;
    private int speed;



    public void initialize() {
        gc1 = gc.getGraphicsContext2D();
        SearchPath.getStyleClass().add("buttonone");
        openclose.setVisible(false);
        openclose1.setVisible(false);
        start.setVisible(false);
        end.setVisible(false);
        Image m1 = mapImage.getLoadedMap("btn_map01");
        NodeObj n;
        selectFloorWithPath("1");
        currentMap.setImage(m1);
        btn_map01.setOpacity(1);
        single.getAlgorithm().setPathAlg(new astar());
        single.setMapWidth(currentMap.getFitWidth());
        single.setMapHeight(currentMap.getFitHeight());
        setKioskLoc(2460, 910);
        redraw();
        for (NodeObj n1 : Main.getNodeMap().getNodes()) {
            if (!n1.node.getNodeType().equals("HALL")) {
                SearchOptions.getItems().add(n1.node.getNodeID() + " : " + n1.node.getLongName());
            }
        }
        window.widthProperty().addListener(anchorPaneChanged);
        window.heightProperty().addListener(anchorPaneChanged);
        for (String s : Main.getImportantNodes()) {
            n = Main.nodeMap.getNodeObjByID(s);
            SearchOptions.getItems().add(n.node.getNodeID() + " : " + n.node.getLongName());
        }
        toolTipsInit();
    }

    private void toolTipsInit() {

        //about us
        final Tooltip aboutUs = new Tooltip();
        aboutUs.setText("Learn about the project and our team!");
        toHTML.setTooltip(aboutUs);

        //pan up
        final Tooltip panUp = new Tooltip();
        panUp.setText("Pan up");
        Tup.setTooltip(panUp);

        //pan down
        final Tooltip panDown = new Tooltip();
        panDown.setText("Pan down");
        Tdown.setTooltip(panDown);

        //pan right
        final Tooltip panRight = new Tooltip();
        panRight.setText("Pan right");
        Tright.setTooltip(panRight);

        //pan left
        final Tooltip panLeft = new Tooltip();
        panLeft.setText("Pan left");
        Tleft.setTooltip(panLeft);

        //zoom
        final Tooltip zoom = new Tooltip();
        zoom.setText("Drag slider to change zoom level");
        zoomBar.setTooltip(zoom);

        //node search
        final Tooltip searchNode = new Tooltip();
        searchNode.setText("Search for a node ID");
        SearchNodeID.setTooltip(searchNode);

        //node search result select from list
        final Tooltip searchResults = new Tooltip();
        searchResults.setText("Click to see search results");
        SearchOptions.setTooltip(searchResults);

        //confirm selection
        final Tooltip confirmSelection = new Tooltip();
        confirmSelection.setText("Click to zoom to selection");
        SearchForNode.setTooltip(confirmSelection);

        //login
        final Tooltip login = new Tooltip();
        login.setText("Click to login");
        loginButton.setTooltip(login);

        //floor button
        final Tooltip selectFloorL2 = new Tooltip();
        selectFloorL2.setText("Switch to L2 map");
        btn_mapL2.setTooltip(selectFloorL2);

        final Tooltip selectFloorL1 = new Tooltip();
        selectFloorL1.setText("Switch to L1 map");
        btn_mapL1.setTooltip(selectFloorL1);

        final Tooltip selectFloorG = new Tooltip();
        selectFloorG.setText("Switch to ground floor map");
        btn_mapG.setTooltip(selectFloorG);

        final Tooltip selectFloor1 = new Tooltip();
        selectFloor1.setText("Switch to first floor map");
        btn_map01.setTooltip(selectFloor1);

        final Tooltip selectFloor2 = new Tooltip();
        selectFloor2.setText("Switch to second floor map");
        btn_map02.setTooltip(selectFloor2);

        final Tooltip selectFloor3 = new Tooltip();
        selectFloor3.setText("Switch to third floor map");
        btn_map03.setTooltip(selectFloor3);

        //floor order label
        final Tooltip floorLabel = new Tooltip();
        floorLabel.setText("Order in which floor is visited");
        floorL2Label.setTooltip(floorLabel);
        floorL1Label.setTooltip(floorLabel);
        floorGLabel.setTooltip(floorLabel);
        floor1Label.setTooltip(floorLabel);
        floor2Label.setTooltip(floorLabel);
        floor3Label.setTooltip(floorLabel);

        //text directions
        final Tooltip textDirections = new Tooltip();
        textDirections.setText("Click to see text directions");
        directionsButton.setTooltip(textDirections);

        //map itself
        final Tooltip mapInstructions = new Tooltip();
        mapInstructions.setText("Navigate to a location: left click on map\nChange start location: right click on map");

        final Tooltip searchForStart = new Tooltip();
        searchForStart.setText("Click to set start location");
        SearchForStart.setTooltip(searchForStart);

        final Tooltip switchDir = new Tooltip();
        switchDir.setText("Click to switch start and end");
        reversePath.setTooltip(switchDir);

    }

    // Listener to handle when the image ratio is changed
    final ChangeListener<Number> anchorPaneChanged = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            ReadOnlyDoubleProperty p = (ReadOnlyDoubleProperty) observable;
            String name = p.getName();
            Double value = p.getValue();
            if (name == "width"){
                currentMap.setFitWidth(value);
                gc.setWidth(value);
                redraw();
            } else if (name == "height"){
                currentMap.setFitHeight(value);
                gc.setHeight(value);
                redraw();
            }
            if (oldAnimation != null)
                oldAnimation.stop();
            openclose.setVisible(false);
            openclose1.setVisible(false);
            start.setVisible(false);
            end.setVisible(false);
            redraw();
        }
    };
    public void redraw() {
        if (gc1 == null)
            gc1 = gc.getGraphicsContext2D();
        gc1.clearRect(0, 0, currentMap.getFitWidth(), currentMap.getFitHeight());
        try {
            oldAnimation.stop();
        }catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    @FXML
    void changeMap(Event e) {
        start.setVisible(false);
        end.setVisible(false);
        openclose.setVisible(false);
        openclose1.setVisible(false);
        resetFloorButtons();
        if(oldAnimation != null) {
            oldAnimation.stop();
        }
        Main.controllers.updateAllMaps(e);
        if (currPath != null) {
            if (oldAnimation != null) {
                oldAnimation.stop();
                gc1.clearRect(0, 0, currentMap.getFitWidth(), currentMap.getFitHeight());
                redraw();
                gc.getGraphicsContext2D().setStroke(Color.PURPLE);
            }
           // gc1.setStroke(new Color(0,0,.45,1));
            Animation animation = createPathAnimation(convertPath(pathFloorFilter(currPath)), Duration.millis(pathAnimationSpeed()));
            animation.play();
            oldAnimation = animation;
            DrawCurrentFloorPath();
        }
    }

    void getMap(Event e) {


        String clickedID = null;
        try{
            clickedID = ((JFXButton)e.getSource()).getId();
        }catch(ClassCastException elv){
            clickedID = ((TreeTableView<ServiceRequest>) e.getSource()).getId();
        }

        if (reversePath.isSelected()) {
            String currFloor = goal.node.getFloor();
            floorSwitch(currFloor);
        }
        clearChosenFloor();
        floorSwitch(clickedID);

        if (!clickedID.equals("SearchForNode")) {
            Image map = mapImage.getLoadedMap(clickedID);
            this.currentMap.setImage(map);
            redraw();
        }

    }

    //code for get map so code is not duplicated.
    private void floorSwitch(String caseString) {
        switch (caseString) {
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
            case "SearchForNode":
                String searchNewNodeID = SearchOptions.getValue().split(":")[0].trim();
                NodeObj newSearchNode = Main.getNodeMap().getNodeObjByID(searchNewNodeID);
                redraw();
                try {
                    if (newSearchNode == null)
                        throw new InvalidNodeException("no node with that ID");
                    Floors = null;
                    clearChosenFloor();
                    redraw();
                    Main.getNodeMap().setCurrentFloor(newSearchNode.node.getFloor());
                    Image map = mapImage.getLoadedMap("btn_map" + newSearchNode.node.getFloor());
                    this.currentMap.setImage(map);
                    gc1.setFill(Color.DARKRED);
                    gc1.fillOval(newSearchNode.node.getxLoc() * currentMap.getFitWidth() / 5000 - 5,
                            newSearchNode.node.getyLoc() * currentMap.getFitHeight() / 3400 - 5,
                            10,
                            10);
                } catch (InvalidNodeException exc) {
                    exc.printStackTrace();
                }
                break;
        }

       /* if (!clickedID.equals("SearchForNode")) {
            Image map = mapImage.getLoadedMap(clickedID);
            this.currentMap.setImage(map);*/
            redraw();
        //}
    }

    void clearChosenFloor() {
        btn_mapL2.setOpacity(.5);
        btn_mapL1.setOpacity(.5);
        btn_mapG.setOpacity(.5);
        btn_map01.setOpacity(.5);
        btn_map02.setOpacity(.5);
        btn_map03.setOpacity(.5);
        if (Floors != null) {
            /*for(int i = 0; i < Floors.size(); i++){
                selectFloor(Floors.get(i), i+1);
            }*/
            //selectFloorWithPath(Main.getNodeMap().currentFloor);
            for (int i = Floors.size(); i > 0; i--) {
                selectFloor(Floors.get(i - 1), (Floors.size() + 1) - i);
            }
        }
    }

    void resetFloorButtons() {
        btn_mapL2.setOpacity(.5);
        btn_mapL1.setOpacity(.5);
        btn_mapG.setOpacity(.5);
        btn_map01.setOpacity(.5);
        btn_map02.setOpacity(.5);
        btn_map03.setOpacity(.5);
        floorL2Label.setText("");
        floorL1Label.setText("");
        floorGLabel.setText("");
        floor1Label.setText("");
        floor2Label.setText("");
        floor3Label.setText("");
    }

    void selectFloor(String selectedFloor, int order) {
        switch (selectedFloor) {
            case "L2":
                btn_mapL2.setOpacity(1);
                if (floorL2Label.getText().equals("")) {
                    floorL2Label.setText(order + "");
                } else {
                    floorL2Label.setText(floorL2Label.getText() + ", " + order + "");
                }
                break;
            case "L1":
                btn_mapL1.setOpacity(1);
                if (floorL1Label.getText().equals("")) {
                    floorL1Label.setText(order + "");
                } else {
                    floorL1Label.setText(floorL1Label.getText() + ", " + order + "");
                }
                break;
            case "G":
                btn_mapG.setOpacity(1);
                if (floorGLabel.getText().equals("")) {
                    floorGLabel.setText(order + "");
                } else {
                    floorGLabel.setText(floorGLabel.getText() + ", " + order + "");
                }
                break;
            case "1":
                btn_map01.setOpacity(1);
                if (floor1Label.getText().equals("")) {
                    floor1Label.setText(order + "");
                } else {
                    floor1Label.setText(floor1Label.getText() + ", " + order + "");
                }
                break;
            case "2":
                btn_map02.setOpacity(1);
                if (floor2Label.getText().equals("")) {
                    floor2Label.setText(order + "");
                } else {
                    floor2Label.setText(floor2Label.getText() + ", " + order + "");
                }
                break;
            case "3":
                btn_map03.setOpacity(1);
                if (floor3Label.getText().equals("")) {
                    floor3Label.setText(order + "");
                } else {
                    floor3Label.setText(floor3Label.getText() + ", " + order + "");
                }
                break;
        }
    }

    void selectFloorWithPath(String selectedFloor) {

        switch (selectedFloor) {
            case "L2":
                btn_mapL2.setOpacity(.7);

                break;
            case "L1":
                btn_mapL1.setOpacity(.7);

                break;
            case "G":
                btn_mapG.setOpacity(.7);

                break;
            case "1":
                btn_map01.setOpacity(.7);

                break;
            case "2":
                btn_map02.setOpacity(.7);

                break;
            case "3":
                btn_map03.setOpacity(.7);

                break;
        }
    }

    // Currently in Singleton
//    /*
//     * setKioskLoc sets the default location for the floor
//     */
    void setKioskLoc(int xCoord, int yCoord) {
        try {
            Main.setKiosk(Main.getNodeMap().getNearestNeighborFilter(xCoord, yCoord));
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
    }

    public void DrawCurrentFloorPath() {
        /*HealthCareRun health = new HealthCareRun();
        try {
            health.run(100,500,100,100,"view/stylesheets/default.css","","");
        } catch (ServiceException e) {
            e.printStackTrace();
        }*/
        openClose1Flag = false;
        openCloseFlag = false;
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
                if ((reversePath.isSelected() && (n != Main.getKiosk())) || (!reversePath.isSelected() && (n != goal))) {
                    if (n.node.getFloor().equals(Main.getNodeMap().currentFloor) &&
                            tempDraw.node.getFloor().equals(Main.getNodeMap().currentFloor)) {
                        gc1.strokeLine(n.node.getxLoc() * currentMap.getFitWidth() / 5000,
                                n.node.getyLoc() * currentMap.getFitHeight() / 3400,
                                tempDraw.node.getxLoc() * currentMap.getFitWidth() / 5000,
                                tempDraw.node.getyLoc() * currentMap.getFitHeight() / 3400);
                    } else if (n.node.getFloor().equals(Main.getNodeMap().currentFloor) && !tempDraw.node.getFloor().equals(n.node.getFloor())) {
                        gc1.setFill(Color.BLACK);
                        if (n.node.getFloor().equals(Main.getNodeMap().currentFloor)) {
                            gc1.fillOval(n.node.getxLoc() * currentMap.getFitWidth() / 5000 - 5,
                                    n.node.getyLoc() * currentMap.getFitHeight() / 3400 - 5,
                                    10,
                                    10);
                            if (reversePath.isSelected()) {
                                if(single.getZoom() <= 1) {
                                    openclose.setVisible(true);
                                }
                                openCloseFlag = true;
                                openclose.setX(n.node.getxLoc() * currentMap.getFitWidth() / 5000 - openclose.getFitWidth() / 2 - 15);
                                openclose.setY(n.node.getyLoc() * currentMap.getFitHeight() / 3400 - openclose.getFitHeight() / 2 - 5);
                                openCloseNode = n;
                            } else {
                                if(single.getZoom() <= 1) {
                                    openclose1.setVisible(true);
                                }
                                openClose1Flag = true;
                                openclose1.setX(n.node.getxLoc() * currentMap.getFitWidth() / 5000 - openclose1.getFitWidth() / 2 - 15);
                                openclose1.setY(n.node.getyLoc() * currentMap.getFitHeight() / 3400 - openclose1.getFitHeight() / 2 - 5);
                                openClose1Node = n;
                            }
                        }

                    } else if (!n.node.getFloor().equals(Main.getNodeMap().currentFloor) && !tempDraw.node.getFloor().equals(n.node.getFloor())) {
                        gc1.setFill(Color.GOLD);
                        if (tempDraw.node.getFloor().equals(Main.getNodeMap().currentFloor)) {
                            gc1.fillOval(n.node.getxLoc() * currentMap.getFitWidth() / 5000 - 5,
                                    n.node.getyLoc() * currentMap.getFitHeight() / 3400 - 5,
                                    10,
                                    10);
                            if (reversePath.isSelected()) {
                                if(single.getZoom() <= 1) {
                                    openclose1.setVisible(true);
                                }
                                openClose1Flag = true;
                                openclose1.setX(n.node.getxLoc() * currentMap.getFitWidth() / 5000 - openclose1.getFitWidth() / 2 - 15);
                                openclose1.setY(n.node.getyLoc() * currentMap.getFitHeight() / 3400 - openclose1.getFitHeight() / 2 - 5);
                                openClose1Node = tempDraw;
                            } else {
                                if(single.getZoom() <= 1) {
                                    openclose.setVisible(true);
                                }
                                openCloseFlag = true;
                                openclose.setX(n.node.getxLoc() * currentMap.getFitWidth() / 5000 - openclose.getFitWidth() / 2 - 15);
                                openclose.setY(n.node.getyLoc() * currentMap.getFitHeight() / 3400 - openclose.getFitHeight() / 2 - 5);
                                openCloseNode = tempDraw;
                            }
                        }
                    }
                }
            }
            if (Floors.size() > 0) {
                if (!(Floors.get(Floors.size() - 1).equals(n.getNode().getFloor()) || n.getNode().getNodeType().equals("ELEV"))) {
                    Floors.add(n.getNode().getFloor());

                }
            } else if (!(n.getNode().getNodeType().equals("ELEV"))) {

                Floors.add(n.getNode().getFloor());
            }
            tempDraw = n;
        }

        if (goal.node.getFloor().equals(Main.getNodeMap().currentFloor)) {
            gc1.setFill(Color.DARKRED);
            gc1.fillOval(goal.node.getxLoc() * currentMap.getFitWidth() / 5000 - 5,
                    goal.node.getyLoc() * currentMap.getFitHeight() / 3400 - 5,
                    10,
                    10);
            if (reversePath.isSelected()) {
                if(single.getZoom() <= 1) {
                    start.setVisible(true);
                }
                start.setX(goal.node.getxLoc() * currentMap.getFitWidth() / 5000 - 5 - end.getFitWidth() / 2 + 5);
                start.setY(goal.node.getyLoc() * currentMap.getFitHeight() / 3400 - 5 - end.getFitHeight() / 2 + 5);
            } else {
                if(single.getZoom() <= 1) {
                    end.setVisible(true);
                }
                end.setX(goal.node.getxLoc() * currentMap.getFitWidth() / 5000 - 5 - end.getFitWidth() / 2 + 5);
                end.setY(goal.node.getyLoc() * currentMap.getFitHeight() / 3400 - 5 - end.getFitHeight() / 2 + 5);
            }
        }
        if (Main.getKiosk().node.getFloor().equals(Main.getNodeMap().currentFloor)) {
            gc1.setFill(Color.DARKGREEN);
            gc1.fillOval(Main.getKiosk().node.getxLoc() * currentMap.getFitWidth() / 5000 - 5,
                    Main.getKiosk().node.getyLoc() * currentMap.getFitHeight() / 3400 - 5,
                    10,
                    10);
            if (reversePath.isSelected()) {
                end.setX(Main.getKiosk().node.getxLoc() * currentMap.getFitWidth() / 5000 - 5 - end.getFitWidth() / 2);
                end.setY(Main.getKiosk().node.getyLoc() * currentMap.getFitHeight() / 3400 - 5 - end.getFitHeight() / 2);
                if(single.getZoom() <=1) {
                    end.setVisible(true);
                }
            } else {
                start.setX(Main.getKiosk().node.getxLoc() * currentMap.getFitWidth() / 5000 - 5 - end.getFitWidth() / 2);
                start.setY(Main.getKiosk().node.getyLoc() * currentMap.getFitHeight() / 3400 - 5 - end.getFitHeight() / 2);
                if(single.getZoom() <=1){
                    start.setVisible(true);
                }
            }
        }
        gc1.setFill(Color.YELLOW);
        clearChosenFloor();
        System.out.println(Floors.toString());
    }

    //function that handles going to next floor when you click on a node
    @FXML
    private void goToNextFloor() {
        start.setVisible(false);
        end.setVisible(false);
        NodeObj node = null;
        node = openCloseNode;
        int i = currPath.indexOf(node);
        System.out.println(node.node.getFloor());
        String floor = null;
        String nextFloor = currPath.get(i + 1).node.getFloor();
        String prevFloor = currPath.get(i - 1).node.getFloor();

        //go to previous floor
        if (!nextFloor.equals(currPath.get(i).node.getFloor())) {
            floor = nextFloor;
            System.out.println(floor);
            floorToMap(floor);
            System.out.println("next floor");
        }
        //go to next floor
        else if (!prevFloor.equals(currPath.get(i).node.getFloor())) {
            floor = prevFloor;
            floorToMap(floor);
            System.out.println(floor);
            System.out.println("previous floor");
            prevFloor = floor;
        }
    }

    //function that handles going to previous floor when clicking on a node
    @FXML
    private void goToPreviousFloor() {
        start.setVisible(false);
        end.setVisible(false);
        NodeObj node = null;
        node = openClose1Node;
        int i = currPath.indexOf(node);
        System.out.println(node.node.getFloor());
        String floor = null;
        String nextFloor = currPath.get(i + 1).node.getFloor();
        String prevFloor = currPath.get(i - 1).node.getFloor();
        //go to previous floor
        if (!nextFloor.equals(currPath.get(i).node.getFloor())) {
            floor = nextFloor;
            System.out.println(floor);
            floorToMap(floor);
            System.out.println("previous floor");
        }
        //go to next floor
        if (!prevFloor.equals(currPath.get(i).node.getFloor())) {
            floor = prevFloor;
            floorToMap(floor);
            System.out.println(floor);
            System.out.println("next floor");
        }
    }

    //helper function that goes from floor to corresponding map
    private void floorToMap(String floor) {
        switch (floor) {
            case "L2":
                Main.getNodeMap().setCurrentFloor("L2");
                btn_mapL2.setOpacity(1);
                btn_mapL2.fire();
                break;
            case "L1":
                Main.getNodeMap().setCurrentFloor("L1");
                btn_mapL1.setOpacity(1);
                btn_mapL1.fire();
                break;
            case "G":
                Main.getNodeMap().setCurrentFloor("G");
                btn_mapG.setOpacity(1);
                btn_mapG.fire();
                break;
            case "1":
                Main.getNodeMap().setCurrentFloor("1");
                btn_map01.setOpacity(1);
                btn_map01.fire();
                break;
            case "2":
                Main.getNodeMap().setCurrentFloor("2");
                btn_map02.setOpacity(1);
                btn_map02.fire();
                break;
            case "3":
                Main.getNodeMap().setCurrentFloor("3");
                btn_map03.setOpacity(1);
                btn_map03.fire();
                break;
        }
    }

    @FXML
    void ourWebsite() throws IOException {
        FXMLLoader servContLoad = new FXMLLoader(this.getClass().getClassLoader().getResource("view/ui/Webpane.fxml"));
        Parent root = (Parent) servContLoad.load();
        Scene scene = new Scene(root, 1000, 1000);


        WebView oursite = (WebView) scene.lookup("#website");
        WebEngine webEngine = oursite.getEngine();
        webEngine.load("https://cs3733-about-us.herokuapp.com");

        Stage webStage = new Stage();
        webStage.setTitle("Team E Website");
        webStage.setScene(scene);
        webStage.show();
    }


    /*
     * findPath pathfinds, and draws the route to the screen
     */
    public void findPath(MouseEvent event) {
        directionsButton.setVisible(true);
        //create a new astar object
        SearchPath.setVisible(false);
        openclose1.setVisible(false);
        openclose.setVisible(false);
        start.setVisible(false);
        end.setVisible(false);
        double mousex = (5000 * event.getX()) / currentMap.getFitWidth();
        double mousey = (3400 * event.getY()) / currentMap.getFitHeight();
        if (gc1 == null)
            gc1 = gc.getGraphicsContext2D();
        gc1.clearRect(0, 0, currentMap.getFitWidth(), currentMap.getFitHeight());
        gc1.setLineWidth(2);
        gc1.setStroke(Color.BLUE);
        gc1.setFill(Color.RED);
        //get node that corr. to click from ListOfNodeObjects made in main
        try {
            goal = Main.getNodeMap().getNearestNeighborFilter
                    ((int) Math.floor(mousex), (int) Math.floor(mousey));
            //sets startx and starty = use in reversePath;
            startX = (int) Math.floor(mousex);
            startY = (int) Math.floor(mousey);
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
        //getStart
        NodeObj Kiosk = Main.getKiosk();
        //set the path to null
        strPath = new ArrayList<>();
        if (!Kiosk.getNode().getNodeID().equals(goal.getNode().getNodeID())) {
            //try a*
            if (single.getAlgorithm().getPathAlg().pathfind(Kiosk, goal)) {
                gc1.clearRect(0, 0, currentMap.getFitWidth(), currentMap.getFitHeight());

                strPath = single.getAlgorithm().getPathAlg().getGenPath();
                currPath = strPath;
                if(mostUsedPaths.containsKey(currPath)){
                    mostUsedPaths.replace(currPath, (mostUsedPaths.get(currPath) + 1));
                }else{
                    mostUsedPaths.put(currPath, 1);
                }
                toggleTextArea.setText(textDirections.getTextDirections(strPath));


            } else {
                try {
                    throw new InvalidNodeException("this is not accessable with the current map");
                } catch (InvalidNodeException e) {
                    e.printStackTrace();
                }
            }
            if (oldAnimation != null) {
                oldAnimation.stop();
                gc1.clearRect(0, 0, currentMap.getFitWidth(), currentMap.getFitHeight());
                redraw();
                gc.getGraphicsContext2D().setStroke(Color.BLUE);
            }
            Animation animation = createPathAnimation(convertPath(pathFloorFilter(currPath)), Duration.millis(pathAnimationSpeed()));
            animation.play();
            oldAnimation = animation;
            DrawCurrentFloorPath();
        }
    }

    @FXML
    public void redrawPath() {
        redraw();
        clearChosenFloor();
        resetFloorButtons();
        gc1.clearRect(0, 0, currentMap.getFitWidth(), currentMap.getFitHeight());

        gc1.setLineWidth(2);
        gc1.setStroke(Color.BLUE);
        gc1.setFill(Color.DARKBLUE);

        resetAnimations(oldAnimation);
        directionsButton.setVisible(true);
        //create a new astar object
        SearchPath.setVisible(false);

        if (gc1 == null)
            gc1 = gc.getGraphicsContext2D();
        gc1.clearRect(0, 0, currentMap.getFitWidth(), currentMap.getFitHeight());
        gc1.setLineWidth(2);
        gc1.setStroke(Color.BLUE);
        gc1.setFill(Color.DARKBLUE);
        NodeObj Kiosk = Main.getKiosk();

        if (!Kiosk.getNode().getNodeID().equals(goal.getNode().getNodeID())) {
            drawingPath();
        }
    }

    private void drawingPath(){
        ArrayList<NodeObj> reversedPath = new ArrayList<>();
        //getStart
        NodeObj Kiosk = Main.getKiosk();
        //set the path to null
        strPath = new ArrayList<>();
        //try a*
        if (single.getAlgorithm().getPathAlg().pathfind(Kiosk, goal)) {
            gc1.clearRect(0, 0, currentMap.getFitWidth(), currentMap.getFitHeight());

            strPath = single.getAlgorithm().getPathAlg().getGenPath();
            currPath = strPath;
            if(reversePath.isSelected()){
                Collections.reverse(currPath);
            }
            toggleTextArea.setText(textDirections.getTextDirections(strPath));


        } else {
            try {
                throw new InvalidNodeException("this is not accessable with the current map");
            } catch (InvalidNodeException e) {
                e.printStackTrace();
            }
        }
        if (oldAnimation != null) {
            resetAnimations(oldAnimation);
        }
        Animation animation = createPathAnimation(convertPath(pathFloorFilter(currPath)), Duration.millis(4000));
        animation.play();
        oldAnimation = animation;
        DrawCurrentFloorPath();
    }

    //Functions required for animation fun times
    private Path convertPath(ArrayList<NodeObj> list) {
        Path path = new Path();
        ArrayList<NodeObj> reverseList = list;
        Collections.reverse(reverseList);
        int x = 0;
        for (int i = 0; i < (reverseList.size() - 1); i++) {
            ArrayList<NodeObj> neighbors = reverseList.get(i).getListOfNeighbors();
            if (neighbors.contains(reverseList.get(i + 1))) {
                path.getElements().addAll(new MoveTo(reverseList.get(i).node.getxLoc(), reverseList.get(i).node.getyLoc()), new LineTo(reverseList.get(i + 1).node.getxLoc(), reverseList.get(i + 1).node.getyLoc()));
            }
        }
        try {
            path.getElements().addAll(new MoveTo(reverseList.get(reverseList.size() - 1).node.getxLoc(), reverseList.get(reverseList.size() - 1).node.getyLoc()), new LineTo(reverseList.get(reverseList.size() - 1).node.getxLoc(), reverseList.get(reverseList.size() - 1).node.getyLoc()));
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("wat. somehow fixes the last floor selected showing as selected bug");
        }
        return path;
    }

    private Animation createPathAnimation(Path path, Duration duration) {
        GraphicsContext gc = gc1;
        Circle pen = new Circle(0, 0, 4);

        PathTransition pathTransition = new PathTransition(duration, path, pen);
        pathTransition.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            Location oldLocation = null;
            Location oldOldLocation = null;

            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                if (oldValue == Duration.ZERO) {
                    return;
                }
                double x = pen.getTranslateX();
                double y = pen.getTranslateY();

                if (oldLocation == null) {
                    oldLocation = new Location();
                    oldLocation.x = x;
                    oldLocation.y = y;
                    return;
                }

                gc.setStroke(Color.RED);
                gc.setFill(Color.YELLOW);
                gc.setLineWidth(4);
                try {
                    NodeObj a = Main.getNodeMap().getNearestNeighborFilter((int) oldLocation.x, (int) oldLocation.y);
                    NodeObj b = Main.getNodeMap().getNearestNeighborFilter((int) x, (int) y);

                    if (a.getListOfNeighbors().contains(b)) {
                        gc.strokeLine(oldLocation.x * currentMap.getFitWidth() / 5000, oldLocation.y * currentMap.getFitHeight() / 3400, x * currentMap.getFitWidth() / 5000, y * currentMap.getFitHeight() / 3400);
                        oldLocation.x = x;
                        oldLocation.y = y;
                        oldOldLocation = oldLocation;
                    } else if (oldOldLocation != null && oldOldLocation.x - oldLocation.x < 10 && oldOldLocation.y - oldLocation.y < 10) {
                        gc.strokeLine(oldOldLocation.x * currentMap.getFitWidth() / 5000, oldOldLocation.y * currentMap.getFitHeight() / 3400, oldLocation.x * currentMap.getFitWidth() / 5000, oldLocation.y * currentMap.getFitHeight() / 3400);
                        oldLocation.x = x;
                        oldLocation.y = y;
                    }
                } catch (InvalidNodeException e) {
                    e.getMessage();
                } catch (NullPointerException e) {
                    e.getMessage();
                }
                oldLocation.x = x;
                oldLocation.y = y;
            }
        });

        return pathTransition;

    }
    //function to reset animations
    private void resetAnimations(Animation animation){
        animation.stop();
        gc1.clearRect(0, 0, currentMap.getFitWidth(), currentMap.getFitHeight());
        redraw();
        gc.getGraphicsContext2D().setStroke(Color.BLUE);
    }


    private ArrayList<NodeObj> pathFloorFilter(ArrayList<NodeObj> path) {
        ArrayList<NodeObj> filteredPath = new ArrayList<>();
        for (NodeObj n : path) {
            if (n.getNode().getFloor().equals(Main.getNodeMap().currentFloor))
                filteredPath.add(n);
        }
        return filteredPath;
    }

    public static class Location {
        double x;
        double y;
    }

    @FXML
    void mousePress(MouseEvent event) {
        if ((event.getButton() == MouseButton.SECONDARY) || ((event.getButton() == MouseButton.PRIMARY) && (event.isControlDown()))) {
            oldAnimation.stop();
            setStartNode(event);
        } else if (event.getButton() == MouseButton.PRIMARY) {
            findPath(event);
        }
    }

    @FXML
    void getTextDirections() throws IOException {
        if (strPath != null) {
            FXMLLoader dirContLoad = new FXMLLoader(getClass().getClassLoader().getResource("view/ui/TextDirections.fxml"));
            Parent root = dirContLoad.load();
            TextDirectionsController dirCont = dirContLoad.getController();
            Stage servStage = new Stage();
            servStage.setTitle("Text Directions");
            servStage.setScene(new Scene(root, 192, 550));
            dirCont.dirArea.setText(textDirections.getTextDirections(strPath));
            servStage.show();
        }
    }

    /*
     * setStartNode sets the start node to the node nearest to the given coords in the current building
     */
    @FXML
    void setStartNode(MouseEvent event) {
        double mousex = (5000 * event.getX()) / currentMap.getFitWidth();
        double mousey = (3400 * event.getY()) / currentMap.getFitHeight();
        String newStartNodeID = null;
        try {
            newStartNodeID = Main.getNodeMap().getNearestNeighborFilter((int) mousex, (int) mousey).getNode().getNodeID();
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
        NodeObj newStartNode = Main.getNodeMap().getNodeObjByID(newStartNodeID);
        Main.setKiosk(newStartNode);
        redraw();
    }

    @FXML
    void setSearchStart(){
        NodeObj newStartNode = Main.getNodeMap().getNodeObjByID(SearchOptions.getValue().split(":")[0].trim());
        Main.setKiosk(newStartNode);
        redraw();
    }

    @FXML
    void UpdateSearch() {
        SearchOptions.getItems().clear();
        ArrayList<NodeObj> SearchNodes = new ArrayList<>();
        String search = SearchNodeID.getText();
        if (search.length() > 2) {
            for (NodeObj n : Main.getNodeMap().getNodes()) {
                if (FuzzySearch.partialRatio(n.node.getLongName(), search) > 70 ||
                        n.node.getNodeID().contains(search.toUpperCase().trim())) {
                    SearchNodes.add(n);
                    SearchOptions.getItems().add(n.node.getNodeID() + " : " + n.node.getLongName());
                }
            }
        } else if (search.length() == 0) {

            NodeObj n;
            for (String s : Main.getImportantNodes()) {
                n = Main.nodeMap.getNodeObjByID(s);
                SearchOptions.getItems().add(n.node.getNodeID() + " : " + n.node.getLongName());
            }
            SearchOptions.show();
        }
    }

    @FXML
    void setSearchNode(Event e) {
        if (((JFXButton) e.getSource()).getId().equals("SearchForNode")) {
            try {
                if(SearchNodeID.getText().toLowerCase().trim().split(" ")[0].trim().equals("closest")) {
                    if (SearchNodeID.getText().toLowerCase().trim().equals("closest bathroom") || SearchNodeID.getText().toLowerCase().trim().equals("closest restroom")) {
                        findClosestRestroom();
                    }
                    if (SearchNodeID.getText().toLowerCase().trim().equals("closest elevator")) {
                        findClosestElevator();
                    }
                    if (SearchNodeID.getText().toLowerCase().trim().equals("closest exit")) { // nice
                        findClosestExit();
                    }
                }
                else {
                    String searchNewNodeID = SearchOptions.getValue().split(":")[0].trim();

                    NodeObj newSearchNode = Main.getNodeMap().getNodeObjByID(searchNewNodeID);
                    try {
                        if (newSearchNode == null)
                            throw new InvalidNodeException("no node with that ID");
                        getMap(e);
                        ((JFXButton) e.getSource()).setId("btn_map" + newSearchNode.node.getFloor());
                        Main.controllers.updateAllMaps(e);
                        ((JFXButton) e.getSource()).setId("SearchForNode");
                        gc1.setFill(Color.DARKRED);
                        gc1.fillOval(newSearchNode.node.getxLoc() * currentMap.getFitWidth() / 5000 - 5,
                                newSearchNode.node.getyLoc() * currentMap.getFitHeight() / 3400 - 5,
                                10,
                                10);
                        SearchPath.setVisible(true);
                        SearchPath.setText(searchNewNodeID);
                        SearchPath.setLayoutX(newSearchNode.node.getxLoc() * currentMap.getFitWidth() / 5000);
                        SearchPath.setLayoutY(newSearchNode.node.getyLoc() * currentMap.getFitHeight() / 3400);
                        openclose.setVisible(false);
                        openclose1.setVisible(false);
                        start.setVisible(false);
                        end.setVisible(false);
                    } catch (InvalidNodeException exc) {
                        exc.printStackTrace();
                    }
                }
            } catch (NullPointerException exc) {
                exc.getMessage();
            }
        }

    }

    @FXML
    void PathToHere() {
        //create a new astar object
        SearchPath.setVisible(false);
        if (gc1 == null)
            gc1 = gc.getGraphicsContext2D();
        gc1.clearRect(0, 0, currentMap.getFitWidth(), currentMap.getFitHeight());
        gc1.setLineWidth(2);
        gc1.setStroke(Color.BLUE);
        gc1.setFill(Color.RED);
        //get node that corr. to click from ListOfNodeObjects made in main
        if (Main.getNodeMap().getNodeObjByID(SearchPath.getText()) != null) {
            goal = Main.getNodeMap().getNodeObjByID(SearchPath.getText());
            //getStart
            NodeObj Kiosk = Main.getKiosk();
            //set the path to null
            ArrayList<NodeObj> path;
            if (!Kiosk.getNode().getNodeID().equals(goal.getNode().getNodeID())) {
                //try a*
                if (single.getAlgorithm().getPathAlg().pathfind(Kiosk, goal)) {
                    path = single.getAlgorithm().getPathAlg().getGenPath();
                    currPath = path;
                    toggleTextArea.setText(textDirections.getTextDirections(path));
                } else {
                    try {
                        throw new InvalidNodeException("this is not accessable with the current map");
                    } catch (InvalidNodeException e) {
                        e.printStackTrace();
                    }
                }
                if (oldAnimation != null) {
                    oldAnimation.stop();
                    gc1.clearRect(0, 0, currentMap.getFitWidth(), currentMap.getFitHeight());
                    redraw();
                    gc.getGraphicsContext2D().setStroke(Color.BLUE);
                }
                Animation animation = createPathAnimation(convertPath(pathFloorFilter(currPath)), Duration.millis(pathAnimationSpeed()));
                animation.play();
                oldAnimation = animation;
                DrawCurrentFloorPath();
            }
        }
    }

    private void findClosestRestroom() {
        findClosestFromCsv("src/model/docs/Restrooms.csv");
    }

    private void findClosestElevator() {
        findClosestFromCsv("src/model/docs/Elevators.csv");
    }

    private void findClosestExit() {
        findClosestFromCsv("src/model/docs/Exits.csv");
    }

    /*
     * takes a filepath to a csv file, and produces an ArrayList<NodeObj> containing the nodes
     * on the shortest path to that location
     */
    private void findClosestFromCsv(String csvFile) {
        ArrayList<String> nodeIDs = new ArrayList<>();
        ArrayList<NodeObj> nodes = new ArrayList<>();
        ArrayList<NodeObj> shortestSoFar = new ArrayList<>();
        ArrayList<NodeObj> nextPath;

        try {
            CSVtoArrayList.readCSVToArray(csvFile, 1, nodeIDs);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(String id : nodeIDs) {
            nodes.add(Main.nodeMap.getNodeObjByID(id));
        }

        if(!nodes.isEmpty()) {
            shortestSoFar = constructPath(Main.kiosk, nodes.get(0));
        }

        for(NodeObj end : nodes) {
            nextPath = constructPath(Main.kiosk, end);
            shortestSoFar = findShorterPath(shortestSoFar, nextPath);
        }

        // the next two lines fix a bug where the path would bee-line to the previous goal, or other strange behavior
        goal = shortestSoFar.get(shortestSoFar.size()-1);
        Collections.reverse(shortestSoFar);
        currPath = shortestSoFar;

        redraw();
        DrawCurrentFloorPath();
    }

    /*
     * helper method for findClosestFromCsvFile
     * produces an ArrayList<NodeObj> with the nodes the current algorithm chooses for the path
     */
    private ArrayList<NodeObj> constructPath(NodeObj start, NodeObj end) {
        ArrayList<NodeObj> path = new ArrayList<>();
        if(single.getAlgorithm().getPathAlg().pathfind(start, end)) {
            path = single.getAlgorithm().getPathAlg().getGenPath();
        }
        return path;
    }

    // returns the path with the lower total distance, used for findClosestFromCsv
    private ArrayList<NodeObj> findShorterPath(ArrayList<NodeObj> shortestSoFar, ArrayList<NodeObj> newPath) {
        int pathleng = 0;
        int genleng = 0;
        NodeObj prevN = null;
        for (NodeObj n : shortestSoFar) {
            if(prevN == null){
                prevN = n;
            }
            else{
                pathleng += n.getDistance(prevN);
            }
        }
        prevN = null;
        for(NodeObj n : newPath){
            if(prevN == null){
                prevN = n;
            }
            else{
                genleng += n.getDistance(prevN);
            }
        }

        if(pathleng > genleng) {
            shortestSoFar = newPath;
        }

        return shortestSoFar;
    }

    @FXML
    void adminLogin() throws IOException {
        FXMLLoader LogIn = new FXMLLoader(getClass().getClassLoader().getResource("view/ui/login.fxml"));
        Parent root = LogIn.load();
        Stage servStage = new Stage();
        servStage.setTitle("Login");
        servStage.setScene(new Scene(root, 380, 358));
        servStage.show();
    }

    /*
        Needs to snap to relative x and y position when zoomed in or out
     */

    @FXML
    void Zin() {
        SearchPath.setVisible(false);
        single.setZoom(zoomBar.getValue());
        single.setXTrans((int) clamp(single.getXTrans(), (-1 * Math.floor(currentMap.getFitWidth() * (single.getZoom() - 1) / 2)), Math.floor(currentMap.getFitWidth() * (single.getZoom() - 1) / 2)));
        single.setYTrans((int) clamp(single.getYTrans(), (-1 * Math.floor(currentMap.getFitHeight() * (single.getZoom() - 1) / 2)), Math.floor(currentMap.getFitHeight() * (single.getZoom() - 1) / 2)));
        openclose.setVisible(false);
        openclose1.setVisible(false);
        start.setVisible(false);
        end.setVisible(false);
        resize();

    }

    /*
        No more problems with errors with zooming but current x values are not being updates when we translate, need to
        get a current x values
     */

    @FXML
    void Tleft() {
        int deltaX = (int) (100.0 / single.getZoom());
        if (single.getXTrans() + deltaX <= Math.floor(currentMap.getFitWidth() * (single.getZoom() - 1) / 2)) {
            single.addX(deltaX);
            resize();
        }
    }

    @FXML
    void Tright() {
        int deltaX = (int) (100.0 / single.getZoom());
        if (single.getXTrans() >= Math.floor(-1 * (currentMap.getFitWidth() * (single.getZoom() - 1) / 2))) {
            single.subX(deltaX);
            resize();
        }
    }

    @FXML
    void Tup() {
        int deltaY = (int) (80.0 / single.getZoom());
        if (single.getYTrans() <= Math.floor(currentMap.getFitHeight() * (single.getZoom() - 1) / 2)) {
            single.addY(deltaY);
            resize();
        }
    }

    @FXML
    void Tdown() {
        int deltaY = (int) (80.0 / single.getZoom());
        if (single.getYTrans() >= Math.floor(-1 * (currentMap.getFitHeight() * (single.getZoom() - 1) / 2))) {
            single.subY(deltaY);
            resize();
        }
    }

    double clamp(double x, double min, double max) {
        if (x < min) {
            return min;
        } else if (x > max) {
            return max;
        } else {
            return x;
        }
    }

    @FXML
    void pathingHoverStart(MouseEvent event) {
        String hoveredID = ((JFXButton) event.getSource()).getId();
        opacHandler(1, hoveredID);
    }

    @FXML
    void pathingHoverStop(MouseEvent event) {
        String hoveredID = ((JFXButton) event.getSource()).getId();
        opacHandler(.5, hoveredID);
    }

    @FXML
    void floorHoverStart(MouseEvent event){
        String hoveredID = ((JFXButton) event.getSource()).getId();
        opacHandler(.5, hoveredID);
    }

    @FXML
    void floorHoverEnd(MouseEvent event){
        String hoveredID = ((JFXButton) event.getSource()).getId();
        opacHandler(-.5, hoveredID);
    }

    void opacHandler(double opacity, String hoveredID) {

        switch (hoveredID) {
            case "Tup":
                Tup.setOpacity(opacity);
                break;
            case "Tdown":
                Tdown.setOpacity(opacity);
                break;
            case "Tleft":
                Tleft.setOpacity(opacity);
                break;
            case "Tright":
                Tright.setOpacity(opacity);
                break;
            case "SearchForNode":
                SearchForNode.setOpacity(opacity);
                break;
            case "SearchForStart":
                SearchForStart.setOpacity(opacity);
                break;
            case "directionsButton":
                directionsButton.setOpacity(opacity);
                break;
            case "toHTML":
                toHTML.setOpacity(opacity);
                break;
            case "btn_map03":
                if((opacity + btn_map03.getOpacity()) > 1){
                    opacity = 1;
                }else if((opacity + btn_map03.getOpacity() < .5)){
                    opacity = .5;
                }
                btn_map03.setOpacity(btn_map03.getOpacity()+opacity);
                break;
            case "btn_map02":
                if((opacity + btn_map02.getOpacity()) > 1){
                    opacity = 1;
                }else if((opacity + btn_map02.getOpacity() < .5)){
                    opacity = .5;
                }
                btn_map02.setOpacity(btn_map02.getOpacity()+opacity);
                break;
            case "btn_map01":
                if((opacity + btn_map01.getOpacity()) > 1){
                    opacity = 1;
                }else if((opacity + btn_map01.getOpacity() < .5)){
                    opacity = .5;
                }
                btn_map01.setOpacity(btn_map01.getOpacity()+opacity);
                break;
            case "btn_mapG":
                if((opacity + btn_mapG.getOpacity()) > 1){
                    opacity = 1;
                }else if((opacity + btn_mapG.getOpacity() < .5)){
                    opacity = .5;
                }
                btn_mapG.setOpacity(btn_mapG.getOpacity()+opacity);
                break;
            case "btn_mapL1":
                if((opacity + btn_mapL1.getOpacity()) > 1){
                    opacity = 1;
                }else if((opacity + btn_mapL1.getOpacity() < .5)){
                    opacity = .5;
                }
                btn_mapL1.setOpacity(btn_mapL1.getOpacity()+opacity);
                break;
            case "btn_mapL2":
                if((opacity + btn_mapL2.getOpacity()) > 1){
                    opacity = 1;
                }else if((opacity + btn_mapL2.getOpacity() < .5)){
                    opacity = .5;
                }
                btn_mapL2.setOpacity(btn_mapL2.getOpacity()+opacity);
                break;
        }
    }



    public void resize() {

        if (single.getZoom() <= 1) {
            single.setXTrans(0);
            single.setYTrans(0);
            if(openCloseFlag){
                openclose.setVisible(true);
            }
            if(openClose1Flag){
                openclose1.setVisible(true);
            }
            start.setVisible(true);
            end.setVisible(true);
        }
        gc.setScaleX(single.getZoom());
        gc.setScaleY(single.getZoom());
        gc.setTranslateX(single.getXTrans());
        gc.setTranslateY(single.getYTrans());

        currentMap.setScaleX(single.getZoom());
        currentMap.setScaleY(single.getZoom());

        currentMap.setTranslateX(single.getXTrans());
        currentMap.setTranslateY(single.getYTrans());

        openclose.setTranslateX(single.getXTrans());
        openclose.setTranslateY(single.getYTrans());

        openclose1.setTranslateX(single.getXTrans());
        openclose1.setTranslateY(single.getYTrans());

        start.setTranslateX(single.getXTrans());
        start.setTranslateY(single.getYTrans());

        end.setTranslateX(single.getXTrans());
        end.setTranslateY(single.getYTrans());

        single.setMapWidth(currentMap.getFitWidth());
        single.setMapHeight(currentMap.getFitHeight());
    }

    @FXML
    public void textToggle() {
        if (textToggle.isSelected()) {
            textTogglePane.setVisible(true);
        } else {
            textTogglePane.setVisible(false);
        }
    }

    @FXML
    public void changeText(){
        SearchNodeID.setText(SearchOptions.getValue().split(":")[0].trim());
    }

    public int pathAnimationSpeed() {
        speed = 35000 / single.getPathAnimationSpeed();
        return speed;
    }

    public void setStyle(String styleName) {
        if(styleName == "contrast") {
            Main.patientScene.getRoot().getStylesheets().clear();
            Main.patientScene.getRoot().getStylesheets().add("view/stylesheets/UI2.css");
        } else if(styleName == "normal") {
            Main.patientScene.getRoot().getStylesheets().clear();
            Main.patientScene.getRoot().getStylesheets().add("view/stylesheets/UI.css");
        }
        Image m1 = mapImage.getLoadedMap("btn_map01");
        NodeObj n;
        selectFloorWithPath("1");
        currentMap.setImage(m1);
        btn_map01.setOpacity(1);
        single.getAlgorithm().setPathAlg(new astar());
        single.setMapWidth(currentMap.getFitWidth());
        single.setMapHeight(currentMap.getFitHeight());
        setKioskLoc(2460, 910);
        redraw();
        for (String s : Main.getImportantNodes()) {
            n = Main.nodeMap.getNodeObjByID(s);
            SearchOptions.getItems().add(n.node.getNodeID() + " : " + n.node.getLongName());
        }
        toolTipsInit();

    }
}
