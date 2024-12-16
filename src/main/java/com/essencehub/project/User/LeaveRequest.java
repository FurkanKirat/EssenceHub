package com.essencehub.project.User;

import com.essencehub.project.DatabaseOperations.DatabaseConnection;

import java.sql.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LeaveRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private User employee;
    private String status;
    private int id;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
//Database operations

    public static void addLeaveRequest(LocalDate startDate, LocalDate endDate, int employeeId, String status) {
        String insertSQL = "INSERT INTO LeaveRequest (startDate, endDate, employeeId, status) VALUES (?, ?, ?, ?);";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setDate(1, Date.valueOf(startDate));
            preparedStatement.setDate(2, Date.valueOf(endDate));
            preparedStatement.setInt(3, employeeId);
            preparedStatement.setString(4, status);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("LeaveRequest başarıyla eklendi.");
            } else {
                System.out.println("LeaveRequest eklenemedi.");
            }
        } catch (SQLException e) {
            System.out.println("LeaveRequest ekleme sırasında bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void addLeaveRequest(LeaveRequest leaveRequest) {
        addLeaveRequest(leaveRequest.getStartDate(),leaveRequest.getEndDate(),leaveRequest.getEmployee().getId(),leaveRequest.getStatus());
    }

    public static List<LeaveRequest> getLeaveRequests() {
        String selectSQL = """
        SELECT lr.id AS leaveRequestId, lr.startDate, lr.endDate, lr.status, lr.employeeId,
               u.id, u.name, u.surname, u.phoneNumber, u.salary, u.isAdmin, u.birth,
               u.department, u.email, u.remainingLeaveDays, u.isActive, u.password,
               u.monthlyPerformance, u.bonusSalary, u.imageLocation, u.workingHour
        FROM LeaveRequest lr
        JOIN User u ON lr.employeeId = u.id;
    """;

        List<LeaveRequest> leaveRequests = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL)) {

            while (resultSet.next()) {

                LocalDate startDate = resultSet.getDate("startDate").toLocalDate();
                LocalDate endDate = resultSet.getDate("endDate").toLocalDate();
                String status = resultSet.getString("status");
                int requestID = resultSet.getInt(1);

                int employeeID = resultSet.getInt(6);
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String phoneNumber = resultSet.getString("phoneNumber");
                double baseSalary = resultSet.getDouble("salary");
                boolean isAdmin = resultSet.getBoolean("isAdmin");
                String birth = resultSet.getString("birth");
                String department = resultSet.getString("department");
                String email = resultSet.getString("email");
                int remainingLeaveDays = resultSet.getInt("remainingLeaveDays");
                boolean isActive = resultSet.getBoolean("isActive");
                String password = resultSet.getString("password");
                Performance performance = Performance.valueOf(resultSet.getString("monthlyPerformance").toUpperCase());
                double bonusSalary = resultSet.getDouble("bonusSalary");
                String imageLocation = resultSet.getString("imageLocation");
                String workingHour = resultSet.getString("workingHour");

                User employee = new User(
                        name, surname, phoneNumber, baseSalary, isAdmin, birth, department, email,
                        remainingLeaveDays, isActive, password, performance, bonusSalary, imageLocation,workingHour
                );
                employee.setId(employeeID);

                LeaveRequest leaveRequest = new LeaveRequest(startDate, endDate, employee, status);
                leaveRequest.setId(requestID);

                leaveRequests.add(leaveRequest);
            }
        } catch (SQLException e) {
            System.out.println("LeaveRequest ve User detayları alınırken bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }

        return leaveRequests;
    }




    public static List<LeaveRequest> getLeaveRequestByEmployeeId(int employeeId) {
        String selectSQL = "SELECT * FROM LeaveRequest WHERE employeeId = ?;";
        List<LeaveRequest> leaveRequests = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

            preparedStatement.setInt(1, employeeId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    LocalDate startDate = resultSet.getDate("startDate").toLocalDate();
                    LocalDate endDate = resultSet.getDate("endDate").toLocalDate();
                    String status = resultSet.getString("status");

                    User employee = new User();
                    employee.setId(employeeId);

                    LeaveRequest leaveRequest = new LeaveRequest(startDate, endDate, employee, status);
                    leaveRequests.add(leaveRequest);
                }
            }
        } catch (SQLException e) {
            System.out.println("employeeId'ye göre LeaveRequest alınırken bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }

        return leaveRequests;
    }

    public static void updateLeaveRequest(LeaveRequest leaveRequest) {
        String updateSQL = """
        UPDATE LeaveRequest
        SET status = ?
        WHERE id = ?;
    """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, leaveRequest.getStatus());
            preparedStatement.setInt(2, leaveRequest.getId());

            // Güncelleme işlemi
            int rowsUpdated = preparedStatement.executeUpdate();

            if(rowsUpdated>0){
                System.out.println("LeaveRequest has ben update successfully");
            }
            else{
                System.out.println("LeaveRequest güncellenemedi ");
            }

            // En az bir satır güncellenmişse başarılı

        } catch (SQLException e) {
            System.out.println("LeaveRequest güncellenirken bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
