package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import model.BCrypt;
import model.Employee;

public class LogInController {

    @FXML
    private JFXTextField UsernameField;

    @FXML
    private JFXPasswordField PasswordField;

    @FXML
    private JFXButton cancelButton;

    public static boolean isFirstLogin = true;

    @FXML
    void cancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void SubmitRequest() {
        for (Employee e : Main.getEmployees()) {
            if (e.getUsername().equals(UsernameField.getText()) && BCrypt.checkpw(PasswordField.getText(), e.getPassword())) {
                Main.setCurrUser(e);
                Main.getCurrStage().setScene(Main.getAdminScene());
                if(isFirstLogin) {
                    Main.getAdminCont().startTimer();
                    isFirstLogin = false;
                }
                Main.getAdminCont().timeoutCounter = 0;
                cancel();
                break;
            }
        }
    }
}
