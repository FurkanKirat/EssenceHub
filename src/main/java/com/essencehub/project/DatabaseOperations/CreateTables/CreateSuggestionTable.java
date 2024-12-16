package com.essencehub.project.DatabaseOperations.CreateTables;

import com.essencehub.project.DatabaseOperations.DatabaseConnection;

import java.sql.Connection;
import java.sql.Statement;

public class CreateSuggestionTable {
    public static void createTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Suggestion ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "title VARCHAR(255) NOT NULL,"
                + "message TEXT NOT NULL"
                + ")";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(createTableSQL);
            System.out.println("Suggestion tablosu başarıyla oluşturuldu.");
        } catch (Exception e) {
            System.out.println("Suggestion tablosu oluşturulurken bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        createTable();
    }
}
