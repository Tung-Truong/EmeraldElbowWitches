package model;

import java.sql.*;

public class DeleteDB {
    public static final String JDBC_URL = "jdbc:derby:mapDB;create=true";

    public static void delNode(String delNodeID) throws SQLException {
        String DEL_NODE;
        Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);
        Statement statement = connection.createStatement();
        DEL_NODE = "DELETE FROM NODETABLE WHERE nodeID = '" + delNodeID + "'";
        statement.executeUpdate(DEL_NODE);
        statement.close();
    }

    public static void delEdge(String delEdgeID) throws SQLException {
        String DEL_EDGE = null;
        Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);
        Statement statement = connection.createStatement();
        DEL_EDGE = "DELETE FROM EDGETABLE WHERE edgeID = '" + delEdgeID + "'";
        statement.executeUpdate(DEL_EDGE);
        statement.close();
    }

    public static void delEmployee(String delEmployeeEmail) throws SQLException {
        String DEL_EMPLOYEE = null;
        Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);
        Statement statement = connection.createStatement();
        DEL_EMPLOYEE = "DELETE FROM EMPLOYEETABLE WHERE email = '" + delEmployeeEmail + "'";
        statement.executeUpdate(DEL_EMPLOYEE);
        statement.close();
    }
}
