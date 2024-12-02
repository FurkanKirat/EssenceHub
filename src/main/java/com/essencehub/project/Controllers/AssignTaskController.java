package com.essencehub.project.Controllers;


import com.essencehub.project.User.AdminOperations;
import com.essencehub.project.User.Task;
import com.essencehub.project.User.User;
import com.mysql.cj.log.Log;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AssignTaskController {
    @FXML
    ComboBox <User> comboTasks; // Employees names

    @FXML
    TextField titleTextField;

    @FXML
    TextArea descriptionTextArea;
    @FXML
    public void assignTask(){

        try {
            for(User user: AdminOperations.users){
                if(user.getId()==LoginPageController.getResultSet().getInt("ID")){
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
