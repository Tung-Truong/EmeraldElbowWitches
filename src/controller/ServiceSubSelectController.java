package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.stage.Stage;
import model.Employee;
import model.InterpreterService;

import java.io.IOException;

public class ServiceSubSelectController {
    public ServiceController servCont;
    public Scene servScene;
    public Scene servTypeScene;
    public String selected;
    public String pass;

    String servNeeded;
    Stage ServStage;

    @FXML
    public TreeTableView<String> table;

    @FXML
    public TreeTableColumn<String, String> column;

    @FXML
    private Label label;

    @FXML
    private JFXComboBox<String> employeeNames;

    public void passInfo(String info){
        pass = info;
    }

    public void setServScene(Scene servScene) {
        this.servScene = servScene;
    }

    public void setServStage(Stage servStage) {
        ServStage = servStage;
    }

    public void setActivity(String needed) {
        this.servNeeded = needed;
    }

    public void setServTypeScene(Scene servtypeScene) {
        this.servTypeScene = servtypeScene;
    }

    public TreeTableView init(){

        TreeItem<String> base = new TreeItem<>();

        base.setExpanded(true);
        label.setText(label.getText() + " " + servNeeded);

        if(servNeeded.equals("Interpreter")){
            base.setValue("Language");

            for(String s: InterpreterService.getLanguages()){
                base.getChildren().add(new TreeItem<>(s));
            }

        } else if(servNeeded.equals("Janitor")){
            base.setValue("Cleanup Type");
        }

        column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue()));
        table.setRoot(base);

        return table;
    }

    @FXML
    public void setEmployeeNames(){
        employeeNames.setPromptText("EMPLOYEES");
        employeeNames.setDisable(false);
        employeeNames.getItems().clear();

        if(servNeeded.equals("Interpreter")){
            for(Employee e: Main.getEmployees()){
                if(e.getAvailability().equals("T") && e.getDepartment().equals("interpret")
                        && e.getLanguage().equals(table.getSelectionModel().getSelectedItem().getValue())){
                    employeeNames.getItems().add(e.getFirstName() + " " + e.getLastName() + " : " + e.getId());
                }
            }

        } else if(servNeeded.equals("Janitor")){
            for(Employee e : Main.getEmployees()){
                if (e.getAvailability().equals("T") && e.getDepartment().equals("janitor")){
                    employeeNames.getItems().add(e.getFirstName() + " " + e.getLastName() + " : " + e.getId());
                }
            }
        }

        if(employeeNames.getItems().size() == 0){
            employeeNames.setPromptText("No employees available at this time");
            employeeNames.setDisable(true);
        }
    }

    @FXML
    void Next() throws IOException {

        FXMLLoader servContLoad = new FXMLLoader(getClass().getClassLoader().getResource("view/ui/ServiceRequestConfirm.fxml"));
        Parent root = servContLoad.load();
        ServiceConfirmController servConfCont = servContLoad.getController();

        servConfCont.setServCont(this);
        servConfCont.setServStage(ServStage);

        selected = employeeNames.getValue();

        servConfCont.setAssigned(selected);
        servConfCont.init(pass + " " + table.getSelectionModel().getSelectedItem().getValue());

        ServStage.setTitle("Service Confirm");
        servConfCont.setServScene(servTypeScene);
        ServStage.setScene(new Scene(root, 350, 600));
    }

    @FXML
    void Back() throws IOException {
        ServStage.setScene(servScene);
    }

}
