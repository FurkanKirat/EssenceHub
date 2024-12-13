package com.essencehub.project.User;

import com.essencehub.project.DatabaseOperations.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Message {

    private User sender;
    private User receiver;
    private String message;
    private LocalDateTime sendDateTime;

    public Message() {
    }

    public Message(User sender, User receiver, String message, LocalDateTime time) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.sendDateTime = time;
    }

    // Getter and Setter Methods

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getSendDateTime() {
        return sendDateTime;
    }

    public void setSendDateTime(LocalDateTime sendDateTime) {
        this.sendDateTime = sendDateTime;
    }


    // Message database methods
    public static ArrayList<Message> getMessagesBetweenUsers(User sender, User receiver) {
        ArrayList<Message> messages = new ArrayList<>();
        String query = "SELECT sender_id, receiver_id, message, send_date_time " +
                "FROM Message " +
                "WHERE (sender_id = ? AND receiver_id = ?) " +
                "   OR (sender_id = ? AND receiver_id = ?) " +
                "ORDER BY send_date_time ASC";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, sender.getId());
            preparedStatement.setInt(2, receiver.getId());
            preparedStatement.setInt(3, receiver.getId());
            preparedStatement.setInt(4, sender.getId());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    User messageSender = new User(resultSet.getInt("sender_id"));
                    User messageReceiver = new User(resultSet.getInt("receiver_id"));
                    String messageText = resultSet.getString("message");
                    LocalDateTime sendDateTime = resultSet.getTimestamp("send_date_time").toLocalDateTime();

                    Message message = new Message(messageSender, messageReceiver, messageText,sendDateTime);
                    messages.add(message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return messages;
    }

    //SEND MESSAGE
    public static void sendMessageMain(User sender, User receiver, String message, LocalDateTime sendDateTime) {
        Message messageTemp = new Message(sender, receiver, message, sendDateTime);
        sendMessage(messageTemp);
    }

    private static void sendMessage(Message message) {
        String sql = "INSERT INTO Message (sender_id, receiver_id, message, send_date_time) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Adding to the database
            preparedStatement.setInt(1, message.getSender().getId());
            preparedStatement.setInt(2, message.getReceiver().getId());
            preparedStatement.setString(3, message.getMessage());

            // We can set LocalDateTime directly, it is compatible with DATETIME type in MySQL
            preparedStatement.setObject(4, message.getSendDateTime());

            //run SQL command
            preparedStatement.executeUpdate();
            System.out.println("Message sent successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
