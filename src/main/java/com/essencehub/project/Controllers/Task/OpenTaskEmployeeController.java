package com.essencehub.project.Controllers.Task;

import com.essencehub.project.User.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class OpenTaskEmployeeController {


    @FXML
    private TextArea descriptionArea;

    @FXML
    private Label titleLabel;


    @FXML
    private Button markAsDoneBtn;

    @FXML
    private Button markAsUndoneBtn;

    @FXML
    private Label senderNameLabel;

    private Task task;

    public void initialize(){
        task = ViewTaskEmployeeController.getSelectedTask();
        titleLabel.setText(task.getTitle());
        descriptionArea.setText(task.getTask());
        senderNameLabel.setText(task.getSender().toString());
    }


    @FXML
    void markDoneClicked(ActionEvent event) {
        if(!task.isTaskDone()){
            task.setTaskDone(true);
            Task.updateTask(task);

            Alert logOutAlert = new Alert(Alert.AlertType.INFORMATION);
            logOutAlert.setTitle("Task Updated");
            logOutAlert.setHeaderText("Task has marked as done successfully ");
            logOutAlert.showAndWait();
        }

    }

    @FXML
    void markUndoneCLicked(ActionEvent event) {
        if(task.isTaskDone()){
            task.setTaskDone(false);
            Task.updateTask(task);
            Alert logOutAlert = new Alert(Alert.AlertType.INFORMATION);
            logOutAlert.setTitle("Task Updated");
            logOutAlert.setHeaderText("Task has marked as undone successfully ");
            logOutAlert.showAndWait();
        }

    }
}
