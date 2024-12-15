package com.essencehub.project.Controllers.Message;

import com.essencehub.project.Controllers.Menu.AdminMenuController;
import com.essencehub.project.Controllers.Menu.EmployeeMenuController;
import com.essencehub.project.Controllers.Menu.LoginPageController;
import com.essencehub.project.User.Message;
import com.essencehub.project.User.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    private ListView<UserBox> usersListView;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private ImageView profilePicture;

    @FXML
    private Label name;

    private User user;
    private Timer timer;

    @FXML
    private void initialize() {
        text.setWrapText(true);
        name.setVisible(false);
        profilePicture.setVisible(false);
        scrollPane.setVisible(false);
        messageBox.setVisible(false);
        text.setVisible(false);

        messageBox.setSpacing(10);
        scrollPane.setContent(messageBox);
        scrollPane.setFitToWidth(true);

        for(User otheruser : User.getUsers()){
            usersListView.getItems().add(new UserBox(otheruser));
        }
        usersListView.getSelectionModel().select(0);
        text.setPromptText("Type a message...");
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    refresh();
                });
            }
        }, 0, 2500);
    }

    public void refresh(){

        user = LoginPageController.getUser();

        ArrayList<Message> messages = Message.getMessagesBetweenUsers(user, usersListView.getSelectionModel().getSelectedItem().getUser());
        messageBox.getChildren().clear();
        for (Message message : messages) {
            addMessage(message.getMessage(), message.getSender().getId() == user.getId());

        }
        scrollPane.vvalueProperty().bind(messageBox.heightProperty());

    }

        private void addMessage(String message, boolean isUserMessage) {
        HBox messageBalloon = new HBox();
        Label label = new Label();
        label.setText(message);
        label.setWrapText(true);
        label.setMaxWidth(250); // Set max width for wrapping

        if (isUserMessage) {
            label.getStyleClass().add("messageSenderLabel");
            messageBalloon.setAlignment(Pos.CENTER_RIGHT); // Align user message to the right
        } else {
            label.getStyleClass().add("messageReceiverLabel");
            messageBalloon.setAlignment(Pos.CENTER_LEFT); // Align other messages to the left
        }

        messageBalloon.getChildren().add(label);
        messageBox.getChildren().add(messageBalloon);
    }

    @FXML
    void homepageClicked(MouseEvent event) {
        timer.cancel();
        try {
            if(LoginPageController.getUser().isAdmin()){
                AdminMenuController adminMenuController = AdminMenuController.getInstance();
                adminMenuController.settings("/com/essencehub/project/fxml/Menu/AdminMenu.fxml",event);
            }
            else{
                EmployeeMenuController employeeMenuController = EmployeeMenuController.getInstance();
                employeeMenuController.settings("/com/essencehub/project/fxml/Menu/EmployeeMenu.fxml",event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void userSelected(Event event) {

        name.setText(usersListView.getSelectionModel().getSelectedItem().getUser().getFullName());
        profilePicture.setImage(usersListView.getSelectionModel().getSelectedItem().getImage().getImage());
        refresh();

        name.setVisible(true);
        profilePicture.setVisible(true);
        scrollPane.setVisible(true);
        messageBox.setVisible(true);
        text.setVisible(true);
    }


    @FXML
    void enterPressed(KeyEvent event) {
        if(event.getCode() != KeyCode.ENTER){
           return;
        }
        String message = text.getText();
        if (!message.trim().isEmpty()) {
            Message.sendMessageMain(user,usersListView.getSelectionModel().getSelectedItem().getUser(),message, LocalDateTime.now());
            addMessage(message, true); // Add user message
            text.clear();
        }
    }

}
