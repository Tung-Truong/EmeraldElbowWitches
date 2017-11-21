package model;

import java.sql.*;
import java.util.ArrayList;

public class QueryDB {

    public static final String GET_NODES = "select DISTINCT * from nodeTable";
    public static final String GET_EDGES = "select DISTINCT * from edgeTable";

    public static ArrayList<Node> getNodes() throws SQLException {
        ArrayList<Node> nodeList = new ArrayList<Node>();
        Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(GET_NODES);
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();
        while (resultSet.next()) {
            String[] tempVals = new String[columnCount];
            for (int x = 1; x < columnCount+1; x++) {
                tempVals[x-1] = resultSet.getString(x);
            }
            if(tempVals[1] != null)
                System.out.println("" + tempVals[0]);
                nodeList.add(new Node(tempVals[1], tempVals[2], tempVals[3], tempVals[4], tempVals[5], tempVals[6], tempVals[7], tempVals[8], tempVals[0]));
        }
        for (Node nodeN:nodeList) {
            System.out.println("Node: " + nodeN.getNodeID());
            //rewrite export object attributes to its designated row
        }
        if (statement != null) statement.close();
        if (connection != null) connection.close();
        return nodeList;
    }

    public static ArrayList<Edge> getEdges() throws SQLException{
        ArrayList<Edge> edgeList = new ArrayList<Edge>();
        Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(GET_EDGES);
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();
        while(resultSet.next()){
            String[] tempVals = new String[columnCount];
            for(int colInd = 1; colInd < columnCount+1; colInd++){
                tempVals[colInd-1] = resultSet.getString(colInd);
            }
            if(tempVals[0] != null){
                edgeList.add(new Edge(tempVals[1], tempVals[2], tempVals[0]));
            }
        }
        for(Edge edge:edgeList){
            System.out.println("Edge ID:" + edge.getEdgeID() + " Start:" + edge.getNodeAID() + " End:" + edge.getNodeBID());
        }
        if(statement != null){
            statement.close();
        }
        if(connection != null){
            connection.close();
        }
        return edgeList;
    }
}
