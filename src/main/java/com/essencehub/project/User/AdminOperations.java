package com.essencehub.project.User;

import com.essencehub.project.DatabaseOperations.DatabaseConnection;
import com.essencehub.project.Stock.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

public class AdminOperations {
    public static List<User> users = new ArrayList<>();
    public static ArrayList<User> employees = new ArrayList<>();

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

    //SEND MESSAGE
    public static void sendMessageMain(User sender, User receiver, String message, LocalDateTime sendDateTime) {
        Message messageTemp = new Message(sender, receiver, message, sendDateTime);
        sendMessage(messageTemp);
    }

    private static void sendMessage(Message message) {
        String sql = "INSERT INTO Message (sender_id, receiver_id, message, send_date_time) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Adding to the database
            preparedStatement.setInt(1, message.getSender().getId());
            preparedStatement.setInt(2, message.getReceiver().getId());
            preparedStatement.setString(3, message.getMessage());

            // We can set LocalDateTime directly, it is compatible with DATETIME type in MySQL
            preparedStatement.setObject(4, message.getSendDateTime());

            //run SQL command
            preparedStatement.executeUpdate();
            System.out.println("Message sent successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // SEND TASK
    public static void sendTaskMain(User sender, User receiver, String task, String title, LocalDateTime sendDateTime, boolean isTaskDone) {
        Task taskTemp = new Task(sender, receiver, task, title, sendDateTime, isTaskDone);
        sendTask(taskTemp);
    }



    public static void sendTask(Task task) {
        String insertTaskSQL = "INSERT INTO Task (sender_id, receiver_id, task, title, send_date_time, is_task_done) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertTaskSQL)) {

            // In the SQL query ? We replace the parameters
            preparedStatement.setInt(1, task.getSender().getId()); // Sender ID
            preparedStatement.setInt(2, task.getReceiver().getId()); // Receiver ID
            preparedStatement.setString(3, task.getTask()); // Task description
            preparedStatement.setString(4, task.getTitle()); // Task title
            preparedStatement.setObject(5, task.getSendDateTime()); // Sent date
            preparedStatement.setBoolean(6, task.isTaskDone()); // Task completion status

            //Run SQL query
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Task sent successfully.");
            } else {
                System.out.println("An error occurred while submitting the task.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //UPDATE TASK
    public static void updateTask(Task task) {
        String updateTaskSQL = "UPDATE Task SET "
                + "task = ?, "
                + "title = ?, "
                + "is_task_done = ? "
                + "WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateTaskSQL)) {

            // in SQL query We place parameters in (?) places
            preparedStatement.setString(1, task.getTask()); // Task description
            preparedStatement.setString(2, task.getTitle()); // Task title
            preparedStatement.setBoolean(3, task.isTaskDone()); // Task completion status
            preparedStatement.setInt(4, task.getId()); // Task ID (task to be updated)

            //Run SQL query
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Task updated successfully.");
            } else {
                System.out.println("An error occurred while updating the task or the task was not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<User> getUsers() {
        List<User> usersTemp = new ArrayList<>();
        users = usersTemp;
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

    // Adds a new product to the stock table.
    public static void addProduct(Product product) {
        String insertProductSQL = "INSERT INTO Stock (name, count, month_and_year) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertProductSQL)) {
                    preparedStatement.setString(1, product.getName());
                    preparedStatement.setInt(2, product.getCount());
                    preparedStatement.setString(3, product.getMonthAndYear());

                    int affectedRows = preparedStatement.executeUpdate();

                    if (affectedRows > 0) {
                        System.out.println("The product was added successfully.");
                    } else {
                        System.out.println("Adding product failed.");
                    }
                }
            } else {
                System.out.println("Database connection failed.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding product: " + e.getMessage());
            e.printStackTrace();
        }}

    // Increases the count of the selected object by increaseAmount.
    public static void addStock(Product product, int increaseAmount) {

        //SQL UPDATE query
        String updateStockSQL = "UPDATE Stock SET count = count + ? WHERE name = ? AND month_and_year = ?";

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {

                // Prepare the query with PreparedStatement
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateStockSQL)) {

                    // Set query parameters
                    preparedStatement.setInt(1, increaseAmount); // count + increaseAmount
                    preparedStatement.setString(2, product.getName()); // name
                    preparedStatement.setString(3, product.getMonthAndYear()); // month_and_year

                    // Run the query
                    int affectedRows = preparedStatement.executeUpdate();

                    if (affectedRows > 0) {
                        System.out.println("Stock updated successfully.");
                    } else {
                        System.out.println("Product not found. Stock could not be updated.");
                    }
                }
            } else {
                System.out.println("Database connection failed.");
            }
        } catch (SQLException e) {
            System.out.println("Stock update error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Decreases the count of the selected object by decreaseAmount.
    public static void removeStock(Product product, int decreaseAmount) {
        String selectStockSQL = "SELECT count FROM Stock WHERE name = ? AND month_and_year = ?";
        String updateStockSQL = "UPDATE Stock SET count = count - ? WHERE name = ? AND month_and_year = ?";

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                try (PreparedStatement selectStatement = connection.prepareStatement(selectStockSQL)) {
                    selectStatement.setString(1, product.getName());
                    selectStatement.setString(2, product.getMonthAndYear());

                    try (ResultSet resultSet = selectStatement.executeQuery()) {
                        if (resultSet.next()) {
                            int currentCount = resultSet.getInt("count");
                            if (currentCount >= decreaseAmount) {
                                try (PreparedStatement updateStatement = connection.prepareStatement(updateStockSQL)) {
                                    updateStatement.setInt(1, decreaseAmount);
                                    updateStatement.setString(2, product.getName());
                                    updateStatement.setString(3, product.getMonthAndYear());

                                    int affectedRows = updateStatement.executeUpdate();
                                    if (affectedRows > 0) {
                                        System.out.println("Inventory updated successfully.");
                                    } else {
                                        System.out.println("Product not found. Stock could not be updated.");
                                    }
                                }
                            } else {
                                System.out.println("Insufficient stock! Available quantity: " + currentCount);
                            }
                        } else {
                            System.out.println("Product not found.");
                        }
                    }
                }
            } else {
                System.out.println("Database connection failed.");
            }
        } catch (SQLException e) {
            System.out.println("Stock update error: " + e.getMessage());
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

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }


    public static List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        String query = "SELECT id, sender_id, receiver_id, task, title, send_date_time, is_task_done FROM Task ORDER BY send_date_time DESC";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int senderId = resultSet.getInt("sender_id");
                int receiverId = resultSet.getInt("receiver_id");
                String task = resultSet.getString("task");
                String title = resultSet.getString("title");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime sendDateTime = LocalDateTime.parse(resultSet.getString("send_date_time"), formatter);
                boolean isTaskDone = resultSet.getBoolean("is_task_done");

                Task taskObj = new Task(getUserById(senderId), getUserById(receiverId), task, title, sendDateTime, isTaskDone);
                taskObj.setId(id);
                taskList.add(taskObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return taskList;
    }

    public static ArrayList<User> getEmployees() {
        ArrayList<User> employeesTemp = new ArrayList<>();
        employees = employeesTemp;
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

    public static List<Task> getUserTasks(int userID) {
        List<Task> taskList = new ArrayList<>();
        String query = "SELECT id, sender_id, receiver_id, task, title, send_date_time, is_task_done FROM Task WHERE receiver_id = ? ORDER BY send_date_time DESC";

        try {
            PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(query);
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int senderId = resultSet.getInt("sender_id");
                int receiverId = resultSet.getInt("receiver_id");
                String task = resultSet.getString("task");
                String title = resultSet.getString("title");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime sendDateTime = LocalDateTime.parse(resultSet.getString("send_date_time"),formatter);
                boolean isTaskDone = resultSet.getBoolean("is_task_done");

                Task taskObj = new Task(getUserById(senderId),getUserById(receiverId),task, title,sendDateTime,isTaskDone);
                taskObj.setId(id);
                taskList.add(taskObj);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }


        return taskList;
    }


}
