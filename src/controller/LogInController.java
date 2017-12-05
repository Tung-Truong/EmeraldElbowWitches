package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import model.Employee;

public class LogInController {

        @FXML
        private JFXTextField UsernameField;

        @FXML
        private JFXPasswordField PasswordField;

        @FXML
        private JFXButton cancelButton;

        @FXML
        void cancel() {
                Stage stage = (Stage)cancelButton.getScene().getWindow();
                stage.close();
        }

        @FXML
        void SubmitRequest() {
            for (Employee e: Main.getEmployees()) {
                if(e.getUsername().equals(UsernameField.getText()) && e.getPassword().equals(PasswordField.getText())) {
                    Main.setCurrUser(e);
                    Main.getCurrStage().setScene(Main.getAdminScene());
                    cancel();
                    break;
                }
            }
        }

}
