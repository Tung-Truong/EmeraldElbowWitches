package HealthAPI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CreateDB2{
    public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    public static final String JDBC_URL = "jdbc:derby:HealthAPI/Healthcare/HealthDB;create=true";

    public static void run() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        Connection connection = DriverManager.getConnection(JDBC_URL);
        connection.createStatement().execute("create table requestTable2(email VARCHAR(255), MessageHeader VARCHAR(255), Provider VARCHAR(255), Name VARCHAR(255), timeSubmitted VARCHAR(255))");
        System.out.print("The table has been built!");
    }
}
