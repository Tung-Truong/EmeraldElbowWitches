package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName(DRIVER);
        Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);
        Statement statement = connection.createStatement();
        try {
            CreateDB.main();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
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
