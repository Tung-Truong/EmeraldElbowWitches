package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;
import model.AddDB;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Main extends Application{

    //get height of application
    public static int sceneWidth = 1750;
    public static int sceneHeight = 1000;
    public static Scene currScene;
    public static Scene Service;
    public static Stage currStage;
    public static Parent parentRoot;
    public static NodeObj kiosk;
    //contains all the node objects from the entity
    public static ListOfNodeObjs nodeMap;
    public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    //contains all the employee
    public static ArrayList<Employee> employees;
    //contains all the messages
    public static JanitorService janitorService;


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //set up service request
        janitorService = new JanitorService();
        //set up space for database
        File test = new File("mapDB");
        deleteDir(test);
        Class.forName(DRIVER);
        //get the connection for the database
        Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);
        Statement statement = connection.createStatement();
        //run the database
        try {
            CreateDB.run();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //for each of our csv files, read them in and fill their data to one of two tables
        //the node table,edge table, or employee table
        try {
            ReadCSV.runNode("EmeraldElbowWitches/src/model/docs/Nodes.csv");
            ReadCSV.runEdge("EmeraldElbowWitches/src/model/docs/Edges.csv");
            ReadCSV.runEmployee("EmeraldElbowWitches/src/model/docs/Employees.csv");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        //from this csv,generate all of the nodes that will be on the map
        String tablename = "nodeTable";
        statement.executeQuery("SELECT * FROM " + tablename);

        // creates and saves the list of nodes for a map
        ArrayList<Node> listOfNodes = new ArrayList<Node>();
        listOfNodes = QueryDB.getNodes();

        // create a list of all the node objects for a map
        ArrayList<NodeObj> loNodeObj = new ArrayList<NodeObj>();
        for (Node n:listOfNodes) {
            loNodeObj.add(new NodeObj(n));
        }

        //this has all of the current nodes from the database and is useful for adding and deleting the

        nodeMap = new ListOfNodeObjs(loNodeObj);

        // creates and saves the list of edges for a map
        ArrayList<Edge> listOfEdges = new ArrayList<Edge>();
        listOfEdges = QueryDB.getEdges();

        // create edge objects
        //for every edge in the database
        //create the corrisponding edge object and place it into the corrisponding node
        //automatically set the weight for the node by the distance in pixels between noes
        for(Edge edge:listOfEdges){
            EdgeObj newObj = new EdgeObj(edge.getNodeAID(), edge.getNodeBID(), edge.getEdgeID());
            if(nodeMap.pair(newObj)){
                newObj.setWeight(newObj.genWeightFromDistance());
            }
        }

        //creates and saves the list of employees
        ArrayList<Employee> listOfEmployees = new ArrayList<Employee>();
        listOfEmployees = QueryDB.getEmployees();

        //get the kiosk for the assigned floor
        try {
            kiosk = nodeMap.getNearestNeighborFilter(2460, 910);
        }catch(InvalidNodeException e){
            e.printStackTrace();
        }

        System.out.println("Default x: " + kiosk.node.getxLoc() + " Default y: " + kiosk.node.getyLoc());
        //keep this at the end
        //launches fx file and allows for pathfinding to be done
        //What Works: All Nodes are added from the CSV files
        //All Edges are added from the CSV files
        //All Weights Have Been Computed for All Nodes
        //getDistToGoal has been removed and replaced with NodeObj.getDistance(goal)
        javafx.application.Application.launch(args);
    }


    //taken from https://stackoverflow.com/questions/12835285/create-directory-if-exists-delete-directory-and-its-content-and-create-new-one
    public static boolean deleteDir(File dir) {
        //clear the database for every time the system is run
        //recursively delete everything
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    //this sets the stage for the application,
    //running the fxml file to open the UI
    //and handing control to the controller
    @Override
    public void start(Stage primaryStage) throws Exception{

        this.currStage = primaryStage;
        primaryStage.setTitle("Map");
        Scene Start = new Scene(FXMLLoader.load(getClass().getResource("../view/ui/ServiceRequest.fxml")), sceneWidth, sceneHeight);
        currScene=Start;

        Scene ServReq = new Scene(FXMLLoader.load(getClass().getResource("../view/ui/UI_v1.fxml")), sceneWidth, sceneHeight);
        Service = ServReq;
        this.currScene=Start;
        primaryStage.setScene(Start);
        primaryStage.show();
    }

    //do a graceful exit: when the close button is clicked at the top of the map
    //add everything to the database tables,
    //recreate the csv files, allowing for persistance
    @Override
    public void stop() throws SQLException {
        for(NodeObj n : nodeMap.getNodes()){
            for(EdgeObj e : n.getListOfEdgeObjs()){
                AddDB.addEdge(e.objToEntity());
            }
            AddDB.addNode(n.getNode());
        }
        try {
            WriteNodes.runNodes();
            WriteEdges.runEdges();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    //this allows for access from main by the controller
//this will be modified to use simpleton methodologies
    public static NodeObj getKiosk() {
        return kiosk;
    }

    public static ListOfNodeObjs getNodeMap() {
        return nodeMap;
    }

    public static Scene getCurrScene() {
        return currScene;
    }

    public static Stage getCurrStage() {
        return currStage;
    }

    public static Parent getParentRoot() {
        return parentRoot;
    }
    public static ArrayList<Employee> getEmployee(){
        return employees;
    }

    public static Scene getService() {
        return Service;
    }

    public static void setKiosk(NodeObj kiosk) {
        Main.kiosk = kiosk;
    }

    public static JanitorService getJanitorService() {
        return janitorService;
    }
    //this runs the survice request
    public static void setJanitorService(JanitorService janitorService) {
        Main.janitorService = janitorService;
    }
}
