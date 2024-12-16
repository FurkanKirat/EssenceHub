package com.essencehub.project.Controllers.Finance;

import com.essencehub.project.DatabaseOperations.DatabaseConnection;
import com.essencehub.project.Finance.Outgoings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class UpdateOutcomeController {

    @FXML
    private Button updateOutcomeButton;

    @FXML
    private ComboBox<String> updateOutcomeSelectComboBox;

    @FXML
    private ComboBox<String> updateOutcomeStatusComboBox;

    @FXML
    private TextField updateOutcomeTextField;

    public ObservableList<Outgoings> getOutcomeList() {
        ObservableList<Outgoings> outgoings = FXCollections.observableArrayList();
        String sql = "SELECT date, title, cost FROM Outcome";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                LocalDate date = resultSet.getDate("date").toLocalDate();
                String title = resultSet.getString("title");
                String cost = resultSet.getString("cost");

                Outgoings outgoing = new Outgoings(date, title, cost);

                outgoings.add(outgoing); // Add the user to the ObservableList
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return outgoings;
    }
    public ObservableList<Outgoings> outgoings;

    @FXML
    void isUpdateOutcomeButtonClicked(ActionEvent event) {
        String outcomeString = updateOutcomeSelectComboBox.getValue();

        if (outcomeString == null || outcomeString.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Update Failed", "No Selection Made", "Please select an outcome record to update.");
            return;
        }

        // Parse selected item to extract title, date, and cost
        String[] parts = outcomeString.split("\\|");
        String title = parts[0].trim();
        String date = parts[1].trim();
        String cost = parts[2].trim();

        int outgoingsId = -1;

        // Step 1: Fetch the ID of the selected record
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT id FROM Outcome WHERE title = ? AND date = ? AND cost = ? LIMIT 1")) {

            pstmt.setString(1, title);
            pstmt.setString(2, date);
            pstmt.setString(3, cost);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                outgoingsId = rs.getInt("id");
                System.out.println("Retrieved ID: " + outgoingsId);
            } else {
                showAlert(Alert.AlertType.WARNING, "Update Failed", "Record Not Found", "No matching record was found.");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        // Step 2: Get the new value
        String whatToChange = updateOutcomeStatusComboBox.getValue();
        if(whatToChange.equals("Date")){
            if (isValidDate(whatToChange, "yyyy-MM-dd")) {

            } else {
                showAlert(Alert.AlertType.ERROR, "Invalid Date", "Format Error", "Please enter a date in the format yyyy-MM-dd.");
                return;
            }
        }
        String newValue = switch (whatToChange) {
            case "Title" -> updateOutcomeTextField.getText();
            case "Outcome" -> updateOutcomeTextField.getText();
            case "Date" -> updateOutcomeTextField.getText();
            default -> null;
        };


        if (newValue == null || newValue.trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Invalid Input", "Date", "date should be yyyy-mm-dd");
            return;
        }

        // Step 3: Update the record using ID
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(true); // Ensure changes are committed automatically

            String sql = switch (whatToChange) {
                case "Title" -> "UPDATE Outcome SET title = ? WHERE id = ?";
                case "Cost" -> "UPDATE Outcome SET cost = ? WHERE id = ?";
                case "Date" -> "UPDATE Outcome SET date = ? WHERE id = ?";
                default -> null;
            };

            if (sql != null) {
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, newValue);
                    pstmt.setInt(2, outgoingsId);

                    System.out.println("Executing Query: " + sql);
                    System.out.println("Parameters: New Value = " + newValue + ", ID = " + outgoingsId);

                    int rowsUpdated = pstmt.executeUpdate();
                    if (rowsUpdated > 0) {
                        showAlert(Alert.AlertType.INFORMATION, "Update Successful", "Update Completed", "Record updated successfully!");
                    } else {
                        showAlert(Alert.AlertType.WARNING, "Update Failed", "No Changes Made", "No rows were updated.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initialize();
    }

    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void initialize(){
        outgoings = getOutcomeList();
        populateUpdateOutcomeComboBox();
        populateUpdateOutcomeStatusComboBox();
    }

    private void populateUpdateOutcomeComboBox() {
        // Clear existing items
        updateOutcomeSelectComboBox.getItems().clear();

        // Populate ComboBox with formatted Outcome data
        for (Outgoings outcome : outgoings) {
            String formattedItem = String.format("%s | %s | %s",
                    outcome.getTitle(),
                    outcome.getDate().toString(),
                    outcome.getCost());
            updateOutcomeSelectComboBox.getItems().add(formattedItem);
        }
    }
    private void populateUpdateOutcomeStatusComboBox() {
        // Clear existing items
        updateOutcomeStatusComboBox.getItems().clear();
        // Add fixed options
        updateOutcomeStatusComboBox.getItems().addAll("Title", "Cost", "Date");

    }
    private boolean isValidDate(String dateStr, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        try {
            // Parse the date to validate format
            LocalDate.parse(dateStr, formatter);
            return true; // Date is valid
        } catch (DateTimeParseException e) {
            return false; // Invalid date format
        }
    }

}
