package com.essencehub.project.Controllers.Settings;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class ThemeController {
    private double savedWidth;
    private double savedHeight;

    @FXML
    private Button changeButton;

    @FXML
    private ComboBox<String> themePicker;
    public static String theme = "/com/essencehub/project/css/style.css";
    @FXML
    void changeButtonClicked(MouseEvent event) {
        // Tema seçimine göre kaynak CSS dosyasını belirleyin
        if (themePicker.getValue().equals("Gray Theme")) {
            theme = "/com/essencehub/project/css/GrayStyle.css";

        }
        else if(themePicker.getValue().equals("Blue Theme")){
            theme = "/com/essencehub/project/css/BlueStyle.css";

        }
        else if(themePicker.getValue().equals("Light Blue Theme")){
            theme = "/com/essencehub/project/css/LightBlue.css";
        }
        else if(themePicker.getValue().equals("Green Theme")){
            theme = "/com/essencehub/project/css/GreenStyle.css";
        }
        else if(themePicker.getValue().equals("Purple Theme")){
            theme = "/com/essencehub/project/css/PurpleTheme.css";
        }
        else if(themePicker.getValue().equals("Red Theme")){
            theme = "/com/essencehub/project/css/RedStyle.css";
        }

        String destinationCssFile = "EssenceHub/src/main/resources/com/essencehub/project/css/style.css";

        try {
            // Kaynak dosyayı ClassLoader kullanarak okuyun
            URL sourceUrl = getClass().getResource(theme);
            if (sourceUrl == null) {
                throw new IOException("Kaynak dosya bulunamadı: " + theme);
            }
            Path sourcePath = Paths.get(sourceUrl.toURI());

            // Kaynak dosyayı okuyun
            String cssContent = Files.readString(sourcePath);

            // İçeriği hedef CSS dosyasına yazın
            Path destinationPath = Paths.get(destinationCssFile);
            Files.writeString(destinationPath, cssContent);

            System.out.println("CSS dosyası başarıyla kopyalandı ve tema değiştirildi.");
            sceneLoader(event);
            FXMLoader("/com/essencehub/project/fxml/Settings/theme.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    public void initialize() {
        themePicker.getItems().addAll("Blue Theme", "Gray Theme","Green Theme","Light Blue Theme","Purple Theme","Red Theme");
        themePicker.setValue("Blue Theme");
    }
    public static void changeTheme(Scene scene){
        scene.getStylesheets().clear();
        scene.getStylesheets().add(ThemeController.class.getResource(theme).toExternalForm());

    }
    void FXMLoader(String FXMLFile){
        try {
            SettingsController settings = SettingsController.getInstance();

            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLFile));
            Node newContent = loader.load();
            settings.getCenterVBox().getChildren().clear();
            settings.getCenterVBox().getChildren().add(newContent);
            VBox.setVgrow(newContent, Priority.ALWAYS);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void sceneLoader(MouseEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/essencehub/project/fxml/Settings/settings.fxml"));
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





}