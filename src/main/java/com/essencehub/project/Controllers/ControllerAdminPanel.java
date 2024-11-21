package com.essencehub.project.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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
    void assignTaskPanelClicked(MouseEvent event) {

    }

    @FXML
    void dashboradClicked(MouseEvent event) {

    }

    @FXML
    void employeePanelClicked(MouseEvent event) {

    }

    @FXML
    void financeClicked(MouseEvent event) {
        loadFXMLContent("/com/essencehub/project/piechart.fxml");
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
    void stockTrackingCLicked(MouseEvent event)  {
        loadFXMLContent("/com/essencehub/project/trial.fxml");

    }
    public void loadFXMLContent(String fxmlFile) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Node newContent = loader.load();

            functions.getChildren().clear();
            functions.getChildren().add(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
