package controller;

//import Healthcare.HealthCareRun;
//import Healthcare.ServiceException;

import com.jfoenix.controls.*;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.tools.Tool;


public class PatientController extends Controller {

    @FXML
    private Pane window;

    @FXML
    private JFXButton directionsButton, SearchForNode, btn_map03, btn_map02, btn_map01, btn_mapG, btn_mapL1, btn_mapL2,
            SearchPath, Tleft, Tright, Tdown, Tup, toHTML, loginButton;

    @FXML
    private JFXTogglePane textTogglePane;

    @FXML
    private JFXToggleButton textToggle;

    @FXML
    private JFXSlider zoomBar;

    @FXML
    private JFXComboBox<String> SearchOptions;

    @FXML
    private JFXTextField SearchNodeID;

    @FXML
    private Canvas gc;

    @FXML
    public ImageView currentMap;

    @FXML
    private JFXTextArea toggleTextArea;

    @FXML
    private Label floor3Label, floor2Label, floor1Label, floorGLabel, floorL1Label, floorL2Label;


    public static TextDirections textDirections = new TextDirections();
    ArrayList<NodeObj> currPath = null;
    NodeObj goal = null;
    ArrayList<String> Floors;
    double startX;
    double startY;
    ArrayList<NodeObj> strPath;
    Animation oldAnimation;
    SingleController single = SingleController.getController();
    private ImageLoader mapImage = new ImageLoader();
    private GraphicsContext gc1 = null;

    public void initialize() {
        Image m1 = mapImage.getLoadedMap("btn_map01");
        selectFloorWithPath("1");
        currentMap.setImage(m1);
        btn_map01.setOpacity(1);
        single.getAlgorithm().setPathAlg(new astar());
        single.setMapWidth(currentMap.getFitWidth());
        single.setMapHeight(currentMap.getFitHeight());
        setKioskLoc(2460, 910);
        redraw();
        for (NodeObj n : Main.getNodeMap().getNodes()) {
            if (!n.node.getNodeType().equals("HALL")) {
                SearchOptions.getItems().add(n.node.getNodeID() + " : " + n.node.getLongName());
            }
        }
        toolTipsInit();
    }

    private void toolTipsInit(){

        //about us
        final Tooltip aboutUs= new Tooltip();
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
        //TODO: add a button/icon to make this text appear

    }

    private void redraw() {
        if (gc1 == null)
            gc1 = gc.getGraphicsContext2D();
        gc1.clearRect(0, 0, currentMap.getFitWidth(), currentMap.getFitHeight());
    }

    @FXML
    void changeMap(Event e) {
        resetFloorButtons();
        oldAnimation.stop();
        Main.controllers.updateAllMaps(e);
        if (currPath != null) {
            if (oldAnimation != null) {
                oldAnimation.stop();
                gc1.clearRect(0, 0, single.getMapWidth(), single.getMapHeight());
                redraw();
                gc.getGraphicsContext2D().setStroke(Color.BLUE);
            }
            Animation animation = createPathAnimation(convertPath(pathFloorFilter()), Duration.millis(4000));
            animation.play();
            oldAnimation = animation;
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
                    gc1.fillOval(newSearchNode.node.getxLoc() * single.getMapWidth() / 5000 - 5,
                            newSearchNode.node.getyLoc() * single.getMapHeight() / 3400 - 5,
                            10,
                            10);
                } catch (InvalidNodeException exc) {
                    exc.printStackTrace();
                }
                break;
        }

