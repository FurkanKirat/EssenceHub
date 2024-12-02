package com.essencehub.project.Controllers.Menu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;
import java.sql.SQLException;

public class AdminMenuController {

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
        loadFXMLContent("/com/essencehub/project/dashboard.fxml");


    }

    @FXML
    void assignTaskPanelClicked(MouseEvent event) {
        loadFXMLContent("/com/essencehub/project/fxml/Task/TaskGeneralManager.fxml");
    }

    @FXML
    void dashboradClicked(MouseEvent event) {
        loadFXMLContent("/com/essencehub/project/dashboard.fxml");
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
        
    }

    @FXML
    void settingIconClicked(MouseEvent event) {

    }

    @FXML
    void stockTrackingCLicked(MouseEvent event)  {
        loadFXMLContent("/com/essencehub/project/fxml/StockTracking/stocktrial.fxml");

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

    public static AdminMenuController getInstance() {
        return instance;
    }
}
