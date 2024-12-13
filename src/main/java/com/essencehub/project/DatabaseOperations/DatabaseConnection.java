package com.essencehub.project.DatabaseOperations;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() {
        String url = "jdbc:mysql://34.65.64.126:3306/BusinessProject?user=fatihpehlivan&password=ruhi1234";
        try {

            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}
