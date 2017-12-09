package controller;

import com.jfoenix.controls.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static java.lang.Thread.sleep;


public class AdminController extends Controller {

    @FXML
    private JFXButton serviceRequestBtn;

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

    @FXML
    private Pane edgeInfoPane;

    @FXML
    private JFXTextField weightField;

    @FXML
    private JFXTextField nodeBField;

    @FXML
    private JFXTextField nodeAField;

    @FXML
    private JFXButton astarBtn;

    @FXML
    private JFXButton depthBtn;

    @FXML
    private JFXButton breadthBtn;

    @FXML
    private JFXButton dijkstrasBtn;

    @FXML
    private JFXButton beamBtn;

    @FXML
    private JFXButton bestBtn;

    private NodeObj nodeA = null;
    private SingleController single = SingleController.getController();

    public void initialize(){
        Image m1 = single.getMapImage().getLoadedMap("btn_map01");
        currentMap.setImage(m1);
        single.getAlgorithm().setPathAlg(new astar());
        single.setMapWidth(currentMap.getFitWidth());
        single.setMapHeight(currentMap.getFitHeight());
        astarBtn.setStyle("-fx-background-color: #4286f4");
        redraw();
    }


    private void redraw(){
        nodeInfoPane.setVisible(false);
        edgeInfoPane.setVisible(false);
        serviceRequestBtn.setVisible(false);
        nodeIDField.clear();
        xLocField.clear();
        yLocField.clear();
        floorField.clear();
        nodeTypeField.clear();
        teamField.clear();
        buildingField.clear();
        longNameField.clear();
        shortNameField.clear();
        weightField.clear();
        nodeAField.clear();
        nodeBField.clear();
        if(single.getGc() == null)
            single.setGc(gc.getGraphicsContext2D());
        single.getGc().clearRect(0, 0, currentMap.getFitWidth(), currentMap.getFitHeight());
        single.getGc().setLineWidth(2);
        single.getGc().setFill(Color.BLACK);
        for(NodeObj n: Main.getNodeMap().getFilteredNodes()){
            for(EdgeObj e: n.getListOfEdgeObjs()){
                single.getGc().setStroke(Color.BLUE);
                single.getGc().strokeLine(e.getNodeA().node.getxLoc()*single.getMapWidth()/5000,
                        e.getNodeA().node.getyLoc()*single.getMapHeight()/3400,
                        e.getNodeB().node.getxLoc()*single.getMapWidth()/5000,
                        e.getNodeB().node.getyLoc()*single.getMapHeight()/3400);
            }

        }

        for(NodeObj n: Main.getNodeMap().getFilteredNodes()){
            single.getGc().setFill(Color.BLACK);
            single.getGc().fillOval(n.node.getxLoc()*single.getMapWidth()/5000 - 5,
                    n.node.getyLoc()*single.getMapHeight()/3400 - 5,
                    10,
                    10);
            single.getGc().setFill(Color.LIGHTBLUE);
            single.getGc().fillOval(n.node.getxLoc()*single.getMapWidth()/5000 - 4,
                    n.node.getyLoc()*single.getMapHeight()/3400 - 4,
                    8,
                    8);
        }
        single.getGc().setFill(Color.BLUE);
    }

    @FXML
    void changeMap(Event e){
        Main.controllers.updateAllMaps(e);
    }


    void getMap(Event e) {
        btn_mapL2.setStyle("-fx-background-color:  #4286f4");
        btn_mapL1.setStyle("-fx-background-color:  #4286f4");
        btn_mapG.setStyle("-fx-background-color:  #4286f4");
        btn_map01.setStyle("-fx-background-color:  #4286f4");
        btn_map02.setStyle("-fx-background-color:  #4286f4");
        btn_map03.setStyle("-fx-background-color:  #4286f4");

        String clickedID = ((JFXButton) e.getSource()).getId();
        switch (clickedID) {
            case "btn_mapL2":
                Main.getNodeMap().setCurrentFloor("L2");
                btn_mapL2.setStyle("-fx-background-color:  #1b5cc4");

                break;
            case "btn_mapL1":
                Main.getNodeMap().setCurrentFloor("L1");
                btn_mapL1.setStyle("-fx-background-color:  #1b5cc4");

                break;
            case "btn_mapG":
                Main.getNodeMap().setCurrentFloor("G");
                btn_mapG.setStyle("-fx-background-color:  #1b5cc4");

                break;
            case "btn_map01":
                Main.getNodeMap().setCurrentFloor("1");
                btn_map01.setStyle("-fx-background-color:  #1b5cc4");

                break;
            case "btn_map02":
                Main.getNodeMap().setCurrentFloor("2");
                btn_map02.setStyle("-fx-background-color:  #1b5cc4");

                break;
            case "btn_map03":
                Main.getNodeMap().setCurrentFloor("3");
                btn_map03.setStyle("-fx-background-color:  #1b5cc4");
                break;

        }
        single.getGc().clearRect(0, 0, currentMap.getFitWidth(), currentMap.getFitHeight());
        Image map = single.getMapImage().getLoadedMap(clickedID);
        this.currentMap.setImage(map);
        redraw();
    }


