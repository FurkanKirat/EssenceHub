package com.essencehub.project.Controllers.Task;

import com.essencehub.project.Controllers.Menu.AdminMenuController;
import com.essencehub.project.Controllers.Menu.EmployeeMenuController;
import com.essencehub.project.User.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class EmployeeOpenTaskController {


    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private ImageView goBackIcon;

    @FXML
    private TextField receiverTextField;

    @FXML
    private TextField titleTextField;

    @FXML
    private DatePicker datePicker;

    private Task task;

    @FXML
    void goBackIconClicked(MouseEvent event) {
        EmployeeMenuController employeeMenuController = EmployeeMenuController.getInstance();
        employeeMenuController.loadFXMLContent("/com/essencehub/project/fxml/Task/EmployeeViewTask.fxml");
    }

    public void initialize(){
        task = EmployeeViewTaskController.getSelectedTask();
        titleTextField.setText(task.getTitle());
        descriptionTextArea.setText(task.getTask());
        receiverTextField.setText(task.getSender().toString());
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
    void markUndoneClicked(ActionEvent event) {
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