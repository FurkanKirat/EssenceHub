package com.essencehub.project.Finance;

import java.time.LocalDate;

public class Outcome {
    private LocalDate date;
    private String title;
    private String cost;

    public Outcome(LocalDate date, String title, String cost){
        this.date = date;
        this.title = title;
        this.cost = cost;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    // Getter and Setter for Title
    public String getTitle() {
        return title;
    }

    public void setTitle(String Title) {
        this.title = Title;
    }

    // Getter and Setter for Amount
    public String getAmount() {
        return cost;
    }

    public void setAmount(String Amount) {
        this.cost = Amount;
    }
}
