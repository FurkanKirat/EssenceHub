package com.essencehub.project.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerAdminPanel {

    @FXML
    private HBox assignTaskPanel;

    @FXML
    private HBox dashboardPanel;

    @FXML
    private HBox employeePanel;

    @FXML
    private HBox financePanel;

    @FXML
    private ImageView msgIcon;

    @FXML
    private Label namePanel;

    @FXML
    private BorderPane borderPane;

    @FXML
    private ImageView profilPicturePanel;

    @FXML
    private ImageView settingIcon;

    @FXML
    private Label statusPanel;

    @FXML
    private HBox stockTrackingPanel;

    @FXML
    private HBox functions;

    @FXML
    void assignTaskPanelClicked(MouseEvent event) throws IOException {

            }

    @FXML
    void dashboradClicked(MouseEvent event) {

    }

    @FXML
    void employeePanelClicked(MouseEvent event) {

    }

    @FXML
    void financeClicked(MouseEvent event) {
        try{
            Parent root1 = FXMLLoader.load(getClass().getResource("/com/essencehub/project/piechart.fxml"));
            functions.getChildren().clear();
            functions.getChildren().add(root1);
            //Stage prevStage = (Stage) stockTrackingPanel.getScene().getWindow();
            //Parent root2 = prevStage.getScene().getRoot();

            //StackPane stackPane = new StackPane();
            //root1.relocate(300,300);
            //stackPane.getChildren().addAll(root2, root1);
            //stackPane.setAlignment(root1, Pos.BOTTOM_RIGHT);

            //Scene scene = new Scene(stackPane);

            /*Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            //stage.setScene(scene);
            stage.setTitle("Essence Hub");

            stage.show();*/
        }
        catch(Exception e){

        }


    }

    @FXML
    void msgIconClicked(MouseEvent event) {

    }

    @FXML
    void profilePicturePanelClicked(MouseEvent event) {

    }

    @FXML
    void settingIconClicked(MouseEvent event) {

    }

    @FXML
    void stockTrackingCLicked(MouseEvent event) {

    }

}
