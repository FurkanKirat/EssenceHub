package com.essencehub.project.DatabaseOperations.InsertData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;

import com.essencehub.project.DatabaseOperations.DatabaseConnection;
import com.essencehub.project.Finance.Income;

public class CreateIncomes {
    private String[] titles = {
            "Product Sale", "Consulting", "Investment", "Partnership", "Freelance Project", "License Sale", "Rental Income"
    };

    private String[] amounts = {
            "1000", "2500", "5000", "7500", "1500", "3000", "12000"
    };

    private Random random = new Random();

    private LocalDate getRandomDate() {
        int year = 2020 + random.nextInt(5); // Between 2020 and 2024
        int month = 1 + random.nextInt(12); // Between 1 and 12
        int day = 1 + random.nextInt(28); // Between 1 and 28 (simplified)
        return LocalDate.of(year, month, day);
    }

    public Income generateRandomIncome() {
        String title = titles[random.nextInt(titles.length)];
        String amount = amounts[random.nextInt(amounts.length)];
        LocalDate date = getRandomDate();
        return new Income(date, title, amount);
    }

    public void insertIncomeIntoDatabase() {
        String insertSQL = "INSERT INTO Income (date, title, amount) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            for (int i = 0; i < 10; i++) { // Insert 10 records as an example
                Income income = generateRandomIncome();

                preparedStatement.setString(1, income.getDate().toString());
                preparedStatement.setString(2, income.getTitle());
                preparedStatement.setString(3, income.getAmount());

                preparedStatement.executeUpdate();
                System.out.println("Inserted: " + income.getTitle() + " - " + income.getAmount());
            }

            System.out.println("Başarılı");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        CreateIncomes dataGenerator = new CreateIncomes();
        dataGenerator.insertIncomeIntoDatabase();
    }
}
