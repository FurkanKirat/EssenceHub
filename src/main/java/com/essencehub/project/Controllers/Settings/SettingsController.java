package com.essencehub.project.Controllers.Settings;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.essencehub.project.Controllers.Menu.AdminMenuController;
import com.essencehub.project.Controllers.Menu.EmployeeMenuController;
import com.essencehub.project.Controllers.Menu.LoginPageController;

import java.io.IOException;
import java.sql.SQLException;

public class SettingsController {

    @FXML
    private Button editProfileButton;

    @FXML
    private Button EditInfoButton;

    @FXML
    private ImageView HomePageIcon;

    @FXML
    private Button LogOutButton;

    @FXML
    private HBox PasswordMenuPanelClicked;

    @FXML
    private HBox ProfileInfoPanel;

    @FXML
    private Button SaveInfoButton;

    @FXML
    private HBox ThemeMenuPanel;

    @FXML
    private Label UserNamePanel;

    @FXML
    private TextField bonusSalaryTextField;

    @FXML
    private VBox centerVBox;

    @FXML
    private TextField dateOfBirthTextField;

    @FXML
    private TextField departmentTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField performanceTextField;

    @FXML
    private TextField phoneNumTextField;

    @FXML
    private TextField salaryTextField;

    @FXML
    private TextField surnameTextField;

    @FXML
    private TextField vacationDaysTextField;

    @FXML
    private ImageView profilePicture;

    @FXML
    void EditInfoButtonClicked(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/com/essencehub/project/fxml/Settings/contactInfoUpdate.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Update Contact Info");

        stage.setResizable(false);
        stage.setScene(scene);
        stage.getIcons().add(new Image( getClass().getResourceAsStream( "/com/essencehub/project/images/logo.jpg" )));
        stage.show();
    }
    private Node currentNode;

    @FXML
    void HomePageIconClicked(MouseEvent event) {
        try {
            if(LoginPageController.getResultSet().getBoolean("isAdmin")){
                AdminMenuController adminMenuController = AdminMenuController.getInstance();
                adminMenuController.settings("/com/essencehub/project/fxml/Menu/AdminMenu.fxml",event);
            }
            else{
                EmployeeMenuController employeeMenuController = EmployeeMenuController.getInstance();
                employeeMenuController.settings("/com/essencehub/project/fxml/Menu/EmployeeMenu.fxml",event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void initialize(){

        try {

            String name =  LoginPageController.getResultSet().getString("name");
            String surname = LoginPageController.getResultSet().getString("surname");
            UserNamePanel.setText(name + " " + surname);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        profilePicture.setImage(LoginPageController.getImage());
        currentNode = ProfileInfoPanel;

        profileInfo();
    }

    @FXML
    void LogOutButtonClicked(MouseEvent event) {
        Alert logOutAlert = new Alert(Alert.AlertType.CONFIRMATION);
        logOutAlert.setTitle("Log Out");
        logOutAlert.setHeaderText("Are you sure you want to log out?");

        logOutAlert.showAndWait().ifPresent(response->{
            if(response== ButtonType.OK){
                try{

                    Parent root = FXMLLoader.load(getClass().getResource("/com/essencehub/project/fxml/Menu/LoginPage.fxml"));
                    Scene scene = new Scene(root);

                    Stage stage = new Stage();
                    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setTitle("Essence Hub");
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.getIcons().add(new Image( getClass().getResourceAsStream( "/com/essencehub/project/images/logo.jpg" )));
                    currentStage.close();
                    stage.show();

                } catch (Exception e) {

                }
            }
        });

    }

    @FXML
    void PasswordMenuPanelClicked(MouseEvent event) {
        changeScene("/com/essencehub/project/fxml/Settings/password.fxml");
        PasswordMenuPanelClicked.getStyleClass().add("selected-border");
        currentNode=PasswordMenuPanelClicked;

    }

    @FXML
    void ProfileInfoPanelClicked(MouseEvent event) {
        currentNode.getStyleClass().remove("selected-border");
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/essencehub/project/fxml/Settings/settings.fxml"));
            Parent newContent = loader.load();
            Scene scene = new Scene(newContent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ProfileInfoPanel.getStyleClass().add("selected-border");
        currentNode=ProfileInfoPanel;
        profileInfo();
    }

    @FXML
    void ThemeMenuPanelClicked(MouseEvent event) {
        changeScene("/com/essencehub/project/fxml/Settings/theme.fxml");
        ThemeMenuPanel.getStyleClass().add("selected-border");
        currentNode=ThemeMenuPanel;
    }

    @FXML
    void UserNamePanelClicked(MouseEvent event) {

    }
    void changeScene(String fxmlFile){
        currentNode.getStyleClass().remove("selected-border");
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Node newContent = loader.load();

            centerVBox.getChildren().clear();
            centerVBox.getChildren().add(newContent);
            VBox.setVgrow(newContent, Priority.ALWAYS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void profileInfo(){
        try {
            idTextField.setText(LoginPageController.getResultSet().getInt("ID")+"");
            nameTextField.setText(LoginPageController.getResultSet().getString("name"));
            surnameTextField.setText(LoginPageController.getResultSet().getString("surname"));
            dateOfBirthTextField.setText(LoginPageController.getResultSet().getString("birth"));
            departmentTextField.setText(LoginPageController.getResultSet().getString("department"));
            phoneNumTextField.setText(LoginPageController.getResultSet().getString("phoneNumber"));
            emailTextField.setText(LoginPageController.getResultSet().getString("email"));

            salaryTextField.setText(LoginPageController.getResultSet().getInt("salary")+"");
            performanceTextField.setText(LoginPageController.getResultSet().getString("monthlyPerformance"));
            bonusSalaryTextField.setText(LoginPageController.getResultSet().getInt("bonusSalary")+"");
            vacationDaysTextField.setText(LoginPageController.getResultSet().getInt("remainingLeaveDays")+"");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void editProfileButtonClicked(MouseEvent event) throws IOException {
        createNewScene("/com/essencehub/project/fxml/Settings/pickProfilePicture.fxml","Edit Profile");


    }
    void createNewScene(String FXMLFile,String title) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource(FXMLFile));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.setResizable(false);
        stage.show();

    }

}

