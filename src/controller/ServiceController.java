package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class ServiceController {

    @FXML
    public JFXTextField servLocField;

    @FXML
    private JFXComboBox<String> serviceList;

    @FXML
    private JFXComboBox<String> employeeList;

    @FXML
    private JFXTextField servNoteField;

    @FXML
    private JFXSlider urgencyMeter;

    @FXML
    private JFXButton cancelButton;

    public void initialize(){
        serviceList.getItems().addAll("Doggos", "Interpreter");
    }

    @FXML
    void close(){
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        stage.close();
    }

}
