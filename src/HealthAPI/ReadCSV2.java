package HealthAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;


public class ReadCSV2 {

    final static int NUMROWSREQUEST = 5;


    final static String REQUESTTABLE = "requestTable2";
    public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    public static final String JDBC_URL = "jdbc:derby:HealthAPI/Healthcare/HealthDB;create=true";

    /*
    *runEmployee reads a csv file containing a list of employees from the given path into our table of Employees in the the database
     */
    public static void runRequest(String path) throws ClassNotFoundException, SQLException, FileNotFoundException{
        Class.forName(DRIVER);
        String mapERequests = path; //ex: "src/model/docs/MapEEdges.csv"
        File mapERequestsCSV = new File(mapERequests);
        readFile(mapERequestsCSV, NUMROWSREQUEST, REQUESTTABLE);
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
