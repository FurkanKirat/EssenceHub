package com.essencehub.project.Controllers;

import com.essencehub.project.Controllers.Menu.LoginPageController;
import com.essencehub.project.DatabaseOperations.DatabaseConnection;
import com.essencehub.project.User.Performance;
import com.essencehub.project.User.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class ApplicationMenuController {


    @FXML
    private TabPane tabPane;

    @FXML
    private Label birthLabel;

    @FXML
    private ComboBox<String> isAdminCombobox;

    @FXML
    private DatePicker birthPicker;

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
    private ComboBox<String> propertyComboBox;

    @FXML
    private TextField statusTextField;

    @FXML
    private DatePicker birthPickerHire;

    private int whichStatus = 0;
    @FXML
    void statusSelected(ActionEvent event) {
        initializeCombobox();
    }

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
        workerStatusComboBox.getItems().addAll("Name", "Surname","Phone Number","Salary", "Status", "Birth", "Department", "Email", "RemainingLeaveDays","Performance","Password");
        workerStatusComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
        });
        birthPickerHire.setValue(LocalDate.of(2000, Month.JANUARY,1));
        isAdminCombobox.setValue("Employee");
        isAdminCombobox.getItems().addAll("Admin","Employee");



    }

    private void populateComboBox() {
        fireComboBox.getItems().clear();
        selectUpdateComboBox.getItems().clear();
        int currentUserId = LoginPageController.getUser().getId();

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
        String birthDate = birthPickerHire.getValue().toString();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String department = departmantField.getText();
        String salaryText = salaryField.getText();

        String isAdminText = isAdminCombobox.getValue().equals("Admin") ? "true" : "false";

        String password = passwordField.getText();
        String remainingDaysText = remainingDaysField.getText();


        if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || department.isEmpty() || salaryText.isEmpty() || birthDate.isEmpty() || phone.isEmpty() || remainingDaysText.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Fields");
            alert.setHeaderText("Required Fields Missing");
            alert.setContentText("Please fill in all required fields");
            alert.showAndWait();
            return;
        }

        double salary;
        boolean isAdmin;
        int remainingDays;

        try {
            salary = Double.parseDouble(salaryText);

            isAdmin = Boolean.parseBoolean(isAdminText);

            remainingDays = Integer.parseInt(remainingDaysText);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Number Format Error");
            alert.setContentText("Please ensure salary, bonus, and remaining days are valid numbers.");
            alert.showAndWait();
            return;
        }


        User user = new User(name, surname, phone, salary, isAdmin, birthDate, department, email, remainingDays, true,password, Performance.F,0, "/com/essencehub/project/images/ProfilePictures/defaultpicture1.png");
        User.addUser(user);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("User Added");
        alert.setContentText("The user " + name + " " + surname + " has been added successfully.");
        alert.showAndWait();

        //initialize();
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


        String newValue;
        switch(whichStatus){
            case 1 -> newValue = propertyComboBox.getValue().equals("Admin") ? "1": "0";
            case 2 -> newValue = birthPicker.getValue().toString();
            default -> newValue = statusTextField.getText();
        };

        int rowsUpdated = 0;

        try (Connection conn = DatabaseConnection.getConnection()) {

            String sql = switch (whatToChange) {
                case "Name" -> "UPDATE User SET name = ? WHERE id = ?";
                case "Surname" -> "UPDATE User SET surname = ? WHERE id = ?";
                case "Phone Number" -> "UPDATE User SET phoneNumber = ? WHERE id = ?";
                case "Salary" -> "UPDATE User SET salary = ? WHERE id = ?";
                case "Status" -> "UPDATE User SET isAdmin = ? WHERE id = ?";
                case "Birth" -> "UPDATE User SET birth = ? WHERE id = ?";
                case "Department" -> "UPDATE User SET department = ? WHERE id = ?";
                case "Email" -> "UPDATE User SET email = ? WHERE id = ?";
                case "RemainingLeaveDays" -> "UPDATE User SET remainingLeaveDays = ? WHERE id = ?";
                case "Performance" -> "UPDATE User SET monthlyPerformance = ? WHERE id = ?";
                case "Password" -> "UPDATE User SET password = ? WHERE id = ?";
                default -> null;
            };


            if (sql != null) {
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setString(1, newValue);
                    pstmt.setInt(2, workerId);

                    if((whichStatus==0&&!statusTextField.getText().isEmpty())||(whichStatus==1&&!propertyComboBox.getValue().isEmpty())||(whichStatus==2)){
                        rowsUpdated = pstmt.executeUpdate();
                    }
                    if (rowsUpdated > 0) {
                        if((whichStatus==0&&!statusTextField.getText().isEmpty())||(whichStatus==1&&!propertyComboBox.getValue().isEmpty())||(whichStatus==2)){
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
        // workerStatusComboBox.setValue(null);

    }
    private void initializeCombobox(){
        switch (workerStatusComboBox.getValue()){
            case "Name":
            case "Surname":
            case "Phone Number":
            case "Salary":
            case "Department":
            case "Email":
            case "RemainingLeaveDays":
            case "password":
                propertyComboBox.setVisible(false);
                birthPicker.setVisible(false);
                statusTextField.setVisible(true);
                whichStatus=0;
                break;
            case "Status":
                statusTextField.setVisible(false);
                birthPicker.setVisible(false);
                propertyComboBox.setVisible(true);
                propertyComboBox.setValue("Employee");
                propertyComboBox.getItems().setAll("Admin","Employee");
                whichStatus=1;
                break;
            case "Performance":
                whichStatus=1;
                statusTextField.setVisible(false);
                birthPicker.setVisible(false);
                propertyComboBox.setValue("F");
                propertyComboBox.getItems().setAll("A_PLUS","A","A_MINUS","B_PLUS","B","B_MINUS","C_PLUS","C","C_MINUS","D","F");
                propertyComboBox.setVisible(true);
                break;
            case "Birth":
                whichStatus=2;
                birthPicker.setValue(LocalDate.of(2000,Month.JANUARY,1));
                statusTextField.setVisible(false);
                propertyComboBox.setVisible(false);
                birthPicker.setVisible(true);

        }
    }
}
