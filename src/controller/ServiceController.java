package controller;

import com.sun.org.apache.regexp.internal.RE;
import javafx.scene.control.*;
import jdk.internal.org.objectweb.asm.tree.analysis.Interpreter;
import model.*;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import controller.Main;

import java.util.ArrayList;

public class ServiceController {
    // Attributes
    private String serviceNeeded;
    private ServiceRequest service;
    // private ArrayList<Employee> employees;

    @FXML
    private MenuButton FoodDropdown,
            LocationDropdown, RequestServiceDropdown;

    @FXML
    private Button removeNodeSelector;

    @FXML
    private JFXTextField NotesTextField;

    @FXML
    private ComboBox<String> employees;


    // Getters
    public String getServiceNeeded() {
        return this.serviceNeeded;
    }

    public ServiceRequest getService() {
        return this.service;
    }

    // Setters
    public void setServiceNeeded(String service) {
        this.serviceNeeded = service;
    }

    public void setService() {
        String needed = this.RequestServiceDropdown.getText();

        if (needed.toUpperCase().equals("INTERPRETER")) {
            service = new InterpreterService();
            // placeholder
            service.setAccountTo("kgrant@wpi.edu");
            serviceNeeded = "Interpreter";
        } else if (needed.toUpperCase().equals("MAINTENANCE")) {
            service = new JanitorService();
            serviceNeeded = "Janitor";
        } else {
            service = new CafeteriaService();
            // placeholder
            service.setAccountTo("kgrant@wpi.edu");
            serviceNeeded = "Food";
        }
    }

    // FXML Methods
    @FXML
    void BackToUIV1() {
        Main.getCurrStage().setScene(Main.getService());
    }

    @FXML
    void SubmitRequest() {
        this.setService();

        service.setLocation(LocationDropdown.getText());
        service.setMessageText(NotesTextField.getText());
        service.sendEmailServiceRequest();

        // Header field is not being updated so definitely look into this more

        System.out.println("Message sent succesfully");
    }

    //these three items handle changing the employyee names available
    @FXML
    void MaintenanceItem() {
        employees.getItems().clear();
        for (Employee e : Main.getEmployee()) {
            if (e.getDepartment().equals("janitor") && e.getAvailability().equals("T")) {
                employees.getItems().addAll(e.getFirstName() + " " + e.getLastName());
            }
        }
        employeeAvailabile();
        RequestServiceDropdown.setText("Maintenance");
    }

    @FXML
    void CafeteriaItem() {
        employees.getItems().clear();
        for (Employee e : Main.getEmployee()) {
            if (e.getDepartment().equals("cafeteria") && e.getAvailability().equals("T")) {
                employees.getItems().addAll(e.getFirstName() + " " + e.getLastName());
            }
        }
        employeeAvailabile();
        RequestServiceDropdown.setText("Cafeteria");
    }

    @FXML
    void InterpreterItem() {
        employees.getItems().clear();
        for (Employee e : Main.getEmployee()) {
            if (e.getDepartment().equals("interpret") && e.getAvailability().equals("T")) {
                employees.getItems().addAll(e.getFirstName() + " " + e.getLastName() + " " + "Language: " + e.getLanguage());
            }
        }
        employeeAvailabile();
        RequestServiceDropdown.setText("Interpreter");
    }


    @FXML
    void NoodlesItem() {
        FoodDropdown.setText("Noodles");
    }

    @FXML
    void BurgerItem() {
        FoodDropdown.setText("Burger");
    }

    @FXML
    void TapiocaItem() {
        FoodDropdown.setText("Tapioca");
    }


    @FXML
    void WaitingRoomItem() {
        LocationDropdown.setText("WaitingRoom");
    }

    @FXML
    void XrayItem() {
        LocationDropdown.setText("Xray");

    }


    //function that just sets the menu items to display no employee available if there is none.
    private void employeeAvailabile() {
        if (employees.getItems().size() == 0) {
            employees.getItems().add("No Employees Currently Available");
        }
    }

}
