package HealthAPI;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteDB2 {
    public static final String JDBC_URL = "jdbc:derby:HealthAPI/Healthcare/HealthDB;create=true";

    public static void delRequest(String delRequest) throws SQLException {
        String DEL_REQUEST = null;
        Connection connection = DriverManager.getConnection(CreateDB2.JDBC_URL);
        Statement statement = connection.createStatement();
        DEL_REQUEST = "DELETE FROM requestTable2 WHERE timeSubmitted = '" + delRequest.toString().trim() + "'";
        statement.executeUpdate(DEL_REQUEST);
        statement.close();
    }
}
