package com.essencehub.project.Controllers.Task;

import com.essencehub.project.Controllers.Menu.AdminMenuController;
import com.essencehub.project.Controllers.Menu.EmployeeMenuController;
import com.essencehub.project.Controllers.Menu.LoginPageController;
import com.essencehub.project.User.AdminOperations;
import com.essencehub.project.User.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;

public class ViewTaskEmployeeController {

    @FXML
    private Label headerLabel;

    @FXML
    private TableColumn<Task, String> statusColumn;

    @FXML
    private TableColumn<Task, String> taskColumn;

    @FXML
    private TableView<Task> taskTable;

    @FXML
    private Button viewTaskButton;

    @FXML
    private TableColumn<Task, String> senderColumn;

    private static Task task;

    @FXML
    void openTask(ActionEvent event) {
        if(!taskTable.getSelectionModel().isEmpty()){
            task = taskTable.getSelectionModel().getSelectedItem();

            EmployeeMenuController employeeMenuController = EmployeeMenuController.getInstance();
            employeeMenuController.loadFXMLContent("/com/essencehub/project/fxml/Task/OpenTaskEmployee.fxml");
        }

    }
    public void initialize(){

        statusColumn.setCellValueFactory(new PropertyValueFactory<>("taskDone"));
        taskColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        senderColumn.setCellValueFactory(new PropertyValueFactory<>("sender"));
        ObservableList<Task> tasks = null;
        try {
            tasks = FXCollections.observableArrayList(AdminOperations.getUserTasks(LoginPageController.getResultSet().getInt("ID")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        taskTable.setItems(tasks);
    }
    public static Task getSelectedTask(){
        return task;
    }
}
