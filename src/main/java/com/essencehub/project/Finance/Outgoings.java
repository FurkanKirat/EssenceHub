package com.essencehub.project.Finance;

import java.time.LocalDate;

public class Outgoings {
    private LocalDate date;
    private String title;
    private String cost;

    public Outgoings(LocalDate date, String title, String cost){
        this.date = date;
        this.title = title;
        this.cost = cost;
    }

    // Getter and Setters
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String Title) {
        this.title = Title;
    }

    public String getAmount() {
        return cost;
    }

    public void setAmount(String Amount) {
        this.cost = Amount;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
