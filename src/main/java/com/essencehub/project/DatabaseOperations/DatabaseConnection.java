package com.essencehub.project.DatabaseOperations;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/BusinessProject?user=root&password=ruhi1234"; // URL'de kullanıcı adı ve şifreyi belirtmek yeterli
        try {
            // JDBC sürücüsünü yükleme (bu, genellikle MySQL 8+ için gereksizdir çünkü JDBC 8.0 sürücüleri otomatik yüklenir)
            // Class.forName("com.mysql.cj.jdbc.Driver"); // Bu satır MySQL 8 ve sonrasında genellikle gerekmez.

            // Bağlantıyı oluşturma
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("Veritabanı bağlantı hatası: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}
