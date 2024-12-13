package com.essencehub.project.DatabaseOperations.CreateTables;

import com.essencehub.project.DatabaseOperations.DatabaseConnection;

import java.sql.Connection;
import java.sql.Statement;

public class CreateIncomeTable {

    public static void createIncomeTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Income ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "date DATE NOT NULL,"
                + "title VARCHAR(255) NOT NULL,"
                + "amount VARCHAR(255) NOT NULL"
                + ");";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(createTableSQL);
            System.out.println("Income tablosu başarıyla oluşturuldu veya zaten mevcut.");

        } catch (Exception e) {
            System.out.println("Income tablosu oluşturulurken bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        createIncomeTable();
    }
}
