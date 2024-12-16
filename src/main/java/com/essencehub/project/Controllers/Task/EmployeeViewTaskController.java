package com.essencehub.project.Controllers.Task;

import com.essencehub.project.Controllers.Menu.EmployeeMenuController;
import com.essencehub.project.Controllers.Menu.LoginPageController;
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

import java.time.LocalDateTime;

public class EmployeeViewTaskController {

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

    @FXML
    private TableColumn<Task, LocalDateTime> taskDueColumn;

    @FXML
    private TableColumn<Task, LocalDateTime> sentDateColumn;

    @FXML
    private TableColumn<Task, Integer> progressColumn;

    private static Task task;

    public static void setTask(Task task) {
        EmployeeViewTaskController.task = task;
    }

    @FXML
    void openTask(ActionEvent event) {
        if(!taskTable.getSelectionModel().isEmpty()){
            task = taskTable.getSelectionModel().getSelectedItem();

            EmployeeMenuController employeeMenuController = EmployeeMenuController.getInstance();
            employeeMenuController.loadFXMLContent("/com/essencehub/project/fxml/Task/EmployeeOpenTask.fxml");
        }

    }
    public void initialize(){

        statusColumn.setCellValueFactory(new PropertyValueFactory<>("taskDone"));
        taskColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        senderColumn.setCellValueFactory(new PropertyValueFactory<>("sender"));
        sentDateColumn.setCellValueFactory(new PropertyValueFactory<>("sendDateTime"));
        taskDueColumn.setCellValueFactory(new PropertyValueFactory<>("finishTime"));
        progressColumn.setCellValueFactory(new PropertyValueFactory<>("progress"));
        ObservableList<Task> tasks = null;
        try {
            tasks = FXCollections.observableArrayList(Task.getUserTasks(LoginPageController.getUser().getId()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        taskTable.setItems(tasks);
        taskTable.getSelectionModel().select(0);
    }
    public static Task getSelectedTask(){
        return task;
    }
}
