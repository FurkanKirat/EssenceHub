package com.essencehub.project.DatabaseOperations;

import java.sql.Connection;
import java.sql.Statement;

public class CreateSQL {
    public static void createMessageTable(){
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                String createTableSQL = "CREATE TABLE IF NOT EXISTS Message ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY, "
                        + "sender_id INT NOT NULL, "
                        + "receiver_id INT NOT NULL, "
                        + "message TEXT NOT NULL, "
                        + "send_date_time DATETIME NOT NULL, "
                        + "FOREIGN KEY (sender_id) REFERENCES User(id), "
                        + "FOREIGN KEY (receiver_id) REFERENCES User(id)"
                        + ");";
                try (Statement statement = connection.createStatement()) {
                    statement.execute(createTableSQL);
                    System.out.println("Message tablosu başarıyla oluşturuldu.");
                }
            } else {
                System.out.println("Veritabanı bağlantısı başarısız.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void createTaskTable(){
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                String createTableSQL = "CREATE TABLE IF NOT EXISTS Task ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY, "
                        + "sender_id INT NOT NULL, "
                        + "receiver_id INT NOT NULL, "
                        + "task TEXT NOT NULL, "
                        + "title VARCHAR(50), "
                        + "send_date_time DATETIME NOT NULL, "
                        + "is_task_done BOOLEAN DEFAULT FALSE, "
                        + "FOREIGN KEY (sender_id) REFERENCES User(id), "
                        + "FOREIGN KEY (receiver_id) REFERENCES User(id)"
                        + ");";
                try (Statement statement = connection.createStatement()) {
                    statement.execute(createTableSQL);
                    System.out.println("Task tablosu başarıyla oluşturuldu.");
                }
            } else {
                System.out.println("Veritabanı bağlantısı başarısız.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void createUserTable(){
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                String createTableSQL = "CREATE TABLE IF NOT EXISTS User ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY, "
                        + "name VARCHAR(50) NOT NULL, "
                        + "surname VARCHAR(50) NOT NULL, "
                        + "phoneNumber VARCHAR(15), "
                        + "salary DOUBLE, "
                        + "isAdmin BOOLEAN, "
                        + "birth VARCHAR(50), "
                        + "department VARCHAR(50), "
                        + "email VARCHAR(100), "
                        + "remainingLeaveDays INT, "
                        + "monthlyPerformance VARCHAR(50), "
                        + "bonusSalary DOUBLE, "
                        + "isActive BOOLEAN, "
                        + "password VARCHAR(255) NOT NULL"
                        + ");";
                try (Statement statement = connection.createStatement()) {
                    statement.execute(createTableSQL);
                    System.out.println("User tablosu başarıyla oluşturuldu.");
                }
            } else {
                System.out.println("Veritabanı bağlantısı başarısız.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        createUserTable();
        createMessageTable();
        createTaskTable();
    }
}
