package com.essencehub.project.DatabaseOperations.CreateTables;

import com.essencehub.project.DatabaseOperations.DatabaseConnection;
import com.essencehub.project.User.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Test {
    public static User getUserById(int userId) {
        User user = null;
        String query = "SELECT id, name, surname, phoneNumber, salary, isAdmin, birth, department, email, "
                + "remainingLeaveDays, isActive, password, imageLocation FROM User WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                user = new User();
                user.setId(resultSet.getInt("id"));

                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setPhoneNumber(resultSet.getString("phoneNumber"));
                user.setSalary(resultSet.getDouble("salary"));
                user.setAdmin(resultSet.getBoolean("isAdmin"));
                user.setBirth(resultSet.getString("birth"));
                user.setDepartment(resultSet.getString("department"));
                user.setEmail(resultSet.getString("email"));
                user.setRemainingLeaveDays(resultSet.getInt("remainingLeaveDays"));
                user.setActive(resultSet.getBoolean("isActive"));
                user.setPassword(resultSet.getString("password"));
                user.setImageLocation(resultSet.getString("imageLocation"));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }
    public static void getUsers(){
        String query = "SELECT id, password, workingHour FROM User";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String password = resultSet.getString("password");
                String workingHour = resultSet.getString("workingHour");
                System.out.println("id: " + id + " password: " + password+ " working hour: " +workingHour) ;

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
//        double timeB = System.nanoTime();
//        User.getUserById(5);
//        double timeA = System.nanoTime();
//        System.out.println(((timeA-timeB)/1000000000));
        getUsers();
    }

}
