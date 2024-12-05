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
    private TextField statusTextField;

    @FXML
    void isFireButtonClicked(ActionEvent event) {
        User selectedWorker = fireComboBox.getValue();
        if (selectedWorker == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An Error Occurred");
            alert.setContentText("No one is selected");
            alert.showAndWait();
            return;
        }

        int workerId = selectedWorker.getId();

        boolean success = fireWorker(workerId);

        if (success) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Deactivation Successful");
            alert.setHeaderText("User Deactivated");
            alert.setContentText("The user with ID " + workerId + " has been successfully deactivated.");
            alert.showAndWait();
            populateComboBox();
        }
    }

    public boolean fireWorker(int workerId) {
        String sql = "UPDATE User SET isActive = 0 WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, workerId);

            int rowsUpdated = statement.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An Error Occurred");
            alert.setContentText("Failed to deactivate the user. Please try again.");
            alert.showAndWait();
            return false;
        }
    }
    @FXML
    private void initialize() {
        populateComboBox();
        workerStatusComboBox.getItems().addAll("Name", "Surname","Phone Number","Salary", "IsAdmin", "Birth", "Department", "Email", "RemainingLeaveDays","Performance", "BonusSalary","password");
        workerStatusComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

            }
        });
    }

    private void populateComboBox() {
        fireComboBox.getItems().clear();
        selectUpdateComboBox.getItems().clear();
        int currentUserId = LoginPageController.getUserID();


        List<User> activeWorkers = getActiveWorkers();

        List<User> filteredWorkers = activeWorkers.stream()
                .filter(user -> user.getId() != currentUserId)
                .toList();


        fireComboBox.getItems().addAll(filteredWorkers);


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


        selectUpdateComboBox.getItems().addAll(filteredWorkers);


        selectUpdateComboBox.setCellFactory(comboBox -> new ListCell<>() {
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
        String name = nameField.getText();
        String surname = surnameField.getText();
        String birthDate = BirthField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String department = departmantField.getText();
        String salaryText = salaryField.getText();
        String bonusText = bonusField.getText();
        String isAdminText = isAdminField.getText();
        String password = passwordField.getText();
        String remainingDaysText = remainingDaysField.getText();


        if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || department.isEmpty() || salaryText.isEmpty() || birthDate.isEmpty() || phone.isEmpty()|| remainingDaysText.isEmpty()|| password.isEmpty()|| bonusText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Fields");
            alert.setHeaderText("Required Fields Missing");
            alert.setContentText("Please fill in all required fields");
            alert.showAndWait();
            return;
        }
        if(isAdminText.equalsIgnoreCase("True") || isAdminText.equalsIgnoreCase("False")){

        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("False Input On IsAdmin ");
            alert.setHeaderText("MisWritten Word");
            alert.setContentText("Should be true or false");
            alert.showAndWait();
            return;
        }

        double salary = 0;
        double bonus = 0;
        boolean isAdmin = false;
        int remainingDays = 0;

        try {
            salary = Double.parseDouble(salaryText);
            if (!bonusText.isEmpty()) {
                bonus = Double.parseDouble(bonusText);
            }
            isAdmin = Boolean.parseBoolean(isAdminText);

            if (!remainingDaysText.isEmpty()) {
                remainingDays = Integer.parseInt(remainingDaysText);
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Number Format Error");
            alert.setContentText("Please ensure salary, bonus, and remaining days are valid numbers.");
            alert.showAndWait();
            return;
        }

        AdminOperations ao = new AdminOperations();
        User user = new User(name, surname, phone, salary, isAdmin, birthDate, department, email, remainingDays, true,password);
        ao.addUser(user);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("User Added");
        alert.setContentText("The user " + name + " " + surname + " has been added successfully.");
        alert.showAndWait();

        initialize();
    }

    @FXML
    void isUpdateButtonClicked(ActionEvent event) {

        User selectedWorker = selectUpdateComboBox.getValue();

        if (selectedWorker == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Update Failed");
            alert.setHeaderText("No Changes Made");
            alert.setContentText("No rows were updated. Please check the input.");
            alert.showAndWait();
            return;
        }

        int workerId = selectedWorker.getId();
        String whatToChange = workerStatusComboBox.getValue();
        String newValue = statusTextField.getText();
        int rowsUpdated = 0;

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


            if (sql != null) {
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setString(1, newValue);
                    pstmt.setInt(2, workerId);

                    if(!statusTextField.getText().isEmpty()){
                        rowsUpdated = pstmt.executeUpdate();
                    }
                    if (rowsUpdated > 0) {
                       if(!statusTextField.getText().isEmpty()){
                           Alert alert = new Alert(Alert.AlertType.INFORMATION);
                           alert.setTitle("Update Successful");
                           alert.setHeaderText("Update Completed");
                           alert.setContentText("The field \"" + whatToChange + "\" for worker \"" + selectedWorker.getName() + "\" has been updated successfully!");
                           alert.showAndWait();
                       }else{
                           Alert alert = new Alert(Alert.AlertType.WARNING);
                           alert.setTitle("Update Failed");
                           alert.setHeaderText("No Changes Made");
                           alert.setContentText("No rows were updated. Please check the input.");
                           alert.showAndWait();
                       }

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(rowsUpdated == 0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Update Failed");
            alert.setHeaderText("No Changes Made");
            alert.setContentText("No rows were updated. Please check the input.");
            alert.showAndWait();
        }

        populateComboBox();
        statusTextField.setText("");
        workerStatusComboBox.setValue(null);




    }
}
