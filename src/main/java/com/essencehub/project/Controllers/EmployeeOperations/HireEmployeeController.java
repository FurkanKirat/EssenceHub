package com.essencehub.project.Controllers.EmployeeOperations;

import com.essencehub.project.Controllers.Menu.AdminMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    private ComboBox<?> isAdminCombobox;

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

}
