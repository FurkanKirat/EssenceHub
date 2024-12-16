package com.essencehub.project.DatabaseOperations.CreateTables;

import com.essencehub.project.DatabaseOperations.DatabaseConnection;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class createLeaveRequestTable {
    public static void createTable() {
        String createTableSQL = """
                CREATE TABLE IF NOT EXISTS LeaveRequest (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    startDate DATE NOT NULL,
                    endDate DATE NOT NULL,
                    employeeId INT NOT NULL,
                    status VARCHAR(50) NOT NULL,
                    FOREIGN KEY (employeeId) REFERENCES User(id)
                );
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(createTableSQL);
            System.out.println("LeaveRequest tablosu başarıyla oluşturuldu.");
        } catch (SQLException e) {
            System.out.println("LeaveRequest tablosu oluşturulurken bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        createTable();
    }
}
