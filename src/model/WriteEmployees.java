package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class WriteEmployees {
    public static final String JDBC_URL = "jdbc:derby:mapDB;create=true";

    /*
    * runEdges takes all rows from the EMPLOYEETABLE in our database and saves it to our edge csv file.
     */
    public static void runEmployees() throws SQLException, IOException {

        try {
            Connection conn = DriverManager.getConnection(JDBC_URL);
            String query = "select DISTINCT * from employeeTable";
            String filename = "src/model/docs/Employees.csv";
            File file = new File(filename);
            FileWriter fw = new FileWriter(file);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            fw.append("email, firstName, lastName, department, language, availability\n");
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
                fw.append('\n');
            }
            fw.flush();
            fw.close();
            conn.close();
            System.out.println("CSV File is created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*try {
            Connection conn = DriverManager.getConnection(JDBC_URL);
            String query = "SELECT DISTINCT * FROM employeeTable";
            String filename = "src/model/docs/Employees1.csv";
            File file = new File(filename);
            FileWriter fw = new FileWriter(file);
            ArrayList<String> noRepeat = new ArrayList();

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("NoRepeat list made!");

            System.out.println("third connection made!");
            fw.append("email,firstName,lastName,department,language,availability\n");
            while (rs.next()) {
                if (noRepeat.contains(rs.getString(1))) {
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
                    fw.append('\n');
                }
            }
            fw.flush();
            fw.close();
            conn.close();
            System.out.println("CSV File is created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    }
}
