package com.essencehub.project.DatabaseOperations.InsertData;

import com.essencehub.project.DatabaseOperations.DatabaseConnection;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.Random;

public class CreateTasks {

    public static void main(String[] args) {
        Random random = new Random();

        String[] taskTitles = {"Prepare Report", "Fix Bug", "Design Layout", "Update Database", "Team Meeting", "Prepare Presentation"};
        String[] taskDescriptions = {"Complete the monthly report", "Fix the critical bug in the system", "Create a design for the new project", "Update the database with new information", "Discuss the project updates", "Prepare the slides for the upcoming presentation"};

        String insertSQL = "INSERT INTO Task (sender_id, receiver_id, task, title, send_date_time, is_task_done) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
                    for (int i = 0; i < 10; i++) {
                        int senderId = 2;//ADMIN IDSI VERDİM DEĞİŞTİRİLEBİLİR
                        int receiverId = random.nextInt(10) + 1;

                        String title = taskTitles[random.nextInt(taskTitles.length)];
                        String taskDescription = taskDescriptions[random.nextInt(taskDescriptions.length)];

                        LocalDateTime sendDateTime = LocalDateTime.now().minusDays(random.nextInt(7));

                        boolean isTaskDone = random.nextBoolean();

                        preparedStatement.setInt(1, senderId);
                        preparedStatement.setInt(2, receiverId);
                        preparedStatement.setString(3, taskDescription);
                        preparedStatement.setString(4, title);
                        preparedStatement.setString(5, sendDateTime.toString());
                        preparedStatement.setBoolean(6, isTaskDone);

                        preparedStatement.executeUpdate();
                    }

                    System.out.println("DONE.");
                }
            } else {
                System.out.println("Database connection failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

