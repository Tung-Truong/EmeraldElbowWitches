package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Employee;
import model.InterpreterService;
import model.JanitorService;
import model.ServiceRequest;

public class ServiceConfirmController {
    public ServiceSubSelectController servTypeCont;
    public Scene servTypeScene;
    Stage ServStage;
    private Employee assigned = new Employee();
    String[] passed;
    private ServiceRequest service;

    @FXML
    private JFXButton confirm, backBtn;

    @FXML
    private Label request, email, name, time, date, loc, info;

    @FXML
    private JFXTextArea notes;

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
    }

    @FXML
    void Back() {
        ServStage.setScene(servTypeScene);
    }

    @FXML
    void Confirm() {
        try {
            this.setService();

            if (service instanceof JanitorService) {
                service.setMessageHeader("Supplies needed at: " + passed[1]);
            } else if (service instanceof InterpreterService) {
                service.setMessageHeader("Interpreter needed at: " + passed[1]);
            }/* else {
                service.setMessageHeader("Food needed in: " + location);
            }*/
            service.setLocation(passed[1]);

            service.setMessageText("Requested service to be completed by: " + passed[2] + " at "
                    + passed[3] + "\n\n" + "Notes: \n\t" + notes.getText());
            service.sendEmailServiceRequest();

            service.toString();

            service.setLocation(passed[1]);
            service.setUpdateRequestType();
            service.setUpdateSentString();
            Main.requests.add(service);
            System.out.println("Message sent succesfully");
            close();
        }
        catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    void close(){
        Stage stage = (Stage)confirm.getScene().getWindow();
        stage.close();
    }

    public void setService() throws NullPointerException{

        if(passed[0] == null)
            throw new NullPointerException("No service added");

        if (passed[0].toUpperCase().equals("INTERPRETER")) {
            service = new InterpreterService();
            // placeholder
            service.setAssigned(assigned);
            service.setAccountTo(assigned.getEmail());
            //serviceNeeded = "Interpreter";
        } else if (passed[0].toUpperCase().equals("JANITOR")) {
            service = new JanitorService();
            service.setAssigned(assigned);
            service.setAccountTo(assigned.getEmail());
            //serviceNeeded = "Janitor";
        }

        /*else {
            service = new CafeteriaService();
            // placeholder
            service.setAssigned(assign);
            service.setAccountTo(email);
            serviceNeeded = "Food";
        }*/
    }
}
