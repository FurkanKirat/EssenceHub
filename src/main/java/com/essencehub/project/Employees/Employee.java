package com.essencehub.project.Employees;

public class Employee extends Staff{
    public Employee(String ID, String password) {
        super(ID,password);
        this.setAdmin(false);
    }
}
