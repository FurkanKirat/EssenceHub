package com.essencehub.project.Controllers.Menu;

import com.essencehub.project.Controllers.Settings.ThemeController;
import com.essencehub.project.User.NotificationSender;
import com.essencehub.project.User.Task;
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
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    private HBox leaveDaysPanel;

    @FXML
    private VBox func;

    @FXML
    private HBox suggestionsPanel;

    private static AdminMenuController instance;


    public AdminMenuController() {
        instance = this;
    }
    Node currentNode;
    public void initialize() {
        try {

            String name =  LoginPageController.getUser().getName();
            String surname = LoginPageController.getUser().getSurname();
            namePanel.setText(name + " " + surname);

            profilPicturePanel.setImage(LoginPageController.getUser().getImage());

        }
        catch (Exception e){
            e.printStackTrace();
        }
        loadFXMLContent("/com/essencehub/project/fxml/Dashboard/DashboardAdmin.fxml");
        dashboardPanel.getStyleClass().add("selected-border");

        currentNode = dashboardPanel;

        List<Task> tasks = Task.getAllTasks();
        for(Task task: tasks){
            if(!task.isTaskDone()&& LocalDateTime.now().isAfter(task.getFinishTime())){
                NotificationSender.send("Task not finished","Someone did not finished their task before the deadline!");
                break;
            }
        }
    }

    @FXML
    void assignTaskPanelClicked(MouseEvent event) {
        currentNode.getStyleClass().remove("selected-border");
        loadFXMLContent("/com/essencehub/project/fxml/Task/AdminViewTasks.fxml");
        assignTaskPanel.getStyleClass().add("selected-border");
        currentNode=assignTaskPanel;
    }

    @FXML
    void dashboradClicked(MouseEvent event) {
        currentNode.getStyleClass().remove("selected-border");
        loadFXMLContent("/com/essencehub/project/fxml/Dashboard/DashboardAdmin.fxml");
        dashboardPanel.getStyleClass().add("selected-border");
        currentNode=dashboardPanel;
    }

    @FXML
    void employeePanelClicked(MouseEvent event) {
        currentNode.getStyleClass().remove("selected-border");
        loadFXMLContent("/com/essencehub/project/fxml/EmployeeOption/hireEmployee.fxml");
        employeePanel.getStyleClass().add("selected-border");
        currentNode=employeePanel;
    }

    @FXML
    void financeClicked(MouseEvent event) {
        currentNode.getStyleClass().remove("selected-border");
        loadFXMLContent("/com/essencehub/project/fxml/Finance/Income.fxml");
        financePanel.getStyleClass().add("selected-border");
        currentNode=financePanel;
    }

    @FXML
    void leaveDaysPanelClicked(MouseEvent event) {
        currentNode.getStyleClass().remove("selected-border");
        loadFXMLContent("/com/essencehub/project/fxml/RequestLeave/AdminViewRequests.fxml");
        leaveDaysPanel.getStyleClass().add("selected-border");
        currentNode=leaveDaysPanel;
    }

    @FXML
    void suggestionsPanelClicked(MouseEvent event) {
        currentNode.getStyleClass().remove("selected-border");
        loadFXMLContent("/com/essencehub/project/fxml/Suggestions/viewSuggestionsTitle.fxml");
        suggestionsPanel.getStyleClass().add("selected-border");
        currentNode=suggestionsPanel;
    }

    @FXML
    void msgIconClicked(MouseEvent event) {
        settings("/com/essencehub/project/fxml/Message/MessageApp.fxml",event);
    }

    @FXML
    void profilePicturePanelClicked(MouseEvent event) {
        settings("/com/essencehub/project/fxml/Settings/Settings.fxml",event);
    }

    @FXML
    void settingIconClicked(MouseEvent event) {
        settings("/com/essencehub/project/fxml/Settings/Settings.fxml",event);
    }

    @FXML
    void stockTrackingCLicked(MouseEvent event)  {
        currentNode.getStyleClass().remove("selected-border");
        loadFXMLContent("/com/essencehub/project/fxml/StockTracking/ViewStock.fxml");
        stockTrackingPanel.getStyleClass().add("selected-border");
        currentNode=stockTrackingPanel;

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



    public static AdminMenuController getInstance() {
        return instance;
    }


}
