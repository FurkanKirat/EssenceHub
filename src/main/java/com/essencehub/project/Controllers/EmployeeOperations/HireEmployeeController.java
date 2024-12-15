package com.essencehub.project.Controllers.EmployeeOperations;

import com.essencehub.project.Controllers.Menu.AdminMenuController;
import com.essencehub.project.User.Performance;
import com.essencehub.project.User.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class HireEmployeeController {

    @FXML
    private DatePicker birthPickerHire;

    @FXML
    private TextField departmantField;

    @FXML
    private Label departmantLabel;

    @FXML
    private TextField emailField;

    @FXML
    private ImageView fireIcon;

    @FXML
    private ImageView hireIcon;

    @FXML
    private ComboBox<String> isAdminCombobox;

    @FXML
    private Label isAdminLabel;

    @FXML
    private TextField nameField;

    @FXML
    private Label nameLabel;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField phoneField;

    @FXML
    private Label phoneNumberLabel;

    @FXML
    private Label phoneNumberLabel1;

    @FXML
    private Label phoneNumberLabel2;

    @FXML
    private Label remainingDaysLabel1;

    @FXML
    private TextField salaryField;

    @FXML
    private Label salaryLabel;

    @FXML
    private TextField surnameField;

    @FXML
    private ImageView updateIcon;

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


        if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || department.isEmpty() || salaryText.isEmpty() || birthDate.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Fields");
            alert.setHeaderText("Required Fields Missing");
            alert.setContentText("Please fill in all required fields");
            alert.showAndWait();
            return;
        }

        double salary;
        boolean isAdmin;

        try {
            salary = Double.parseDouble(salaryText);

            isAdmin = Boolean.parseBoolean(isAdminText);

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Number Format Error");
            alert.setContentText("Please ensure salary, bonus, and remaining days are valid numbers.");
            alert.showAndWait();
            return;
        }


        User user = new User(name, surname, phone, salary, isAdmin, birthDate, department, email, 40, true,password, Performance.F,0, "/com/essencehub/project/images/ProfilePictures/defaultpicture1.png");
        User.addUser(user);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("User Added");
        alert.setContentText("The user " + name + " " + surname + " has been added successfully.");
        alert.showAndWait();

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

    public void initialize(){
        isAdminCombobox.setValue("Employee");
        isAdminCombobox.getItems().addAll("Admin","Employee");
    }

}
