package controller;


import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import model.InvalidNodeException;
import model.NodeObj;
import model.astar;
import sun.plugin.javascript.navig4.Layer;

import java.util.ArrayList;

public class UI_v1 {

    @FXML
    private MenuItem Map1;

    @FXML
    private ImageView currentMap;

    @FXML
    private MenuButton MapDropDown;

    @FXML
    private MenuItem Map2;

    @FXML
    private AnchorPane mapContainer;

    @FXML
    private Layer lineLayer;

    private static class Mpoint {
        double x, y;
    }

    @FXML
    void AddLine(MouseEvent event) throws InvalidNodeException {
        //getX/stageWidth = w/5000
        //5000*getX/stageWidth
        Scene currScene = model.Main.getCurrScene();
        double mapWidth = currentMap.getFitWidth();
        double mapHeight = currentMap.getFitHeight();
        //convert click resolution to map ratio
        System.out.println((5000*event.getX())/mapWidth + " " + (3400*event.getY())/mapHeight);
        //far left stair node
        System.out.println("Far left stair coords: " + 2190 + " " + 910);
        //Rehab center
        System.out.println("Rehab center coords: " + 2790 + " " + 1380);


        astar newpathGen = new astar();
        //get node that corr. to click
        NodeObj goal = model.Main.getNodeMap().getNearestNeighbor
                ((int)Math.floor((5000*event.getX())/mapWidth), (int)Math.floor((3400*event.getY())/mapHeight));
        //getStart
        NodeObj Kiosk = model.Main.getKiosk();
        ArrayList<NodeObj> path = null;
        if(newpathGen.Pathfind(Kiosk,goal))
            path = newpathGen.getGenPath();
        else
            throw new InvalidNodeException("this is cot accessable with the current map");
        int i;


        /*currScene.setFill(Color.BLUE);
        Line path = new Line(2190, 910, 2790,1380);
        path.setFill(Color.BLUE);
        path.setStrokeWidth(50);
        path.toFront();
        mapContainer.getChildren().add(path);
        currentMap.toBack();
        model.Main.getCurrStage().show();*/
    }

    @FXML
    void GetMap1() {
        MapDropDown.setText("75 Francis Floor 2 Center");
        Image m1 =  new Image("file:src/view/media/basicMap.png");
        currentMap.setImage(m1);
    }

    @FXML
    void GetMap2() {
        MapDropDown.setText("Shapiro Building Floor 2");
        Image m2 =  new Image("file:src/view/media/Shapiro.png");
        currentMap.setImage(m2);
    }
}
