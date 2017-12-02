package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;


public class ReadCSV {

    final static int NUMROWSNODE = 9;
    final static int NUMROWSEDGE = 3;
    final static int NUMROWSEMPLOYEE = 6;
    final static int NUMROWSREQUEST = 4;
    final static String EDGETABLE = "edgeTable";
    final static String NODETABLE = "nodeTable";
    final static String EMPLOYEETABLE = "employeeTable";
    final static String REQUESTTABLE = "requestTable";
    public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    public static final String JDBC_URL = "jdbc:derby:mapDB;create=true";


    /*
    *runNode reads a csv file containing a list of nodes from the given path into our table of Node in the database
     */
    public static void runNode(String path) throws ClassNotFoundException, SQLException, FileNotFoundException {
        Class.forName(DRIVER);
        String mapENodes = path;
        File mapENodesCSV = new File(mapENodes);
        readFile(mapENodesCSV, NUMROWSNODE, NODETABLE);
    }

    /*
    *runEdge reads a csv file containing a list of nodes from the given path into our table of Edge in the the database
     */
    public static void runEdge(String path) throws ClassNotFoundException, SQLException, FileNotFoundException {
        Class.forName(DRIVER);
        String mapEEdges = path; //ex: "src/model/docs/MapEEdges.csv"
        File mapEEdgesCSV = new File(mapEEdges);
        readFile(mapEEdgesCSV, NUMROWSEDGE, EDGETABLE);
    }

    /*
    *runEmployee reads a csv file containing a list of employees from the given path into our table of Employees in the the database
     */
    public static void runEmployee(String path) throws ClassNotFoundException, SQLException, FileNotFoundException{
        Class.forName(DRIVER);
        String mapEEmployees = path; //ex: "src/model/docs/MapEEdges.csv"
        File mapEEmployeeCSV = new File(mapEEmployees);
        readFile(mapEEmployeeCSV, NUMROWSEMPLOYEE, EMPLOYEETABLE);
    }

    /*
    *runEmployee reads a csv file containing a list of employees from the given path into our table of Employees in the the database
     */
    public static void runRequest(String path) throws ClassNotFoundException, SQLException, FileNotFoundException{
        Class.forName(DRIVER);
        String mapERequests = path; //ex: "src/model/docs/MapEEdges.csv"
        File mapERequestsCSV = new File(mapERequests);
        readFile(mapERequestsCSV, NUMROWSEMPLOYEE, EMPLOYEETABLE);
    }

    /*
    * readFile takes a generic file to read, number of expected columns, and the destination table, and reads all data from the file into the database
     */
    private static void readFile(File fileToRead, int numColExpected, String destTable) throws ClassNotFoundException, SQLException, FileNotFoundException {
        Connection connection = DriverManager.getConnection(JDBC_URL);

        String[] newRow = new String[numColExpected];

        try {
            Scanner inputStream = new Scanner(fileToRead);
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

                String buildSQLStr = " VALUES ("; //build the sql template
                for (int numCol = 0; numCol < numColExpected - 1; numCol++) {
                    buildSQLStr = buildSQLStr + "?,";
                }
                buildSQLStr = buildSQLStr + "?)";

                String SQL = "INSERT INTO " + destTable + buildSQLStr; //insert row into database

                PreparedStatement pState = connection.prepareStatement(SQL);
                for (int eachCol = 0; eachCol < numColExpected; eachCol++) {
                    pState.setString(eachCol + 1, newRow[eachCol]);
                }
                pState.executeUpdate();
                pState.close();

            }
            inputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
