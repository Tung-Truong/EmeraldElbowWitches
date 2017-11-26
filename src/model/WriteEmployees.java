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
            String query = "SELECT DISTINCT * FROM EMPLOYEETABLE";
            String filename = "src/model/docs/dummy.csv";
            File file = new File(filename);
            FileWriter fw = new FileWriter(file);
            ArrayList<String> noRepeat = new ArrayList();

            Connection conn = DriverManager.getConnection(JDBC_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);

                String firstThenLast = firstName + "_" + lastName;
                String lastThenFirst = lastName + "_" + firstName;

                if (noRepeat.contains(firstThenLast) || noRepeat.contains(lastThenFirst)) {
                    return;
                } else {
                    noRepeat.add(firstThenLast);
                }
            }

            conn.close();
            System.out.println("NoRepeat list made!");

            Connection conn2 = DriverManager.getConnection(JDBC_URL);
            Statement stmt2 = conn2.createStatement();
            ResultSet rs2 = stmt2.executeQuery(query);

            System.out.println("Second connection made!");
            fw.append("email,firstName,lastName,department,language,availability\n");
            while (rs2.next()) {
                if (noRepeat.contains(rs2.getString(1))) {
                    fw.append(rs2.getString(1));
                    fw.append(',');
                    fw.append(rs2.getString(2));
                    fw.append(',');
                    fw.append(rs2.getString(3));
                    fw.append(',');
                    fw.append(rs2.getString(4));
                    fw.append(',');
                    fw.append(rs2.getString(5));
                    fw.append(',');
                    fw.append(rs2.getString(6));
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