    @FXML
    void clickHandler(MouseEvent event) throws InvalidNodeException {
        int mousex = (int)((5000 * event.getX()) / single.getMapWidth());
        int mousey = (int)((3400 * event.getY()) / single.getMapHeight());
        if((event.getButton() == MouseButton.SECONDARY) || ((event.getButton() == MouseButton.PRIMARY) && (event.isControlDown()))){
            redraw();
            createNewNode(mousex,mousey);
        }else if(event.getButton() == MouseButton.PRIMARY){
            if(nodeA == null){
                selectNodeA(event);
            }else{
                if(nodeA.getNode().getNodeID().equals(Main.getNodeMap().getNearestNeighborFilter(mousex,mousey).getNode().getNodeID())){
                    edgeInfoPane.setVisible(false);
                    selectNode(mousex,mousey);
                    nodeA = null;
                }else {
                    nodeInfoPane.setVisible(false);
                    selectEdge(event);
                    nodeA = null;

                }
            }
        }
    }

    @FXML
    private void createNewNode(int mousex, int mousey){
        nodeIDField.setText("");
        xLocField.setText(mousex + "");
        yLocField.setText(mousey + "");
        floorField.setText(Main.getNodeMap().currentFloor);
        teamField.setText("Team E");
        //now make all fields visible with .setVisibility(true)
        nodeInfoPane.setVisible(true);
    }

