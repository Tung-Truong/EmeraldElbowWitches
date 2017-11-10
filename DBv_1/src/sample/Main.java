package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);
        Statement statement = connection.createStatement();
        CreateDB.main();
        ReadCSV.main();
        String filename;
        filename = "docs/updated.csv";
        String tablename = "testTable";
        Statement.executeQuery("SELECT * INTO OUTFILE \"" + filename + "\" FROM " + tablename);
    }
}
