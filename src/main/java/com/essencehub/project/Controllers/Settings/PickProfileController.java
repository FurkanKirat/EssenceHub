package com.essencehub.project.Controllers.Settings;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
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
    private Pane pane1;

    @FXML
    private Pane pane2;

    @FXML
    private Pane pane3;

    @FXML
    private Pane pane4;

    @FXML
    private Pane pane5;

    @FXML
    private Pane pane6;

    private List<ImageView> images;
    private List<Pane> panes;

    @FXML
    public void initialize() {
        // Listeleri initialize içinde doldurun
        images = Arrays.asList(img1, img2, img3, img4, img5, img6);
        panes = Arrays.asList(pane1, pane2, pane3, pane4, pane5, pane6);
    }
    @FXML
    void editProfileButtonClicked(MouseEvent event) {
    }

    @FXML
    void img1Clicked(MouseEvent event) {
        clearStyle();
        pane1.getStyleClass().add("container");
    }

    @FXML
    void img2Clicked(MouseEvent event) {
        clearStyle();
        pane2.getStyleClass().add("container");
        img2.getStyleClass().add("buttonClicked");
    }

    @FXML
    void img3Clicked(MouseEvent event) {
        clearStyle();
        pane3.getStyleClass().add("container");
        img3.getStyleClass().add("buttonClicked");
    }

    @FXML
    void img4Clicked(MouseEvent event) {
        clearStyle();
        pane4.getStyleClass().add("container");
        img4.getStyleClass().add("buttonClicked");
    }

    @FXML
    void img5Clicked(MouseEvent event) {
        clearStyle();
        pane5.getStyleClass().add("container");
        img5.getStyleClass().add("buttonClicked");
    }

    @FXML
    void img6Clicked(MouseEvent event) {
        clearStyle();
        pane6.getStyleClass().add("container");
        img6.getStyleClass().add("buttonClicked");
    }
    void clearStyle(){
        for(int i = 0;i<6;i++) {
            images.get(i).getStyleClass().remove("buttonClicked");
            panes.get(i).getStyleClass().remove("container");
        }
    }




}
