package controller;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;
import model.*;
import model.AddDB;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class Main extends Application {

    //get height of application
    public static int sceneWidth = 1400;
    public static int sceneHeight = 900;
    public static Scene patientScene;
    public static Scene adminScene;
    public static Scene Service;
    public static Stage currStage;
    public static Parent parentRoot;
    public static NodeObj kiosk;        // default location of the starting point for pathfinding
    //contains all the node objects from the entity
    public static ListOfNodeObjs nodeMap;
    public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    //contains all the employee
    public static ArrayList<Employee> employees;
    //contains all the messages
    public static JanitorService janitorService;
    public static ControllerListener controllers;



    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //set up service request
        janitorService = new JanitorService();
        //set up space for database
        File test = new File("mapDB");
        Class.forName(DRIVER);
        //get the connection for the database
        Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);
        Statement statement = connection.createStatement();
        //run the database

        ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM SYS.SYSTABLES WHERE TABLETYPE = 'T'");
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        if (resultSet.next() && resultSet.getInt(1) < 1) {//if DB not yet created
            try {
                CreateDB.run();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        //for each of our csv files, read them in and fill their data to one of two tables
        //the node table,edge table, or employee table
        try {
            File nodeCSVTest = new File("src/model/docs/Nodes.csv");
            File edgeCSVTest = new File("src/model/docs/Edges.csv");
            if(nodeCSVTest.exists() && edgeCSVTest.exists()){           //if map has been edited, load edited files
                ReadCSV.runNode("src/model/docs/Nodes.csv");
                ReadCSV.runEdge("src/model/docs/Edges.csv");
            }else {
                ReadCSV.runNode("src/model/docs/MapAnodes.csv");
                ReadCSV.runNode("src/model/docs/MapBnodes.csv");
                ReadCSV.runNode("src/model/docs/MapCnodes.csv");
                ReadCSV.runNode("src/model/docs/MapDnodes.csv");
                ReadCSV.runNode("src/model/docs/MapENodes.csv");
                ReadCSV.runNode("src/model/docs/MapFNodes.csv");
                ReadCSV.runNode("src/model/docs/MapGNodes.csv");
                ReadCSV.runNode("src/model/docs/MapHnodes.csv");
                ReadCSV.runNode("src/model/docs/MapInodes.csv");
                ReadCSV.runNode("src/model/docs/MapWnodes.csv");

                ReadCSV.runEdge("src/model/docs/MapAedges.csv");
                ReadCSV.runEdge("src/model/docs/MapBedges.csv");
                ReadCSV.runEdge("src/model/docs/MapCedges.csv");
                ReadCSV.runEdge("src/model/docs/MapDedges.csv");
                ReadCSV.runEdge("src/model/docs/MapEEdges.csv");
                ReadCSV.runEdge("src/model/docs/MapFEdges.csv");
                ReadCSV.runEdge("src/model/docs/MapGEdges.csv");
                ReadCSV.runEdge("src/model/docs/MapHedges.csv");
                ReadCSV.runEdge("src/model/docs/MapIedges.csv");
                ReadCSV.runEdge("src/model/docs/MapWedges.csv");
            }
            ReadCSV.runEmployee("src/model/docs/Employees.csv");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
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
        for (Node n : listOfNodes) {
            loNodeObj.add(new NodeObj(n));
        }

        //this has all of the current nodes from the database and is useful for adding and deleting the

        nodeMap = new ListOfNodeObjs(loNodeObj);

        // creates and saves the list of edges for a map
        ArrayList<Edge> listOfEdges;
        listOfEdges = QueryDB.getEdges();

        // assigns and saves employees from the database
        employees = QueryDB.getEmployees();

        // create edge objects
        //for every edge in the database
        //create the corrisponding edge object and place it into the corrisponding node
        //create the corresponding edge object and place it into the corrisponding node
        //automatically set the weight for the node by the distance in pixels between noes
        for (Edge edge : listOfEdges) {
            EdgeObj newObj = new EdgeObj(edge.getNodeAID(), edge.getNodeBID(), edge.getEdgeID());
            if (nodeMap.pair(newObj)) {
                if (((newObj.getNodeA().getNode().getTeam().equals("Team W"))
                        && (newObj.getNodeA().getNode().getNodeType().equals("ELEV"))) &&
                        ((newObj.getNodeB().getNode().getTeam().equals("Team W"))
                                && (newObj.getNodeB().getNode().getNodeType().equals("ELEV")))) {
                    newObj.setWeight(50000);
                } else
                    newObj.setWeight(newObj.genWeightFromDistance());
            }
        }

        //creates and saves the list of employees
        ArrayList<Employee> listOfEmployees = new ArrayList<Employee>();
        listOfEmployees = QueryDB.getEmployees();
        employees = listOfEmployees;
        //get the kiosk for the assigned floor
        try {
            kiosk = nodeMap.getNearestNeighborFilter(2460, 910);
        } catch (InvalidNodeException e) {
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

    //this sets the stage for the application,
    //running the fxml file to open the UI
    //and handing control to the controller
    @Override
    public void start(Stage primaryStage) throws Exception {

        this.controllers = new ControllerListener();

        this.currStage = primaryStage;
        primaryStage.setTitle("Map");

        FXMLLoader patientContLoad = new FXMLLoader(getClass().getClassLoader().getResource("view/ui/Patient.fxml"));
        Scene Start = new Scene(patientContLoad.load(), sceneWidth, sceneHeight);
        PatientController patCont = patientContLoad.getController();
        patientScene = Start;

        this.controllers.addObserver(patCont);

        FXMLLoader adminContLoad = new FXMLLoader(getClass().getClassLoader().getResource("view/ui/Admin.fxml"));
        adminScene = new Scene(adminContLoad.load(), sceneWidth, sceneHeight);
        AdminController adminCont = adminContLoad.getController();

        this.controllers.addObserver(adminCont);

        this.patientScene = Start;
        primaryStage.setScene(Start);
        primaryStage.show();
    }

    public static Scene getAdminScene() {
        return adminScene;
    }

    //do a graceful exit: when the close button is clicked at the top of the map
    //add everything to the database tables,
    //recreate the csv files, allowing for persistence
    @Override
    public void stop() throws SQLException {
        for (NodeObj n : nodeMap.getNodes()) {
            for (EdgeObj e : n.getListOfEdgeObjs()) {
                AddDB.addEdge(e.objToEntity());
            }
            AddDB.addNode(n.getNode());
        }
        for (Employee e : employees) {
            AddDB.addEmployee(e);
        }
        try {
            WriteNodes.runNodes();
            WriteEdges.runEdges();
            WriteEmployees.runEmployees();
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

    public static Scene getPatientScene() {
        return patientScene;
    }

    public static Stage getCurrStage() {
        return currStage;
    }

    public static Parent getParentRoot() {
        return parentRoot;
    }

    public static Scene getService() {
        return Service;
    }

    public static ArrayList<Employee> getEmployees(){
        return employees;
    }

    public static void setKiosk(NodeObj kiosk) {
        Main.kiosk = kiosk;
    }

    public static JanitorService getJanitorService() {
        return janitorService;
    }

    public static ControllerListener getControllers() {
        return controllers;
    }

    //this runs the service request
    public static void setJanitorService(JanitorService janitorService) {
        Main.janitorService = janitorService;
    }
}
