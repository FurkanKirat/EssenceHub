package com.essencehub.project.Controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class StockTrialController {

    @FXML
    private StackPane barChartPane;

    @FXML
    private StackPane geoChartPane;

    @FXML
    private StackPane linechartPane;

    @FXML
    private StackPane pieChartPane;

    @FXML
    void barChartPaneClicked(MouseEvent event) {
        ControllerAdminPanel controllerAdminPanel = ControllerAdminPanel.getInstance();
        controllerAdminPanel.loadFXMLContent("/com/essencehub/project/piechart.fxml");
    }

    @FXML
    void geoChartPaneClicked(MouseEvent event) {
        ControllerAdminPanel controllerAdminPanel = ControllerAdminPanel.getInstance();
        controllerAdminPanel.loadFXMLContent("/com/essencehub/project/geoChartClicked.fxml");
    }

    @FXML
    void linechartPaneClicked(MouseEvent event) {
        ControllerAdminPanel controllerAdminPanel = ControllerAdminPanel.getInstance();
        controllerAdminPanel.loadFXMLContent("/com/essencehub/project/lineChartClicked.fxml");
    }

    @FXML
    void pieChartPaneClicked(MouseEvent event) {
        ControllerAdminPanel controllerAdminPanel = ControllerAdminPanel.getInstance();
        controllerAdminPanel.loadFXMLContent("/com/essencehub/project/piechart.fxml");
    }

}
