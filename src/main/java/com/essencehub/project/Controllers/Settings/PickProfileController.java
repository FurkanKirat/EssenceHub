package com.essencehub.project.Controllers.Settings;

import com.essencehub.project.Controllers.Menu.LoginPageController;
import com.essencehub.project.User.AdminOperations;
import com.essencehub.project.User.User;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
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
    private ImageView img7;

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

    @FXML
    private VBox vBox7;

    private List<ImageView> images;
    private List<Pane> boxes;

    private String selectedImageLocation;
    private User user;


    @FXML
    public void initialize() {

        images = Arrays.asList(img1, img2, img3, img4, img5, img6,img7);
        boxes = Arrays.asList(vBox1, vBox2, vBox3, vBox4, vBox5, vBox6,vBox7);
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

    @FXML
    void img7Clicked(MouseEvent event) {
        clearStyle();
        vBox7.getStyleClass().add("container");
        img7.getStyleClass().add("buttonClicked");
        uploadImage(event);
    }

    void clearStyle(){
        for(int i = 0;i<7;i++) {
            images.get(i).getStyleClass().remove("buttonClicked");
            boxes.get(i).getStyleClass().remove("container");
        }
    }

    void uploadImage(Event event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PNG Files", "*.png")
        );
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile == null || !selectedFile.exists()) {
            //System.err.println("Geçersiz dosya seçildi.");
            return;
        }

        // Kullanıcı ana dizini altında hedef yol
        String absolutePath = System.getProperty("user.home") + "/essencehub/ProfilePictures/" + user.getId() + ".png";

        selectedImageLocation = "file:" + System.getProperty("user.home") + "\\essencehub\\ProfilePictures\\" + user.getId() + ".png";

        System.out.println(selectedImageLocation);
        File profilePicture = new File(absolutePath);

        // Klasörü kontrol et ve oluştur
        if (!profilePicture.getParentFile().exists()) {
            boolean created = profilePicture.getParentFile().mkdirs();
            if (!created) {
                System.err.println("Hedef klasör oluşturulamadı: " + profilePicture.getParentFile().getAbsolutePath());
                return;
            }
        }

        try {
            // Dosyayı kopyala
            Files.copy(selectedFile.toPath(), profilePicture.toPath(), StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Dosya başarıyla kopyalandı: " + profilePicture.getAbsolutePath());

            // Kullanıcı profil resmini güncelle
            String imageUrl = profilePicture.toURI().toString();

            System.out.println("Profil resmi güncellendi: " + imageUrl);
        } catch (IOException e) {
            System.err.println("Dosya kopyalanırken hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }








}
