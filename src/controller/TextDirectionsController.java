package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class TextDirectionsController {
    @FXML
    public JFXTextArea dirArea;

    @FXML
    private JFXButton closeButton;

    @FXML
    void close(){
        Stage stage = (Stage)closeButton.getScene().getWindow();
        stage.close();
    }
}
