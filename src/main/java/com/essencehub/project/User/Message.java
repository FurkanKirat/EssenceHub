package com.essencehub.project.User;

import java.time.LocalDateTime;

public class Message {

    private User sender;
    private User receiver;
    private String message;
    private String title; 
    private LocalDateTime sendDateTime;

    public Message() {
    }

    public Message(User sender, User receiver, String message, String title) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.title = title;
        this.sendDateTime = LocalDateTime.now();
    }

    // Getter ve Setter metotları

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        this.title = message.length() > 50 ? message.substring(0, 50) : message; // Mesaj değiştiğinde title güncellenir
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getSendDateTime() {
        return sendDateTime;
    }

    public void setSendDateTime(LocalDateTime sendDateTime) {
        this.sendDateTime = sendDateTime;
    }
}
