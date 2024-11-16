package com.essencehub.project.Employees;

public class Admin extends Staff{
    public Admin(String ID, String password) {
        super(ID, password);
        this.setAdmin(true);
    }
}
