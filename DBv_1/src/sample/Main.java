package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);
        Statement statement = connection.createStatement();
        try {
            CreateDB.main();
            ReadCSV.main();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String filename;
        filename = "docs/updated.csv";
        String tablename = "testTable";
        statement.executeQuery("SELECT * FROM " + tablename);
    }
}
