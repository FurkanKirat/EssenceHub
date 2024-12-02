package com.essencehub.project.Controllers.Task;


import com.essencehub.project.Controllers.Menu.LoginPageController;
import com.essencehub.project.User.AdminOperations;
import com.essencehub.project.User.Task;
import com.essencehub.project.User.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;

public class AssignTaskController {
    @FXML
    ComboBox <User> comboTasks; // Employees

    @FXML
    TextField titleTextField;

    @FXML
    TextArea descriptionTextArea;
    @FXML
    public void assignTask(){

        try {
            for(User user: AdminOperations.users){
                if(user.getId()== LoginPageController.getResultSet().getInt("ID")){
                    Task task = new Task(user, comboTasks.getValue(), descriptionTextArea.getText(), titleTextField.getText());
                    AdminOperations.sendTask(task);
                    break;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    public void initialize(){
        AdminOperations.getUsers();
        for(User user: AdminOperations.users){
            if(user.isActive()&&!user.isAdmin()){
                comboTasks.getItems().add(user);
            }
        }
    }


}
