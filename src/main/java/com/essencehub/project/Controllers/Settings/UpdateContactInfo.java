package com.essencehub.project.Controllers.Settings;

import com.essencehub.project.Controllers.Menu.LoginPageController;
import com.essencehub.project.User.AdminOperations;
import com.essencehub.project.User.User;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class UpdateContactInfo {

    private User user;
    @FXML
    private Button SaveInfoButton;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    void SaveInfoButtonClicked(MouseEvent event) {
        user.setEmail(emailField.getText());
        user.setPhoneNumber(phoneNumberField.getText());
        AdminOperations.updateUser(user);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        SettingsController settingsController = SettingsController.getInstance();
        settingsController.updateInfo(user.getEmail(),user.getPhoneNumber());
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"You have changed your contact info successfully!");
        alert.showAndWait();
    }
    public void initialize(){
        user = LoginPageController.getUser();
        emailField.setText(user.getEmail());
        phoneNumberField.setText(user.getPhoneNumber());
    }

}
