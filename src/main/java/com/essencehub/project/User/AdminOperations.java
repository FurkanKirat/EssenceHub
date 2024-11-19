package com.essencehub.project.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminOperations {

    // **************************** HIRE, FIRE, UPDATE EMPLOYEE METHODS ****************************

    public void hireEmployee(User user) {
        insertUser(user);
    }

    public void hireEmployee(String name, String surname, String phoneNumber, double baseSalary, int authorizedDegree, 
                             String birth, String department, String email, int remainingLeaveDays) {
        User user = new User(name, surname, phoneNumber, baseSalary, authorizedDegree, birth, department, email, remainingLeaveDays);
        insertUser(user);
    }

    public void fireEmployee(User user) {
        deleteUser(user.getId());
    }

    public void fireEmployee(int id) {
        deleteUser(id);
    }

    public void updateEmployee(User user) {
        updateUser(user);
    }

    public void updateEmployee(String name, String surname, String phoneNumber, double baseSalary, int authorizedDegree, 
                               String birth, String department, String email, int remainingLeaveDays) {
        User user = new User(name, surname, phoneNumber, baseSalary, authorizedDegree, birth, department, email, remainingLeaveDays);
        updateUser(user);
    }

    // **************************** SQL METHODS ****************************

    private void insertUser(User user) {
        String sql = "INSERT INTO users (id, name, surname, phone_number, salary, authorized_degree, birth, department, email, remaining_leave_days, monthly_performance, bonus_salary, start_date) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            pstmt.setInt(1, user.getId());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getSurname());
            pstmt.setString(4, user.getPhoneNumber());
            pstmt.setDouble(5, user.getSalary());
            pstmt.setInt(6, user.getAuthorizedDegree().getLevel());
            pstmt.setString(7, user.getBirth());
            pstmt.setString(8, user.getDepartment());
            pstmt.setString(9, user.getEmail());
            pstmt.setInt(10, user.getRemainingLeaveDays());
            pstmt.setString(11, user.getMonthlyPerformance() != null ? user.getMonthlyPerformance().name() : null);
            pstmt.setDouble(12, user.getBonusSalary());
            pstmt.setTimestamp(13, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now())); // İşe başlama tarihi
    
            pstmt.executeUpdate();
            System.out.println("User added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    private void deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
            System.out.println("User deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateUser(User user) {
        String sql = "UPDATE users SET name = ?, surname = ?, phone_number = ?, salary = ?, authorized_degree = ?, birth = ?, department = ?, email = ?, remaining_leave_days = ?, monthly_performance = ?, bonus_salary = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getSurname());
            pstmt.setString(3, user.getPhoneNumber());
            pstmt.setDouble(4, user.getSalary());
            pstmt.setInt(5, user.getAuthorizedDegree().getLevel());
            pstmt.setString(6, user.getBirth());
            pstmt.setString(7, user.getDepartment());
            pstmt.setString(8, user.getEmail());
            pstmt.setInt(9, user.getRemainingLeaveDays());
            pstmt.setString(10, user.getMonthlyPerformance() != null ? user.getMonthlyPerformance().name() : null);
            pstmt.setDouble(11, user.getBonusSalary());
            pstmt.setInt(12, user.getId());

            pstmt.executeUpdate();
            System.out.println("User updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // **************************** MAIN METHOD FOR TESTING ****************************

    public static void main(String[] args) {
        AdminOperations adminOps = new AdminOperations();

        // Test: Create and hire a new user
        User user = new User("Jane", "Smith", "987654321", 4500, 2, "1985-05-15", "IT", "jane.smith@example.com", 20);
        adminOps.hireEmployee(user);

        // Update performance and bonus salary
        user.setMonthlyPerformance(Performance.A);
        user.setBonusSalary(user.getMonthlyPerformance().getBonus());
        adminOps.updateEmployee(user);
    }
}
