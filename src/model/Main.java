package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Main {
    public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        File test = new File("mapDB");
        deleteDir(test);
        Class.forName(DRIVER);
        Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);
        Statement statement = connection.createStatement();
        try {
            CreateDB.run();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            ReadCSV.runNode("src/model/docs/MapENodes.csv");
            ReadCSV.runEdge("src/model/docs/MapEEdges.csv");
            ReadCSV.runNode("src/model/docs/MapShapiroNodes.csv");
            ReadCSV.runEdge("src/model/docs/MapShapiroEdges.csv");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
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

        ListOfNodeObjs nodeMap = new ListOfNodeObjs(loNodeObj);

        // creates and saves the list of edges for a map
        ArrayList<Edge> listOfEdges = new ArrayList<Edge>();
        listOfEdges = QueryDB.getEdges();

        // create edge objects
        for(Edge edge:listOfEdges){
            EdgeObj newObj = new EdgeObj(edge.getNodeAID(), edge.getNodeBID());
            if(nodeMap.pair(newObj)){
                newObj.setWeight(newObj.genWeightFromDistance());
            }
        }
    }


    //taken from https://stackoverflow.com/questions/12835285/create-directory-if-exists-delete-directory-and-its-content-and-create-new-one
    public static boolean deleteDir(File dir) {
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

}