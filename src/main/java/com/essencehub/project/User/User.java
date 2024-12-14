package com.essencehub.project.User;

import com.essencehub.project.DatabaseOperations.DatabaseConnection;
import javafx.scene.image.Image;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class User {

    private int id;
    private String name;
    private String surname;
    private String password;
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
    private String imageLocation;
    private String fullName;

    public User() {

    }

    public User(int id) {
        this.id = id;
    }

    public User(String name, String surname, String phoneNumber, double baseSalary, boolean isAdmin, String birth,
                String department, String email, int remainingLeaveDays, boolean isActive, String password, Performance monthlyPerformance, double bonusSalary, String imageLocation) {
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
        this.imageLocation = imageLocation;
        //this.image = new Image(imageLocation);
        this.fullName =  name + " " + surname;

    }

    // Getter and Setters
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
        this.fullName = name + " " + surname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
        this.fullName = name + " " + surname;
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public String getFullName() {
        return fullName;
    }

    public void changePassword(String password) {
        this.password = password;

        updatePasswordInDatabase();
    }



    public void changeImage(String imageLocation) {
        this.imageLocation = imageLocation;
        this.image = new Image(imageLocation);

        updateImageInDatabase();
    }

    private void updateImageInDatabase() {
        String updateSQL = "UPDATE User SET imageLocation = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, this.imageLocation);
            preparedStatement.setInt(2, this.id);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Image value updated successfully. User ID: " + this.id);
            } else {
                System.out.println("Failed to update image. User not found.");
            }
        } catch (SQLException e) {
            System.out.println("Database update error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    // Database user methods
    private void updatePasswordInDatabase() {
        String updateSQL = "UPDATE User SET password = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, this.password);
            preparedStatement.setInt(2, this.id);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Password updated successfully. User ID: " + this.id);
            } else {
                System.out.println("Failed to update password. User not found.");
            }
        } catch (SQLException e) {
            System.out.println("Database update error: " + e.getMessage());
            e.printStackTrace();
        }
    }

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

    public static User getUsername(int userId) {
        User user = null;
        String query = "SELECT id, name, surname FROM User WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    // Hire employee
    public static void addUser(User user) {
        String sql = "INSERT INTO User (name, surname, phoneNumber, salary, isAdmin, birth, department, email, remainingLeaveDays, monthlyPerformance, bonusSalary, isActive, password,imageLocation) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // We set the parameters
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getPhoneNumber());
            statement.setDouble(4, user.getSalary());
            statement.setBoolean(5, user.isAdmin());
            statement.setString(6, user.getBirth());
            statement.setString(7, user.getDepartment());
            statement.setString(8, user.getEmail());
            statement.setInt(9, user.getRemainingLeaveDays());
            statement.setString(10,
                    user.getMonthlyPerformance() != null ? user.getMonthlyPerformance().toString() : null);
            statement.setDouble(11, user.getBonusSalary());
            statement.setBoolean(12, user.isActive());
            statement.setString(13, user.getPassword()); // Şifreyi ekliyoruz
            statement.setString(14,user.getImageLocation());

            // We add it to the database
            statement.executeUpdate();
            System.out.println("User added successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //UPDATE USER
    public static void updateUser(User user) {
        String sql = "UPDATE User SET name = ?, surname = ?, phoneNumber = ?, salary = ?, isAdmin = ?, birth = ?, department = ?, email = ?, remainingLeaveDays = ?, monthlyPerformance = ?, bonusSalary = ?, isActive = ?, imageLocation = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getPhoneNumber());
            statement.setDouble(4, user.getSalary());
            statement.setBoolean(5, user.isAdmin());
            statement.setString(6, user.getBirth());
            statement.setString(7, user.getDepartment());
            statement.setString(8, user.getEmail());
            statement.setInt(9, user.getRemainingLeaveDays());
            statement.setString(10,
                    user.getMonthlyPerformance() != null ? user.getMonthlyPerformance().toString() : null);
            statement.setDouble(11, user.getBonusSalary());
            statement.setBoolean(12, user.isActive());
            statement.setString(13, user.getImageLocation());
            statement.setInt(14, user.getId());


            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User updated successfully.");
            } else {
                System.out.println("User not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateUser(String name, String surname, String phoneNumber, double baseSalary, boolean isAdmin, String birth,String department,
                                  String email, int remainingLeaveDays, boolean isActive, String password, Performance monthlyPerformance, int bonusSalary, String imageLocation) {
        User user = new User(name,surname,phoneNumber,baseSalary,isAdmin,birth,department,email,remainingLeaveDays,isActive,password,monthlyPerformance,bonusSalary, imageLocation);
        String sql = "UPDATE User SET name = ?, surname = ?, phoneNumber = ?, salary = ?, isAdmin = ?, birth = ?, department = ?, email = ?, remainingLeaveDays = ?, monthlyPerformance = ?, bonusSalary = ?, isActive = ?, imageLocation WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {


            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getPhoneNumber());
            statement.setDouble(4, user.getSalary());
            statement.setBoolean(5, user.isAdmin());
            statement.setString(6, user.getBirth());
            statement.setString(7, user.getDepartment());
            statement.setString(8, user.getEmail());
            statement.setInt(9, user.getRemainingLeaveDays());
            statement.setString(10,
                    user.getMonthlyPerformance() != null ? user.getMonthlyPerformance().toString() : null);
            statement.setDouble(11, user.getBonusSalary());
            statement.setBoolean(12, user.isActive());
            statement.setString(13, user.getImageLocation());
            statement.setInt(14, user.getId());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User updated successfully.");
            } else {
                System.out.println("User not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<User> getUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM User";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                User user = new User();

                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setPhoneNumber(resultSet.getString("phoneNumber"));
                user.setSalary(resultSet.getDouble("salary"));
                user.setAdmin(resultSet.getBoolean("isAdmin"));

                String dateTimeString = resultSet.getString("birth");
                if (dateTimeString != null) {
                    user.setBirth(dateTimeString);
                }

                user.setDepartment(resultSet.getString("department"));
                user.setEmail(resultSet.getString("email"));
                user.setRemainingLeaveDays(resultSet.getInt("remainingLeaveDays"));

                String performanceValue = resultSet.getString("monthlyPerformance");
                if (performanceValue != null) {
                    Performance performance = Performance.valueOf(performanceValue.toUpperCase());
                    user.setMonthlyPerformance(performance);
                }

                user.setBonusSalary(resultSet.getDouble("bonusSalary"));
                user.setActive(resultSet.getBoolean("isActive"));
                user.setPassword(resultSet.getString("password"));
                user.setImageLocation(resultSet.getString("imageLocation"));

                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Database query error: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    public static ArrayList<User> getEmployees() {
        ArrayList<User> employees = new ArrayList<>();
        String query = "SELECT * FROM User WHERE isAdmin = 0 AND isActive = 1";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                User employee = new User();

                employee.setId(resultSet.getInt("id"));
                employee.setName(resultSet.getString("name"));
                employee.setSurname(resultSet.getString("surname"));
                employee.setPhoneNumber(resultSet.getString("phoneNumber"));
                employee.setSalary(resultSet.getDouble("salary"));
                employee.setAdmin(false);

                String dateTimeString = resultSet.getString("birth");
                if (dateTimeString != null) {
                    employee.setBirth(dateTimeString);
                }

                employee.setDepartment(resultSet.getString("department"));
                employee.setEmail(resultSet.getString("email"));
                employee.setRemainingLeaveDays(resultSet.getInt("remainingLeaveDays"));

                String performanceValue = resultSet.getString("monthlyPerformance");
                if (performanceValue != null) {
                    Performance performance = Performance.valueOf(performanceValue.toUpperCase());
                    employee.setMonthlyPerformance(performance);
                }

                employee.setBonusSalary(resultSet.getDouble("bonusSalary"));
                employee.setActive(true);
                employee.setPassword(resultSet.getString("password"));
                employee.setImageLocation(resultSet.getString("imageLocation"));

                employees.add(employee);
            }
        } catch (SQLException e) {
            System.out.println("Database query error: " + e.getMessage());
            e.printStackTrace();
        }
        return employees;
    }

}
