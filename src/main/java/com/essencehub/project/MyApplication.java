package com.essencehub.project;
import com.essencehub.project.Controllers.LoginPageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;

import static javafx.application.Application.launch;
import static jdk.xml.internal.SecuritySupport.getResourceAsStream;

public class MyApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        try{
            //LineChartView lineChartView = new LineChartView();
            //Scene scene = new Scene(lineChartView.createLineChart(), 800, 600);
            Parent root = FXMLLoader.load(getClass().getResource("loginpage.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Essence Hub");
            var bounds = Screen.getPrimary().getVisualBounds();

            // Sahne boyutunu ayarlayın
            stage.setX(bounds.getMinX());
            stage.setY(bounds.getMinY());
            stage.setWidth(bounds.getWidth());
            stage.setHeight(bounds.getHeight());
            stage.setMaximized(true);
            stage.setMinHeight(400);
            stage.setMinWidth(600);
            stage.setScene(scene);
            stage.getIcons().add(
                    new Image( getClass().getResourceAsStream( "/com/essencehub/project/images/logo.jpg" )));
            stage.show();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}