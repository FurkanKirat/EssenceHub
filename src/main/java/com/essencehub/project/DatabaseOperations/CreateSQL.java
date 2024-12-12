package com.essencehub.project.DatabaseOperations;

import java.sql.*;

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
    public static void cloudUser(){
        try {
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
                    + "password VARCHAR(255) NOT NULL, "
                    + "imageLocation VARCHAR(255)"
                    + ");";
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(createTableSQL); // createTableSQL yukarıdaki komut
            System.out.println("Table created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void createUser(){

        String url = "jdbc:mysql://34.38.61.250:3306/BusinessProject?user=Admin&password=ruhi1234";

        String query = "INSERT INTO User (name, surname, phoneNumber, salary, isAdmin, birth, department, email, remainingLeaveDays, monthlyPerformance, bonusSalary, isActive, password, imageLocation) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, "Fatih");
            pstmt.setString(2, "Pehlivan");
            pstmt.setString(3, "1234567890");
            pstmt.setDouble(4, 1.0);
            pstmt.setBoolean(5, true);
            pstmt.setString(6, "2000-01-01");
            pstmt.setString(7, "HR");
            pstmt.setString(8, "john.doe@example.com");
            pstmt.setInt(9, 1);
            pstmt.setString(10, "A_PLUS");
            pstmt.setDouble(11, 1.0);
            pstmt.setBoolean(12, true);
            pstmt.setString(13, "123");
            pstmt.setString(14, "/com/essencehub/project/images/ProfilePictures/defaultpicture5.png");

            pstmt.executeUpdate();

            System.out.println("User added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void stockTable(){
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                String createTableSQL = "CREATE TABLE IF NOT EXISTS Stock ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY, "
                        + "name VARCHAR(255) NOT NULL, "
                        + "count INT NOT NULL, "
                        + "month_and_year VARCHAR(10) NOT NULL"
                        + ")";

                try (Statement statement = connection.createStatement()) {
                    statement.execute(createTableSQL);
                    System.out.println("Stock tablosu oluşru.");
                } catch (SQLException e) {
                    System.out.println("Tablo oluşturma hatası: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            System.out.println("Veritabanı bağlantı hatası: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
//        createUserTable();
//        createMessageTable();
//        createTaskTable();
//        cloudUser();
//            createUser();
        stockTable();
    }
}
