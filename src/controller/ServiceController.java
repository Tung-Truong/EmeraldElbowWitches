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

public class ServiceController {
    // Attributes
    private String serviceNeeded;
    private ServiceRequest service;
    private Employee foundMod, foundDel;
    

    @FXML
    private MenuButton FoodDropdown,
            LocationDropdown, RequestServiceDropdown;
    // LocationDropdown will not be present for the next iteration so there is no real use working with it now

    @FXML
    private Button removeNodeSelector;

    @FXML
    private JFXTextField NotesTextField;

    @FXML
    private TextField emailOne, firstOne, lastOne, depOne, langOne, availOne,
    emailTwo, firstTwo, lastTwo, depTwo, langTwo, availTwo;

    @FXML
    private ComboBox<String> AssignEmployee, DeleteEmployee, ModifyEmployee;


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

    public void setService() {
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
    }

    // FXML Methods
    @FXML
    void BackToAdmin() {
        Main.getCurrStage().setScene(Main.getAdminScene());
    }

    @FXML
    void SubmitRequest() {
        this.setService();
        try {
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

            System.out.println("Message sent succesfully");
            Main.requests.add(service);
        }
        catch(NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    //these three items handle changing the employee names available
    @FXML
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
        FoodDropdown.setVisible(false);
    }

    @FXML
    void CafeteriaItem() {
        FoodDropdown.setVisible(true);
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
        FoodDropdown.setVisible(false);
    }

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
        foundMod = new Employee();
        String name = ModifyEmployee.getValue().split(":")[0].trim();
        String email = ModifyEmployee.getValue().split(":")[1].trim();

        for(Employee e: Main.getEmployees()){
            if(name.equals(e.getFirstName()) && email.equals(e.getEmail())){
                foundMod = e;
                break;
            }
        }

        emailTwo.setText(foundMod.getEmail());
        firstTwo.setText(foundMod.getFirstName());
        lastTwo.setText(foundMod.getLastName());
        depTwo.setText(foundMod.getDepartment());
        langTwo.setText(foundMod.getLanguage());
        availTwo.setText(foundMod.getAvailability());

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
        foundMod.setEmail(emailTwo.getText());
        foundMod.setFirstName(firstTwo.getText());
        foundMod.setLastName(lastTwo.getText());
        foundMod.setDepartment(depTwo.getText());
        foundMod.setLanguage(langTwo.getText());
        foundMod.setAvailability(availTwo.getText());

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
        AssignEmployee.getItems().remove(foundDel.getFirstName() + " " + foundDel.getLastName());

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
    } // LocationDropdown will not be present for the next iteration so there is no real use working with it now

    @FXML
    void XrayItem() {
        LocationDropdown.setText("X Ray");

    } // LocationDropdown will not be present for the next iteration so there is no real use working with it now

    @FXML
    void Refresh(){
        // Printing this stuff is a later order concern so get back to it later
        String finalString = " ";
        ArrayList<ServiceRequest> searchInactive = Main.requests;
        for(ServiceRequest serve : searchInactive) {
            if(serve.getAssigned() != null) {
                serve.resolveRequest();
                if (serve.isActive()) {
                    serve.generateReport();
                }
            }
            else {
                finalString.concat("No active Requests");
            }
        }
        for(ServiceRequest s : searchInactive){
            if (!s.isActive()){
                Main.requests.remove(s);
            }
        }

        NotesTextField.setText("Active Requests: " + Main.requests.get(0).generateReport());
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
