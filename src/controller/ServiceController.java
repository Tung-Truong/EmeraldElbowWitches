package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import model.*;

public class ServiceController {

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
        }
        catch(NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

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

    /*void CafeteriaItem() {
        AssignEmployee.getItems().clear();
        for (Employee e : Main.getEmployees()) {
            if (e.getDepartment().equals("cafeteria") && e.getAvailability().equals("T")) {
                AssignEmployee.getItems().addAll(e.getFirstName() + " " + e.getLastName());
            }
        }
        employeeAvailable();
    }*/

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
        } else if (needed.toUpperCase().equals("MAINTENANCE")) {
            service = new JanitorService();
            service.setAccountTo(email);
            serviceNeeded = "Janitor";
        } /*else {
            service = new CafeteriaService();
            // placeholder
            service.setAccountTo(email);
            serviceNeeded = "Food";*/
        }

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
