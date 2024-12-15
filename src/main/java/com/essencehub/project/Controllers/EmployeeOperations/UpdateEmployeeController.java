package com.essencehub.project.Controllers.EmployeeOperations;

import com.essencehub.project.Controllers.Menu.AdminMenuController;
import com.essencehub.project.Controllers.Menu.LoginPageController;
import com.essencehub.project.DatabaseOperations.DatabaseConnection;
import com.essencehub.project.User.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class UpdateEmployeeController {

    @FXML
    private DatePicker birthPicker;

    @FXML
    private ImageView fireIcon;

    @FXML
    private ImageView hireIcon;

    @FXML
    private ComboBox<String> propertyComboBox;

    @FXML
    private ComboBox<User> selectUpdateComboBox;

    @FXML
    private TextField statusTextField;

    @FXML
    private Button updateButton;

    @FXML
    private ImageView updateIcon;

    @FXML
    private ComboBox<String> workerStatusComboBox;

    private int whichStatus = 0;

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
            case 1 ->  {
                newValue = propertyComboBox.getValue();
                if(newValue.equals("Admin")){
                    newValue = "1";
                }else if(newValue.equals("Employee")){
                    newValue = "0";
                }
            }
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
    }

    @FXML
    void statusSelected(ActionEvent event) {
        initializeCombobox();
    }

    @FXML
    void fireIconClicked(MouseEvent event) {
        AdminMenuController adminMenuController = AdminMenuController.getInstance();
        adminMenuController.loadFXMLContent("/com/essencehub/project/fxml/EmployeeOption/fireEmployee.fxml");
    }

    @FXML
    void hireIconClicked(MouseEvent event) {
        AdminMenuController adminMenuController = AdminMenuController.getInstance();
        adminMenuController.loadFXMLContent("/com/essencehub/project/fxml/EmployeeOption/hireEmployee.fxml");
    }

    @FXML
    void updateIconClicked(MouseEvent event) {
        AdminMenuController adminMenuController = AdminMenuController.getInstance();
        adminMenuController.loadFXMLContent("/com/essencehub/project/fxml/EmployeeOption/updateEmployee.fxml");
    }

    @FXML
    void infoIconClicked(MouseEvent event) {
        AdminMenuController adminMenuController = AdminMenuController.getInstance();
        adminMenuController.loadFXMLContent("/com/essencehub/project/fxml/EmployeeOption/InfoEmployee.fxml");
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
                birthPicker.setValue(LocalDate.of(2000, Month.JANUARY,1));
                statusTextField.setVisible(false);
                propertyComboBox.setVisible(false);
                birthPicker.setVisible(true);

        }
    }
    private void populateComboBox() {
        selectUpdateComboBox.getItems().clear();
        int currentUserId = LoginPageController.getUser().getId();

        List<User> activeWorkers = getActiveWorkers();

        List<User> filteredWorkers = activeWorkers.stream()
                .filter(user -> user.getId() != currentUserId)
                .toList();


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

    public void initialize(){
        populateComboBox();
        workerStatusComboBox.getItems().addAll("Name", "Surname","Phone Number","Salary", "Status", "Birth", "Department", "Email", "RemainingLeaveDays","Performance","Password");
    }


}