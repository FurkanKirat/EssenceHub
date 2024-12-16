package com.essencehub.project.Controllers.Finance;

import com.essencehub.project.DatabaseOperations.DatabaseConnection;
import com.essencehub.project.Finance.Outgoings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddOutcomeController {

    @FXML
    private Button addOutcomeButtonClicked;

    @FXML
    private TextField birthField;

    @FXML
    private TextField costField;

    @FXML
    private TextField titleField;

    private String cost;
    private LocalDate date;
    private String title;

    @FXML
    void isAddOutcomeButtonClicked(ActionEvent event) {
        if(titleField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An Error Occurred");
            alert.setContentText("Title must not be empty");
            alert.showAndWait();
            return;
        }
        cost = costField.getText();

        try {
            double numericValue = Double.parseDouble(cost); // Attempt to parse the input
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An Error Occurred");
            alert.setContentText("Amount must be numeric");
            alert.showAndWait();
            return;
        }

        title = titleField.getText();
        String dateString = birthField.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try {
            // Parse the string using the formatter
            date = LocalDate.parse(dateString, formatter);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An Error Occurred");
            alert.setContentText("Birth date is not convenient with desired format");
            alert.showAndWait();
            return;
        }

        Outgoings newOutcome = new Outgoings(date, title, cost);
        addOutgoings(newOutcome);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Operation Successful");
        alert.setContentText("successfully added");
        alert.showAndWait();
    }
    public static void addOutgoings(Outgoings outgoings) {
        String sql = "INSERT INTO Income (date, title, amount)"
                + "VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // We set the parameters
            statement.setString(1, outgoings.getDate().toString());
            statement.setString(2, outgoings.getTitle());
            statement.setString(3, outgoings.getCost());

            // We add it to the database
            statement.executeUpdate();
            System.out.println("hey");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}