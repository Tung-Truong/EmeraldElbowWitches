package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;


public class ReadCSV {
    public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    public static final String JDBC_URL = "jdbc:derby:testDB;create=true";
    public static void run() throws ClassNotFoundException, SQLException {
        String fileName = "docs/MapENodes.csv";
        File file = new File(fileName);
        String[] currentRow = new String[9];
        Class.forName(DRIVER);
        Connection connection = DriverManager.getConnection(JDBC_URL);
        try {
            Scanner inputStream = new Scanner(file);
            inputStream.useDelimiter(",|\\n");

            for (int p = 0; p <= 8; p++) inputStream.next();


            for (int rowsAdded = 0; rowsAdded <= 57; rowsAdded++) {
                int i = 0;
                while (inputStream.hasNext() && (i <= 8)) {
                    String data = inputStream.next();
                    currentRow[i] = data;
                    i++;
                }
                String SQL = "INSERT INTO testTable VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pState = connection.prepareStatement(SQL);
                pState.setString(1, currentRow[0]);
                pState.setString(2, currentRow[1]);
                pState.setString(3, currentRow[2]);
                pState.setString(4, currentRow[3]);
                pState.setString(5, currentRow[4]);
                pState.setString(6, currentRow[5]);
                pState.setString(7, currentRow[6]);
                pState.setString(8, currentRow[7]);
                pState.setString(9, currentRow[8]);
                pState.executeUpdate();
                pState.close();
                currentRow = new String[9];

            }
            inputStream.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
/*
        "(2175, 910, '1', '45 Francis', 'HALL', 'Hallway Connector 2 Floor 1', 'HallwayW0201', 'Team E', 'EHALL00201')");

*/
    }
}
