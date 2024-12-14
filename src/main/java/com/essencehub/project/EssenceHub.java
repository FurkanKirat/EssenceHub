package com.essencehub.project;

import com.essencehub.project.Controllers.Settings.ThemeController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class EssenceHub extends Application {
    @Override
    public void start(Stage stage) {

        try{
            Parent root = FXMLLoader.load(getClass().getResource("/com/essencehub/project/fxml/Menu/LoginPage.fxml"));

            Scene scene = new Scene(root);
            ThemeController.changeTheme(scene);
            stage.setTitle("Essence Hub");

            stage.setResizable(false);
            stage.setScene(scene);
            stage.getIcons().add(new Image( getClass().getResourceAsStream( "/com/essencehub/project/images/logo.jpg" )));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}