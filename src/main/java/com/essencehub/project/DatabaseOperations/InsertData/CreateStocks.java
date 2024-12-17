package com.essencehub.project.DatabaseOperations.InsertData;

import com.essencehub.project.DatabaseOperations.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.Random;

public class CreateStocks {

    public static void main(String[] args) {
        Random random = new Random();
        String[] productNames = {"Laptop", "Mouse", "Keyboard", "Monitor", "Headphones", "Printer", "Tablet", "Phone"};

        String insertSQL = "INSERT INTO Stock (name, count, buyingDate, sellingDate, buyingPrice, sellingPrice) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

                    for (int i = 0; i < 10; i++) {
                        String name = productNames[random.nextInt(productNames.length)];
                        int count = random.nextInt(50) + 1;
                        double buyingPrice = random.nextInt(1000) + 500;
                        double sellingPrice = buyingPrice + random.nextInt(500) + 100;

                        LocalDate buyingDate = LocalDate.now().minusDays(random.nextInt(30));
                        LocalDate sellingDate = LocalDate.now().plusDays(random.nextInt(30));

                        preparedStatement.setString(1, name);
                        preparedStatement.setInt(2, count);
                        preparedStatement.setString(3, buyingDate.toString());
                        preparedStatement.setString(4, sellingDate.toString());
                        preparedStatement.setDouble(5, buyingPrice);
                        preparedStatement.setDouble(6, sellingPrice);

                        preparedStatement.executeUpdate();
                    }

                    System.out.println("OPERATION DONE");
                }
            } else {
                System.out.println("Database connection failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
