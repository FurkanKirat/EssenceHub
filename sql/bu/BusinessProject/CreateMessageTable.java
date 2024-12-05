package BusinessProject;

import java.sql.Connection;
import java.sql.Statement;

public class CreateMessageTable {

    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                String createTableSQL = "CREATE TABLE IF NOT EXISTS Message ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY, "
                        + "sender_id INT NOT NULL, "
                        + "receiver_id INT NOT NULL, "
                        + "message TEXT NOT NULL, "
                        + "title VARCHAR(50), "
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
}
