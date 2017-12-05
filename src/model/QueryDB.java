package model;

import java.sql.*;
import java.util.ArrayList;

public class QueryDB {

    public static final String GET_NODES = "select DISTINCT * from nodeTable";
    public static final String GET_EDGES = "select DISTINCT * from edgeTable";
    public static final String GET_EMPLOYEES = "select DISTINCT * from employeeTable";
    public static final String GET_REQUESTS = "select DISTINCT * from requestTable";

    public static ArrayList<Node> getNodes() throws SQLException {
        ArrayList<Node> nodeList = new ArrayList<Node>();
        Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(GET_NODES);
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();
        while (resultSet.next()) {
            String[] tempVals = new String[columnCount];
            for (int x = 1; x < columnCount + 1; x++) {
                tempVals[x - 1] = resultSet.getString(x);
            }
            nodeList.add(new Node(tempVals[1], tempVals[2], tempVals[3], tempVals[4], tempVals[5], tempVals[6], tempVals[7], tempVals[8], tempVals[0]));
        }
        if (statement != null) statement.close();
        if (connection != null) connection.close();
        return nodeList;
    }

    public static ArrayList<Edge> getEdges() throws SQLException {
        ArrayList<Edge> edgeList = new ArrayList<Edge>();
        Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(GET_EDGES);
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();
        while (resultSet.next()) {
            String[] tempVals = new String[columnCount];
            for (int colInd = 1; colInd < columnCount + 1; colInd++) {
                tempVals[colInd - 1] = resultSet.getString(colInd);
            }
            if (tempVals[0] != null) {
                edgeList.add(new Edge(tempVals[1], tempVals[2], tempVals[0]));
            }
        }
        if (statement != null) {
            statement.close();
        }
        if (connection != null) {
            connection.close();
        }
        return edgeList;
    }

    public static ArrayList<Employee> getEmployees() throws SQLException{
        ArrayList<Employee> employeeList = new ArrayList<Employee>();
        Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(GET_EMPLOYEES);
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();
        while(resultSet.next()){
            String[] tempVals = new String[columnCount];
            for(int colInd = 1; colInd < columnCount+1; colInd++){
                tempVals[colInd-1] = resultSet.getString(colInd);
            }
            if(tempVals[0] != null){
                employeeList.add(new Employee(tempVals[0], tempVals[1], tempVals[2],tempVals[3], tempVals[4],
                        tempVals[5], tempVals[6], tempVals[7]));
            }
        }
        if(statement != null){
            statement.close();
        }
        if(connection != null){
            connection.close();
        }
        return employeeList;
    }

    public static ArrayList<ServiceRequest> getRequests() throws SQLException{
        ArrayList<ServiceRequest> requestList = new ArrayList<ServiceRequest>();
        Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(GET_REQUESTS);
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();
        while(resultSet.next()){
            String[] tempVals = new String[columnCount];
            for(int colInd = 1; colInd < columnCount+1; colInd++){
                tempVals[colInd-1] = resultSet.getString(colInd);
            }
            if(tempVals[0] != null){
                requestList.add(new ServiceRequest() {
                    @Override
                    public String generateReport() {  return "x";  }
                });
            }
        }
        if(statement != null){
            statement.close();
        }
        if(connection != null){
            connection.close();
        }
        return requestList;
    }
}
