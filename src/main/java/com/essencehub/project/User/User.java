package com.essencehub.project.User;

import com.essencehub.project.DatabaseOperations.DatabaseConnection;
import javafx.scene.image.Image;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class User {

    private int id;
    private static int idCounter = 2000; // static değişken idCounter
    private String name;
    private String surname;
    private String password;  // Password özelliği eklendi
    private String phoneNumber;
    private double salary;
    private boolean isAdmin;  
    private String birth;
    private String department;
    private String email;
    private int remainingLeaveDays;
    private Performance monthlyPerformance;
    private double bonusSalary;
    private boolean isActive;
    private Image image;

    private ArrayList<Task> taskE;
    private static ArrayList<Task> taskA;

    public User() {
        this.id = idCounter++;
    }

    // Constructor, password parametresi eklendi
    public User(String name, String surname, String phoneNumber, double baseSalary, boolean isAdmin, String birth,
                String department, String email, int remainingLeaveDays, boolean isActive, String password, Performance monthlyPerformance, int bonusSalary) {
        this();
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.salary = baseSalary;
        this.isAdmin = isAdmin;
        this.birth = birth;
        this.department = department;
        this.email = email;
        this.remainingLeaveDays = remainingLeaveDays;
        this.isActive = isActive;
        this.password = password;

        this.monthlyPerformance = monthlyPerformance;
        this.bonusSalary = bonusSalary;
    }

    // Getter ve Setter Metotları
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Diğer getter ve setter metotları
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRemainingLeaveDays() {
        return remainingLeaveDays;
    }

    public void setRemainingLeaveDays(int remainingLeaveDays) {
        this.remainingLeaveDays = remainingLeaveDays;
    }

    public Performance getMonthlyPerformance() {
        return monthlyPerformance;
    }

    public void setMonthlyPerformance(Performance monthlyPerformance) {
        this.monthlyPerformance = monthlyPerformance;
    }

    public double getBonusSalary() {
        return bonusSalary;
    }

    public void setBonusSalary(double bonusSalary) {
        this.bonusSalary = this.monthlyPerformance.getBonus();
        this.salary = salary + bonusSalary;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    // Getter ve Setter metotları password için
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return  "ID: " +  id+ " " +name +" " + surname ;

    }

}
