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
    private String title;
    private LocalDateTime sendDateTime;
    private boolean isTaskDone;
    private int progress;
    private LocalDateTime finishTime;

    public Task(User sender, User receiver, String task, String title, LocalDateTime sendDateTime, boolean isTaskDone, int progress, LocalDateTime finishTime) {
        this.sender = sender;
        this.receiver = receiver;
        this.task = task;
        this.title = title != null && !title.isEmpty() ? title : task.length() > 50 ? task.substring(0, 50) : task;
        this.sendDateTime = Objects.requireNonNullElseGet(sendDateTime, LocalDateTime::now);
        this.isTaskDone = isTaskDone;
        this.progress = Math.max(0, Math.min(progress, 100));
        this.finishTime = finishTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        this.title = task.length() > 50 ? task.substring(0, 50) : task;
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

    public boolean isTaskDone() {
        return isTaskDone;
    }

    public void setTaskDone(boolean isTaskDone) {
        this.isTaskDone = isTaskDone;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = Math.max(0, Math.min(progress, 100));
    }

    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }

    public void markTaskAsDone() {
        this.isTaskDone = true;
        this.progress = 100;
        this.finishTime = LocalDateTime.now();
    }

    public void markTaskAsPending() {
        this.isTaskDone = false;
        this.progress = 0;
        this.finishTime = null;
    }
    public static void deleteTask(Task task) {
        String deleteSQL = "DELETE FROM Task WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
                    preparedStatement.setInt(1, task.getId());
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Görev başarıyla silindi: " + task.getId());
                    } else {
                        System.out.println("Silinmek istenen görev bulunamadı: " + task.getId());
                    }
                }
            } else {
                System.out.println("Veritabanı bağlantısı başarısız.");
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
        t.progress, t.finish_time,
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

                int progress = resultSet.getInt("progress");
                LocalDateTime finishDate = resultSet.getTimestamp("finish_time") != null ?
                        resultSet.getTimestamp("finish_time").toLocalDateTime() : null;

                User sender = new User(senderId);
                sender.setName(resultSet.getString("sender_name"));
                sender.setSurname(resultSet.getString("sender_surname"));

                User receiver = new User(receiverId);
                receiver.setName(resultSet.getString("receiver_name"));
                receiver.setSurname(resultSet.getString("receiver_surname"));

                Task taskObj = new Task(sender, receiver, task, title, sendDateTime, isTaskDone, progress, finishDate);
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
            t.finish_time, t.progress,
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

                    LocalDateTime finishTime = resultSet.getTimestamp("finish_time") != null
                            ? resultSet.getTimestamp("finish_time").toLocalDateTime()
                            : null; // if finish time is null
                    int progress = resultSet.getInt("progress");

                    // Sender Infos
                    User sender = new User(senderId);
                    sender.setName(resultSet.getString("sender_name"));
                    sender.setSurname(resultSet.getString("sender_surname"));

                    // Receiver Infos
                    User receiver = new User(receiverId);
                    receiver.setName(resultSet.getString("receiver_name"));
                    receiver.setSurname(resultSet.getString("receiver_surname"));

                    // Creates task and adds it to the list
                    Task taskObj = new Task(sender, receiver, task, title, sendDateTime, isTaskDone, progress, finishTime);
                    taskObj.setId(id);
                    taskList.add(taskObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return taskList;
    }


    public static void sendTaskMain(User sender, User receiver, String task, String title, LocalDateTime sendDateTime, boolean isTaskDone, int progress, LocalDateTime finishTime) {
        Task taskTemp = new Task(sender, receiver, task, title, sendDateTime, isTaskDone, progress, finishTime);
        sendTask(taskTemp);
    }

    public static void sendTask(Task task) {
        String insertTaskSQL = "INSERT INTO Task (sender_id, receiver_id, task, title, send_date_time, is_task_done, progress, finish_time) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertTaskSQL)) {

            preparedStatement.setInt(1, task.getSender().getId());
            preparedStatement.setInt(2, task.getReceiver().getId());
            preparedStatement.setString(3, task.getTask());
            preparedStatement.setString(4, task.getTitle());
            preparedStatement.setObject(5, task.getSendDateTime());
            preparedStatement.setBoolean(6, task.isTaskDone());
            preparedStatement.setInt(7, task.getProgress());
            preparedStatement.setObject(8, task.getFinishTime());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Görev başarıyla gönderildi.");
            } else {
                System.out.println("Görev gönderilirken bir hata oluştu.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateTask(Task task) {
        String updateTaskSQL = "UPDATE Task SET "
                + "task = ?, "
                + "title = ?, "
                + "is_task_done = ?, "
                + "progress = ?, "
                + "finish_time = ? "
                + "WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateTaskSQL)) {

            preparedStatement.setString(1, task.getTask());
            preparedStatement.setString(2, task.getTitle());
            preparedStatement.setBoolean(3, task.isTaskDone());
            preparedStatement.setInt(4, task.getProgress());
            preparedStatement.setObject(5, task.getFinishTime());
            preparedStatement.setInt(6, task.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Görev başarıyla güncellendi.");
            } else {
                System.out.println("Görev güncellenirken bir hata oluştu veya görev bulunamadı.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
