package com.essencehub.project.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LeaveRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private User employee;
    private String status;

    public LeaveRequest(LocalDate startDate, LocalDate endDate, User employee, String status) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.employee = employee;
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public User getEmployee() {
        return employee;
    }

    public void setEmployee(User employee) {
        this.employee = employee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
