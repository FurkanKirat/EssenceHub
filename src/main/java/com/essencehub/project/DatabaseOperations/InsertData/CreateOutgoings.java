package com.essencehub.project.DatabaseOperations.InsertData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;

import com.essencehub.project.DatabaseOperations.DatabaseConnection;
import com.essencehub.project.Finance.Outgoings;

public class CreateOutgoings {
    private String[] titles = {
            "Rent", "Electricity", "Water", "Office Supplies", "Meals", "Staff Salary", "Maintenance", "Software License"
    };

    private String[] costs = {
            "250", "500", "750", "1200", "3000", "4500", "150", "2000"
    };

    private Random random = new Random();

    // Generate a random date
    private LocalDate getRandomDate() {
        int year = 2020 + random.nextInt(5); // Between 2020 and 2024
        int month = 1 + random.nextInt(12); // Between 1 and 12
        int day = 1 + random.nextInt(28); // Between 1 and 28 (simplified)
        return LocalDate.of(year, month, day);
    }

    // Generate a random Outgoings object
    public Outgoings generateRandomOutgoings() {
        String title = titles[random.nextInt(titles.length)];
        String cost = costs[random.nextInt(costs.length)];
        LocalDate date = getRandomDate();
        return new Outgoings(date, title, cost);
    }

    // Insert generated data into the database
    public void insertOutgoingsIntoDatabase() {
        String insertSQL = "INSERT INTO Outcome (date, title, cost) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            for (int i = 0; i < 10; i++) { // Insert 10 records as an example
                Outgoings outgoings = generateRandomOutgoings();

                preparedStatement.setString(1, outgoings.getDate().toString());
                preparedStatement.setString(2, outgoings.getTitle());
                preparedStatement.setString(3, outgoings.getCost());

                preparedStatement.executeUpdate();
                System.out.println("Inserted: " + outgoings.getTitle() + " - " + outgoings.getCost());
            }

            System.out.println("Data insertion completed successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        CreateOutgoings dataGenerator = new CreateOutgoings();
        dataGenerator.insertOutgoingsIntoDatabase();
    }
}
