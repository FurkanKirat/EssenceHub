package com.essencehub.project.DatabaseOperations;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/BusinessProject";  // Veritabanı URL'si
        String user = "root"; // Veritabanı kullanıcı adı
        String password = "ruhi1234"; // Veritabanı şifresi

        try {
            // JDBC sürücüsünü yükleme
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Bağlantıyı oluşturma
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Sürücüsü bulunamadı: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Veritabanı bağlantı hatası: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
}
