package view.ui;


import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    void GetMap1() {
        MapDropDown.setText("nice")
        Image m1 =  new Image("file:src/view/media/basicMap.png");
        currentMap.setImage(m1);
    }

    @FXML
    void GetMap2() {
        Image m2 =  new Image("file:src/view/media/Shapiro.png");
        currentMap.setImage(m2);
    }
}
