package model;

import java.sql.*;

public class DeleteDB {
    public static final String JDBC_URL = "jdbc:derby:mapDB;create=true";

    /**
     * Removes a node from the database.
     * @param delNodeID This is the node to be deleted.
     * @throws SQLException Throws an SQL exception.
     */
    public static void delNode(String delNodeID) throws SQLException {
        String DEL_NODE;
        Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);
        Statement statement = connection.createStatement();
        DEL_NODE = "DELETE FROM NODETABLE WHERE nodeID = '" + delNodeID + "'";
        statement.executeUpdate(DEL_NODE);
        statement.close();
        delNodeInfo(DEL_NODE);
    }

    /**
     * Removes node information from database.
     * @param delNodeID Node whose info is to be deleted.
     * @throws SQLException Throws an SQL exception.
     */
    public static void delNodeInfo(String delNodeID) throws SQLException {
        String DEL_NODE;
        Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);
        Statement statement = connection.createStatement();
        DEL_NODE = "DELETE FROM NODEINFOTABLE WHERE nodeID = '" + delNodeID + "'";
        statement.executeUpdate(DEL_NODE);
        statement.close();
    }

    /**
     * Removes an edge from the database.
     * @param delEdgeID This is the edge to be deleted.
     * @throws SQLException Throws an SQL exception.
     */
    public static void delEdge(String delEdgeID) throws SQLException {
        String DEL_EDGE = null;
        Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);
        Statement statement = connection.createStatement();
        DEL_EDGE = "DELETE FROM EDGETABLE WHERE edgeID = '" + delEdgeID + "'";
        statement.executeUpdate(DEL_EDGE);
        statement.close();
    }

    /**
     * Removes an employee from the database.
     * @param delEmployeeEmail This is the employee to be deleted.
     * @throws SQLException Throws an SQL exception.
     */
    public static void delEmployee(String delEmployeeEmail) throws SQLException {
        String DEL_EMPLOYEE = null;
        Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);
        Statement statement = connection.createStatement();
        DEL_EMPLOYEE = "DELETE FROM EMPLOYEETABLE WHERE email = '" + delEmployeeEmail + "'";
        statement.executeUpdate(DEL_EMPLOYEE);
        statement.close();
    }

    /**
     * Deletes request from the database.
     * @param delRequest Request to be deleted.
     * @throws SQLException Throws SQL exception.
     */
    public static void delRequest(Date delRequest) throws SQLException {
        String DEL_REQUEST = null;
        Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);
        Statement statement = connection.createStatement();
        DEL_REQUEST = "DELETE FROM REQUESTTABLE WHERE date = '" + delRequest + "'";
        statement.executeUpdate(DEL_REQUEST);
        statement.close();
    }
}
