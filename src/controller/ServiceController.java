package controller;

import com.sun.org.apache.regexp.internal.RE;
import javafx.scene.control.*;
import jdk.internal.org.objectweb.asm.tree.analysis.Interpreter;
import model.*;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import javax.xml.stream.Location;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceController {
    // Attributes
    private String serviceNeeded;
    private ServiceRequest service;

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
        String[] requestedEmployee = employees.getValue().split(" ");
        String email = "";
        for (Employee e : Main.getEmployee()) {
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
        } else if (needed.toUpperCase().equals("MAINTENANCE")) {
            service = new JanitorService();
            service.setAccountTo(email);
            serviceNeeded = "Janitor";
        } else {
            service = new CafeteriaService();
            // placeholder
            service.setAccountTo(email);
            serviceNeeded = "Food";
        }
    }

    // FXML Methods
    @FXML
    void BackToAdmin() {
        Main.getCurrStage().setScene(Main.getPatientScene());
    }

    @FXML
    void SubmitRequest() {
        this.setService();
        String location = LocationDropdown.getText();

        if (service instanceof JanitorService) {
            service.setMessageHeader("Supplies needed at: " + location);
        } else if (service instanceof InterpreterService) {
            service.setMessageHeader("Interpreter needed at: " + location);
        } else {
            service.setMessageHeader("Food needed in: " + location);
        }
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
        employees.setPromptText("Employees Available");
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
        employees.setPromptText("Employees Available");
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
        LocationDropdown.setText("Waiting Room");
    }

    @FXML
    void XrayItem() {
        LocationDropdown.setText("X Ray");

    }

    @FXML
    void removeEmployee() {
        Employee employee = employees.getValue();
        Main.employees.remove(employee);
        try {
            DeleteDB.delEmployee(employee);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //function that just sets the menu items to display no employee available if there is none.
    private void employeeAvailabile() {
        if (employees.getItems().size() == 0) {
            employees.setPromptText("No Employees Currently Available");
            employees.setDisable(true);
        } else {
            employees.setDisable(false);
        }
    }
}
