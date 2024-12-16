package com.essencehub.project.User;

import com.essencehub.project.DatabaseOperations.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Suggestion {

    private int id;
    private String title;
    private String Message;

    public Suggestion(String title, String message) {
        this.title = title;
        Message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    @Override
    public String toString() {
        return title;
    }

    public static void addSuggestion(Suggestion suggestion) {
        String insertSQL = "INSERT INTO Suggestion (title, message) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setString(1, suggestion.getTitle());
            preparedStatement.setString(2, suggestion.getMessage());
            preparedStatement.executeUpdate();

            System.out.println("Yeni suggestion başarıyla eklendi.");
        } catch (Exception e) {
            System.out.println("Suggestion eklenirken bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static List<Suggestion> getAllSuggestions() {
        String selectSQL = "SELECT * FROM Suggestion";
        List<Suggestion> suggestions = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String message = resultSet.getString("message");

                Suggestion suggestion = new Suggestion(title, message);
                suggestion.setId(id);
                suggestions.add(suggestion);
            }

        } catch (Exception e) {
            System.out.println("Suggestion kayıtları alınırken bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }

        return suggestions;
    }
}
