package model;

import java.sql.*;
import java.util.ArrayList;

public class QueryDB {
    public static final String SQL_STATEMENT = "select * from testTable";
    public static ArrayList<Node> run() throws SQLException {
        ArrayList<Node> nodeList = new ArrayList<Node>();
        Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL_STATEMENT);
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();
        for (int x = 1; x <= columnCount+1; x++) {
        }
        while (resultSet.next()) {
            String[] tempVals = new String[10];
            for (int x = 1; x < columnCount+1; x++) {
                tempVals[x] = resultSet.getString(x);
            }
            if(tempVals[1] != null)
                nodeList.add(new Node(tempVals[2], tempVals[3], tempVals[4], tempVals[5], tempVals[6], tempVals[7], tempVals[8], tempVals[9], tempVals[1]));


        }
        for (Node nodeN:nodeList) {
            System.out.println(nodeN.getNodeID());

            //rewrite export object attributes to its designated row
        }
        if (statement != null) statement.close();
        if (connection != null) connection.close();
        return nodeList;
    }
}
