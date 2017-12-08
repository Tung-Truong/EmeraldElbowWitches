package controller;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import model.ServiceRequest;

public class TextDirectionsController {
    @FXML
    public JFXTextArea dirArea;

    @FXML
    private JFXToggleButton sendText, sendEmail;

    @FXML
    private JFXTextField emailField;

    @FXML
    private JFXButton closeButton;

    ServiceRequest directions;

    @FXML
    void close() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void sendMessage() {
        boolean messageSent = false;
        if (sendEmail.isSelected()) {
            directions = new ServiceRequest();
            directions.setAccountTo(emailField.getText());
            directions.setMessageText("To get to your destination, please follow these steps:\n" + dirArea.getText());
            directions.setMessageHeader("Step by Step Directions");
            directions.sendEmailServiceRequest();
            messageSent = true;
        } else if (sendText.isSelected()) {
            messageSent = true;
        }
        if (messageSent) {
            close();
        }
    }
}
