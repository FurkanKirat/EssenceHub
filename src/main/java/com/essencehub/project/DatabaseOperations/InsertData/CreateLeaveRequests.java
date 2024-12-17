package com.essencehub.project.DatabaseOperations.InsertData;


import com.essencehub.project.DatabaseOperations.DatabaseConnection;
import com.essencehub.project.User.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;

public class CreateLeaveRequests {

    public static void main(String[] args) {
        Random random = new Random();

        String[] statuses = {"Approved", "Pending", "Rejected"};

        String insertSQL = "INSERT INTO LeaveRequest (startDate, endDate, employeeId, status) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
                    for (int i = 0; i < 10; i++) {
                        LocalDate startDate = LocalDate.now().minusDays(random.nextInt(30));
                        LocalDate endDate = startDate.plusDays(random.nextInt(7) + 1);


                        int employeeId = random.nextInt(12) + 20;


                        String status = statuses[random.nextInt(statuses.length)];


                        preparedStatement.setDate(1, java.sql.Date.valueOf(startDate));
                        preparedStatement.setDate(2, java.sql.Date.valueOf(endDate));
                        preparedStatement.setInt(3, employeeId);
                        preparedStatement.setString(4, status);

                        preparedStatement.executeUpdate();
                    }

                    System.out.println("DOne.");
                }
            } else {
                System.out.println("Veritabanı bağlantısı başarısız.");
            }
        } catch (SQLException e) {
            System.out.println("Veritabanına veri eklerken bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

