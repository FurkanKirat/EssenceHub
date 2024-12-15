package com.essencehub.project.User;

import java.time.LocalDateTime;

public class Notification {
    private String title;
    private String message;
    private String type;
    private LocalDateTime sentDate;
    private boolean isDeleted;
    private User receiver;

    public Notification(User receiver, String title, String message, LocalDateTime sentDate, String type, boolean isDeleted) {
        this.receiver = receiver;
        this.title = title;
        this.message = message;
        this.sentDate = sentDate;
        this.type = type;
        this.isDeleted = isDeleted;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDateTime sentDate) {
        this.sentDate = sentDate;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
