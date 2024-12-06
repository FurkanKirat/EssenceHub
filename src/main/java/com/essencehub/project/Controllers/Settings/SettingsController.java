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
import javafx.stage.Modality;
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
        ThemeController.changeTheme(scene);
        stage.setTitle("Update Contact Info");
        Stage parentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.initOwner(parentStage);
        stage.initModality(Modality.WINDOW_MODAL);

        stage.setResizable(false);
        stage.setScene(scene);
        stage.getIcons().add(new Image( getClass().getResourceAsStream( "/com/essencehub/project/images/logo.jpg" )));
        stage.show();
    }
    private Node currentNode;

    @FXML
    void HomePageIconClicked(MouseEvent event) {
        try {
            if(LoginPageController.getUser().isAdmin()){
                AdminMenuController adminMenuController = AdminMenuController.getInstance();
                adminMenuController.settings("/com/essencehub/project/fxml/Menu/AdminMenu.fxml",event);
            }
            else{
                EmployeeMenuController employeeMenuController = EmployeeMenuController.getInstance();
                employeeMenuController.settings("/com/essencehub/project/fxml/Menu/EmployeeMenu.fxml",event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void initialize(){

        try {

            String name =  LoginPageController.getUser().getName();
            String surname = LoginPageController.getUser().getSurname();
            UserNamePanel.setText(name + " " + surname);
        } catch (Exception e) {
            e.printStackTrace();
        }
        profilePicture.setImage(LoginPageController.getUser().getImage());
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
                    ThemeController.changeTheme(scene);

                    Stage stage = new Stage();
                    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setTitle("Essence Hub");
                    Stage parentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                    stage.initOwner(parentStage);
                    stage.initModality(Modality.WINDOW_MODAL);
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
            ThemeController.changeTheme(scene);
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
            idTextField.setText(LoginPageController.getUser().getId()+"");
            nameTextField.setText(LoginPageController.getUser().getName());
            surnameTextField.setText(LoginPageController.getUser().getSurname());
            dateOfBirthTextField.setText(LoginPageController.getUser().getBirth());
            departmentTextField.setText(LoginPageController.getUser().getDepartment());
            phoneNumTextField.setText(LoginPageController.getUser().getPhoneNumber());
            emailTextField.setText(LoginPageController.getUser().getEmail());

            salaryTextField.setText(LoginPageController.getUser().getSalary()+"");
            performanceTextField.setText(LoginPageController.getUser().getMonthlyPerformance().toString());
            bonusSalaryTextField.setText(LoginPageController.getUser().getBonusSalary()+"");
            vacationDaysTextField.setText(LoginPageController.getUser().getRemainingLeaveDays()+"");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void editProfileButtonClicked(MouseEvent event) throws IOException {
        createNewScene("/com/essencehub/project/fxml/Settings/pickProfilePicture.fxml","Edit Profile",event);


    }
    void createNewScene(String FXMLFile,String title, Event event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource(FXMLFile));

        Scene scene = new Scene(root);
        Stage parentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.initOwner(parentStage);
        stage.initModality(Modality.WINDOW_MODAL);

        ThemeController.changeTheme(scene);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.setResizable(false);
        stage.show();

    }

    public VBox getCenterVBox() {
        return centerVBox;
    }
}

