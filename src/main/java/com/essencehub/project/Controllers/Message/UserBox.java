package com.essencehub.project.Controllers.Message;

import com.essencehub.project.User.User;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class UserBox extends VBox {
    User user;
    Label nameLabel;
    ImageView image;

    public UserBox(User user) {
        this.user = user;
        nameLabel = new Label(user.getFullName());
        nameLabel.getStyleClass().add("messageLabel");
        image = new ImageView();
        image.setFitHeight(50);
        image.setFitWidth(50);
        image.setImage(new Image(user.getImageLocation()));
        this.getChildren().addAll(image,nameLabel);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Label getNameLabel() {
        return nameLabel;
    }

    public void setNameLabel(Label nameLabel) {
        this.nameLabel = nameLabel;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }
}
