package com.essencehub.project.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/BusinessProject";  // Veritabanı URL'si
        String user = "buraya server ıdsini yazın beyler"; // Veritabanı kullanıcı adı
        String password = "buraya server şifresini yazın beyler."; // Veritabanı şifresi

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
