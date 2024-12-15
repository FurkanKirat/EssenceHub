package com.essencehub.project.User;

public class Notification {
    private int receiverId;
    private String title;
    private String message;

    public Notification(int receiverId, String title, String message) {
        this.receiverId = receiverId;
        this.title = title;
        this.message = message;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
