package com.essencehub.project.Controllers;

import com.essencehub.project.Controllers.Menu.LoginPageController;
import com.essencehub.project.DatabaseOperations.DatabaseConnection;
import com.essencehub.project.User.AdminOperations;
import com.essencehub.project.User.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApplicationMenuController {



    @FXML
    private TextField BirthField;

    @FXML
    private TabPane tabPane;

    @FXML
    private Label birthLabel;

    @FXML
    private TextField bonusField;

    @FXML
    private Label bonusLabel;

    @FXML
    private TextField departmantField;

    @FXML
    private Label departmantLabel;

    @FXML
    private TextField emailField;

    @FXML
    private Label emailLabel;

    @FXML
    private AnchorPane fireAnchorPane;

    @FXML
    private Button fireButton;

    @FXML
    private ComboBox<User> fireComboBox;

    @FXML
    private Tab fireTab;

    @FXML
    private AnchorPane generalAnchorPane;

    @FXML
    private AnchorPane hireAnchorPane;

    @FXML
    private Tab hireTab;

    @FXML
    private TextField isActiveField;

    @FXML
    private Label isActiveLabel;

    @FXML
    private TextField isAdminField;

    @FXML
    private Label isAdminLabel;

    @FXML
    private TextField nameField;

    @FXML
    private Label nameLabel;

    @FXML
    private TextField passwordField;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField performanceField;

    @FXML
    private Label performanceLabel;

    @FXML
    private TextField phoneField;

    @FXML
    private Label phoneNumberLabel;

    @FXML
    private TextField remainingDaysField;

    @FXML
    private Label remainingDaysLabel;

    @FXML
    private TextField salaryField;

    @FXML
    private Label salaryLabel;

    @FXML
    private ComboBox<String> selectStatusComboBox;

    @FXML
    private ComboBox<User> selectUpdateComboBox;

    @FXML
    private ComboBox<String> workerStatusComboBox;

    @FXML
    private TextField surnameField;

    @FXML
    private Label surnameLabel;

    @FXML
    private Button updateButton;

    @FXML
    private AnchorPane updateStatusPane;

    @FXML
    private Tab updateStatusTab;

    @FXML
    void isFireButtonClicked(ActionEvent event) {
        User selectedWorker = fireComboBox.getValue(); // Get selected worker from ComboBox
        if (selectedWorker == null) {
            System.out.println("No worker selected.");
            return;
        }

        int workerId = selectedWorker.getId(); // Retrieve worker's ID

        // Call method to update worker's status in the database

        boolean success = fireWorker(workerId);

        if (success) {
            System.out.println("Worker fired successfully: " + selectedWorker.getName() + " " + selectedWorker.getSurname());
            populateComboBox(); // Refresh the ComboBox to remove fired worker
        } else {
            System.out.println("Failed to fire worker.");
        }
    }

    public boolean fireWorker(int workerId) {
        String sql = "UPDATE User SET isActive = 0 WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, workerId);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0; // Return true if the update was successful

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @FXML
    private void initialize() {
        populateComboBox();
        workerStatusComboBox.getItems().addAll("Name", "Surname","Phone Number","Salary", "IsAdmin", "Birth", "Department", "Email", "RemainingLeaveDays","Performance", "BonusSalary","password");
        workerStatusComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("Selected: " + newValue);
            }
        });
    }

    private void populateComboBox() {
        fireComboBox.getItems().clear();
        selectUpdateComboBox.getItems().clear();
        int currentUserId = LoginPageController.getUserID();

        // Fetch all active workers from the database
        List<User> activeWorkers = getActiveWorkers();

        // Filter out the current user from the list
        List<User> filteredWorkers = activeWorkers.stream()
                .filter(user -> user.getId() != currentUserId) // Exclude users with the same ID
                .toList();

        // Add the filtered workers to the ComboBox
        fireComboBox.getItems().addAll(filteredWorkers);

        // Customize the display in the ComboBox
        fireComboBox.setCellFactory(comboBox -> new ListCell<>() {
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                if (empty || user == null) {
                    setText(null);
                } else {
                    setText(user.getName() + " " + user.getSurname());
                }
            }
        });

        fireComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                if (empty || user == null) {
                    setText(null);
                } else {
                    setText(user.getName() + " " + user.getSurname());
                }
            }
        });


        selectUpdateComboBox.getItems().addAll(filteredWorkers); // Add workers to the ComboBox

        // Customize the display of the ComboBox
        selectUpdateComboBox.setCellFactory(comboBox -> new ListCell<>() {
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                if (empty || user == null) {
                    setText(null);
                } else {
                    setText(user.getName() + " " + user.getSurname()); // Display "Name Surname"
                }
            }
        });

        // Display selected item in the ComboBox
        selectUpdateComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                if (empty || user == null) {
                    setText(null);
                } else {
                    setText(user.getName() + " " + user.getSurname());
                }
            }
        });
    }

    public ArrayList<User> getActiveWorkers() {
        ArrayList<User> activeWorkers = new ArrayList<>();
        String sql = "SELECT id, name, surname FROM User WHERE isActive = 1";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");

                // Create a User object for each worker
                User user = new User();
                user.setId(id);
                user.setName(name);
                user.setSurname(surname);
                activeWorkers.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activeWorkers;
    }

    @FXML
    void isHireButtonClicked(ActionEvent event) {
        // Get the input values from the fields
        String name = nameField.getText();
        String surname = surnameField.getText();
        String birthDate = BirthField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String department = departmantField.getText();
        String salaryText = salaryField.getText();
        String bonusText = bonusField.getText();
        String isAdminText = isAdminField.getText();
        String isActiveText = isActiveField.getText();
        String password = passwordField.getText();
        String performanceText = performanceField.getText();
        String remainingDaysText = remainingDaysField.getText();


        if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || department.isEmpty() || salaryText.isEmpty()) {
            System.out.println("Please fill in all required fields.");
            return;
        }


        double salary = 0;
        double bonus = 0;
        boolean isAdmin = false;
        boolean isActive = false;
        int performance = 0;
        int remainingDays = 0;

        try {
            salary = Double.parseDouble(salaryText);
            if (!bonusText.isEmpty()) {
                bonus = Double.parseDouble(bonusText);
            }
            isAdmin = Boolean.parseBoolean(isAdminText);
            isActive = Boolean.parseBoolean(isActiveText);
            if (!performanceText.isEmpty()) {
                performance = Integer.parseInt(performanceText);
            }
            if (!remainingDaysText.isEmpty()) {
                remainingDays = Integer.parseInt(remainingDaysText);
            }
        } catch (NumberFormatException e) {
            // Handle parsing errors
            System.out.println("Invalid number format in one or more fields.");
            return;
        }

        AdminOperations ao = new AdminOperations();
        User user = new User(name, surname, phone, salary, isAdmin, birthDate, department, email, remainingDays, isActive,password);
        ao.addUser(user);

        initialize();// to see added person immediately
    }

    @FXML
    void isUpdateButtonClicked(ActionEvent event) {

        User selectedWorker = selectUpdateComboBox.getValue();

        if (selectedWorker == null) {
            System.out.println("No worker selected.");
            return;
        }

        int workerId = selectedWorker.getId();
        String whatToChange = workerStatusComboBox.getValue();
        String newValue = selectStatusComboBox.getValue();

        try (Connection conn = DatabaseConnection.getConnection()) {

            String sql = null;
            if (whatToChange.equals("Name")) {
                sql = "UPDATE User SET name = ? WHERE id = ?";
            } else if (whatToChange.equals("Surname")) {
                sql = "UPDATE User SET surname = ? WHERE id = ?";
            } else if (whatToChange.equals("Phone Number")) {
                sql = "UPDATE User SET phoneNumber = ? WHERE id = ?";
            } else if (whatToChange.equals("Salary")) {
                sql = "UPDATE User SET salary = ? WHERE id = ?";
            } else if (whatToChange.equals("IsAdmin")) {
                sql = "UPDATE User SET isAdmin = ? WHERE id = ?";
            } else if (whatToChange.equals("Birth")) {
                sql = "UPDATE User SET birth = ? WHERE id = ?";
            } else if (whatToChange.equals("Department")) {
                sql = "UPDATE User SET department = ? WHERE id = ?";
            } else if (whatToChange.equals("Email")) {
                sql = "UPDATE User SET email = ? WHERE id = ?";
            } else if (whatToChange.equals("RemainingLeaveDays")) {
                sql = "UPDATE User SET remainingLeaveDays = ? WHERE id = ?";
            } else if (whatToChange.equals("Performance")) {
                sql = "UPDATE User SET monthlyPerformance = ? WHERE id = ?";
            } else if (whatToChange.equals("BonusSalary")) {
                sql = "UPDATE User SET bonusSalary = ? WHERE id = ?";
            } else if (whatToChange.equals("password")) {
                sql = "UPDATE User SET password = ? WHERE id = ?";
            }

            // Execute the query if the field matches
            if (sql != null) {
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    // Set the new value and worker ID
                    pstmt.setString(1, newValue);
                    pstmt.setInt(2, workerId);

                    // Execute update
                    int rowsUpdated = pstmt.executeUpdate();
                    System.out.println("Rows updated: " + rowsUpdated);
                }
            } else {
                System.out.println("Invalid field to update: " + whatToChange);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        populateComboBox();



    }
}
