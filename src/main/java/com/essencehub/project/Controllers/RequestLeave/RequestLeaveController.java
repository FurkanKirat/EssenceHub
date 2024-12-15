package com.essencehub.project.Controllers.RequestLeave;

import com.essencehub.project.Controllers.Menu.EmployeeMenuController;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class RequestLeaveController {

    @FXML
    private DatePicker endPicker;

    @FXML
    private TextField remainingLeavesTextField;

    @FXML
    private DatePicker startPicker;

    @FXML
    void sendRequestClicked(MouseEvent event) {
        // Send new request to the database
    }

    @FXML
    void sendRequestIcon(MouseEvent event) {
        EmployeeMenuController employeeMenuController = EmployeeMenuController.getInstance();
        employeeMenuController.loadFXMLContent("/com/essencehub/project/fxml/RequestLeave/EmployeeRequest.fxml");
    }

    @FXML
    void viewRequestsIcon(MouseEvent event) {
        EmployeeMenuController employeeMenuController = EmployeeMenuController.getInstance();
        employeeMenuController.loadFXMLContent("/com/essencehub/project/fxml/RequestLeave/EmployeeViewRequests.fxml");
    }

}
