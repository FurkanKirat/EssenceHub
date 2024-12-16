package com.essencehub.project.Controllers.EmployeeOperations;

import com.essencehub.project.Controllers.Menu.AdminMenuController;
import com.essencehub.project.Controllers.Menu.LoginPageController;
import com.essencehub.project.User.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class InfoEmployeeController {

    @FXML
    private TextField bonusSalaryTextField;

    @FXML
    private VBox centerVBox;

    @FXML
    private TextField dateOfBirthTextField;

    @FXML
    private TextField departmentTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private ImageView fireIcon;

    @FXML
    private ImageView hireIcon;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField performanceTextField;

    @FXML
    private TextField phoneNumTextField;

    @FXML
    private TextField salaryTextField;

    @FXML
    private TextField surnameTextField;

    @FXML
    private ImageView updateIcon;

    @FXML
    private TextField vacationDaysTextField;

    @FXML
    private ImageView infoIcon;

    @FXML
    private ComboBox<User> userCombobox;

    @FXML
    private TextField workHoursEndTextField;

    @FXML
    private TextField workHoursStartTextField;


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
        userCombobox.getItems().addAll(User.getUsers());
        userCombobox.setValue(LoginPageController.getUser());
        initializeInfo(userCombobox.getValue());
    }

    @FXML
    void userChanged(ActionEvent event) {
        initializeInfo(userCombobox.getValue());
    }

    private void initializeInfo(User user){
        idTextField.setText(user.getId()+"");
        nameTextField.setText(user.getName());
        surnameTextField.setText(user.getSurname());
        dateOfBirthTextField.setText(user.getBirth());
        departmentTextField.setText(user.getDepartment());
        phoneNumTextField.setText(user.getPhoneNumber());
        emailTextField.setText(user.getEmail());

        salaryTextField.setText(user.getSalary()+"");
        performanceTextField.setText(user.getMonthlyPerformance().toString());
        bonusSalaryTextField.setText(user.getBonusSalary()+"");
        vacationDaysTextField.setText(user.getRemainingLeaveDays()+"");

        workHoursStartTextField.setText(user.getWorkingHour().substring(0,user.getWorkingHour().indexOf("-")));
        workHoursEndTextField.setText(user.getWorkingHour().substring(user.getWorkingHour().indexOf("-")+1));
    }



}
