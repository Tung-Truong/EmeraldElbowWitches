package sample;

import java.sql.*;
import java.util.ArrayList;

public class QueryDB {
    public static final String SQL_STATEMENT = "select * from testTable";
    public static void main(String[] args) throws SQLException {
        ArrayList<Node> nodeList = new ArrayList<Node>();
        Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL_STATEMENT);
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();
        for (int x = 1; x <= columnCount; x++) {
        }
        while (resultSet.next()) {
            String[] tempVals = new String[9];
            for (int x = 1; x < columnCount; x++) {
                tempVals[x] = resultSet.getString(x);
            }
            nodeList.add(new Node(tempVals[0], tempVals[1], tempVals[2], tempVals[3], tempVals[4], tempVals[5], tempVals[6], tempVals[7], tempVals[8]));


        }
        for (Node nodeN:nodeList) {
            System.out.println(nodeN.getNodeID());

            //rewrite export object attributes to its designated row
        }
        if (statement != null) statement.close();
        if (connection != null) connection.close();
    }
}
