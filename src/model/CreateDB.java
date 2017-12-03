package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CreateDB {
    public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    public static final String JDBC_URL = "jdbc:derby:mapDB;create=true";

    public static void run() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        Connection connection = DriverManager.getConnection(JDBC_URL);
        connection.createStatement().execute("create table nodeTable(nodeID VARCHAR(255), xLoc VARCHAR(255), yLoc VARCHAR(255), floor VARCHAR(255), building VARCHAR(255), nodeType VARCHAR(255), shortName VARCHAR(255), longName VARCHAR(255), team VARCHAR(255))");
        connection.createStatement().execute("create table edgeTable(edgeID VARCHAR(255), startNode VARCHAR(255), endNode VARCHAR(255))");
        connection.createStatement().execute("create table employeeTable(email VARCHAR(255), firstName VARCHAR(255), lastName VARCHAR(255), department VARCHAR(255), language VARCHAR(255), availability VARCHAR(255))");
        connection.createStatement().execute("create table requestTable(employeeId, VARCHAR(255), requestType VARCHAR(255), timeSubmitted VARCHAR(255), isActive VARCHAR(255))");
        System.out.print("The table has been built!");
    }
}
