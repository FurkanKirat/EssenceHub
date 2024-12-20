package com.essencehub.project.Controllers.Finance;

import com.essencehub.project.Controllers.Menu.AdminMenuController;
import com.essencehub.project.DatabaseOperations.DatabaseConnection;
import com.essencehub.project.Finance.Income;
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

public class UpdateIncomeController {

    @FXML
    private Button updateIncomeButton;

    @FXML
    private ComboBox<String> updateIncomeSelectComboBox;

    @FXML
    private ComboBox<String> updateIncomeStatusComboBox;

    @FXML
    private TextField updateIncomeTextField;
    public ObservableList<Income> getIncomeList() {
        ObservableList<Income> incomes = FXCollections.observableArrayList();
        String sql = "SELECT date, title, amount FROM Income";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                LocalDate date = resultSet.getDate("date").toLocalDate();
                String title = resultSet.getString("title");
                String amount = resultSet.getString("amount");

                Income income = new Income(date, title, amount);

                incomes.add(income); // Add the user to the ObservableList
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return incomes;
    }
    public ObservableList<Income> incomes;

    @FXML
    void isUpdateIncomeButtonClicked(ActionEvent event) {
        String incomeString = updateIncomeSelectComboBox.getValue();

        if (incomeString == null || incomeString.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Update Failed", "No Selection Made", "Please select an income record to update.");
            return;
        }

        // Parse selected item to extract title, date, and amount
        String[] parts = incomeString.split("\\|");
        String title = parts[0].trim();
        String date = parts[1].trim();
        String amount = parts[2].trim();

        int incomeId = -1;

        // Step 1: Fetch the ID of the selected record
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT id FROM Income WHERE title = ? AND date = ? AND amount = ? LIMIT 1")) {

            pstmt.setString(1, title);
            pstmt.setString(2, date);
            pstmt.setString(3, amount);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                incomeId = rs.getInt("id");
                System.out.println("Retrieved ID: " + incomeId);
            } else {
                showAlert(Alert.AlertType.WARNING, "Update Failed", "Record Not Found", "No matching record was found.");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        // Step 2: Get the new value
        String whatToChange = updateIncomeStatusComboBox.getValue();
        if(whatToChange.equals("Date")){
            if (isValidDate(updateIncomeTextField.getText(), "yyyy-MM-dd")) {

            } else {
                showAlert(Alert.AlertType.ERROR, "Invalid Date", "Format Error", "Please enter a date in the format yyyy-MM-dd.");
                return;
            }
        }
        String newValue = switch (whatToChange) {
            case "Title" -> updateIncomeTextField.getText();
            case "Amount" -> updateIncomeTextField.getText();
            case "Date" -> updateIncomeTextField.getText();
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
                case "Title" -> "UPDATE Income SET title = ? WHERE id = ?";
                case "Amount" -> "UPDATE Income SET amount = ? WHERE id = ?";
                case "Date" -> "UPDATE Income SET date = ? WHERE id = ?";
                default -> null;
            };

            if (sql != null) {
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, newValue);
                    pstmt.setInt(2, incomeId);

                    System.out.println("Executing Query: " + sql);
                    System.out.println("Parameters: New Value = " + newValue + ", ID = " + incomeId);

                    int rowsUpdated = pstmt.executeUpdate();
                    if (rowsUpdated > 0) {
                        showAlert(Alert.AlertType.INFORMATION, "Update Successful", "Update Completed", "Record updated successfully!");
                        AdminMenuController adminMenuController = AdminMenuController.getInstance();
                        adminMenuController.loadFXMLContent("/com/essencehub/project/fxml/Finance/Income.fxml");
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
        incomes = getIncomeList();
        populateUpdateIncomeComboBox();
        populateUpdateIncomeStatusComboBox();
    }

    private void populateUpdateIncomeComboBox() {
        // Clear existing items
        updateIncomeSelectComboBox.getItems().clear();

        // Populate ComboBox with formatted Income data
        for (Income income : incomes) {
            String formattedItem = String.format("%s | %s | %s",
                    income.getTitle(),
                    income.getDate().toString(),
                    income.getAmount());
            updateIncomeSelectComboBox.getItems().add(formattedItem);
        }
    }
    private void populateUpdateIncomeStatusComboBox() {
        // Clear existing items
        updateIncomeStatusComboBox.getItems().clear();
        // Add fixed options
        updateIncomeStatusComboBox.getItems().addAll("Title", "Amount", "Date");

    }
    private boolean isValidDate(String dateStr, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        try {
            // Parse the date to validate format
            System.out.println(dateStr);
            LocalDate.parse(dateStr, formatter);
            return true; // Date is valid
        } catch (DateTimeParseException e) {
            return false; // Invalid date format
        }
    }

}