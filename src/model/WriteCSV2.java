package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class WriteCSV2 {
    public static final String JDBC_URL = "jdbc:derby:mapDB;create=true";

    public static void runEdges() throws SQLException, IOException {
        try {
            String query = "SELECT DISTINCT * FROM EDGETABLE";
            String filename = "src/model/docs/Edges.csv";
            File file = new File(filename);
            FileWriter fw = new FileWriter(file);
            ArrayList<String> noRepeat = new ArrayList();

            Connection conn = DriverManager.getConnection(JDBC_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String startNode = rs.getString(2);
                String endNode = rs.getString(3);

                String pathAtoB = startNode + "_" + endNode;
                String pathBtoA = endNode + "_" + startNode;

                if (noRepeat.contains(pathAtoB) || noRepeat.contains(pathBtoA)) {
                    return;
                } else {
                    noRepeat.add(rs.getString(1));
                }
            }

            conn.close();
            System.out.println("NoRepeat list made!");

            Connection conn2 = DriverManager.getConnection(JDBC_URL);
            Statement stmt2 = conn2.createStatement();
            ResultSet rs2 = stmt2.executeQuery(query);

            System.out.println("Second connection made!");
            fw.append("edgeID,startNode,endNode\n");
            while (rs2.next()) {
                if (noRepeat.contains(rs2.getString(1))) {
                    fw.append(rs2.getString(1));
                    fw.append(',');
                    fw.append(rs2.getString(2));
                    fw.append(',');
                    fw.append(rs2.getString(3));
                    fw.append('\n');
                }
            }
            fw.flush();
            fw.close();
            conn2.close();
            System.out.println("CSV File is created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
