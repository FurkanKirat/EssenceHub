package com.essencehub.project.Controllers.Task;

import com.essencehub.project.Controllers.Menu.AdminMenuController;
import com.essencehub.project.User.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class AdminOpenTaskController {

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private ImageView goBackIcon;

    @FXML
    private TextField receiverTextField;

    @FXML
    private TextField senderTextField;

    @FXML
    private TextField titleTextField;

    @FXML
    private DatePicker datePicker;

    private Task task;

    @FXML
    void goBackIconClicked(MouseEvent event) {
        AdminMenuController adminMenuController = AdminMenuController.getInstance();
        adminMenuController.loadFXMLContent("/com/essencehub/project/fxml/Task/AdminViewTasks.fxml");
    }

    @FXML
    void updateTaskClicked(ActionEvent event) {
        task.setTask(descriptionTextArea.getText());
        task.setTitle(titleTextField.getText());
        task.setFinishTime(datePicker.getValue().atStartOfDay());

        Alert logOutAlert = new Alert(Alert.AlertType.INFORMATION);
        logOutAlert.setTitle("Task Updated");
        logOutAlert.setHeaderText("Task has updated successfully ");
        logOutAlert.showAndWait();

        Task.updateTask(task);

    }

    public void initialize(){
        task = AdminViewTaskController.getSelectedTask();

        receiverTextField.setText(task.getReceiver().getFullName());
        senderTextField.setText(task.getSender().getFullName());

        titleTextField.setText(task.getTitle());
        descriptionTextArea.setText(task.getTask());
        datePicker.setValue(task.getFinishTime().toLocalDate());
    }

}
