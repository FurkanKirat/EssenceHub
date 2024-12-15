package com.essencehub.project.Controllers.RequestLeave;

import com.essencehub.project.Controllers.Menu.AdminMenuController;
import com.essencehub.project.User.LeaveRequest;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;

public class AdminViewRequests {

    @FXML
    private TableColumn<LeaveRequest, LocalDate> endDateColumn;

    @FXML
    private TableColumn<LeaveRequest, String> nameColumn;

    @FXML
    private TableColumn<LeaveRequest, LocalDate> startDateColumn;

    @FXML
    private TableColumn<LeaveRequest, String> statusColumn;

    @FXML
    private TableView<LeaveRequest> tableView;

    private static LeaveRequest leaveRequest;

    @FXML
    void tableViewClicked(MouseEvent event) {
        leaveRequest = tableView.getSelectionModel().getSelectedItem();
        AdminMenuController adminMenuController = AdminMenuController.getInstance();
        adminMenuController.loadFXMLContent("/com/essencehub/project/fxml/RequestLeave/AdminAcceptRequest.fxml");
    }

    public void initialize(){
        //Get requests from database
    }

    public static LeaveRequest getLeaveRequest() {
        return leaveRequest;
    }

}
