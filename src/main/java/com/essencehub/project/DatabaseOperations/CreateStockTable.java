package com.essencehub.project.DatabaseOperations;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

    public class CreateStockTable {

        //Stock Table'ı oluşturmak için bu classı runlıyoruz beyler.
        public static void main(String[] args) {

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
}
