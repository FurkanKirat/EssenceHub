package com.essencehub.project.Controllers.Task;

import com.essencehub.project.Controllers.Menu.AdminMenuController;
import com.essencehub.project.User.AdminOperations;
import com.essencehub.project.User.Task;
import com.essencehub.project.User.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ViewTaskManagerController {

    @FXML
    private Button deleteTaskButton;

    @FXML
    private TableColumn<Task, String> employeeNameColumn;

    @FXML
    private TableColumn<Task, Boolean> statusColumn;

    @FXML
    private TableColumn<Task, String> taskColumn;

    @FXML
    private TableView<Task> taskTable;

    @FXML
    private TableColumn<Task, String> employeeNameColumn1;

    private static Task task;

    @FXML
    void deleteTask(ActionEvent event) {
        if(!taskTable.getSelectionModel().isEmpty()){
            task = taskTable.getSelectionModel().getSelectedItem();
            Task.deleteTask(task);
            AdminMenuController adminMenuController = AdminMenuController.getInstance();
            adminMenuController.loadFXMLContent("/com/essencehub/project/fxml/Task/ViewTaskManager.fxml");
        }
    }

    @FXML
    void openTask(ActionEvent event) {

        if(!taskTable.getSelectionModel().isEmpty()){
            task = taskTable.getSelectionModel().getSelectedItem();

            AdminMenuController adminMenuController = AdminMenuController.getInstance();
            adminMenuController.loadFXMLContent("/com/essencehub/project/fxml/Task/OpenTask.fxml");
        }


    }
    public void initialize(){

        employeeNameColumn.setCellValueFactory(new PropertyValueFactory<>("receiver"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("taskDone"));
        taskColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        employeeNameColumn1.setCellValueFactory(new PropertyValueFactory<>("sender"));
        ObservableList<Task> tasks = FXCollections.observableArrayList(AdminOperations.getAllTasks());
        taskTable.setItems(tasks);
    }
    public static Task getSelectedTask(){
        return task;
    }


}
