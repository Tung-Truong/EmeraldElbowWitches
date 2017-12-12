package model;

import java.sql.*;

public class DeleteDB {
    public static final String JDBC_URL = "jdbc:derby:mapDB;create=true";

    // remove a node from the database
    public static void delNode(String delNodeID) throws SQLException {
        String DEL_NODE;
        Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);
        Statement statement = connection.createStatement();
        DEL_NODE = "DELETE FROM NODETABLE WHERE nodeID = '" + delNodeID + "'";
        statement.executeUpdate(DEL_NODE);
        statement.close();
        delNodeInfo(DEL_NODE);
    }

    public static void delNodeInfo(String delNodeID) throws SQLException {
        String DEL_NODE;
        Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);
        Statement statement = connection.createStatement();
        DEL_NODE = "DELETE FROM NODEINFOTABLE WHERE nodeID = '" + delNodeID + "'";
        statement.executeUpdate(DEL_NODE);
        statement.close();
    }

    // remove an edge from the database
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
        DEL_EMPLOYEE = "DELETE FROM EMPLOYEETABLE WHERE username = '" + delEmployeeEmail + "'";
        statement.executeUpdate(DEL_EMPLOYEE);
        statement.close();
    }

    public static void delAllEmployee() throws SQLException {
        String DEL_EMPLOYEE = null;
        Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);
        Statement statement = connection.createStatement();
        DEL_EMPLOYEE = "DELETE FROM EMPLOYEETABLE";
        statement.executeUpdate(DEL_EMPLOYEE);
        statement.close();
    }

    public static void delRequest(String delRequest) throws SQLException {
        String DEL_REQUEST = null;
        Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);
        Statement statement = connection.createStatement();
        DEL_REQUEST = "DELETE FROM REQUESTTABLE WHERE date = '" + delRequest + "'";
        statement.executeUpdate(DEL_REQUEST);
        statement.close();
    }
}
