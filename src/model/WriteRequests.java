package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class WriteRequests {
    public static final String JDBC_URL = "jdbc:derby:mapDB;create=true";

    /**
     * Uses the getParent function of nodes and adds the parent to the path and stops when the parent is the start node.
     * @param goal Ending node.
     * @param start Starting node.
     * @return ArrayList of Node objects.
     */
    public static void runRequests() throws SQLException, IOException {

        try {
            Connection conn = DriverManager.getConnection(JDBC_URL);
            String query = "select DISTINCT * from requestTable";
            String filename = "src/model/docs/ServiceRequests.csv";
            File file = new File(filename);
            FileWriter fw = new FileWriter(file);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            fw.append("employeeId, requestType, isActive, timeSubmitted\n");
            while (rs.next()) {
                fw.append(rs.getString(1));
                fw.append(',');
                fw.append(rs.getString(2));
                fw.append(',');
                fw.append(rs.getString(3));
                fw.append(',');
                fw.append(rs.getString(4));
                fw.append('\n');
            }
            fw.flush();
            fw.close();
            conn.close();
            System.out.println("CSV File is created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
