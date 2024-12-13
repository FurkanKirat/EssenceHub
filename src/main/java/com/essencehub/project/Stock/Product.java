package com.essencehub.project.Stock;

import com.essencehub.project.DatabaseOperations.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Product {
    private String name;
    private String monthAndYear;
    private int count;

    public Product(String name, int count, String monthAndYear) {
        this.name = name;
        this.count = count;
        this.monthAndYear = monthAndYear;
    }

    public String getName(){
        return this.name;
    }
    public String getMonthAndYear(){
        return this.monthAndYear;
    }
    public int getCount(){
        return this.count;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMonthAndYear(String monthAndYear) {
        this.monthAndYear = monthAndYear;
    }

    public void setCount(int count) {
        this.count = count;
    }
// Database Stock Operations

    // Adds a new product to the stock table.
    public static void addProduct(Product product) {
        String insertProductSQL = "INSERT INTO Stock (name, count, month_and_year) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertProductSQL)) {
                    preparedStatement.setString(1, product.getName());
                    preparedStatement.setInt(2, product.getCount());
                    preparedStatement.setString(3, product.getMonthAndYear());

                    int affectedRows = preparedStatement.executeUpdate();

                    if (affectedRows > 0) {
                        System.out.println("The product was added successfully.");
                    } else {
                        System.out.println("Adding product failed.");
                    }
                }
            } else {
                System.out.println("Database connection failed.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding product: " + e.getMessage());
            e.printStackTrace();
        }}

    // Increases the count of the selected object by increaseAmount.
    public static void addStock(Product product, int increaseAmount) {

        //SQL UPDATE query
        String updateStockSQL = "UPDATE Stock SET count = count + ? WHERE name = ? AND month_and_year = ?";

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {

                // Prepare the query with PreparedStatement
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateStockSQL)) {

                    // Set query parameters
                    preparedStatement.setInt(1, increaseAmount); // count + increaseAmount
                    preparedStatement.setString(2, product.getName()); // name
                    preparedStatement.setString(3, product.getMonthAndYear()); // month_and_year

                    // Run the query
                    int affectedRows = preparedStatement.executeUpdate();

                    if (affectedRows > 0) {
                        System.out.println("Stock updated successfully.");
                    } else {
                        System.out.println("Product not found. Stock could not be updated.");
                    }
                }
            } else {
                System.out.println("Database connection failed.");
            }
        } catch (SQLException e) {
            System.out.println("Stock update error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Decreases the count of the selected object by decreaseAmount.
    public static void removeStock(Product product, int decreaseAmount) {
        String selectStockSQL = "SELECT count FROM Stock WHERE name = ? AND month_and_year = ?";
        String updateStockSQL = "UPDATE Stock SET count = count - ? WHERE name = ? AND month_and_year = ?";

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                try (PreparedStatement selectStatement = connection.prepareStatement(selectStockSQL)) {
                    selectStatement.setString(1, product.getName());
                    selectStatement.setString(2, product.getMonthAndYear());

                    try (ResultSet resultSet = selectStatement.executeQuery()) {
                        if (resultSet.next()) {
                            int currentCount = resultSet.getInt("count");
                            if (currentCount >= decreaseAmount) {
                                try (PreparedStatement updateStatement = connection.prepareStatement(updateStockSQL)) {
                                    updateStatement.setInt(1, decreaseAmount);
                                    updateStatement.setString(2, product.getName());
                                    updateStatement.setString(3, product.getMonthAndYear());

                                    int affectedRows = updateStatement.executeUpdate();
                                    if (affectedRows > 0) {
                                        System.out.println("Inventory updated successfully.");
                                    } else {
                                        System.out.println("Product not found. Stock could not be updated.");
                                    }
                                }
                            } else {
                                System.out.println("Insufficient stock! Available quantity: " + currentCount);
                            }
                        } else {
                            System.out.println("Product not found.");
                        }
                    }
                }
            } else {
                System.out.println("Database connection failed.");
            }
        } catch (SQLException e) {
            System.out.println("Stock update error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
