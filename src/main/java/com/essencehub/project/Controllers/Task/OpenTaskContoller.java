package com.essencehub.project.Controllers.Task;

import com.essencehub.project.User.AdminOperations;
import com.essencehub.project.User.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class OpenTaskContoller {

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private TextField titleTextField;

    @FXML
    private Label receiverNameLabel;
    private Task task;
    @FXML
    void updateTaskClicked(ActionEvent event) {
        task.setTask(descriptionTextArea.getText());
        task.setTitle(titleTextField.getText());
        AdminOperations.updateTask(task);
    }
    public void initialize(){
        task = ViewTaskManagerController.getSelectedTask();
        receiverNameLabel.setText(task.getReceiver().toString());
        titleTextField.setText(task.getTitle());
        descriptionTextArea.setText(task.getTask());
    }

}
