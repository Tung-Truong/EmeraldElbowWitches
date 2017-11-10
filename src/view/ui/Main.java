package view.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("UI_v1.fxml"));
        primaryStage.setTitle("Map");
        primaryStage.setScene(new Scene(root, 1000, 750));
        primaryStage.show();
    }


    public static void main(String[] args) {
        javafx.application.Application.launch(args);
    }
}
