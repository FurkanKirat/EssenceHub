package com.essencehub.project.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LeaveRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private int employeeID;
    private String status;

    public LeaveRequest(LocalDate startDate, LocalDate endDate, int employeeID, String status) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.employeeID = employeeID;
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

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
