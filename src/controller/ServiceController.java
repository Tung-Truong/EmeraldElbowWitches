package controller;

//import controllers.API.APIApp;
import HealthAPI.HealthCareRun;
import com.jfoenix.controls.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import foodRequest.FoodRequest;
//import messenger.API;
import model.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ServiceController {
    // Attributes
    public String serviceNeeded;

    private ServiceRequest service;
    private SingleController single = SingleController.getController();

    Scene ServScene;
    Stage ServStage;
    ServiceController meCont;
    LocalDateTime now;

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
    private JFXDatePicker DateChoice;

    @FXML
    private JFXTimePicker TimeChoice;


    @FXML
    private JFXButton cancelButton;

    @FXML
    private JFXButton nextBtn;



    public void initialize(){
        RequestServiceDropdown.getItems().addAll("Janitor", "Interpreter", "Healthcare", "Food", "IT", "Transport");//, "cafeteria");
    }

//    public String getServiceNeeded() {
//        return this.serviceNeeded;
//    }

    boolean checkDate(){
        now = LocalDateTime.now();

        if(DateChoice.getValue().isBefore(now.toLocalDate())) {
            DateChoice.getEditor().clear();
            DateChoice.setStyle("-fx-prompt-text-fill: #cc0000");
            DateChoice.setPromptText("Please select a later date");
            return false;
        } else if (DateChoice.getValue().compareTo(now.toLocalDate()) == 0 && TimeChoice.getValue().isBefore(now.toLocalTime())){
            TimeChoice.getEditor().clear();
            TimeChoice.setStyle("-fx-prompt-text-fill: #cc0000");
            TimeChoice.setPromptText("Please select a later time");
            return false;
        } else {
            return true;
        }
    }

//    @FXML
//    void checkTime(){
//
//        if(TimeChoice.getValue().isBefore(now.toLocalTime())){
//            TimeChoice.setStyle("-fx-text-fill: #cc0000");
//            TimeChoice.setPromptText("Please select a later time");
//        } else {
//            TimeChoice.setStyle("-fx-text-fill: #000000");
//        }
//
//    }

    public void setMeCont(ServiceController meCont) {
        this.meCont = meCont;
    }

    public ServiceRequest getService() {
        return this.service;
    }

    public void setServScene(Scene servScene) {
        ServScene = servScene;
    }

    public void setServStage(Stage servStage) {
        ServStage = servStage;
    }

    // Setters
//    public void setServiceNeeded(String service) throws NullPointerException {
//        this.serviceNeeded = service;
//    }

    //@FXML
    void close(){
        Stage stage = (Stage)nextBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onServiceTypeSelection(){
        if((RequestServiceDropdown.getValue() != null) && (RequestServiceDropdown.getValue().equals("Healthcare"))){ //can be implemented to launch different API for different service requests
            HealthCareRun health = new HealthCareRun();
            try {
                close();
                health.run(-500,-500,600,350,"view/stylesheets/default.css","","");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } /*else if (RequestServiceDropdown.getValue() != null && (RequestServiceDropdown.getValue().equals("Food"))){
            FoodRequest foodRequest = new FoodRequest();
            try{
                close();
                foodRequest.run(0,0,1900,1000,null,null,null);
            }catch (Exception e){
                System.out.println("Failed to run API");
                e.printStackTrace();
            }
        } else if (RequestServiceDropdown.getValue() != null && (RequestServiceDropdown.getValue().equals("IT"))){
            API itRequest = new API();
            try{
                close();
                itRequest.run(0,0,600,350,"src/view/stylesheets/default.css",null,null);
            }catch (Exception e){
                System.out.println("Failed to run API");
                e.printStackTrace();
            }
        } else if (RequestServiceDropdown.getValue() != null && (RequestServiceDropdown.getValue().equals("Transport"))){
            try{
                close();
                APIApp.run(0,0,600,350,"src/view/stylesheets/default.css",null,null);
            }catch (Exception e){
                System.out.println("Failed to run API");
                e.printStackTrace();
            }
        }*/
    }

    @FXML
    void Next() throws IOException {
        if(DateChoice.getPromptText().trim().equals("Date")){
            DateChoice.getEditor().clear();
            TimeChoice.setStyle("-fx-prompt-text-fill: #cc0000");
            DateChoice.setPromptText("Please select a date");
        }
        if(TimeChoice.getPromptText().trim().equals("Time")){
            TimeChoice.getEditor().clear();
            TimeChoice.setStyle("-fx-prompt-text-fill: #cc0000");
            TimeChoice.setPromptText("Please select a time");
        }
        try {
            if (checkDate()) {

                FXMLLoader servContLoad = new FXMLLoader(getClass().getClassLoader().getResource("view/ui/ServiceType.fxml"));
                Parent root = servContLoad.load();
                ServiceSubSelectController servTypeCont = servContLoad.getController();

                serviceNeeded = RequestServiceDropdown.getValue();

                servTypeCont.setActivity(serviceNeeded);
                servTypeCont.passInfo(serviceNeeded + " " + servLocField.getText() + " " + DateChoice.getValue().toString()
                        + " " + TimeChoice.getValue().toString());
                servTypeCont.setServStage(ServStage);
                servTypeCont.setServScene(ServScene);
                ServStage.setTitle("Service Type");

                Scene servTypeScene = new Scene(new Group(root), 350, 600);
                servTypeCont.setServTypeScene(servTypeScene);

                Group servRoot = (Group) servTypeScene.getRoot();
                servRoot.getChildren().add(servTypeCont.init());

                ServStage.setScene(servTypeScene);
            }
        } catch (NullPointerException n){
            RequestServiceDropdown.setStyle("-fx-prompt-text-fill: #cc0000");
            RequestServiceDropdown.setPromptText("Please select a service");
        }
    }

//    //@FXML
//    void SubmitRequest() {
//        try {
//            this.setService();
//            String location = servLocField.getText();
//
//            if (service instanceof JanitorService) {
//                service.setMessageHeader("Supplies needed at: " + location);
//            } else if (service instanceof InterpreterService) {
//                service.setMessageHeader("Interpreter needed at: " + location);
//            }/* else {
//                service.setMessageHeader("Food needed in: " + location);
//            }*/
//            service.setLocation(location);
//
//            service.setMessageText("Requested service to be completed by: " + DateChoice.getValue() + " at "
//                    + TimeChoice.getValue() + "\n\n" + "Notes: \n\t" + NotesTextField.getText());
//            service.sendEmailServiceRequest();
//
//            service.toString();
//            System.out.println("add");
//            System.out.println(Main.getRequestList().size());
//            Main.requests.add(service);
//            System.out.println(Main.getRequestList().size());
//            System.out.println("Message sent succesfully");
//            close();
//        }
//        catch(NullPointerException e){
//            e.printStackTrace();
//        }
//    }

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

//    //@FXML
//    void CafeteriaItem() {
//        AssignEmployee.getItems().clear();
//
//        for (Employee e : Main.getEmployees()) {
//            if (e.getDepartment().equals("cafeteria") && e.getAvailability().equals("T")) {
//                AssignEmployee.getItems().addAll(e.getFirstName() + " " + e.getLastName());
//            }
//        }
//        employeeAvailable();
//    }

    //@FXML
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

    /*public void setService() throws NullPointerException{//need to search through all employees and find correct ID
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
            single.setServiceNeeded("Interpreter");
        } else if (needed.toUpperCase().equals("JANITOR")) {
            service = new JanitorService();
            service.setAccountTo(email);
            serviceNeeded = "Janitor";
        } else {
            service = new CafeteriaService();
            // placeholder
            service.setAccountTo(email);
            serviceNeeded = "Food";
        }*/

//        public void setService() throws NullPointerException{
//        String needed = this.RequestServiceDropdown.getValue();
//        if(AssignEmployee.getValue().split(" ") == null)
//            throw new NullPointerException("No service added");
//        String[] requestedEmployee = AssignEmployee.getValue().split(" ");
//        String email = "";
//        Employee assign = new Employee();
//        for (Employee e : Main.getEmployees()) {
//            if (e.getLastName().equals(requestedEmployee[1]) && e.getFirstName().equals(requestedEmployee[0])) {
//                email = e.getEmail();
//                assign = e;
//            } else {
//                //TO DO throw an exception because employee was never set
//            }
//        }
//        if (needed.toUpperCase().equals("INTERPRETER")) {
//            service = new InterpreterService();
//            // placeholder
//            service.setAssigned(assign);
//            service.setAccountTo(email);
//            //serviceNeeded = "Interpreter";
//        } else if (needed.toUpperCase().equals("JANITOR")) {
//            service = new JanitorService();
//            System.out.println("righthrt");
//            System.out.println("" + assign.getId());
//            service.setAssigned(assign);
//            service.setAccountTo(email);
//            System.out.println("" + service.getAssigned().getId());
//            //serviceNeeded = "Janitor";
//        }
//
//        /*else {
//            service = new CafeteriaService();
//            // placeholder
//            service.setAssigned(assign);
//            service.setAccountTo(email);
//            serviceNeeded = "Food";
//        }*/
//    }

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
