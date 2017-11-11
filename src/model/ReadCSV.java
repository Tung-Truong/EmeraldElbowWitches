package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;



public class ReadCSV {
    final static int NUMROWSNODE = 9;
    final static int NUMROWSEDGE = 3;
    final static String EDGETABLE = "edgeTable";
    final static String NODETABLE = "testTable";
    public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    public static final String JDBC_URL = "jdbc:derby:testDB;create=true";


    public static void run() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);

        String mapENodes = "src/model/docs/MapENodes.csv";
        File mapENodesCSV = new File(mapENodes);
        readFile(mapENodesCSV, NUMROWSNODE, NODETABLE);

        String mapEEdges = "src/model/docs/MapEEdges.csv";
        File mapEEdgesCSV = new File(mapEEdges);
        readFile(mapEEdgesCSV, NUMROWSEDGE, EDGETABLE);
    }

    private static void readFile(File fileToRead, int numColExpected, String destTable) throws ClassNotFoundException, SQLException{
        Connection connection = DriverManager.getConnection(JDBC_URL);
        String[] newRow = new String[numColExpected];
        try {
            Scanner inputStream = new Scanner(fileToRead);
            inputStream.useDelimiter(",|\\n");
            for (int p = 0; p < newRow.length; p++){
                inputStream.next();
            }
            for (int rowsAdded = 0; inputStream.hasNext(); rowsAdded++) {
                for(int i = 0; ((inputStream.hasNext()) && (i<newRow.length)); i++){
                    String data = inputStream.next();
                    newRow[i] = data;
                }
                String buildSQLStr = " VALUES (";
                for(int numCol = 0; numCol < numColExpected-1; numCol++){
                    buildSQLStr = buildSQLStr+"?,";
                }
                buildSQLStr = buildSQLStr+"?)";
                String SQL = "INSERT INTO " + destTable + buildSQLStr;
                PreparedStatement pState = connection.prepareStatement(SQL);
                for(int eachCol = 0; eachCol < numColExpected; eachCol++){
                    pState.setString(eachCol+1, newRow[eachCol]);
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
