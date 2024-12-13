package com.essencehub.project.Controllers.Settings;

import com.essencehub.project.Controllers.Menu.LoginPageController;
import com.essencehub.project.User.User;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ChangePasswordWindow {
    @FXML
    private TextField newPasswordAgainTextField;

    @FXML
    private TextField newPasswordTextField;

    @FXML
    private Button savePasswordButton;

    @FXML
    void savePasswordButtonClicked(MouseEvent event) {

        if(newPasswordTextField.getText().equals(newPasswordAgainTextField.getText())&&!newPasswordTextField.getText().isEmpty()){
            User user = LoginPageController.getUser();
            user.changePassword(newPasswordTextField.getText());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"You have changed your password successfully!");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING,"Passwords must match and should not be blank!");
            alert.showAndWait();
        }


    }

}
