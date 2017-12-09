package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ServiceSubSelectController {
    public ServiceController servCont;

    public void setServCont(ServiceController servCont) {
        this.servCont = servCont;
    }

    @FXML
    void Next() throws IOException {
        FXMLLoader servContLoad = new FXMLLoader(getClass().getClassLoader().getResource("view/ui/ServiceRequestConfirm.fxml"));
        Parent root = servContLoad.load();
        ServiceConfirmController servTypeCont = servContLoad.getController();
        servTypeCont.setServCont(this);
        Stage servStage = new Stage();
        servStage.setTitle("Service Type");
        servStage.setScene(new Scene(root, 350, 600));
        servStage.show();
    }

    @FXML
    void Back() throws IOException {

    }

}
