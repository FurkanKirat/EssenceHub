package com.essencehub.project.Controllers.RequestLeave;

import com.essencehub.project.Controllers.Menu.AdminMenuController;
import com.essencehub.project.User.LeaveRequest;
import com.essencehub.project.User.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.time.temporal.ChronoUnit;

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

    @FXML
    private TextField remainingLeaveDaysTextField;

    @FXML
    private TextField requestedLeaveDaysTextField;
    private LeaveRequest leaveRequest;


    @FXML
    void goBackBoxClicked(MouseEvent event) {
        AdminMenuController adminMenuController = AdminMenuController.getInstance();
        adminMenuController.loadFXMLContent("/com/essencehub/project/fxml/RequestLeave/AdminViewRequests.fxml");
    }

    @FXML
    void setClicked(ActionEvent event) {
        if(!leaveRequest.getStatus().equals("Pending")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Could not changed");
            alert.setHeaderText("This leave request is already answered!");
            alert.showAndWait();
            return;
        }
        leaveRequest.setStatus(setPermissionComboBox.getValue());

        //Update in database
        LeaveRequest.updateLeaveRequest(leaveRequest);

        if(leaveRequest.getStatus().equals("Approved")){
            User employee = leaveRequest.getEmployee();
            employee.setRemainingLeaveDays((int) (employee.getRemainingLeaveDays()-(1+ ChronoUnit.DAYS.between(leaveRequest.getStartDate(), leaveRequest.getEndDate()))));
            User.updateUser(employee);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Leave Request");
        alert.setHeaderText("You have successfully answered the leave request!");
        alert.showAndWait();

    }

    public void initialize(){
        leaveRequest = AdminViewRequests.getLeaveRequest();
        senderTextField.setText(leaveRequest.getEmployee().getFullName());
        startDateTextField.setText(leaveRequest.getStartDate().toString());
        endDateTextField.setText(leaveRequest.getEndDate().toString());
        setPermissionComboBox.getItems().addAll("Approved","Rejected");
        setPermissionComboBox.setValue(leaveRequest.getStatus());
        remainingLeaveDaysTextField.setText(leaveRequest.getEmployee().getRemainingLeaveDays()+"");
        requestedLeaveDaysTextField.setText((1+ ChronoUnit.DAYS.between(leaveRequest.getStartDate(), leaveRequest.getEndDate())+""));
    }

}
