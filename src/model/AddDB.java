package model;

import model.CreateDB;
import model.Node;
import model.Edge;

import java.sql.*;

public class AddDB {
    public static final String JDBC_URL = "jdbc:derby:mapDB;create=true";

    // Generate Nodes from database using CSV files
    public static void addNode(Node addNode) throws SQLException {

        Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);

        // Input values from CSV files based on data between commas (Format: NodeID, X location, Y location, Floor No., Node Type, Long Name, Short Name, Team Assigned)
        String buildSQLStr = " VALUES ('" + addNode.getNodeID() + "','" +
                addNode.getxLoc() + "','" + addNode.getyLoc() + "','" + addNode.getFloor() + "','"
                + addNode.getBuilding() + "','" + addNode.getNodeType() + "','" + addNode.getLongName() + "','"
                + addNode.getShortName() + "','" + addNode.getTeam() + "')"; //build the sql template

        String SQL = "INSERT INTO NODETABLE" + buildSQLStr; //insert row into database
        try {
            PreparedStatement pState = connection.prepareStatement(SQL);
            pState.executeUpdate();
            pState.close();
        } catch (SQLSyntaxErrorException e) {
            e.printStackTrace();
        }
    }

    // Generate Edges from database using CSV files
    public static void addEdge(Edge addEdge) throws SQLException {

        Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);

        String buildSQLStr = " VALUES ('" + addEdge.getEdgeID() + "','" +
                addEdge.getNodeAID() + "','" + addEdge.getNodeBID() + "')"; //build the sql template

        String SQL = "INSERT INTO EDGETABLE" + buildSQLStr; //insert row into database

        PreparedStatement pState = connection.prepareStatement(SQL);
        pState.executeUpdate();
        pState.close();
    }

    public static void addEmployee(Employee addEmployee) throws SQLException {

        Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);

        String buildSQLStr = " VALUES ('" + addEmployee.getEmail() + "','" +
                addEmployee.getFirstName() + "','" + addEmployee.getLastName() + "','" + addEmployee.getDepartment() + "','" + addEmployee.getLanguage() + "','" + addEmployee.getAvailability() + "','" + addEmployee.getUsername() + "','" + addEmployee.getPassword() + "')"; //build the sql template

        String SQL = "INSERT INTO EMPLOYEETABLE" + buildSQLStr; //insert row into database

        PreparedStatement pState = connection.prepareStatement(SQL);
        pState.executeUpdate();
        pState.close();
    }

    public static void addRequest(ServiceRequest addService) throws SQLException {

        Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);

        String buildSQLStr = "";

        if (addService.getAssigned() != null) {
            buildSQLStr = " VALUES ('" + addService.getAssigned().getId() + "','" +
                    addService.getClass().toString() + "','" + addService.isActive() +
                    "','" + addService.getSent() + "')"; //build the sql template
        } else {
            buildSQLStr = " VALUES ('" + addService.getAssigned() + "','" +
                    addService.getClass().toString() + "','" + addService.isActive() +
                    "','" + addService.getSent() + "')"; //build the sql template
        }

        String SQL = "INSERT INTO REQUESTTABLE" + buildSQLStr; //insert row into database

        PreparedStatement pState = connection.prepareStatement(SQL);
        pState.executeUpdate();
        pState.close();
    }

    public static void addNodeInfo(NodeObj addNode) throws SQLException {

        Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);

        // Input values from CSV files based on data between commas (Format: nodeID, numOfPathsAcross, avgLength)
        String buildSQLStr = " VALUES ('" + addNode.getNode().getNodeID() + "','" +
                addNode.getTimesPathed() + "','" + addNode.getAvgPathLen() + "')"; //build the sql template

        String SQL = "INSERT INTO NODEINFOTABLE" + buildSQLStr; //insert row into database
        try {
            PreparedStatement pState = connection.prepareStatement(SQL);
            pState.executeUpdate();
            pState.close();
        } catch (SQLSyntaxErrorException e) {
            e.printStackTrace();
        }
    }

}
