package com.essencehub.project.Stock;

import com.essencehub.project.DatabaseOperations.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;

public class Product {
    private String name;
    private int count;
    private int buyingPrice;
    private int sellingPrice;
    private LocalDate sellingDate;
    private LocalDate buyingDate;

    public Product(String name, int count, LocalDate buyingDate, LocalDate sellingDate, int buyingPrice, int sellingPrice) {
        this.name = name;
        this.count = count;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
        this.sellingDate = sellingDate;
        this.buyingDate = buyingDate;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBuyingDate() {
        return buyingDate;
    }

    public LocalDate getSellingDate() {
        return sellingDate;
    }

    public int getCount() {
        return count;
    }


    public void setCount(int count) {
        this.count = count;
    }


    public static void addProduct(Product product) {
        String insertProductSQL = "INSERT INTO Stock (name, count, buyingDate, sellingDate) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertProductSQL)) {
                    preparedStatement.setString(1, product.getName());
                    preparedStatement.setInt(2, product.getCount());
                    preparedStatement.setDate(3, Date.valueOf(product.getBuyingDate())); // LocalDate -> SQL Date Çağkan kardeşim şuna göre table oluştur
                    preparedStatement.setDate(4, Date.valueOf(product.getSellingDate())); // LocalDate -> SQL Date Çağkan kardeşim şuna göre table oluştur

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
        }
    }
    public static void removeProduct(String productName, int quantityToRemove) {
        String selectStockSQL = "SELECT count FROM Stock WHERE name = ?";
        String updateStockSQL = "UPDATE Stock SET count = count - ? WHERE name = ? AND count >= ?";

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {

                try (PreparedStatement selectStatement = connection.prepareStatement(selectStockSQL)) {
                    selectStatement.setString(1, productName);

                    try (ResultSet resultSet = selectStatement.executeQuery()) {
                        if (resultSet.next()) {
                            int currentCount = resultSet.getInt("count");

                            if (currentCount >= quantityToRemove) {

                                try (PreparedStatement updateStatement = connection.prepareStatement(updateStockSQL)) {
                                    updateStatement.setInt(1, quantityToRemove);
                                    updateStatement.setString(2, productName);
                                    updateStatement.setInt(3, quantityToRemove);

                                    int affectedRows = updateStatement.executeUpdate();
                                    if (affectedRows > 0) {
                                        System.out.println("Product removed successfully.");
                                    } else {
                                        System.out.println("Error removing product.");
                                    }
                                }
                            } else {

                                System.out.println("Insufficient stock! Available quantity: " + currentCount);
                            }
                        } else {

                            System.out.println("Product not found in the stock.");
                        }
                    }
                }
            } else {
                System.out.println("Database connection failed.");
            }
        } catch (SQLException e) {
            System.out.println("Error removing product: " + e.getMessage());
            e.printStackTrace();
        }
    }
}