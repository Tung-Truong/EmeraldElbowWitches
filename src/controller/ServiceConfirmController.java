package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ServiceConfirmController {
    @FXML
    private JFXButton confirm, back;

    @FXML
    private Label request, email, name, time, date, location, info;

    @FXML
    private JFXTextArea notes;

    public void initialize(){
        // set fields here for service information
    }
}
