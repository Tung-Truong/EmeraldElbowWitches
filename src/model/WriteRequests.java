package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class WriteRequests {
    public static final String JDBC_URL = "jdbc:derby:mapDB;create=true";

    /*
    * runEdges takes all rows from the REQUESTTABLE in our database and saves it to our edge csv file.
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
            fw.append("employeeId, requestType, isActive, timeSubmitted, location\n");
            while (rs.next()) {
                fw.append(rs.getString(1));
                fw.append(',');
                fw.append(rs.getString(2));
                fw.append(',');
                fw.append(rs.getString(3));
                fw.append(',');
                fw.append(rs.getString(4));
                fw.append(',');
                fw.append(rs.getString(5));
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
