package com.essencehub.project.User;

public class User {

    private int id;
    private String name;
    private String surname;
    private String phoneNumber;
    private double salary;
    private AuthorizedDegree authorizedDegree;
    private String birth;
    private String department;
    private String email;
    private int remainingLeaveDays;
    private Performance monthlyPerformance;
    private double bonusSalary;

    // Constructor
    public User(String name, String surname, String phoneNumber, double baseSalary, int authorizedDegree, String birth, 
                String department, String email, int remainingLeaveDays) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.salary = baseSalary;
        this.authorizedDegree = AuthorizedDegree.fromLevel(authorizedDegree);
        this.birth = birth;
        this.department = department;
        this.email = email;
        this.remainingLeaveDays = remainingLeaveDays;
    }

    // Getter ve Setter Metotları
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public AuthorizedDegree getAuthorizedDegree() {
        return authorizedDegree;
    }

    public void setAuthorizedDegree(AuthorizedDegree authorizedDegree) {
        this.authorizedDegree = authorizedDegree;
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
        this.bonusSalary = bonusSalary;
    }
}
