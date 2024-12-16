package com.essencehub.project.Controllers.EmployeeOperations;

import com.essencehub.project.Controllers.Menu.AdminMenuController;
import com.essencehub.project.User.Performance;
import com.essencehub.project.User.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private ComboBox<String> workHoursEndComboBox;

    @FXML
    private ComboBox<String> workHoursStartComboBox;

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

        String workingHour = workHoursStartComboBox.getValue() + "-" + workHoursEndComboBox.getValue();
        User user = new User(name, surname, phone, salary, isAdmin, birthDate, department, email, 40, true,password, Performance.F,0, "/com/essencehub/project/images/ProfilePictures/defaultpicture1.png",workingHour);
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

    @FXML
    void infoIconClicked(MouseEvent event) {
        AdminMenuController adminMenuController = AdminMenuController.getInstance();
        adminMenuController.loadFXMLContent("/com/essencehub/project/fxml/EmployeeOption/InfoEmployee.fxml");
    }

    public void initialize(){
        birthPickerHire.setValue(LocalDate.now().minusYears(25));
        isAdminCombobox.setValue("Employee");
        isAdminCombobox.getItems().addAll("Admin","Employee");
        workHoursStartComboBox.getItems().addAll("00:00","00:30","01:00","01:30","02:00","02:30","03:00","03:30","04:00","04:30","05:00","05:30","06:00","06:30","07:00","07:30","08:00","08:30","09:00","09:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30","17:00","17:30","18:00","18:30","19:00","19:30","20:00","20:30","21:00","21:30","22:00","22:30","23:00","23:30"
        );
        workHoursStartComboBox.setValue("08:30");
        workHoursEndComboBox.getItems().addAll("00:00","00:30","01:00","01:30","02:00","02:30","03:00","03:30","04:00","04:30","05:00","05:30","06:00","06:30","07:00","07:30","08:00","08:30","09:00","09:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30","17:00","17:30","18:00","18:30","19:00","19:30","20:00","20:30","21:00","21:30","22:00","22:30","23:00","23:30"
        );
        workHoursEndComboBox.setValue("17:30");
    }

}
