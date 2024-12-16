package com.essencehub.project.Controllers.RequestLeave;

import com.essencehub.project.Controllers.Menu.EmployeeMenuController;
import com.essencehub.project.Controllers.Menu.LoginPageController;
import com.essencehub.project.User.LeaveRequest;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static java.time.temporal.ChronoUnit.DAYS;

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
        if(startPicker.getValue().isAfter(endPicker.getValue())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Leave Request Failed");
            alert.setHeaderText("End date cannot be before the start date!");
            alert.showAndWait();
            return;
        }

        if(LoginPageController.getUser().getRemainingLeaveDays() < 1+ChronoUnit.DAYS.between(startPicker.getValue(), endPicker.getValue())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Leave Request Failed");
            alert.setHeaderText("You do not have enough leave days to request!");
            alert.showAndWait();
            return;
        }

        LeaveRequest leaveRequest = new LeaveRequest(startPicker.getValue(),endPicker.getValue(), LoginPageController.getUser(),"Pending");
        LeaveRequest.addLeaveRequest(leaveRequest);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Leave Request");
        alert.setHeaderText("Leave Request has sent successfully");
        alert.showAndWait();

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
    public void initialize(){
        remainingLeavesTextField.setText(LoginPageController.getUser().getRemainingLeaveDays()+"");
        startPicker.setValue(LocalDate.now());
        endPicker.setValue(LocalDate.now().plusDays(3));
    }

}
