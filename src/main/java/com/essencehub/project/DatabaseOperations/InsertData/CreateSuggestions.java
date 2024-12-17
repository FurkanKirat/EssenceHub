package com.essencehub.project.DatabaseOperations.InsertData;

import com.essencehub.project.DatabaseOperations.DatabaseConnection;
import com.essencehub.project.User.Suggestion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class CreateSuggestions {

    public static void main(String[] args) {
        Random random = new Random();

        String[] titles = {"Feature Request", "Bug Report", "UI Improvement", "General Feedback"};
        String[] messages = {
                "A new feature suggestion to improve user experience.",
                "A bug was reported in the system.",
                "A suggestion to enhance the user interface design.",
                "General feedback about the project management tool."
        };

        String insertSQL = "INSERT INTO Suggestion (title, message) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
                    for (int i = 0; i < 10; i++) {
                        String title = titles[random.nextInt(titles.length)];
                        String message = messages[random.nextInt(messages.length)];

                        preparedStatement.setString(1, title);
                        preparedStatement.setString(2, message);

                        preparedStatement.executeUpdate();
                    }

                    System.out.println("Done");
                }
            } else {
                System.out.println("Veritabanı bağlantısı başarısız.");
            }
        } catch (SQLException e) {
            System.out.println("Veritabanına veri eklerken bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

