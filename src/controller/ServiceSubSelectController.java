package controller;

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
import model.InterpreterService;

import java.io.IOException;

public class ServiceSubSelectController {
    public ServiceController servCont;
    public Scene servScene;
    public Scene servTypeScene;
    String servNeeded;
    Stage ServStage;

    @FXML
    public TreeTableView<String> table;

    @FXML
    public TreeTableColumn<String, String> column;

    @FXML
    private Label label;

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
    void Next() throws IOException {
        FXMLLoader servContLoad = new FXMLLoader(getClass().getClassLoader().getResource("view/ui/ServiceRequestConfirm.fxml"));
        Parent root = servContLoad.load();
        ServiceConfirmController servConfCont = servContLoad.getController();

        servConfCont.setServCont(this);
        servConfCont.setServStage(ServStage);

        ServStage.setTitle("Service Confirm");
        servConfCont.setServScene(servTypeScene);
        ServStage.setScene(new Scene(root, 350, 600));
    }

    @FXML
    void Back() throws IOException {
        ServStage.setScene(servScene);
    }

}
