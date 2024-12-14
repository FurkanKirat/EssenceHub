package com.essencehub.project.Controllers.Settings;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.*;

public class PasswordController {

    @FXML
    private ImageView HomePageIcon;

    @FXML
    private Button LogOutButton;

    @FXML
    private HBox PasswordMenuPanelClicked;

    @FXML
    private HBox ProfileInfoPanel;

    @FXML
    private HBox ThemeMenuPanel;

    @FXML
    private Label UserNamePanel;

    @FXML
    private Button editPasswordButton;

    @FXML
    private TextField passwordTextField;

    @FXML
    void HomePageIconClicked(MouseEvent event) {

    }

    @FXML
    void LogOutButtonClicked(MouseEvent event) {

    }

    @FXML
    void PasswordMenuPanelClicked(MouseEvent event) {

    }

    @FXML
    void ProfileInfoPanelClicked(MouseEvent event) {

    }

    @FXML
    void ThemeMenuPanelClicked(MouseEvent event) {

    }

    @FXML
    void UserNamePanelClicked(MouseEvent event) {

    }

    @FXML
    void editPasswordButtonClicked(MouseEvent event) {
        try{
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/essencehub/project/fxml/Settings/PasswordUpdate.fxml"));
            stage.getIcons().add(new Image( getClass().getResourceAsStream( "/com/essencehub/project/images/logo.jpg" )));
            Scene scene = new Scene(root);
            ThemeController.changeTheme(scene);
            stage.setTitle("Edit Password");

            stage.setResizable(false);

            Stage parentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.initOwner(parentStage);
            stage.initModality(Modality.WINDOW_MODAL);

            stage.setScene(scene);

            stage.getIcons().add(new Image( getClass().getResourceAsStream( "/com/essencehub/project/images/logo.jpg" )));
            stage.showAndWait();

        } catch (Exception e) {

        }
    }

}

