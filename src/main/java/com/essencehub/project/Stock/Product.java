package com.essencehub.project.Stock;

import com.essencehub.project.Controllers.Finance.AddIncomeController;
import com.essencehub.project.Controllers.Finance.AddOutcomeController;
import com.essencehub.project.DatabaseOperations.DatabaseConnection;
import com.essencehub.project.Finance.Income;
import com.essencehub.project.Finance.Outgoings;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public int getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(int buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public int getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(int sellingPrice) {
        this.sellingPrice = sellingPrice;
    }


    public static void addProduct(Product product) {
        String insertProductSQL = "INSERT INTO Stock (name, count, buyingDate, sellingDate, buyingPrice, sellingPrice) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertProductSQL)) {
                    preparedStatement.setString(1, product.getName());
                    preparedStatement.setInt(2, product.getCount());
                    preparedStatement.setDate(3, Date.valueOf(product.getBuyingDate()));
                    preparedStatement.setDate(4, product.getSellingDate() != null ? Date.valueOf(product.getSellingDate()) : null);
                    preparedStatement.setInt(5, product.getBuyingPrice());
                    preparedStatement.setInt(6, product.getSellingPrice());

                    int affectedRows = preparedStatement.executeUpdate();

                    if (affectedRows > 0) {
                        System.out.println("The product was added successfully.");


                        double totalCost = product.getCount() * product.getBuyingPrice();
                        Outgoings outgoing = new Outgoings(product.getBuyingDate(), "Purchase of " + product.getName(), String.valueOf(totalCost));
                        AddOutcomeController.addOutgoings(outgoing);
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

    // Remove product from stock and record the income
    public static void removeProduct(String productName, int quantityToRemove) {
        String selectStockSQL = "SELECT count, sellingPrice FROM Stock WHERE name = ?";
        String updateStockSQL = "UPDATE Stock SET count = count - ? WHERE name = ? AND count >= ?";

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                try (PreparedStatement selectStatement = connection.prepareStatement(selectStockSQL)) {
                    selectStatement.setString(1, productName);

                    try (ResultSet resultSet = selectStatement.executeQuery()) {
                        if (resultSet.next()) {
                            int currentCount = resultSet.getInt("count");
                            int sellingPrice = resultSet.getInt("sellingPrice");

                            if (currentCount >= quantityToRemove) {
                                try (PreparedStatement updateStatement = connection.prepareStatement(updateStockSQL)) {
                                    updateStatement.setInt(1, quantityToRemove);
                                    updateStatement.setString(2, productName);
                                    updateStatement.setInt(3, quantityToRemove);

                                    int affectedRows = updateStatement.executeUpdate();
                                    if (affectedRows > 0) {
                                        System.out.println("Product removed successfully.");

                                        // Record income
                                        LocalDate currentDate = LocalDate.now();
                                        double totalIncome = quantityToRemove * sellingPrice;
                                        Income income = new Income(currentDate, "Sale of " + productName, String.valueOf(totalIncome));
                                        AddIncomeController.addIncome(income);
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
    public static List<Product> getAllProducts() {
        String query = "SELECT id, name, count, buyingDate, sellingDate, buyingPrice, sellingPrice FROM Stock";
        List<Product> productList = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                     ResultSet resultSet = preparedStatement.executeQuery()) {

                    while (resultSet.next()) {
                        String name = resultSet.getString("name");
                        int count = resultSet.getInt("count");
                        LocalDate buyingDate = resultSet.getDate("buyingDate").toLocalDate();
                        LocalDate sellingDate = resultSet.getDate("sellingDate") != null
                                ? resultSet.getDate("sellingDate").toLocalDate()
                                : null;
                        int buyingPrice = resultSet.getInt("buyingPrice");
                        int sellingPrice = resultSet.getInt("sellingPrice");

                        Product product = new Product(name, count, buyingDate, sellingDate, buyingPrice, sellingPrice);
                        productList.add(product);
                    }
                }
            } else {
                System.out.println("Database connection failed.");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching products: " + e.getMessage());
            e.printStackTrace();
        }

        return productList;
    }

}
