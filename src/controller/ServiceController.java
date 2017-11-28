package controller;

import javafx.scene.control.Menu;
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
    private MenuItem DrDembskiI;

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

        if (needed.toUpperCase() == "INTERPRETER") {
            service = new InterpreterService();
            // placeholder
            service.setAccountTo("kgrant@wpi.edu");
            serviceNeeded = "Interpreter";
        } else if (needed.toUpperCase() == "MAINTENANCE") {
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

    //these two items handle changing the employyee names available
    @FXML
    void MaintenanceItem() {
        ArrayList<MenuItem> items = new ArrayList<>();
        items.add(AdmGrant);
        items.add(DrDembskiI);
        for (MenuItem item:items) {
            item.setText(null);
            item.setVisible(false);
        }
        int i = 0;
        for (Employee e : Main.getEmployee()) {
            if (e.getDepartment().equals("janitor") && e.getAvailability().equals("T")) {
                items.get(i).setText(e.getFirstName() + e.getLastName());
                EmployeeDropdown.setText(e.getFirstName() + e.getLastName());
                items.get(i).setVisible(true);
                i++;
            }
        }
    RequestServiceDropdown.setText("Maintenance");
}

    @FXML
    void InterpreterItem() {
        ArrayList<MenuItem> items = new ArrayList<>();
        items.add(AdmGrant);
        items.add(DrDembskiI);
        for (MenuItem item:items) {
            item.setText(null);
            item.setVisible(false);
        }int i = 0;
        for (Employee e : Main.getEmployee()) {
            if (e.getDepartment().equals("interpret") && e.getAvailability().equals("T")) {
                items.get(i).setText(e.getFirstName() + e.getLastName());
                EmployeeDropdown.setText(e.getFirstName() + e.getLastName());
                i++;
                items.get(i).setVisible(true);
            }
        }
        RequestServiceDropdown.setText("Interpreter");

    }

    //next iteration change how the changing menu items work
    @FXML
    void EmployeeNames() {
        //to do
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
    }

    @FXML
    void AdmGrantItem() {
    }


}
