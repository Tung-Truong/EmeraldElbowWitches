package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ServiceConfirmController {
    public ServiceSubSelectController servTypeCont;
    public Scene servTypeScene;
    Stage ServStage;


    @FXML
    Button Backbtn;

    public void initialize(){
        // set fields here for service information
    }

    public void setServScene(Scene servTypeScene) {
        this.servTypeScene = servTypeScene;
    }

    public void setServStage(Stage servStage) {
        ServStage = servStage;
    }

    public void setServCont(ServiceSubSelectController servSubCont) {
        this.servTypeCont = servSubCont;
    }


    /*@FXML
    private JFXButton confirm, back;

    @FXML
    private Label request, email, name, time, date, location, info;

    @FXML
    private JFXTextArea notes;*/



    @FXML
    void Back() {
        ServStage.setScene(servTypeScene);
    }

    @FXML
    void Confirm() {

    }
}
