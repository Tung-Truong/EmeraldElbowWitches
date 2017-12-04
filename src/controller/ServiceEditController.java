package controller;

import javafx.scene.control.*;
import model.*;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceEditController {
    // Attributes
    private String serviceNeeded;
    private ServiceRequest service;
    private Employee foundMOd, foundDel;

   /* @FXML
    private MenuButton FoodDropdown,
            LocationDropdown, RequestServiceDropdown;*/

    @FXML
    private Button removeNodeSelector;

    @FXML
    private JFXTextField NotesTextField;

    @FXML
    private TextField emailOne, firstOne, lastOne, depOne, langOne, availOne,
    emailTwo, firstTwo, lastTwo, depTwo, langTwo, availTwo;

    @FXML
    private ComboBox<String> /*AssignEmployee, */ DeleteEmployee, ModifyEmployee;


    // Getters
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

   /* public void setService() {
        String needed = this.RequestServiceDropdown.getText();
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
        } else {
            service = new CafeteriaService();
            // placeholder
            service.setAccountTo(email);
            serviceNeeded = "Food";
        }
    }*/

    // FXML Methods


    /*@FXML
    void SubmitRequest() {
        try {
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
        catch(NullPointerException e){
            System.out.println(e.getMessage());
        }
    }*/

    //these three items handle changing the employyee names available
   /* @FXML
    void MaintenanceItem() {
        AssignEmployee.getItems().clear();
        for (Employee e : Main.getEmployees()) {
            if (e.getDepartment().equals("janitor") && e.getAvailability().equals("T")) {
                AssignEmployee.getItems().addAll(e.getFirstName() + " " + e.getLastName());
            }
        }
        AssignEmployee.setPromptText("Employees Available");
        employeeAvailable();
        RequestServiceDropdown.setText("Maintenance");
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
        RequestServiceDropdown.setText("Cafeteria");
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
        RequestServiceDropdown.setText("Interpreter");
    }*/

    @FXML
    void AddEmployee(){
        String email = emailOne.getText().trim();
        String first = firstOne.getText().trim();
        String last = lastOne.getText().trim();
        String dep = depOne.getText().trim();
        String lang = langOne.getText().trim();
        String avail = availOne.getText().trim();

        Employee e = new Employee(email, first, last, dep, lang, avail);

        Main.getEmployees().add(e);
        ModifyEmployee.getItems().add(e.getFirstName() + " : " + e.getEmail());
        DeleteEmployee.getItems().add(e.getFirstName() + " : " + e.getEmail());

        emailOne.clear();
        firstOne.clear();
        lastOne.clear();
        depOne.clear();
        langOne.clear();
        availOne.clear();

    }

    @FXML
    void AutoFill(){
        //SelectEmployee();
        foundMOd = new Employee();
        try{
            String name = ModifyEmployee.getValue().split(":")[0].trim();
            String email = ModifyEmployee.getValue().split(":")[1].trim();

            for (Employee e : Main.getEmployees()) {
                if (name.equals(e.getFirstName()) && email.equals(e.getEmail())) {
                    foundMOd = e;
                    break;
                }
            }

            emailTwo.setText(foundMOd.getEmail());
            firstTwo.setText(foundMOd.getFirstName());
            lastTwo.setText(foundMOd.getLastName());
            depTwo.setText(foundMOd.getDepartment());
            langTwo.setText(foundMOd.getLanguage());
            availTwo.setText(foundMOd.getAvailability());
        }
        catch (NullPointerException e){
            e.getMessage();
        }

    }

    @FXML
    void SelectEmployee(){

        ModifyEmployee.getItems().clear();
        DeleteEmployee.getItems().clear();
        for (Employee e : Main.getEmployees()) {
            ModifyEmployee.getItems().addAll(e.getFirstName() + " : " + e.getEmail());
        }
        DeleteEmployee.getItems().addAll(ModifyEmployee.getItems());
    }

    @FXML
    void ModifyEmployees(){
        foundMOd.setEmail(emailTwo.getText());
        foundMOd.setFirstName(firstTwo.getText());
        foundMOd.setLastName(lastTwo.getText());
        foundMOd.setDepartment(depTwo.getText());
        foundMOd.setLanguage(langTwo.getText());
        foundMOd.setAvailability(availTwo.getText());

        emailTwo.clear();
        firstTwo.clear();
        lastTwo.clear();
        depTwo.clear();
        langTwo.clear();
        availTwo.clear();
    }

    @FXML
    void removeEmployee() {
        foundDel = new Employee();
        String name = DeleteEmployee.getValue().split(":")[0].trim();
        String email = DeleteEmployee.getValue().split(":")[1].trim();

        for(Employee e: Main.getEmployees()){
            if(name.equals(e.getFirstName()) && email.equals(e.getEmail())){
                foundDel = e;
                break;
            }
        }

        Main.employees.remove(foundDel);

        ModifyEmployee.getItems().remove(foundDel.getFirstName() + " : " + foundDel.getEmail());
        DeleteEmployee.getItems().remove(foundDel.getFirstName() + " : " + foundDel.getEmail());
       // AssignEmployee.getItems().remove(foundDel.getFirstName() + " " + foundDel.getLastName());

    }

   /* @FXML
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

    }*/

    //function that just sets the menu items to display no employee available if there is none.
    /*private void employeeAvailable() {
        if (AssignEmployee.getItems().size() == 0) {
            AssignEmployee.setPromptText("No Employees Currently Available");
            AssignEmployee.setDisable(true);
        } else {
            AssignEmployee.setDisable(false);
        }
    }*/

}
