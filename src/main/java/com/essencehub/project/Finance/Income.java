package com.essencehub.project.Finance;

import java.time.LocalDate;

public class Income {
private LocalDate date;
private String title;
private String amount;

    public Income(LocalDate date, String title, String amount){
        this.date = date;
        this.title = title;
        this.amount = amount;
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
        return amount;
    }

    public void setAmount(String Amount) {
        this.amount = Amount;
    }
}
