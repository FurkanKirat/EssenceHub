package com.essencehub.project.Controllers.EmployeeOperations;

import com.essencehub.project.Controllers.Menu.AdminMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class FireEmployeeController {

    @FXML
    private Button fireButton;

    @FXML
    private ComboBox<?> fireComboBox;

    @FXML
    private ImageView fireIcon;

    @FXML
    private ImageView hireIcon;

    @FXML
    private ImageView updateIcon;

    @FXML
    void fireIconClicked(MouseEvent event) {
        AdminMenuController adminMenuController = AdminMenuController.getInstance();
        adminMenuController.loadFXMLContent("/com/essencehub/project/fxml/EmployeeOption/fireEmployee.fxml");
    }

    @FXML
    void hireIconClicked(MouseEvent event) {
        AdminMenuController adminMenuController = AdminMenuController.getInstance();
        adminMenuController.loadFXMLContent("/com/essencehub/project/fxml/EmployeeOption/hireEmployee.fxml");
    }

    @FXML
    void updateIconClicked(MouseEvent event) {
        AdminMenuController adminMenuController = AdminMenuController.getInstance();
        adminMenuController.loadFXMLContent("/com/essencehub/project/fxml/EmployeeOption/updateEmployee.fxml");
    }

    @FXML
    void isFireButtonClicked(ActionEvent event) {

    }

}
