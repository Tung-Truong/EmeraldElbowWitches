package HealthAPI;


import java.sql.*;
import java.util.ArrayList;

public class QueryDB2 {

    public static final String GET_REQUESTS = "select DISTINCT * from requestTable2";


    public static ArrayList<ServiceRequestHealth> getRequests() throws SQLException{
        ArrayList<ServiceRequestHealth> requestList = new ArrayList<ServiceRequestHealth>();
        Connection connection = DriverManager.getConnection(CreateDB2.JDBC_URL);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(GET_REQUESTS);
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();
        while(resultSet.next()){
            String[] tempVals = new String[columnCount];
            for(int colInd = 1; colInd < columnCount+1; colInd++){
                tempVals[colInd-1] = resultSet.getString(colInd);
            }
            if(tempVals[0] != null && !tempVals[1].equals("class model.QueryDB2$1")){
                requestList.add(new ServiceRequestHealth(tempVals[0], tempVals[1], tempVals[2], tempVals[3], tempVals[4]) {
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
