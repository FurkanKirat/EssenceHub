package com.essencehub.project.Controllers.EmployeeOperations;

import com.essencehub.project.Controllers.Menu.AdminMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class UpdateEmployeeController {

    @FXML
    private DatePicker birthPicker;

    @FXML
    private ImageView fireIcon;

    @FXML
    private ImageView hireIcon;

    @FXML
    private ComboBox<?> propertyComboBox;

    @FXML
    private ComboBox<?> selectUpdateComboBox;

    @FXML
    private TextField statusTextField;

    @FXML
    private Button updateButton;

    @FXML
    private ImageView updateIcon;

    @FXML
    private ComboBox<?> workerStatusComboBox;

    @FXML
    void isUpdateButtonClicked(ActionEvent event) {

    }

    @FXML
    void statusSelected(ActionEvent event) {

    }

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

}
