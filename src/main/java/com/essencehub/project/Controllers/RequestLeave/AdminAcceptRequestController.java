package com.essencehub.project.Controllers.RequestLeave;

import com.essencehub.project.Controllers.Menu.AdminMenuController;
import com.essencehub.project.User.LeaveRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class AdminAcceptRequestController {

    @FXML
    private TextField endDateTextField;

    @FXML
    private VBox goBackBox;

    @FXML
    private TextField senderTextField;

    @FXML
    private ComboBox<String> setPermissionComboBox;

    @FXML
    private TextField startDateTextField;

    private LeaveRequest leaveRequest;

    @FXML
    void goBackBoxClicked(MouseEvent event) {
        AdminMenuController adminMenuController = AdminMenuController.getInstance();
        adminMenuController.loadFXMLContent("/com/essencehub/project/fxml/RequestLeave/AdminViewRequests.fxml");
    }

    @FXML
    void setClicked(ActionEvent event) {
        leaveRequest.setStatus(setPermissionComboBox.getValue());
        //Update in database
    }

    public void initialize(){
        leaveRequest = AdminViewRequests.getLeaveRequest();
        senderTextField.setText(leaveRequest.getEmployee().getFullName());
        startDateTextField.setText(leaveRequest.getStartDate().toString());
        endDateTextField.setText(leaveRequest.getEndDate().toString());
        setPermissionComboBox.getItems().addAll("Approved","Rejected");
        setPermissionComboBox.setValue(leaveRequest.getStatus());
    }

}
