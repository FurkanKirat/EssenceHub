package com.essencehub.project.Controllers.Task;

import com.essencehub.project.Controllers.Menu.AdminMenuController;
import com.essencehub.project.User.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AdminViewTaskController {

    @FXML
    private ImageView assignTaskIcon;

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

    @FXML
    private TableColumn<Task, LocalDateTime> taskDueColumn;

    @FXML
    private TableColumn<Task, LocalDateTime> sentDateColumn;

    @FXML
    private TableColumn<Task, Integer> progressColumn;

    @FXML
    void assignTaskIconClicked(MouseEvent event) {
        AdminMenuController adminMenuController = AdminMenuController.getInstance();
        adminMenuController.loadFXMLContent("/com/essencehub/project/fxml/Task/AssignTask.fxml");
    }

    private static Task task;

    @FXML
    void deleteTask(ActionEvent event) {
        if(taskTable.getSelectionModel().getSelectedItem()!=null){
            task = taskTable.getSelectionModel().getSelectedItem();
            Task.deleteTask(task);
            AdminMenuController adminMenuController = AdminMenuController.getInstance();
            adminMenuController.loadFXMLContent("/com/essencehub/project/fxml/Task/AdminViewTasks.fxml");
        }
    }
    @FXML
    void openTask(ActionEvent event) {

        if(!taskTable.getSelectionModel().isEmpty()){
            task = taskTable.getSelectionModel().getSelectedItem();

            AdminMenuController adminMenuController = AdminMenuController.getInstance();
            adminMenuController.loadFXMLContent("/com/essencehub/project/fxml/Task/AdminOpenTask.fxml");
        }
    }
    public void initialize(){

        employeeNameColumn.setCellValueFactory(new PropertyValueFactory<>("receiver"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("taskDone"));
        taskColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        employeeNameColumn1.setCellValueFactory(new PropertyValueFactory<>("sender"));
        sentDateColumn.setCellValueFactory(new PropertyValueFactory<>("sendDateTime"));
        taskDueColumn.setCellValueFactory(new PropertyValueFactory<>("finishTime"));
        progressColumn.setCellValueFactory(new PropertyValueFactory<>("progress"));
        ObservableList<Task> tasks = FXCollections.observableArrayList(Task.getAllTasks());
        taskTable.setItems(tasks);
        taskTable.getSelectionModel().select(0);
    }


    public static Task getSelectedTask(){
        return task;
    }

    public static void setTask(Task task) {
        AdminViewTaskController.task = task;
    }
}
