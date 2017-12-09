package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Employee;

public class ServiceConfirmController {
    public ServiceSubSelectController servTypeCont;
    public Scene servTypeScene;
    Stage ServStage;
    private Employee assigned = new Employee();
    String[] passed;

    @FXML
    private JFXButton confirm, backBtn;

    @FXML
    private Label request, email, name, time, date, loc, info;

    @FXML
    private JFXTextArea notes;

//    @FXML
//    Button Backbtn;

    public void setServScene(Scene servTypeScene) {
        this.servTypeScene = servTypeScene;
    }

    public void setServStage(Stage servStage) {
        ServStage = servStage;
    }

    public void setAssigned(String info){
        int id  = Integer.parseInt(info.split(":")[1].trim());

        for(Employee e : Main.getEmployees()){
            if(id == e.getId()){
                assigned = e;
            }
        }
    }

    public void setServCont(ServiceSubSelectController servSubCont) {
        this.servTypeCont = servSubCont;
    }

    public void init(String pass){
        // passed info is in the following order:
        // service, location, date, time, specification
        passed = pass.split(" ");

        request.setText(request.getText() + " " + passed[0]);
        loc.setText(loc.getText() + " " + passed[1]);
        date.setText(date.getText() + " " + passed[2]);
        time.setText(time.getText() + " " + passed[3]);
        info.setText(info.getText() + " " + passed[4]);
        name.setText(name.getText() + " " + assigned.getFirstName() + " " + assigned.getLastName());
        email.setText(email.getText() + " " + assigned.getEmail());
        // set fields here for service information
    }

    @FXML
    void Back() {
        ServStage.setScene(servTypeScene);
    }

    @FXML
    void Confirm() {

    }
}
