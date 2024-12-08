package com.essencehub.project.Controllers.Task;

import com.essencehub.project.User.AdminOperations;
import com.essencehub.project.User.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    private Task task;

    public void initialize(){
        task = ViewTaskEmployeeController.getSelectedTask();
        titleLabel.setText(task.getTitle());
        descriptionArea.setText(task.getTask());
    }


    @FXML
    void markDoneClicked(ActionEvent event) {
        if(!task.isTaskDone()){
            task.setTaskDone(true);
            AdminOperations.updateTask(task);
        }

    }

    @FXML
    void markUndoneCLicked(ActionEvent event) {
        if(task.isTaskDone()){
            task.setTaskDone(false);
            AdminOperations.updateTask(task);
        }

    }
}