        if (!clickedID.equals("SearchForNode")) {
            Image map = mapImage.getLoadedMap(clickedID);
            this.currentMap.setImage(map);
            redraw();
        }
    }

    void clearChosenFloor() {
        btn_mapL2.setOpacity(.5);
        btn_mapL2.setStyle("-fx-background-color:   #4286f4");
        btn_mapL1.setOpacity(.5);
        btn_mapL1.setStyle("-fx-background-color:   #4286f4");
        btn_mapG.setOpacity(.5);
        btn_mapG.setStyle("-fx-background-color:   #4286f4");
        btn_map01.setOpacity(.5);
        btn_map01.setStyle("-fx-background-color:   #4286f4");
        btn_map02.setOpacity(.5);
        btn_map02.setStyle("-fx-background-color:   #4286f4");
        btn_map03.setOpacity(.5);
        btn_map03.setStyle("-fx-background-color:   #4286f4");
        if (Floors != null) {
            /*for(int i = 0; i < Floors.size(); i++){
                selectFloor(Floors.get(i), i+1);
            }*/
            selectFloorWithPath(Main.getNodeMap().currentFloor);
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
                btn_mapL2.setStyle("-fx-background-color:  #1b5cc4");
                break;
            case "L1":
                btn_mapL1.setOpacity(.7);
                btn_mapL1.setStyle("-fx-background-color:  #1b5cc4");

                break;
            case "G":
                btn_mapG.setOpacity(.7);
                btn_mapG.setStyle("-fx-background-color:  #1b5cc4");

                break;
            case "1":
                btn_map01.setOpacity(.7);
                btn_map01.setStyle("-fx-background-color:  #1b5cc4");

                break;
            case "2":
                btn_map02.setOpacity(.7);
                btn_map02.setStyle("-fx-background-color:  #1b5cc4");

                break;
            case "3":
                btn_map03.setOpacity(.7);
                btn_map03.setStyle("-fx-background-color:  #1b5cc4");

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
                    gc1.strokeLine(n.node.getxLoc() * single.getMapWidth() / 5000,
                            n.node.getyLoc() * single.getMapHeight() / 3400,
                            tempDraw.node.getxLoc() * single.getMapWidth() / 5000,
                            tempDraw.node.getyLoc() * single.getMapHeight() / 3400);
                } else if (n.node.getFloor().equals(Main.getNodeMap().currentFloor) && !tempDraw.node.getFloor().equals(n.node.getFloor())) {
                    gc1.setFill(Color.BLACK);
                    if (n.node.getFloor().equals(Main.getNodeMap().currentFloor)) {
                        gc1.fillOval(n.node.getxLoc() * single.mapWidth / 5000 - 5,
                                n.node.getyLoc() * single.mapHeight / 3400 - 5,
                                10,
                                10);
                    }

                } else if (!n.node.getFloor().equals(Main.getNodeMap().currentFloor) && !tempDraw.node.getFloor().equals(n.node.getFloor())) {
                    gc1.setFill(Color.GOLD);
                    if (tempDraw.node.getFloor().equals(Main.getNodeMap().currentFloor)) {
                        gc1.fillOval(n.node.getxLoc() * single.mapWidth / 5000 - 5,
                                n.node.getyLoc() * single.mapHeight / 3400 - 5,
                                10,
                                10);
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
            gc1.fillOval(goal.node.getxLoc() * single.getMapWidth() / 5000 - 5,
                    goal.node.getyLoc() * single.getMapHeight() / 3400 - 5,
                    10,
                    10);
        }
        if (Main.getKiosk().node.getFloor().equals(Main.getNodeMap().currentFloor)) {
            gc1.setFill(Color.DARKGREEN);
            gc1.fillOval(Main.getKiosk().node.getxLoc() * single.getMapWidth() / 5000 - 5,
                    Main.getKiosk().node.getyLoc() * single.getMapHeight() / 3400 - 5,
                    10,
                    10);
        }
        gc1.setFill(Color.YELLOW);
        clearChosenFloor();
        System.out.println(Floors.toString());
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
        double mousex = (5000 * event.getX()) / single.getMapWidth();
        double mousey = (3400 * event.getY()) / single.getMapHeight();
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
                gc1.clearRect(0, 0, single.getMapWidth(), single.getMapHeight());
                redraw();
                gc.getGraphicsContext2D().setStroke(Color.BLUE);
            }
            Animation animation = createPathAnimation(convertPath(pathFloorFilter()), Duration.millis(4000));
            animation.play();
            oldAnimation = animation;
            DrawCurrentFloorPath();
        }
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
                        gc.strokeLine(oldLocation.x * single.getMapWidth() / 5000, oldLocation.y * single.getMapHeight() / 3400, x * single.getMapWidth() / 5000, y * single.getMapHeight() / 3400);
                        oldLocation.x = x;
                        oldLocation.y = y;
                        oldOldLocation = oldLocation;
                    } else if (oldOldLocation != null && oldOldLocation.x - oldLocation.x < 10 && oldOldLocation.y - oldLocation.y < 10) {
                        gc.strokeLine(oldOldLocation.x * single.getMapWidth() / 5000, oldOldLocation.y * single.getMapHeight() / 3400, oldLocation.x * single.getMapWidth() / 5000, oldLocation.y * single.getMapHeight() / 3400);
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

    ArrayList<NodeObj> pathFloorFilter() {
        ArrayList<NodeObj> filteredPath = new ArrayList<>();
        for (NodeObj n : currPath) {
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
        double mousex = (5000 * event.getX()) / single.getMapWidth();
        double mousey = (3400 * event.getY()) / single.getMapHeight();
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
    void UpdateSearch() {
        SearchOptions.getItems().clear();

        ArrayList<NodeObj> SearchNodes = new ArrayList<>();
        String search = SearchNodeID.getText();
        for (NodeObj n : Main.getNodeMap().getNodes()) {
            if (search.length() > 2 && (n.node.getLongName().contains(search) || n.node.getNodeID().contains(search))) {
                SearchNodes.add(n);
                SearchOptions.getItems().add(n.node.getNodeID() + " : " + n.node.getLongName());
            } else if (search.length() == 0) {
                if (!n.node.getNodeType().equals("HALL")) {
                    SearchOptions.getItems().add(n.node.getNodeID() + " : " + n.node.getLongName());
                }
            }
        }
    }

    @FXML
    void setSearchNode(Event e) {
        if (((JFXButton) e.getSource()).getId().equals("SearchForNode")) {
            try {
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
                    gc1.fillOval(newSearchNode.node.getxLoc() * single.getMapWidth() / 5000 - 5,
                            newSearchNode.node.getyLoc() * single.getMapHeight() / 3400 - 5,
                            10,
                            10);
                    SearchPath.setVisible(true);
                    SearchPath.setText(searchNewNodeID);
                    SearchPath.setLayoutX(newSearchNode.node.getxLoc() * single.getMapWidth() / 5000);
                    SearchPath.setLayoutY(newSearchNode.node.getyLoc() * single.getMapHeight() / 3400);
                } catch (InvalidNodeException exc) {
                    exc.printStackTrace();
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
                    gc1.clearRect(0, 0, single.getMapWidth(), single.getMapHeight());
                    redraw();
                    gc.getGraphicsContext2D().setStroke(Color.BLUE);
                }
                Animation animation = createPathAnimation(convertPath(pathFloorFilter()), Duration.millis(4000));
                animation.play();
                oldAnimation = animation;
                DrawCurrentFloorPath();
            }
        }
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

    @FXML
    void Zin() {
        SearchPath.setVisible(false);
        System.out.println(zoomBar.getValue());
        single.setZoom(zoomBar.getValue());
        resize();

    }

    @FXML
    void Tleft() {
        single.addX((int) (200.0 / single.getZoom()));
        resize();
    }

    @FXML
    void Tright() {
        single.subX((int) (200.0 / single.getZoom()));
        resize();
    }

    @FXML
    void Tup() {
        single.addY((int) (160.0 / single.getZoom()));
        resize();
    }

    @FXML
    void Tdown() {
        single.subY((int) (160.0 / single.getZoom()));
        resize();
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
            case "directionsButton":
                directionsButton.setOpacity(opacity);
                break;
            case "toHTML":
                toHTML.setOpacity(opacity);
                break;
        }
    }


    public void resize() {
        if (single.getZoom() <= 1) {
            single.setXTrans(0);
            single.setYTrans(0);
        }
        gc.setScaleX(single.getZoom());
        gc.setScaleY(single.getZoom());
        gc.setTranslateX(single.getXTrans());
        gc.setTranslateY(single.getYTrans());
        currentMap.setScaleX(single.getZoom());
        currentMap.setScaleY(single.getZoom());
        currentMap.setTranslateX(single.getXTrans());
        currentMap.setTranslateY(single.getYTrans());
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
}
