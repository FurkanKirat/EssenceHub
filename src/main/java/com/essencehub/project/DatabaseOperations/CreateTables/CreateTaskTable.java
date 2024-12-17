package com.essencehub.project.DatabaseOperations.CreateTables;

import com.essencehub.project.DatabaseOperations.DatabaseConnection;

import java.sql.Connection;
import java.sql.Statement;

public class CreateTaskTable {

    public static void main(String[] args) {
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
                        + "progress BOOLEAN DEFAULT FALSE, "  // progress sütunu eklendi
                        + "finish_time DATETIME, "  // finish_time DATETIME olarak güncellendi
                        + "FOREIGN KEY (sender_id) REFERENCES User(id), "
                        + "FOREIGN KEY (receiver_id) REFERENCES User(id)"
                        + ");";

                try (Statement statement = connection.createStatement()) {
                    statement.execute(createTableSQL);
                    System.out.println("Task table created successfully.");
                }
            } else {
                System.out.println("Database connection failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
