package com.essencehub.project.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TrialController {

    @FXML
    private StackPane barChartPane;

    @FXML
    private StackPane geoChartPane;

    @FXML
    private StackPane linechartPane;

    @FXML
    private StackPane pieChartPane;

    @FXML
    void barChartPaneClicked(MouseEvent event) {

    }

    @FXML
    void geoChartPaneClicked(MouseEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/essencehub/project/geoChartClicked.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("Essence Hub");
            stage.setScene(scene);
            stage.getIcons().add(new Image( getClass().getResourceAsStream( "/com/essencehub/project/images/logo.jpg" )));
            stage.setMinWidth(1315);
            stage.setMinHeight(890);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void linechartPaneClicked(MouseEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/essencehub/project/lineChartClicked.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("Essence Hub");
            stage.setScene(scene);
            stage.getIcons().add(new Image( getClass().getResourceAsStream( "/com/essencehub/project/images/logo.jpg" )));

            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void pieChartPaneClicked(MouseEvent event) {

    }

}
