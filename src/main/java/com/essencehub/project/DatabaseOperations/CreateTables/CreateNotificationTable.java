package com.essencehub.project.DatabaseOperations.CreateTables;

import com.essencehub.project.DatabaseOperations.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateNotificationTable {

    public static void createNotificationTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Notification (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "receiver_id INT NOT NULL, " +
                "title VARCHAR(255) NOT NULL, " +
                "message TEXT NOT NULL, " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (receiver_id) REFERENCES User(id) ON DELETE CASCADE" +
                ");";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(createTableSQL);
            System.out.println("Notification tablosu başarıyla oluşturuldu.");

        } catch (SQLException e) {
            System.out.println("Notification tablosu oluşturulurken bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        createNotificationTable();
    }
}
