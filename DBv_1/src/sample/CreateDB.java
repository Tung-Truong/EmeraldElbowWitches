package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CreateDB {
    public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    public static final String JDBC_URL = "jdbc:derby:testDB;create=true";
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        Connection connection = DriverManager.getConnection(JDBC_URL);
        connection.createStatement().execute("create table testTable(xLoc VARCHAR(255), yLoc VARCHAR(255), floor VARCHAR(255), building VARCHAR(255), nodeType VARCHAR(255), shortName VARCHAR(255), longName VARCHAR(255), team VARCHAR(255), nodeID VARCHAR(255))");
        System.out.print("The table has been built!");
    }
}
