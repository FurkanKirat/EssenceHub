package com.essencehub.project.Controllers.Menu;
import com.essencehub.project.Controllers.Settings.ThemeController;
import com.essencehub.project.User.NotificationSender;
import com.essencehub.project.User.Task;
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
import java.time.LocalDateTime;
import java.util.List;

public class EmployeeMenuController {
    private double savedWidth;
    private double savedHeight;

    @FXML
    private HBox dashboardPanel;

    @FXML
    private HBox suggestionsPanel;

    @FXML
    private ImageView msgIcon;

    @FXML
    private ImageView profilePicturePanel;

    @FXML
    private ImageView settingIcon;

    @FXML
    private HBox requestLeavePanel;

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
    Node currentNode;
    @FXML
    void dashboardPanelClicked(MouseEvent event) {
        currentNode.getStyleClass().remove("selected-border");
        loadFXMLContent("/com/essencehub/project/fxml/Dashboard/DashboardEmployee.fxml");
        dashboardPanel.getStyleClass().add("selected-border");
        currentNode=dashboardPanel;

    }

    @FXML
    void msgIconCLicked(MouseEvent event) {
        settings("/com/essencehub/project/fxml/Message/MessageApp.fxml",event);
    }

    @FXML
    void requestLeaveClicked(MouseEvent event) {
        currentNode.getStyleClass().remove("selected-border");
        loadFXMLContent("/com/essencehub/project/fxml/RequestLeave/EmployeeViewRequests.fxml");
        requestLeavePanel.getStyleClass().add("selected-border");
        currentNode=requestLeavePanel;
    }


    @FXML
    void profilePicturePanelClicked(MouseEvent event) {
        settings("/com/essencehub/project/fxml/Settings/Settings.fxml",event);
    }

    @FXML
    void settingsIconClicked(MouseEvent event) {
        settings("/com/essencehub/project/fxml/Settings/Settings.fxml",event);
    }

    @FXML
    void tasksPanelClicked(MouseEvent event) {
        currentNode.getStyleClass().remove("selected-border");
        loadFXMLContent("/com/essencehub/project/fxml/Task/EmployeeViewTask.fxml");
        tasksPanel.getStyleClass().add("selected-border");
        currentNode=tasksPanel;
    }

    @FXML
    void suggestionsPanelClicked(MouseEvent event) {
        currentNode.getStyleClass().remove("selected-border");
        loadFXMLContent("/com/essencehub/project/fxml/Suggestions/sendSuggestions.fxml");
        suggestionsPanel.getStyleClass().add("selected-border");
        currentNode=suggestionsPanel;
    }

    @FXML
    void initialize() {
        try {
            String name =  LoginPageController.getUser().getName();
            String surname = LoginPageController.getUser().getSurname();
            profilePicturePanel.setImage(LoginPageController.getUser().getImage());
            nameLabel.setText(name + " " + surname);
            loadFXMLContent("/com/essencehub/project/fxml/Dashboard/DashboardEmployee.fxml");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        dashboardPanel.getStyleClass().add("selected-border");
        currentNode = dashboardPanel;


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
            ThemeController.changeTheme(scene);

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
