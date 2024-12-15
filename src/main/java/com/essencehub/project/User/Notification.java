package com.essencehub.project.User;

import java.time.LocalDateTime;

public class Notification {
    private String title;
    private String message;
    private String type;
    private LocalDateTime sentDate;
    private boolean isDeleted;

    public Notification(String title, String message, LocalDateTime sentDate, String type, boolean isDeleted) {
        this.title = title;
        this.message = message;
        this.sentDate = sentDate;
        this.type = type;
        this.isDeleted = isDeleted;
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
