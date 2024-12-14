package com.essencehub.project.User;

import com.essencehub.project.DatabaseOperations.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Task {
    private int id;
    private User sender;
    private User receiver;
    private String task;
    private String title; // Newly added title field
    private LocalDateTime sendDateTime;
    private boolean isTaskDone; // Task completion status

    // Constructor

    public Task(User sender, User receiver, String task, String title, LocalDateTime sendDateTime, boolean isTaskDone) {
        this.sender = sender;
        this.receiver = receiver;
        this.task = task;
        // If title is null or given empty, it is derived from task
        this.title = title != null && !title.isEmpty() ? title : task.length() > 50 ? task.substring(0, 50) : task;
        this.sendDateTime = Objects.requireNonNullElseGet(sendDateTime, LocalDateTime::now);
        this.isTaskDone = isTaskDone;

    }

    // Getter and Setters
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
        this.title = task.length() > 50 ? task.substring(0, 50) : task; // Task değişirse title da güncellenir
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getSendDateTime() {
        return sendDateTime;
    }

    public void setSendDateTime(LocalDateTime sendDateTime) {
        this.sendDateTime = sendDateTime;
    }

    // isTaskDone Getter and Setter
    public boolean isTaskDone() {
        return isTaskDone;
    }

    public void setTaskDone(boolean isTaskDone) {
        this.isTaskDone = isTaskDone;
    }

    // Method that changes the state when the task is completed
    public void markTaskAsDone() {
        this.isTaskDone = true;
    }

    // Method that changes state when the task is not completed
    public void markTaskAsPending() {
        this.isTaskDone = false;
    }

    public static void deleteTask(Task task) {
        String deleteSQL = "DELETE FROM Task WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection()) {

            if (connection != null) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
                    preparedStatement.setInt(1, task.getId());
                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Task deleted successfully: " + task.getId());
                    } else {
                        System.out.println("The task to be deleted could not be found: " + task.getId());
                    }
                }
            } else {
                System.out.println("Database connection failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        String query = """
        SELECT 
            t.id, t.sender_id, t.receiver_id, t.task, t.title, t.send_date_time, t.is_task_done,
            s.name AS sender_name, s.surname AS sender_surname,
            r.name AS receiver_name, r.surname AS receiver_surname
        FROM Task t
        JOIN User s ON t.sender_id = s.id
        JOIN User r ON t.receiver_id = r.id
        ORDER BY t.send_date_time DESC
    """;

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int senderId = resultSet.getInt("sender_id");
                int receiverId = resultSet.getInt("receiver_id");
                String task = resultSet.getString("task");
                String title = resultSet.getString("title");
                LocalDateTime sendDateTime = resultSet.getTimestamp("send_date_time").toLocalDateTime();
                boolean isTaskDone = resultSet.getBoolean("is_task_done");

                // Gönderici bilgileri
                User sender = new User(senderId);
                sender.setName(resultSet.getString("sender_name"));
                sender.setSurname(resultSet.getString("sender_surname"));

                // Alıcı bilgileri
                User receiver = new User(receiverId);
                receiver.setName(resultSet.getString("receiver_name"));
                receiver.setSurname(resultSet.getString("receiver_surname"));

                // Görevi oluştur ve listeye ekle
                Task taskObj = new Task(sender, receiver, task, title, sendDateTime, isTaskDone);
                taskObj.setId(id);
                taskList.add(taskObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return taskList;
    }



    public static List<Task> getUserTasks(int currentUserId) {
        List<Task> taskList = new ArrayList<>();
        String query = """
        SELECT 
            t.id, t.sender_id, t.receiver_id, t.task, t.title, t.send_date_time, t.is_task_done,
            s.name AS sender_name, s.surname AS sender_surname,
            r.name AS receiver_name, r.surname AS receiver_surname
        FROM Task t
        JOIN User s ON t.sender_id = s.id
        JOIN User r ON t.receiver_id = r.id
        WHERE t.receiver_id = ?
        ORDER BY t.send_date_time DESC
    """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Receiver ID parametresini ayarla
            preparedStatement.setInt(1, currentUserId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int senderId = resultSet.getInt("sender_id");
                    int receiverId = resultSet.getInt("receiver_id");
                    String task = resultSet.getString("task");
                    String title = resultSet.getString("title");
                    LocalDateTime sendDateTime = resultSet.getTimestamp("send_date_time").toLocalDateTime();
                    boolean isTaskDone = resultSet.getBoolean("is_task_done");

                    // Gönderici bilgileri
                    User sender = new User(senderId);
                    sender.setName(resultSet.getString("sender_name"));
                    sender.setSurname(resultSet.getString("sender_surname"));

                    // Alıcı bilgileri
                    User receiver = new User(receiverId);
                    receiver.setName(resultSet.getString("receiver_name"));
                    receiver.setSurname(resultSet.getString("receiver_surname"));

                    // Görevi oluştur ve listeye ekle
                    Task taskObj = new Task(sender, receiver, task, title, sendDateTime, isTaskDone);
                    taskObj.setId(id);
                    taskList.add(taskObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return taskList;
    }


    // SEND TASK
    public static void sendTaskMain(User sender, User receiver, String task, String title, LocalDateTime sendDateTime, boolean isTaskDone) {
        Task taskTemp = new Task(sender, receiver, task, title, sendDateTime, isTaskDone);
        sendTask(taskTemp);
    }



    public static void sendTask(Task task) {
        String insertTaskSQL = "INSERT INTO Task (sender_id, receiver_id, task, title, send_date_time, is_task_done) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertTaskSQL)) {

            // In the SQL query ? We replace the parameters
            preparedStatement.setInt(1, task.getSender().getId()); // Sender ID
            preparedStatement.setInt(2, task.getReceiver().getId()); // Receiver ID
            preparedStatement.setString(3, task.getTask()); // Task description
            preparedStatement.setString(4, task.getTitle()); // Task title
            preparedStatement.setObject(5, task.getSendDateTime()); // Sent date
            preparedStatement.setBoolean(6, task.isTaskDone()); // Task completion status

            //Run SQL query
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Task sent successfully.");
            } else {
                System.out.println("An error occurred while submitting the task.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //UPDATE TASK
    public static void updateTask(Task task) {
        String updateTaskSQL = "UPDATE Task SET "
                + "task = ?, "
                + "title = ?, "
                + "is_task_done = ? "
                + "WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateTaskSQL)) {

            // in SQL query We place parameters in (?) places
            preparedStatement.setString(1, task.getTask()); // Task description
            preparedStatement.setString(2, task.getTitle()); // Task title
            preparedStatement.setBoolean(3, task.isTaskDone()); // Task completion status
            preparedStatement.setInt(4, task.getId()); // Task ID (task to be updated)

            //Run SQL query
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Task updated successfully.");
            } else {
                System.out.println("An error occurred while updating the task or the task was not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
