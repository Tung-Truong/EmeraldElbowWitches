package HealthAPI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddDB2 {
    public static final String JDBC_URL = "jdbc:derby:HealthAPI/Healthcare/HealthDB;create=true";

    public static void addRequest(ServiceRequestHealth addService) throws SQLException {

        Connection connection = DriverManager.getConnection(CreateDB2.JDBC_URL);

        String buildSQLStr = "";
        //Email, MessageHeader, Provider, Name, timeSubmitted
        if (addService != null) {
            buildSQLStr = " VALUES ('" + addService.email + "','" +
                    addService.getMessageHeader() + "','" + addService.provider +
                    "','" + addService.name + "','" + addService.getSent().toString().trim() + "')"; //build the sql template
        }
        String SQL = "INSERT INTO requestTable2" + buildSQLStr; //insert row into database

        PreparedStatement pState = connection.prepareStatement(SQL);
        pState.executeUpdate();
        pState.close();
    }



}
