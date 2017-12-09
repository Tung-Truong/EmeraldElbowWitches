package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ServiceSubSelectController {
    public ServiceController servCont;
    public Scene servScene;
    public Scene servTypeScene;
    Stage ServStage;

    public void setServScene(Scene servScene) {
        this.servScene = servScene;
    }

    public void setServStage(Stage servStage) {
        ServStage = servStage;
    }

    public void setServCont(ServiceController servCont) {
        this.servCont = servCont;
    }

    public void setServTypeScene(Scene servtypeScene) {
        this.servTypeScene = servtypeScene;
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
