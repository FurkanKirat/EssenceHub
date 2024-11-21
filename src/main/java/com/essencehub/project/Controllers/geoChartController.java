package com.essencehub.project.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class geoChartController {

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
    private ImageView profilPicturePanel;

    @FXML
    private ImageView settingIcon;

    @FXML
    private Label statusPanel;

    @FXML
    private HBox stockTrackingPanel;

    @FXML
    private WebView webView;

    @FXML
    void assignTaskPanelClicked(MouseEvent event) {

    }

    @FXML
    void dashboradClicked(MouseEvent event) {

    }

    @FXML
    void employeePanelClicked(MouseEvent event) {

    }

    @FXML
    void financeClicked(MouseEvent event) {

    }

    @FXML
    void msgIconClicked(MouseEvent event) {

    }

    @FXML
    void profilePicturePanelClicked(MouseEvent event) {

    }

    @FXML
    void settingIconClicked(MouseEvent event) {

    }

    @FXML
    void stockTrackingCLicked(MouseEvent event) {





    }
    @FXML
    void initialize() {
        try{
            WebEngine webEngine = webView.getEngine();


            String highchartsHTML = readHtmlFile();


            webEngine.loadContent(highchartsHTML);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String readHtmlFile() {
        String filePath = "src/main/resources/com/essencehub/project/harita.html"; 
        StringBuilder content = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();
    }



}

