package model;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;


public class WriteNodes {
    final static String EDGETABLE = "edgeTable";
    final static String NODETABLE = "nodeTable";
    public static final String JDBC_URL = "jdbc:derby:mapDB;create=true";

    /*
     * runNodes takes all rows from the NODETABLE in our database and saves it to our node csv file.
     */
    public static void runNodes() throws IOException, SQLException, ClassNotFoundException {
        try {
            Connection conn = DriverManager.getConnection(JDBC_URL);
            String query = "select DISTINCT * from NODETABLE";
            String filename = "src/model/docs/Nodes.csv";
            File file = new File(filename);
            FileWriter fw = new FileWriter(file);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            fw.append("nodeID,xcoord,ycoord,floor,building,nodeType,longName,shortName,teamAssigned\n");
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
                fw.append(',');
                fw.append(rs.getString(6));
                fw.append(',');
                fw.append(rs.getString(7));
                fw.append(',');
                fw.append(rs.getString(8));
                fw.append(',');
                fw.append(rs.getString(9));
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