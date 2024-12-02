package com.essencehub.project.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class FinanceController {

    @FXML
    private StackPane incomePane;

    @FXML
    private StackPane outgoingsPane;

    @FXML
    void incomeClicked(MouseEvent event) {
        ControllerAdminPanel controllerAdminPanel = ControllerAdminPanel.getInstance();
        controllerAdminPanel.loadFXMLContent("/com/essencehub/project/piechart.fxml");
    }

    @FXML
    void outgoingsClicked(MouseEvent event) {
        
    }



}
