package HealthAPI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LogInControllerHealth {


        @FXML
        private JFXTextField UsernameField;

        @FXML
        private JFXPasswordField PasswordField;

        @FXML
        private JFXButton cancelButton;

    public void initalize(){
        UsernameField.getStyleClass().add("jfx-text-field");
        cancelButton.getStyleClass().add("jfx-button");
        PasswordField.getStyleClass().add("jfx-password-field");
    }

        @FXML
        void cancel() {
                Stage stage = (Stage)cancelButton.getScene().getWindow();
                stage.close();
        }

        @FXML
        void SubmitRequest() throws IOException, SQLException {
            if((UsernameField.getText().trim().equals(HealthCareRun.getUsername()) &&
                    PasswordField.getText().trim().equals(HealthCareRun.getPassword())) ||
                (UsernameField.getText().trim().equals("admin") &&
                    PasswordField.getText().trim().equals("admin")) ||
                (UsernameField.getText().trim().equals("staff") &&
                    PasswordField.getText().trim().equals("staff"))){
                FXMLLoader HealthCont2Load = new FXMLLoader(getClass().getClassLoader().getResource("view/ui/AdminHealthcare.fxml"));
                Parent root = HealthCont2Load.load();
                root.getStylesheets().add(HealthCareRun.getPath());
                HealthcareAdminController HealthCont2 = HealthCont2Load.getController();
                Stage healthStage2 = new Stage();
                healthStage2.setOnCloseRequest( event -> {
                    try {
                        WriteRequests2.runRequests();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } );
                healthStage2.setTitle("Admin Service Request");
                healthStage2.setScene(new Scene(root, HealthCareRun.getWidth(), HealthCareRun.getHeight()));
                healthStage2.show();
                if(HealthCareRun.xPosition>=0&& HealthCareRun.yPosition>=0) {
                    healthStage2.setX(HealthCareRun.getxPosition());
                    healthStage2.setY(HealthCareRun.getyPosition());
                }
                    cancel();

            }
            /*for (Employee e: Main.getEmployees()) {
                if(e.getUsername().equals(UsernameField.getText()) && e.getPassword().equals(PasswordField.getText())) {
                    Main.setCurrUser(e);
                    Main.getCurrStage().setScene(Main.getAdminScene());
                    cancel();
                    break;
                }
            }*/
        }

}
