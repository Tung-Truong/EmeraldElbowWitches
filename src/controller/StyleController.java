package controller;

import javafx.event.Event;
import javafx.scene.control.*;
import model.*;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import org.apache.derby.impl.services.bytecode.BCJava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;


public class StyleController extends Controller{

    @FXML
    private Button normalStyle, contrastStyle;


    @FXML
    public void setStyleNormal() {
        Main.patCont.setStyle("normal");
        Main.getCurrStage().setScene(Main.getPatientScene());
    }

    @FXML
    public void setStyleContrast() {
        Main.patCont.setStyle("contrast");
        Main.getCurrStage().setScene(Main.getPatientScene());
    }


    void getMap(Event e) {

    }
}
