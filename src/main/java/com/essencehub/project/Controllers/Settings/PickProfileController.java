package com.essencehub.project.Controllers.Settings;

import com.essencehub.project.Controllers.Menu.LoginPageController;
import com.essencehub.project.User.User;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

public class PickProfileController {

    @FXML
    private Button editProfileButton;

    @FXML
    private ImageView img1;

    @FXML
    private ImageView img2;

    @FXML
    private ImageView img3;

    @FXML
    private ImageView img4;

    @FXML
    private ImageView img5;

    @FXML
    private ImageView img6;

    @FXML
    private VBox vBox1;

    @FXML
    private VBox vBox2;

    @FXML
    private VBox vBox3;

    @FXML
    private VBox vBox4;

    @FXML
    private VBox vBox5;

    @FXML
    private VBox vBox6;


    private List<ImageView> images;
    private List<Pane> boxes;

    private String selectedImageLocation;
    private User user;


    @FXML
    public void initialize() {

        images = Arrays.asList(img1, img2, img3, img4, img5, img6);
        boxes = Arrays.asList(vBox1, vBox2, vBox3, vBox4, vBox5, vBox6);
        user = LoginPageController.getUser();
        selectedImageLocation = user.getImageLocation();


    }
    @FXML
    void editProfileButtonClicked(MouseEvent event) {

        user.changeImage(selectedImageLocation);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        SettingsController settingsController = SettingsController.getInstance();
        settingsController.getProfilePicture().setImage(user.getImage());


    }

    @FXML
    void img1Clicked(MouseEvent event) {
        clearStyle();
        vBox1.getStyleClass().add("container");
        img2.getStyleClass().add("buttonClicked");
        selectedImageLocation = "/com/essencehub/project/images/ProfilePictures/defaultpicture1.png";
    }

    @FXML
    void img2Clicked(MouseEvent event) {
        clearStyle();
        vBox2.getStyleClass().add("container");
        img2.getStyleClass().add("buttonClicked");
        selectedImageLocation = "/com/essencehub/project/images/ProfilePictures/defaultpicture2.png";
    }

    @FXML
    void img3Clicked(MouseEvent event) {
        clearStyle();
        vBox3.getStyleClass().add("container");
        img3.getStyleClass().add("buttonClicked");
        selectedImageLocation = "/com/essencehub/project/images/ProfilePictures/defaultpicture3.png";
    }

    @FXML
    void img4Clicked(MouseEvent event) {
        clearStyle();
        vBox4.getStyleClass().add("container");
        img4.getStyleClass().add("buttonClicked");
        selectedImageLocation = "/com/essencehub/project/images/ProfilePictures/defaultpicture4.png";
    }

    @FXML
    void img5Clicked(MouseEvent event) {
        clearStyle();
        vBox5.getStyleClass().add("container");
        img5.getStyleClass().add("buttonClicked");
        selectedImageLocation = "/com/essencehub/project/images/ProfilePictures/defaultpicture5.png";
    }

    @FXML
    void img6Clicked(MouseEvent event) {
        clearStyle();
        vBox6.getStyleClass().add("container");
        img6.getStyleClass().add("buttonClicked");
        selectedImageLocation = "/com/essencehub/project/images/ProfilePictures/defaultpicture6.png";
    }


    void clearStyle(){
        for(int i = 0;i<6;i++) {
            images.get(i).getStyleClass().remove("buttonClicked");
            boxes.get(i).getStyleClass().remove("container");
        }
    }









}
