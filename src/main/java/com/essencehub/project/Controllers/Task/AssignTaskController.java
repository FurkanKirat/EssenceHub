package com.essencehub.project.Controllers.Task;

import com.essencehub.project.Controllers.Menu.LoginPageController;
import com.essencehub.project.User.AdminOperations;
import com.essencehub.project.User.Task;
import com.essencehub.project.User.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AssignTaskController {
    private User user;
    @FXML
    private ListView <HBox> receiverListView; // Employees names + checkbox

    private CheckBox[] checkBoxes;
    @FXML
    private TextField titleTextField;

    @FXML
    private TextArea descriptionTextArea;

    private ArrayList<User> employees;
    @FXML
    public void initialize() {

        employees = AdminOperations.getEmployees();
        checkBoxes = new CheckBox[employees.size()];
        ObservableList<HBox> list = FXCollections.observableArrayList();

        for (int i = 0; i < employees.size(); i++) {
            HBox hBox = new HBox(10);
            CheckBox checkBox = new CheckBox();
            checkBoxes[i] = checkBox;
            Label label = new Label(employees.get(i).toString());
            label.getStyleClass().add("TaskLabel");

            hBox.getChildren().addAll(checkBox, label);
            list.add(hBox);
        }

        receiverListView.setItems(list);
        user = LoginPageController.getUser();
    }

    @FXML
    public void assignTask(ActionEvent e) {

        try {

            for(int i=0;i<checkBoxes.length;i++){
                if(checkBoxes[i].isSelected()){
                    Task task = new Task(user, employees.get(i), descriptionTextArea.getText(), titleTextField.getText(), LocalDateTime.now(),false);
                    AdminOperations.sendTask(task);

                }
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
