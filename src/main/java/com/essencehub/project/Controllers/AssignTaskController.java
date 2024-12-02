package com.essencehub.project.Controllers;

import com.essencehub.project.User.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AssignTaskController {
    @FXML
    ComboBox <String> comboTasks; // Employees names

    @FXML
    TextField titleTextField;

    @FXML
    TextArea descriptionTextArea;
    @FXML
    public void assignTask(){
        //Task task = new Task(User sender, User receiver, descriptionTextArea.getText(), titleTextField.getText());

    }
    public void initialize(){

        try{

            ResultSet users = LoginPageController.getStatement().executeQuery("SELECT * FROM user where isActive = 1 AND isAdmin = 0");
            while(users.next()){
                comboTasks.getItems().add(users.getString("Name")+" " +users.getString("Surname"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }



}
