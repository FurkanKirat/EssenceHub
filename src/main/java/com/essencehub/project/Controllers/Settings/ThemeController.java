package com.essencehub.project.Controllers.Settings;

import javafx.fxml.FXML;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


public class ThemeController {

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

        } else {
            theme = "/com/essencehub/project/css/BlueStyle.css";

        }

        String destinationCssFile = "src/main/resources/com/essencehub/project/css/style.css";

        try {
            // Kaynak dosyayı ClassLoader kullanarak okuyun
            URL sourceUrl = getClass().getResource(theme);
            if (sourceUrl == null) {
                throw new IOException("Kaynak dosya bulunamadı: " + theme);
            }
            Path sourcePath = Paths.get(sourceUrl.toURI());

            // Kaynak dosyayı okuyun
            String cssContent = Files.readString(sourcePath);

            // Ana sahneyi al
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            System.out.println("CSS dosyası başarıyla kopyalandı ve tema değiştirildi.");
        } catch (Exception e) {
            e.printStackTrace();
        }

            // Yeni sahneyi uygula
            primaryStage.setScene(scene);
            primaryStage.show();

    }

    public void initialize() {
        themePicker.getItems().addAll("Blue Theme", "Gray Theme");
        themePicker.setValue("Blue Theme");
    }
    public static void changeTheme(Scene scene){

        scene.getStylesheets().clear();
        scene.getStylesheets().add(ThemeController.class.getResource(theme).toExternalForm());

    }




}
