package com.essencehub.project.Controllers.Menu;

import com.essencehub.project.User.User;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;

public class AdminMenuController {
    private double savedWidth;
    private double savedHeight;
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
    private VBox func;

    private static AdminMenuController instance;


    public AdminMenuController() {
        instance = this;
    }
    public void initialize() {

        try {

            String name =  LoginPageController.getResultSet().getString("name");
            String surname = LoginPageController.getResultSet().getString("surname");
            namePanel.setText(name + " " + surname);

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        loadFXMLContent("/com/essencehub/project/fxml/dashboard.fxml");


    }

    @FXML
    void assignTaskPanelClicked(MouseEvent event) {
        loadFXMLContent("/com/essencehub/project/fxml/Task/TaskGeneralManager.fxml");
    }

    @FXML
    void dashboradClicked(MouseEvent event) {
        loadFXMLContent("/com/essencehub/project/fxml/dashboard.fxml");
    }

    @FXML
    void employeePanelClicked(MouseEvent event) {

    }

    @FXML
    void financeClicked(MouseEvent event) {
        loadFXMLContent("/com/essencehub/project/fxml/Finance/finance.fxml");
    }

    @FXML
    void msgIconClicked(MouseEvent event) {

    }

    @FXML
    void profilePicturePanelClicked(MouseEvent event) {
        settings("/com/essencehub/project/settings.fxml",event);
    }

    @FXML
    void settingIconClicked(MouseEvent event) {
        settings("/com/essencehub/project/settings.fxml",event);
    }

    @FXML
    void stockTrackingCLicked(MouseEvent event)  {
        loadFXMLContent("/com/essencehub/project/fxml/StockTracking/stock-tracking.fxml");

    }
    public void loadFXMLContent(String fxmlFile) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Node newContent = loader.load();

            func.getChildren().clear();
            func.getChildren().add(newContent);
            VBox.setVgrow(newContent, Priority.ALWAYS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void settings(String fxmlFile, Event event){
        try {


            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent newContent = loader.load();
            Scene scene = new Scene(newContent);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setHeight(savedHeight);

            stage.setWidth(savedWidth);
            savedHeight = stage.getHeight();
            savedWidth = stage.getWidth();

            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static AdminMenuController getInstance() {
        return instance;
    }


}
