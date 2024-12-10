package com.essencehub.project.Controllers.Message;

import com.essencehub.project.Controllers.Menu.LoginPageController;
import com.essencehub.project.User.AdminOperations;
import com.essencehub.project.User.Message;
import com.essencehub.project.User.Task;
import com.essencehub.project.User.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MessageController {
    ObservableList<Message> messages;
    @FXML
    private TableColumn<Message, String> fromColumn;

    @FXML
    private TableColumn<Message, String> messageColumn;

    @FXML
    private TableView<Message> tableView;

    @FXML
    private TableColumn<Message, String> timeColumn;

    @FXML
    private Button viewButton;
    public void initialize(){
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("receiver"));
        messageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        for(User user: AdminOperations.getUsers()){
            messages = FXCollections.observableArrayList(Message.getMessagesBetweenUsers(LoginPageController.getUser(), user));
            for(Message message: messages){
                tableView.getItems().add(message);
            }

        }

    }

    @FXML
    void viewClicked(ActionEvent event) {

    }
}
