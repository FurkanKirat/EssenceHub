package com.essencehub.project.Controllers.RequestLeave;

import com.essencehub.project.Controllers.Menu.EmployeeMenuController;
import com.essencehub.project.Controllers.Menu.LoginPageController;
import com.essencehub.project.User.LeaveRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;

public class EmployeeViewRequestController {

    @FXML
    private TableColumn<LeaveRequest, LocalDate> endDateColumn;

    @FXML
    private TableColumn<LeaveRequest, LocalDate> startDateColumn;

    @FXML
    private TableColumn<LeaveRequest, String> statusColumn;

    @FXML
    private TableView<LeaveRequest> tableView;

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

        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        ObservableList<LeaveRequest> leaveRequests = FXCollections.observableArrayList(LeaveRequest.getLeaveRequestByEmployeeId(LoginPageController.getUser().getId()));
        tableView.setItems(leaveRequests);
    }

}
