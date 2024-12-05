package com.essencehub.project.User;


import javafx.scene.image.Image;

public class ImageManager {

    public static Image getImage(int index){
        try {
            return switch (index) {
                case 0 -> new Image("/com/essencehub/project/images/ProfilePictures/1.png");
                case 1 -> new Image("/com/essencehub/project/images/ProfilePictures/2.png");
                case 2 -> new Image("/com/essencehub/project/images/ProfilePictures/3.png");
                case 3 -> new Image("/com/essencehub/project/images/ProfilePictures/4.png");
                case 4 -> new Image("/com/essencehub/project/images/ProfilePictures/5.png");
                case 5 -> new Image("/com/essencehub/project/images/ProfilePictures/6.png");
                default -> new Image("/com/essencehub/project/images/ProfilePictures/1.png");
            };

        } catch (Exception e) {
            return new Image("/com/essencehub/project/images/ProfilePictures/1.png");
        }
    }


}

