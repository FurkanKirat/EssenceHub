package com.essencehub.project;

public class Product {
    private String name;
    private int region;

    public Product(String name, int region) {
        this.name = name;
        this.region = region;
    }

    // Getter Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }
}
