package com.essencehub.project.Controllers.Settings;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ThemeController {

    @FXML
    private Button changeButton;

    @FXML
    private ComboBox<?> themePicker;

    @FXML
    void changeButtonClicked(MouseEvent event) {
        // Kaynak ve hedef CSS dosyalarının yollarını tanımlayın
        String sourceCssFile = "C:\\Users\\deniz\\OneDrive\\Masaüstü\\java\\CS102 HW\\EssenceHub\\EssenceHub\\src\\main\\resources\\com\\essencehub\\project\\css\\GrayStyle.css";
        String destinationCssFile = "C:\\Users\\deniz\\OneDrive\\Masaüstü\\java\\CS102 HW\\EssenceHub\\EssenceHub\\src\\main\\resources\\com\\essencehub\\project\\css\\style.css";

        try {
            // Kaynak CSS dosyasını okuyun
            Path sourcePath = Paths.get(sourceCssFile);
            String cssContent = Files.readString(sourcePath);

            // İçeriği hedef CSS dosyasına yazın
            Path destinationPath = Paths.get(destinationCssFile);
            Files.writeString(destinationPath, cssContent);



            System.out.println("CSS dosyası başarıyla kopyalandı ve tema değiştirildi.");
        } catch (IOException e) {
            System.err.println("Bir hata oluştu: " + e.getMessage());
        }
    }




}
