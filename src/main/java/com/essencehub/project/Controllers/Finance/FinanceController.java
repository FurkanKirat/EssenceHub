package com.essencehub.project.Controllers.Finance;

import com.essencehub.project.Controllers.Menu.AdminMenuController;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class FinanceController {

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private StackPane incomePane;

    @FXML
    private StackPane outgoingsPane;

    @FXML
    void incomeClicked(MouseEvent event) {
        AdminMenuController adminMenuController = AdminMenuController.getInstance();
        adminMenuController.loadFXMLContent("/com/essencehub/project/fxml/Finance/IncomeX.fxml");
    }

    @FXML
    void outgoingsClicked(MouseEvent event) {
        AdminMenuController adminMenuController = AdminMenuController.getInstance();
        adminMenuController.loadFXMLContent("/com/essencehub/project/fxml/Finance/Outcome.fxml");
    }
}
