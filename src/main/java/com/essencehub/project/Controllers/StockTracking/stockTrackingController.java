package com.essencehub.project.Controllers.StockTracking;

import com.essencehub.project.Controllers.Menu.AdminMenuController;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class StockTrackingController {

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
        AdminMenuController adminMenuController = AdminMenuController.getInstance();
        adminMenuController.loadFXMLContent("/com/essencehub/project/fxml/StockTracking/pie-chart.fxml");
    }

    @FXML
    void geoChartPaneClicked(MouseEvent event) {
        AdminMenuController adminMenuController = AdminMenuController.getInstance();
        adminMenuController.loadFXMLContent("/com/essencehub/project/fxml/StockTracking/geo-chart.fxml");
    }

    @FXML
    void linechartPaneClicked(MouseEvent event) {
        AdminMenuController adminMenuController = AdminMenuController.getInstance();
        adminMenuController.loadFXMLContent("/com/essencehub/project/fxml/StockTracking/lineChartClicked.fxml");
    }

    @FXML
    void pieChartPaneClicked(MouseEvent event) {
        AdminMenuController adminMenuController = AdminMenuController.getInstance();
        adminMenuController.loadFXMLContent("/com/essencehub/project/fxml/StockTracking/pie-chart.fxml");
    }

}
