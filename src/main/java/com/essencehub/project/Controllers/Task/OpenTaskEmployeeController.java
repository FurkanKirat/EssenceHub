package com.essencehub.project.Controllers.Task;

import com.essencehub.project.User.AdminOperations;
import com.essencehub.project.User.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class OpenTaskEmployeeController {


    @FXML
    private Label descriptionLabel;

    @FXML
    private Label titleLabel;

    public void initialize(){
        Task task = ViewTaskEmployeeController.getSelectedTask();
        titleLabel.setText(task.getTitle());
        descriptionLabel.setText(task.getTask());
    }
}
