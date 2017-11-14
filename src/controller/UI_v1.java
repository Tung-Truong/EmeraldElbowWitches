package controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.*;
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
    //This is where pathfinding is currently
    //this shall be renamed later to something better
    void addLine(MouseEvent event) throws InvalidNodeException {
        //getX/stageWidth = w/5000
        //5000*getX/stageWidth

        //get the mouses location and convert that to the corrected map coordinates for the original image
        Scene currScene = model.Main.getCurrScene();
        double mapWidth = currentMap.getFitWidth();
        double mapHeight = currentMap.getFitHeight();

        double mousex = (5000*event.getX())/mapWidth;
        double mousey = (3400*event.getY())/mapHeight;
        //Print to confirm

        //convert click resolution to map ratio
        System.out.println((5000*event.getX())/mapWidth + " " + (3400*event.getY())/mapHeight);
        //far left stair node
        System.out.println("Far left stair coords: " + 2190 + " " + 910);
        //Rehab center
        System.out.println("Rehab center coords: " + 2790 + " " + 1380);

        //create a new astar object
        astar newpathGen = new astar();

        //get node that corr. to click from ListOfNodeObjects made in main
        NodeObj goal = model.Main.getNodeMap().getNearestNeighbor
                ((int)Math.floor(mousex), (int)Math.floor(mousey));

        //getStart
        NodeObj Kiosk = model.Main.getKiosk();
        //set the path to null
        ArrayList<NodeObj> path = null;

        //try a*
        if(newpathGen.pathfind(Kiosk,goal))
            path = newpathGen.getGenPath();
        else
            throw new InvalidNodeException("this is not accessable with the current map");


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
        MapDropDown.setText("45 Francis Floor 1 Center");
        Image m1 =  new Image("file:src/view/media/basicMap.png");
        currentMap.setImage(m1);
        Main.getNodeMap().setCurrentBuilding("45 Francis");
        //need to set currentMap of ListOfNodeObjs to "
    }

    @FXML
    void GetMap2() {
        MapDropDown.setText("Shapiro Building Floor 2");
        Image m2 =  new Image("file:src/view/media/Shapiro.png");
        currentMap.setImage(m2);
        Main.getNodeMap().setCurrentBuilding("Shapiro Building");
    }
}
