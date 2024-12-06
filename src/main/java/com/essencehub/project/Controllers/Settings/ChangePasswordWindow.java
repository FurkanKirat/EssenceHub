package com.essencehub.project.Controllers.Settings;

import com.essencehub.project.Controllers.Menu.LoginPageController;
import com.essencehub.project.User.AdminOperations;
import com.essencehub.project.User.Performance;
import com.essencehub.project.User.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChangePasswordWindow {
    @FXML
    private TextField newPasswordAgainTextField;

    @FXML
    private TextField newPasswordTextField;

    @FXML
    private Button savePasswordButton;

    @FXML
    void savePasswordButtonClicked(MouseEvent event) {

        if(newPasswordTextField.getText().equals(newPasswordAgainTextField.getText())){
            User user = LoginPageController.getUser();
            user.changePassword(newPasswordTextField.getText());

        }
    }

}
