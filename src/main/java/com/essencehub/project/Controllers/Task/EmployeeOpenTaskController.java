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
    private TextField dueDateField;

    @FXML
    private ComboBox<Integer> taskStatusCombobox;


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
        dueDateField.setText(task.getFinishTime().toString());
        initializeCombobox();
    }

    @FXML
    void changeProgressClicked(ActionEvent event) {
        if(task.getProgress()!=100){
            task.setProgress(taskStatusCombobox.getValue());
            if(task.getProgress()==100){
                task.setTaskDone(true);
            }
            Task.updateTask(task);
            Alert logOutAlert = new Alert(Alert.AlertType.INFORMATION);
            logOutAlert.setTitle("Task Updated");
            logOutAlert.setHeaderText("Task progress has been changed as " + taskStatusCombobox.getValue() + " successfully ");
            logOutAlert.showAndWait();
        }
        else{
            Alert logOutAlert = new Alert(Alert.AlertType.WARNING);
            logOutAlert.setTitle("Task Could not Update");
            logOutAlert.setHeaderText("Task progress could not be changed due to it has already finished!");
            logOutAlert.showAndWait();
        }
    }

    private void initializeCombobox(){
        taskStatusCombobox.setValue(task.getProgress());
        taskStatusCombobox.getItems().addAll(0,10,20,30,40,50,60,70,80,90,100);
    }


}