package com.essencehub.project.Stock;

public class Product {
    private String name;
    private String monthAndYear;
    private int count;

    public Product(String name, int count, String monthAndYear) {
        this.name = name;
        this.count = count;
        this.monthAndYear = monthAndYear;
    }

    public String getName(){
        return this.name;
    }
    public String getMonthAndYear(){
        return this.monthAndYear;
    }
    public int getCount(){
        return this.count;
    }
}
