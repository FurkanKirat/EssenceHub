package com.essencehub.project.Controllers.Settings;

import com.essencehub.project.Controllers.Menu.AdminMenuController;
import com.essencehub.project.Controllers.Menu.EmployeeMenuController;
import com.essencehub.project.Controllers.Menu.LoginPageController;
import com.essencehub.project.DatabaseOperations.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.stage.Stage;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class SettingsController {

    @FXML
    private Label nameLabel;

    @FXML
    void homePageClicked(MouseEvent event) {
        try {
            if(LoginPageController.getResultSet().getBoolean("isAdmin")){
                AdminMenuController adminMenuController = AdminMenuController.getInstance();
                adminMenuController.settings("/com/essencehub/project/fxml/Menu/AdminMenu.fxml",event);
            }
            else{
                EmployeeMenuController employeeMenuController = EmployeeMenuController.getInstance();
                employeeMenuController.settings("/com/essencehub/project/fxml/Menu/EmployeeMenu.fxml",event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void initialize(){


        try {

            String name =  LoginPageController.getResultSet().getString("name");
            String surname = LoginPageController.getResultSet().getString("surname");
            nameLabel.setText(name + " " + surname);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void logOutClicked(ActionEvent event) {
        try{

            Parent root = FXMLLoader.load(getClass().getResource("/com/essencehub/project/fxml/Menu/LoginPage.fxml"));
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Essence Hub");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.getIcons().add(new Image( getClass().getResourceAsStream( "/com/essencehub/project/images/logo.jpg" )));
            currentStage.close();
            stage.show();

        } catch (Exception e) {

        }
    }



}

