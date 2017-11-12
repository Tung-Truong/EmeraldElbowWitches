package view.ui;


import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


import java.awt.*;

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
    void AddLine() {
 
        //System.out.println(mousePoint.x + " " + mousePoint.y);
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
