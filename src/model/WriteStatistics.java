package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class WriteStatistics {
    public static final String JDBC_URL = "jdbc:derby:mapDB;create=true";

    /*
    * runStatistics takes all rows from the STATISTICTABLE in our database and saves it to our statistics csv file.
     */
    public static void runJanitorStatistic() throws SQLException, IOException {

        try {
            Connection conn = DriverManager.getConnection(JDBC_URL);
            String query = "select DISTINCT * from JANITORSTATISTICTABLE";
            String filename = "src/model/docs/JanitorStatistics.csv";
            File file = new File(filename);
            FileWriter fw = new FileWriter(file);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            fw.append("# of Supplies Used \n");
            while (rs.next()) {
                    fw.append(rs.getString(1));
                    fw.append('\n');
            }
            fw.flush();
            fw.close();
            conn.close();
            System.out.println("Statistics CSV File is created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void runCafeteriaStatistic() throws SQLException, IOException {

        try {
            Connection conn = DriverManager.getConnection(JDBC_URL);
            String query = "select DISTINCT * from CAFETERIASTATISTICTABLE";
            String filename = "src/model/docs/CafeteriaStatistics.csv";
            File file = new File(filename);
            FileWriter fw = new FileWriter(file);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            fw.append("# of Orders\n");
            while (rs.next()) {
                fw.append(rs.getString(1));
                fw.append('\n');
            }
            fw.flush();
            fw.close();
            conn.close();
            System.out.println("Statistics CSV File is created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void runInterpreterStatistic() throws SQLException, IOException {

        try {
            Connection conn = DriverManager.getConnection(JDBC_URL);
            String query = "select DISTINCT * from INTERPRETERSTATISTICTABLE";
            String filename = "src/model/docs/InterpreterStatistics.csv";
            File file = new File(filename);
            FileWriter fw = new FileWriter(file);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            fw.append("language,# of Interpreters,average time taken \n");
            while (rs.next()) {
                fw.append(rs.getString(1));
                fw.append(',');
                fw.append(rs.getString(2));
                fw.append(',');
                fw.append(rs.getString(3));
                fw.append('\n');
            }
            fw.flush();
            fw.close();
            conn.close();
            System.out.println("Statistics CSV File is created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
