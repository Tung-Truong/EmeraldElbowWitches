package controller;

import model.CafeteriaService;
import model.InterpreterService;
import model.JanitorService;
import model.ServiceRequest;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import controller.Main;

public class ServiceController {

    @FXML
    private MenuButton EmployeeDropdown;

    @FXML
    private Button removeNodeSelector;

    @FXML
    private MenuButton FoodDropdown;

    @FXML
    private MenuButton LocationDropdown;

    @FXML
    private MenuButton RequestServiceDropdown;

    @FXML
    private JFXTextField NotesTextFeild;

    @FXML
    private MenuItem AdmGrant;



    @FXML
    void BackToUIV1( ) {
        Main.getCurrStage().setScene(Main.getService());
    }

    @FXML
    void SubmitRequest( ) {

    }


    // Attributes
    private String serviceNeeded;
    private ServiceRequest service;

    // Constructor
    public void Service (String needed){
        this.serviceNeeded = needed;

        if(needed.toUpperCase() == "INTERPRETER"){
            service = new InterpreterService();
        } else if(needed.toUpperCase() == "JANITOR"){
            service = new JanitorService();
        } else {
            service = new CafeteriaService();
        }
    }

    // Getters
    public String getServiceNeeded() {
        return this.serviceNeeded;
    }

    public ServiceRequest getService() {
        return this.service;
    }

    // Setters
    public void setServiceNeeded(String service){
        this.serviceNeeded = service;
    }

    public void setService(ServiceRequest serve){

    }

    @FXML
    void MateninceItem() {
        RequestServiceDropdown.setText("Maintenance");
    }

    @FXML
    void InterpreterItem( ) {

        RequestServiceDropdown.setText("Interpreter");
    }

    @FXML
    void NoodlesItem( ) {
        FoodDropdown.setText("Noodles");
    }

    @FXML
    void BurgerItem( ) {
        FoodDropdown.setText("Burger");
    }

    @FXML
    void TapiocaItem( ) {
        FoodDropdown.setText("Tapioca");
    }


    @FXML
    void WaitingRoomItem( ) {
        LocationDropdown.setText("WaitingRoom");
    }

    @FXML
    void XrayItem( ) {
        LocationDropdown.setText("Xray");

    }

    @FXML
    void DrDembskiItem( ) {
        EmployeeDropdown.setText("Dr. Dembski");
    }

    @FXML
    void AdmGrantItem( ) {
        EmployeeDropdown.setText("Admin Grant");

    }
}
