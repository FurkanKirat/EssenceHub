package com.essencehub.project.DatabaseOperations.CreateTables;

import com.essencehub.project.DatabaseOperations.DatabaseConnection;

import java.sql.Connection;
import java.sql.Statement;

public class CreateUserTable {

    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                String createTableSQL = "CREATE TABLE IF NOT EXISTS User ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY, "
                        + "name VARCHAR(50) NOT NULL, "
                        + "surname VARCHAR(50) NOT NULL, "
                        + "phoneNumber VARCHAR(15), "
                        + "salary DOUBLE, "
                        + "isAdmin BOOLEAN, "
                        + "birth VARCHAR(50), "
                        + "department VARCHAR(50), "
                        + "email VARCHAR(100), "
                        + "remainingLeaveDays INT, "
                        + "monthlyPerformance VARCHAR(50), "
                        + "bonusSalary DOUBLE, "
                        + "isActive BOOLEAN, "
                        + "password VARCHAR(255) NOT NULL"
                        + "imageLocation VARCHAR(255)"
                        + ");";
                try (Statement statement = connection.createStatement()) {
                    statement.execute(createTableSQL);
                    System.out.println("User table created successfully.");
                }
            } else {
                System.out.println("Database connection failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
