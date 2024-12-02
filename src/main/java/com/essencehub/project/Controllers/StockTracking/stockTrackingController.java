package com.essencehub.project.Controllers.StockTracking;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class stockTrackingController {

    @FXML
    private HBox assignTaskPanel;

    @FXML
    private StackPane barChartPane;

    @FXML
    private HBox dashboardPanel;

    @FXML
    private HBox employeePanel;

    @FXML
    private HBox financePanel;

    @FXML
    private StackPane geoChartPane;

    @FXML
    private StackPane linechartPane;

    @FXML
    private ImageView msgIcon;

    @FXML
    private Label namePanel;

    @FXML
    private StackPane pieChartPane;

    @FXML
    private ImageView profilPicturePanel;

    @FXML
    private ImageView settingIcon;

    @FXML
    private Label statusPanel;

    @FXML
    private HBox stockTrackingPanel;

    @FXML
    void assignTaskPanelClicked(MouseEvent event) {

    }

    @FXML
    void barChartPaneClicked(MouseEvent event) {

    }

    @FXML
    void dashboradClicked(MouseEvent event) {

    }

    @FXML
    void employeePanelClicked(MouseEvent event) {

    }

    @FXML
    void financeClicked(MouseEvent event) {

    }

    @FXML
    void geoChartPaneClicked(MouseEvent event) throws IOException {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/essencehub/project/fxml/StockTracking/geo-chart.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("Essence Hub");
            stage.setScene(scene);
            stage.getIcons().add(new Image( getClass().getResourceAsStream( "/com/essencehub/project/images/logo.jpg" )));
            stage.setMinWidth(1315);
            stage.setMinHeight(890);
            stage.show();

            Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            currentStage.close();

    }

    @FXML
    void linechartPaneClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/essencehub/project/fxml/StockTracking/lineChartClicked.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setTitle("Essence Hub");
        stage.setScene(scene);
        stage.getIcons().add(new Image( getClass().getResourceAsStream( "/com/essencehub/project/images/logo.jpg" )));
        stage.setMinWidth(1315);
        stage.setMinHeight(890);
        stage.show();

        Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void msgIconClicked(MouseEvent event) {

    }

    @FXML
    void pieChartPaneClicked(MouseEvent event) {

    }

    @FXML
    void profilePicturePanelClicked(MouseEvent event) {

    }

    @FXML
    void settingIconClicked(MouseEvent event) {

    }

    @FXML
    void stockTrackingCLicked(MouseEvent event) throws IOException {

    }

}

