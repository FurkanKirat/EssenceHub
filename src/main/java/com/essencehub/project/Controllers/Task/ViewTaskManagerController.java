package com.essencehub.project.Controllers.Task;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;
import java.io.IOException;

public class ViewTaskManagerController {

    @FXML
    Button deleteTaskButton;


    @FXML
    public void deleteTask(ActionEvent e)throws IOException {
        System.out.println("Task deleted");
        //sql code
    }
    @FXML
    public void openTask(ActionEvent e)throws IOException{
        // Opens the task
    }

}
