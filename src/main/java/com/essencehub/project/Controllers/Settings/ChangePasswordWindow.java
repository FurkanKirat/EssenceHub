package com.essencehub.project.Controllers.Settings;

import com.essencehub.project.Controllers.Menu.LoginPageController;
import com.essencehub.project.User.AdminOperations;
import com.essencehub.project.User.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
            ResultSet resultSet = LoginPageController.getResultSet();
            try {
                AdminOperations.updateUser(new User(

                resultSet.getString("name"),
                resultSet.getString("surname"),
                resultSet.getString("phoneNumber"),
                resultSet.getInt("salary"),
                resultSet.getBoolean("isAdmin"),
                resultSet.getString("birth"),
                resultSet.getString("department"),
                resultSet.getString("email"),
                resultSet.getInt("remainingLeaveDays"),
                true,
                newPasswordTextField.getText()

                ));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
