package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Main {

    public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName(DRIVER);
        Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);
        Statement statement = connection.createStatement();
        try {
            CreateDB.run();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            ReadCSV.run();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String filename;
        filename = "docs/updated.csv";
        String tablename = "testTable";
        statement.executeQuery("SELECT * FROM " + tablename);
        QueryDB qDB = new QueryDB();

        // creates and saves the list of nodes for a map
        ArrayList<Node> listOfNodes = new ArrayList<Node>();
        listOfNodes = qDB.run();

        // create a list of all the node objects for a map
        ArrayList<NodeObj> listOfNodeObj = new ArrayList<NodeObj>();
        for (Node n:listOfNodes) {
            listOfNodeObj.add(new NodeObj(n));
        }
        int didWeGetHere;
    }
}
