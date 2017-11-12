package controller;


import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class UI_v1 {

    @FXML
    private MenuItem Map1;

    @FXML
    private ImageView currentMap;

    @FXML
    private MenuButton MapDropDown;

    @FXML
    private MenuItem Map2;

    private static class Mpoint {
        double x, y;
    }

    @FXML
    void AddLine(MouseEvent event) {

        //getX/stageWidth = w/5000
        //5000*getX/stageWidth
        Scene currScene = model.Main.getCurrScene();
        double mapWidth = currentMap.getFitWidth();
        double mapHeight = currentMap.getFitHeight();
        System.out.println((5000*event.getX())/mapWidth + " " + (3400*event.getY())/mapHeight);
        //far left stair node
        System.out.println("Far left stair coords: " + 2190 + " " + 910);
        //Rehab center
        System.out.println("Rehab center coords: " + 2790 + " " + 1380);


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
