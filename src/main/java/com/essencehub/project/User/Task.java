package com.essencehub.project.User;

import java.time.LocalDateTime;

public class Task {
    private int id;
    private static int idCounter = 1;
    private User sender;
    private User receiver;
    private String task;
    private String title; // Yeni eklenen title alanı
    private LocalDateTime sendDateTime;
    private boolean isTaskDone; // Görev tamamlanma durumu

    // Constructor
    public Task() {
        this.id = idCounter++; // Otomatik ID atama
    }

    public Task(User sender, User receiver, String task, String title) {
        this(); 
        this.sender = sender;
        this.receiver = receiver;
        this.task = task;
        // Eğer title null ise veya boş verilirse task'ten türetilir
        this.title = title != null && !title.isEmpty() ? title : task.length() > 50 ? task.substring(0, 50) : task;
        this.sendDateTime = LocalDateTime.now();
        this.isTaskDone = false; // Başlangıçta görev tamamlanmamış olarak ayarlanır
    }

    // Getter ve Setter Metotları
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

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

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
        this.title = task.length() > 50 ? task.substring(0, 50) : task; // Task değişirse title da güncellenir
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

    // isTaskDone Getter ve Setter
    public boolean isTaskDone() {
        return isTaskDone;
    }

    public void setTaskDone(boolean isTaskDone) {
        this.isTaskDone = isTaskDone;
    }

    // Görev tamamlandığında durumu değiştiren metod
    public void markTaskAsDone() {
        this.isTaskDone = true;
    }

    // Görev tamamlanmamış olduğunda durumu değiştiren metod
    public void markTaskAsPending() {
        this.isTaskDone = false;
    }
}
