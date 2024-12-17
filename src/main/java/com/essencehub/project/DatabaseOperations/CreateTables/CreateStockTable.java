package com.essencehub.project.DatabaseOperations.CreateTables;

import com.essencehub.project.DatabaseOperations.DatabaseConnection;

import java.sql.Connection;
import java.sql.Statement;

public class CreateStockTable {

    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                String createTableSQL = "CREATE TABLE IF NOT EXISTS Stock ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY, "
                        + "name VARCHAR(100) NOT NULL, "
                        + "count INT NOT NULL, "
                        + "buyingDate VARCHAR(50) NOT NULL, "
                        + "sellingDate VARCHAR(50), "
                        + "buyingPrice DOUBLE NOT NULL, "
                        + "sellingPrice DOUBLE"
                        + ");";
                try (Statement statement = connection.createStatement()) {
                    statement.execute(createTableSQL);
                    System.out.println("Stock table created successfully.");
                }
            } else {
                System.out.println("Database connection failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
