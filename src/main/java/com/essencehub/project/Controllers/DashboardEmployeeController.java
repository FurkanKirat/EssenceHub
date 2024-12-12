package com.essencehub.project.Controllers;

import com.essencehub.project.Controllers.Menu.AdminMenuController;
import com.essencehub.project.Controllers.Menu.EmployeeMenuController;
import com.essencehub.project.Controllers.Menu.LoginPageController;
import com.essencehub.project.Controllers.Task.ViewTaskEmployeeController;
import com.essencehub.project.Controllers.Task.ViewTaskManagerController;
import com.essencehub.project.User.AdminOperations;
import com.essencehub.project.User.Message;
import com.essencehub.project.User.Task;
import com.essencehub.project.User.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Collections;

public class DashboardEmployeeController {


    @FXML
    private TableColumn<Task, String > employeeNameColumn1;

    @FXML
    private VBox func;

    @FXML
    private HBox functions;

    @FXML
    private TableColumn<Task, Boolean> statusColumn;

    @FXML
    private TableColumn<Task, String > taskColumn;

    @FXML
    private TableView<Task> taskTable;


    public void initialize(){

        statusColumn.setCellValueFactory(new PropertyValueFactory<>("taskDone"));
        taskColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        employeeNameColumn1.setCellValueFactory(new PropertyValueFactory<>("sender"));

        ObservableList<Task> allTasks = FXCollections.observableArrayList(AdminOperations.getUserTasks(LoginPageController.getUser().getId()));

        ObservableList<Task> lastFiveTasks = FXCollections.observableArrayList(
                allTasks.subList(0, Math.min(allTasks.size(), 5))
        );

        taskTable.setItems(lastFiveTasks);
    }
    @FXML
    void taskClicked(MouseEvent event) {
        if(!taskTable.getSelectionModel().isEmpty()){
            ViewTaskEmployeeController.setTask(taskTable.getSelectionModel().getSelectedItem());

            EmployeeMenuController employeeMenuController = EmployeeMenuController.getInstance();
            employeeMenuController.loadFXMLContent("/com/essencehub/project/fxml/Task/OpenTaskEmployee.fxml");
        }
    }
}
