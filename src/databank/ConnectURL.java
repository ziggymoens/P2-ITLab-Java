package databank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectURL {
    public static void main(String[] args) {

        // Create a variable for the connection string.
        String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=ITLab;user=sa;password=myPassw0rd";

        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement()) {
            String SQL = "SELECT TOP 10 * FROM aankondiging";
            ResultSet rs = stmt.executeQuery(SQL);

            // Iterate through the data in the result set and display it.
           //

            while (rs.next()) {
                System.out.println(rs.getString("naam"));
            }


        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}