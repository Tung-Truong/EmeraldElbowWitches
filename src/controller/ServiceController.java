package controller;

import javafx.scene.control.*;
import model.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceController {
    // Attributes
    private String serviceNeeded;
    private ServiceRequest service;

    @FXML
    public JFXTextField servLocField;

    @FXML
    private JFXComboBox<String> RequestServiceDropdown;

    @FXML
    private JFXComboBox<String> AssignEmployee;

    @FXML
    private JFXTextField NotesTextField;

    @FXML
    private JFXSlider urgencyMeter;

    @FXML
    private JFXButton cancelButton;

    public void initialize(){
        RequestServiceDropdown.getItems().addAll("Janitor", "Interpreter");//, "cafeteria");
    }

    public String getServiceNeeded() {
        return this.serviceNeeded;
    }

    public ServiceRequest getService() {
        return this.service;
    }

    // Setters
    public void setServiceNeeded(String service) throws NullPointerException {
        this.serviceNeeded = service;
    }

    @FXML
    void close(){
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void SubmitRequest() {
        try {
            this.setService();
            String location = servLocField.getText();

            if (service instanceof JanitorService) {
                service.setMessageHeader("Supplies needed at: " + location);
            } else if (service instanceof InterpreterService) {
                service.setMessageHeader("Interpreter needed at: " + location);
            } else {
                service.setMessageHeader("Food needed in: " + location);
            }
            service.setLocation(servLocField.getText());

            service.setMessageText(NotesTextField.getText());
            service.sendEmailServiceRequest();

            // Header field is not being updated so definitely look into this more

            System.out.println("Message sent succesfully");
            Main.requests.add(service);
        }
        catch(NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    //these three items handle changing the employyee names available
    @FXML
    void SetServiceType() {
        try {
            switch (RequestServiceDropdown.getValue()) {
                case "Interpreter":
                    InterpreterItem();
                    break;
                case "Janitor":
                    MaintenanceItem();
                    break;
           /* case "cafeteria":
                CafeteriaItem();
                break;*/
            }
        }
        catch (NullPointerException e){
            e.getMessage();
        }
    }

    void MaintenanceItem() {
        AssignEmployee.getItems().clear();
        for (Employee e : Main.getEmployees()) {
            if (e.getDepartment().equals("janitor") && e.getAvailability().equals("T")) {
                AssignEmployee.getItems().addAll(e.getFirstName() + " " + e.getLastName());
            }
        }
        AssignEmployee.setPromptText("Employees Available");
        employeeAvailable();
    }

    @FXML
    void CafeteriaItem() {
        AssignEmployee.getItems().clear();

        for (Employee e : Main.getEmployees()) {
            if (e.getDepartment().equals("cafeteria") && e.getAvailability().equals("T")) {
                AssignEmployee.getItems().addAll(e.getFirstName() + " " + e.getLastName());
            }
        }
        employeeAvailable();
    }

    @FXML
    void InterpreterItem() {
        AssignEmployee.getItems().clear();
        for (Employee e : Main.getEmployees()) {
            if (e.getDepartment().equals("interpret") && e.getAvailability().equals("T")) {
                AssignEmployee.getItems().addAll(e.getFirstName() + " " + e.getLastName() + " " + "Language: " + e.getLanguage());
            }
        }
        AssignEmployee.setPromptText("Employees Available");
        employeeAvailable();
    }

    public void setService() throws NullPointerException{//need to search through all employees and find correct ID
        String needed = RequestServiceDropdown.getValue();
        if(AssignEmployee.getValue().split(" ") == null)
            throw new NullPointerException("No service added");
        String[] requestedEmployee = AssignEmployee.getValue().split(" ");
        String email = "";
        for (Employee e : Main.getEmployees()) {
            if (e.getLastName().equals(requestedEmployee[1]) && e.getFirstName().equals(requestedEmployee[0])) {
                email = e.getEmail();
            } else {
                //TO DO throw an exception because employee was never set
            }
        }
        if (needed.toUpperCase().equals("INTERPRETER")) {
            service = new InterpreterService();
            // placeholder
            service.setAccountTo(email);
            serviceNeeded = "Interpreter";
        } else if (needed.toUpperCase().equals("JANITOR")) {
            service = new JanitorService();
            service.setAccountTo(email);
            serviceNeeded = "Janitor";
        } /*else {
            service = new CafeteriaService();
            // placeholder
            service.setAccountTo(email);
            serviceNeeded = "Food";*/
        }

        /* public void setService() {
        String needed = this.RequestServiceDropdown.getText();
        if(AssignEmployee.getValue().split(" ") == null)
            throw new NullPointerException("No service added");
        String[] requestedEmployee = AssignEmployee.getValue().split(" ");
        String email = "";
        Employee assign = new Employee();
        for (Employee e : Main.getEmployees()) {
            if (e.getLastName().equals(requestedEmployee[1]) && e.getFirstName().equals(requestedEmployee[0])) {
                email = e.getEmail();
                assign = e;
            } else {
                //TO DO throw an exception because employee was never set
            }
        }
        if (needed.toUpperCase().equals("INTERPRETER")) {
            service = new InterpreterService();
            // placeholder
            service.setAssigned(assign);
            service.setAccountTo(email);
            serviceNeeded = "Interpreter";
        } else if (needed.toUpperCase().equals("MAINTENANCE")) {
            service = new JanitorService();
            service.setAssigned(assign);
            service.setAccountTo(email);
            serviceNeeded = "Janitor";
        } else {
            service = new CafeteriaService();
            // placeholder
            service.setAssigned(assign);
            service.setAccountTo(email);
            serviceNeeded = "Food";
        }
    }*/

    //function that just sets the menu items to display no employee available if there is none.
    private void employeeAvailable() {
        if (AssignEmployee.getItems().size() == 0) {
            AssignEmployee.setPromptText("No Employees Currently Available");
            AssignEmployee.setDisable(true);
        } else {
            AssignEmployee.setDisable(false);
        }
    }

}
