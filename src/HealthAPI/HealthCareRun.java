package HealthAPI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;


public class HealthCareRun{

    static int width;
    static int height;
    static int xPosition;
    static int yPosition;
    static String path;
    static String username = null;
    static String password = null;
    public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    public static ArrayList<ServiceRequestHealth> requests;
    //private static HealthCareRun _healthCare = new HealthCareRun();

    public HealthCareRun() {}


    public void run(int xcoord, int ycoord, int windowWidth, int windowLength, String
            cssPath, String destNodeID, String originNodeID) throws SQLException, ClassNotFoundException, ServiceException {
        width = windowLength;
        height = windowWidth;
        xPosition = xcoord;
        yPosition = ycoord;
        path = cssPath;
        try {
            prepAPI();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File test = new File("HealthAPI/Health.txt");
        Class.forName(DRIVER);
        //get the connection for the database
        Connection connection = DriverManager.getConnection(CreateDB2.JDBC_URL);
        Statement statement = connection.createStatement();
        //run the database

        if (!test.exists()) {//if DB not yet created
            try {
                CreateDB2.run();
                test.createNewFile();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            ReadCSV2.runRequest("HealthAPI/ServiceRequests2.csv");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String[] newRow = new String[2];
        try {
            File HealthUser = new File("HealthAPI/AdminEmailInfo.csv");
            Scanner inputStream = new Scanner(HealthUser);
            inputStream.useDelimiter(",|\\n");

            for (int p = 0; p < newRow.length; p++) {
                inputStream.next();
            }
            while (inputStream.hasNext()) {  //while there's still a row
                for (int i = 0; ((inputStream.hasNext()) && (i < newRow.length)); i++) { // get the row
                    String data = inputStream.next();
                    if (data.contains("\r")) {
                        data = data.replace("\r", "");
                    }
                    data = data.trim();
                    newRow[i] = data;
                }
            }
            username = newRow[0];
            password = newRow[1];
            inputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        requests = QueryDB2.getRequests();
        try {
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    static String getUsername(){
        return username;
    }

    static String getPassword(){
        return password;
    }

    static String getPath(){
        return path;
    }


    static int getxPosition(){
        return xPosition;
    }

    static int getyPosition(){
        return yPosition;
    }

    static int getWidth(){
        return width;
    }
    static int getHeight(){
        return height;
    }

    static ArrayList<ServiceRequestHealth> getRequests(){
        return requests;
    }

    void start() throws IOException {
            FXMLLoader HealthContLoad = new FXMLLoader(getClass().getClassLoader().getResource("view/ui/Healthcare.fxml"));
            Parent root = HealthContLoad.load();
            root.getStylesheets().add(path);
            HealthcareController HealthCont = HealthContLoad.getController();
            Stage healthStage = new Stage();
                healthStage.setOnCloseRequest( event -> {
                    try {
                        WriteRequests2.runRequests();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } );
            healthStage.setTitle("Service Request");
            healthStage.setScene(new Scene(root, width, height));
            healthStage.show();
            if(xPosition>=0&&yPosition>=0) {
                healthStage.setX(xPosition);
                healthStage.setY(yPosition);
            }
    }

    public void changeEmail(String user, String pass) throws IOException {

            try {
                String filename = "HealthAPI/AdminEmailInfo.csv";
                File file = new File(filename);
                FileWriter fw = new FileWriter(file);

                fw.append("Username, Password\n");
                fw.append(user + ", " + pass);
                fw.flush();
                fw.close();
                System.out.println("CSV File is created successfully.");
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public static void prepAPI() throws IOException {
        File path = new File("HealthAPI");
        if(!path.exists()){
            path.mkdir();
        }
        File HealthAPI = new File ("HealthAPI");
        if(!HealthAPI.exists()){
            HealthAPI.mkdir();
        }
        File csv1 = new File("HealthAPI/ServiceRequests2.csv");
        if(!csv1.exists()){
            csv1.createNewFile();
            FileWriter fw = new FileWriter(csv1);
            fw.append("Email, MessageHeader, Provider, Name, timeSubmitted");
            fw.flush();
            fw.close();
        }
        File csvTheOtherOne = new File("HealthAPI/AdminEmailInfo.csv");
        if(!csvTheOtherOne.exists()){
            csvTheOtherOne.createNewFile();
            FileWriter fw = new FileWriter(csvTheOtherOne);
            fw.append("Username, Password");
            fw.flush();
            fw.close();
        }
    }


}
