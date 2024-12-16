package com.essencehub.project.Controllers.RequestLeave;

import com.essencehub.project.Controllers.Menu.AdminMenuController;
import com.essencehub.project.User.LeaveRequest;
import com.essencehub.project.User.Task;
import com.essencehub.project.User.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;

public class AdminViewRequests {

    @FXML
    private TableColumn<LeaveRequest, LocalDate> endDateColumn;

    @FXML
    private TableColumn<LeaveRequest, User> nameColumn;

    @FXML
    private TableColumn<LeaveRequest, LocalDate> startDateColumn;

    @FXML
    private TableColumn<LeaveRequest, String> statusColumn;

    @FXML
    private TableView<LeaveRequest> tableView;

    private static LeaveRequest leaveRequest;

    @FXML
    void tableViewClicked(MouseEvent event) {
        if(!tableView.getSelectionModel().isEmpty()){
            leaveRequest = tableView.getSelectionModel().getSelectedItem();
            AdminMenuController adminMenuController = AdminMenuController.getInstance();
            adminMenuController.loadFXMLContent("/com/essencehub/project/fxml/RequestLeave/AdminAcceptRequest.fxml");
        }

    }

    public void initialize(){
        //Get requests from database

        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("employee"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        ObservableList<LeaveRequest> leaveRequests = FXCollections.observableArrayList(LeaveRequest.getLeaveRequests());
        tableView.setItems(leaveRequests);
    }

    public static LeaveRequest getLeaveRequest() {
        return leaveRequest;
    }

}
