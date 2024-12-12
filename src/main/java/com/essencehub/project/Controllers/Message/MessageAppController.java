package com.essencehub.project.Controllers.Message;

import com.essencehub.project.Controllers.Menu.LoginPageController;
import com.essencehub.project.User.AdminOperations;
import com.essencehub.project.User.Message;
import com.essencehub.project.User.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MessageAppController {
    @FXML
    private TextArea text;

    @FXML
    private VBox messageBox;

    @FXML
    private ScrollPane scrollPane;
    private ArrayList<Message> messages;
    private User user;


    @FXML
    private void initialize() {
        text.setWrapText(true);
        
        messageBox.setSpacing(10);
        scrollPane.setContent(messageBox);
        scrollPane.setFitToWidth(true);

        text.setPromptText("Type a message...");
        refresh();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    refresh();
                });
            }
        }, 0, 1);
    }
    public void refresh(){

        user = LoginPageController.getUser();
        messages = Message.getMessagesBetweenUsers(user,new User(2));
        messageBox.getChildren().clear();
        for (Message message : messages) {
            addMessage(message.getMessage(), message.getSender().getId() == user.getId());

        }
    }

    @FXML
    private void handleSendButtonClick(MouseEvent event) {
        String message = text.getText();
        if (!message.trim().isEmpty()) {
            AdminOperations.sendMessageMain(user,new User(2),message, LocalDateTime.now());
            addMessage(message, true); // Add user message
            //addMessage("Hi! How are you?", false); // Add a reply message (simulated)
            text.clear();
            scrollPane.setVvalue(1.0); // Scroll to the bottom
        }
    }

    private void addMessage(String message, boolean isUserMessage) {
        HBox messageBalloon = new HBox();
        Label label = new Label();
        label.setText(message);
        label.setWrapText(true);
        label.setMaxWidth(250); // Set max width for wrapping

        if (isUserMessage) {
            label.setStyle("-fx-background-color: white; -fx-padding: 10px; -fx-border-radius: 10px; -fx-background-radius: 10px;");
            messageBalloon.setAlignment(Pos.CENTER_RIGHT); // Align user message to the right
        } else {
            label.setStyle("-fx-background-color: lightgray; -fx-padding: 10px; -fx-border-radius: 10px; -fx-background-radius: 10px;");
            messageBalloon.setAlignment(Pos.CENTER_LEFT); // Align other messages to the left
        }

        messageBalloon.getChildren().add(label);
        messageBox.getChildren().add(messageBalloon);
    }

}
