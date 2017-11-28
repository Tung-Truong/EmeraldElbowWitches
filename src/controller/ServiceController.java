package controller;


import jdk.internal.org.objectweb.asm.tree.analysis.Interpreter;
import model.*;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import controller.Main;

import java.util.ArrayList;

public class ServiceController {
    // Attributes
    private String serviceNeeded;
    private ServiceRequest service;
    // private ArrayList<Employee> employees;

    @FXML
    private MenuButton EmployeeDropdown, FoodDropdown,
            LocationDropdown, RequestServiceDropdown;

    @FXML
    private Button removeNodeSelector;

    @FXML
    private JFXTextField NotesTextField;

    @FXML
    private MenuItem AdmGrant;

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

        service = ServiceContext.selectService(needed);
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

    @FXML
    void MaintenanceItem() {
        RequestServiceDropdown.setText("Maintenance");
    }

    @FXML
    void InterpreterItem() {

        RequestServiceDropdown.setText("Interpreter");
    }

    @FXML
    void EmployeeNames() {
        if (serviceNeeded.equals("Interpreter")) {
            for (Employee e : Main.getEmployees()) {
                if (e.getDepartment().toLowerCase().equals("interpreter") && e.getAvailability().toUpperCase().equals("TRUE")) {
                    EmployeeDropdown.setText(e.getFirstName() + e.getLastName());
                }

            }

        } else if (serviceNeeded.equals("Janitor")) {
            for (Employee e : Main.getEmployees()) {
                if (e.getDepartment().toLowerCase().equals("janitor") && e.getAvailability().toUpperCase().equals("TRUE")) {
                    EmployeeDropdown.setText(e.getFirstName() + e.getLastName());
                }

            }
        } else if (serviceNeeded.equals("Cafeteria")) {
            for (Employee e : Main.getEmployees()) {
                if (e.getDepartment().toLowerCase().equals("cafeteria") && e.getAvailability().toUpperCase().equals("TRUE")) {
                    EmployeeDropdown.setText(e.getFirstName() + e.getLastName());
                }

            }
        } else {
            EmployeeDropdown.setText("Select a Service");
        }
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

    @FXML
    void DrDembskiItem() {
        EmployeeDropdown.setText("Dr. Dembski");
    }

    @FXML
    void AdmGrantItem() {
        EmployeeDropdown.setText("Admin Grant");

    }
}
