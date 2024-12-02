package com.essencehub.project.Controllers;

import com.essencehub.project.Controllers.Menu.AdminMenuController;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class FinanceController {

    @FXML
    private StackPane incomePane;

    @FXML
    private StackPane outgoingsPane;

    @FXML
    void incomeClicked(MouseEvent event) {
        AdminMenuController adminMenuController = AdminMenuController.getInstance();
        adminMenuController.loadFXMLContent("/com/essencehub/project/fxml/StockTracking/pie-chart.fxml");
    }

    @FXML
    void outgoingsClicked(MouseEvent event) {
        
    }



}
