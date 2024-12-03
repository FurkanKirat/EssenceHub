package com.essencehub.project.Controllers.Menu;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class EmployeeMenuController {
    private double savedWidth;
    private double savedHeight;

    @FXML
    private HBox dashboardPanel;

    @FXML
    private ImageView msgIcon;

    @FXML
    private HBox performancePanel;

    @FXML
    private ImageView profilePicturePanel;

    @FXML
    private ImageView settingIcon;

    @FXML
    private HBox tasksPanel;

    @FXML
    private Label nameLabel;

    @FXML
    private VBox func;

    private static EmployeeMenuController instance;


    public EmployeeMenuController() {
        instance = this;
    }

    @FXML
    void dashboardPanelClicked(MouseEvent event) {
        loadFXMLContent("/com/essencehub/project/fxml/dashboard.fxml");
    }

    @FXML
    void msgIconCLicked(MouseEvent event) {

    }

    @FXML
    void performancePanelClicked(MouseEvent event) {

    }

    @FXML
    void profilePicturePanelClicked(MouseEvent event) {

    }

    @FXML
    void settingsIconClicked(MouseEvent event) {
        settings("/com/essencehub/project/settings.fxml",event);
    }

    @FXML
    void tasksPanelClicked(MouseEvent event) {
        loadFXMLContent("/com/essencehub/project/fxml/Task/ViewTaskEmployee.fxml");
    }

    @FXML
    void initialize() {
        try {
            String name =  LoginPageController.getResultSet().getString("name");
            String surname = LoginPageController.getResultSet().getString("surname");
            nameLabel.setText(name + " " + surname);
        }
        catch (SQLException e){
            e.printStackTrace();
        }

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

    public static EmployeeMenuController getInstance() {
        return instance;
    }
}
