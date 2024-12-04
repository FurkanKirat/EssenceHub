package com.essencehub.project.Controllers.Message;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MessageAppController {
    @FXML
    private TextArea text;

    @FXML
    private VBox messageBox;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private void initialize() {
        text.setWrapText(true);
        messageBox = new VBox();
        messageBox.setSpacing(10);
        scrollPane.setContent(messageBox);
        scrollPane.setFitToWidth(true);

        text.setPromptText("Type a message...");
    }

    @FXML
    private void handleSendButtonClick(MouseEvent event) {
        String message = text.getText();
        if (!message.trim().isEmpty()) {
            addMessage(message, true); // Add user message
            addMessage("Hi! How are you?", false); // Add a reply message (simulated)
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