    @FXML
    private void selectNode(int mousex, int mousey){
        try {
            NodeObj editNode = Main.getNodeMap().getNearestNeighborFilter(mousex, mousey);

            //set each of the fields to their values
            nodeIDField.setText(editNode.getNode().getNodeID());
            xLocField.setText(editNode.getNode().getxLoc() + "");
            yLocField.setText(editNode.getNode().getyLoc() + "");
            floorField.setText(editNode.getNode().getFloor());
            nodeTypeField.setText(editNode.getNode().getNodeType());
            teamField.setText(editNode.getNode().getTeam());
            buildingField.setText(editNode.getNode().getBuilding());
            longNameField.setText(editNode.getNode().getLongName());
            shortNameField.setText(editNode.getNode().getShortName());

            //now make all fields visible with .setVisibility(true)
            nodeInfoPane.setVisible(true);
            serviceRequestBtn.setVisible(true);

        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void removeNode(){
        String delNodeID = nodeIDField.getText();
        Node delNode = new Node(delNodeID); //WARNING: THIS CREATES A Node WITH ONLY AN ID, NO OTHER FIELDS POPULATED. ONLY ATTEMPT TO ACCESS nodeID.
        NodeObj delNodeObj = new NodeObj(delNode);
        Main.getNodeMap().removeNode(delNodeObj);
        redraw();
    }

    @FXML
    void addEditNode(){
        String xLoc = xLocField.getText();
        String yLoc = yLocField.getText();
        String floor = floorField.getText();
        String building = buildingField.getText();
        String nodeType = nodeTypeField.getText();
        String longName = longNameField.getText();
        String shortName = shortNameField.getText();
        String team = teamField.getText();
        String nodeID = nodeIDField.getText();
        Node modNode = new Node(xLoc, yLoc, floor, building, nodeType, longName, shortName, team, nodeID);
        NodeObj modNodeObj = new NodeObj(modNode);
        try {
            Main.getNodeMap().addEditNode(modNodeObj);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        redraw();
    }

    @FXML
    private void selectNodeA(MouseEvent event){
        nodeInfoPane.setVisible(false);
        int mousex = (int)((5000 * event.getX()) / single.getMapWidth());
        int mousey = (int)((3400 * event.getY()) / single.getMapHeight());
        try {
            nodeA = Main.getNodeMap().getNearestNeighborFilter(mousex, mousey);
            nodeAField.setText(nodeA.getNode().getNodeID());
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void selectEdge(MouseEvent event){
        System.out.println("DRAG RELEASED");
        int mousex = (int)((5000 * event.getX()) / single.getMapWidth());
        int mousey = (int)((3400 * event.getY()) / single.getMapHeight());
        try {
            String nodeB = Main.getNodeMap().getNearestNeighborFilter(mousex,mousey).getNode().getNodeID();
            nodeBField.setText(nodeB);
            if (nodeA.getEdgeObj(Main.getNodeMap().getNearestNeighborFilter(mousex, mousey)) != null) {
                weightField.setText(nodeA.getEdgeObj(Main.getNodeMap().getNearestNeighborFilter(mousex, mousey)).getWeight() + "");
            }else{
                weightField.setText("1");
            }
            edgeInfoPane.setVisible(true);
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
    }

    @FXML
        /*
         *UpdateBorderButton is called when adding or editing.
         * It updates the node map, and redraws the screen.
         */
    void addEditEdge() {
        String NIDA = nodeAField.getText();
        String NIDB = nodeBField.getText();
        int eWeight = Integer.parseInt(weightField.getText());
        EdgeObj edgeAB = Main.getNodeMap().addEditEdge(NIDA, NIDB, eWeight);
        redraw();
        single.getGc().setStroke(Color.RED);
        single.getGc().strokeLine(edgeAB.getNodeA().node.getxLoc()*single.getMapWidth()/5000,
                edgeAB.getNodeA().node.getyLoc()*single.getMapHeight()/3400,
                edgeAB.getNodeB().node.getxLoc()*single.getMapWidth()/5000,
                edgeAB.getNodeB().node.getyLoc()*single.getMapHeight()/3400);
    }

    /*
     * DeleteBorderButton is called when deleting an edge.
     * Removes edge from map, and redraws screen.
     */
    @FXML
    void removeEdge() {
        String nodeA = nodeAField.getText();
        String nodeB = nodeBField.getText();
        Main.getNodeMap().deleteEdge(nodeA,nodeB);
        redraw();
    }

    @FXML
    void pathingHoverStart(MouseEvent event){
        String hoveredID = ((JFXButton)event.getSource()).getId();
        opacHandler(1, hoveredID);
    }

    @FXML
    void pathingHoverStop(MouseEvent event){
        String hoveredID = ((JFXButton)event.getSource()).getId();
        opacHandler(.5, hoveredID);
    }

    private void opacHandler(double opacity, String hoveredID){
        switch (hoveredID){
            case "astarBtn":
                astarBtn.setOpacity(opacity);
                break;
            case "depthBtn":
                depthBtn.setOpacity(opacity);
                break;
            case "breadthBtn":
                breadthBtn.setOpacity(opacity);
                break;
            case "dijkstrasBtn":
                dijkstrasBtn.setOpacity(opacity);
                break;
            case "beamBtn":
                beamBtn.setOpacity(opacity);
                break;
            case "bestBtn":
                bestBtn.setOpacity(opacity);
                break;
        }
    }

    @FXML
    void selectPathAlg(Event event){
        String clickedID = ((JFXButton)event.getSource()).getId();
        resetPathBtns();
        switch (clickedID){
            case "astarBtn":
                astarBtn.setStyle("-fx-background-color: #4286f4");
                this.single.getAlgorithm().setPathAlg(new astar());
                break;
            case "depthBtn":
                depthBtn.setStyle("-fx-background-color:  #4286f4");
                this.single.getAlgorithm().setPathAlg(new DepthFirst());
                break;
            case "breadthBtn":
                breadthBtn.setStyle("-fx-background-color:  #4286f4");
                this.single.getAlgorithm().setPathAlg(new BreadthFirst());
                break;
            case "dijkstrasBtn":
                dijkstrasBtn.setStyle("-fx-background-color:  #4286f4");
                this.single.getAlgorithm().setPathAlg(new Dijkstras());
                break;
            case "beamBtn":
                beamBtn.setStyle("-fx-background-color:  #4286f4");
                this.single.getAlgorithm().setPathAlg(new BeamFirst());
                break;
            case "bestBtn":
                bestBtn.setStyle("-fx-background-color:  #4286f4");
                this.single.getAlgorithm().setPathAlg(new BestFirst());
                break;
        }
    }

    private void resetPathBtns(){
        astarBtn.setStyle("-fx-background-color: #21a047");
        depthBtn.setStyle("-fx-background-color:  #21a047");
        breadthBtn.setStyle("-fx-background-color:  #21a047");
        dijkstrasBtn.setStyle("-fx-background-color:  #21a047");
        beamBtn.setStyle("-fx-background-color:  #21a047");
        bestBtn.setStyle("-fx-background-color:  #21a047");
    }


    @FXML
    void adminLogout(){
        Main.getCurrStage().setScene(Main.getPatientScene());
    }

    @FXML
    void makeServiceRequest() throws IOException {
        FXMLLoader servContLoad = new FXMLLoader(getClass().getClassLoader().getResource("view/ui/ServiceRequest.fxml"));
        Parent root = servContLoad.load();
        ServiceController servCont = servContLoad.getController();
        Stage servStage = new Stage();
        servStage.setTitle("Service Request");
        servStage.setScene(new Scene(root, 243, 446));
        servCont.servLocField.setText(nodeIDField.getText());
        servStage.show();
    }

    @FXML
    void EditEmployees() throws IOException {
        FXMLLoader servContLoad = new FXMLLoader(getClass().getClassLoader().getResource("view/ui/ServiceEdit.fxml"));
        Parent root = servContLoad.load();
        Stage servStage = new Stage();
        servStage.setTitle("Service Request");
        servStage.setScene(new Scene(root, single.getMapWidth(), single.getMapHeight()));
        servStage.show();
    }

    @FXML
    void Zin() {
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


}
