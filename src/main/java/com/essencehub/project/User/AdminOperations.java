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

    // KULLANICI İŞE AL
    public static void addUser(User user) {
        String sql = "INSERT INTO User (name, surname, phoneNumber, salary, isAdmin, birth, department, email, remainingLeaveDays, monthlyPerformance, bonusSalary, isActive, password,imageLocation) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            // Parametreleri set ediyoruz
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

            // Veritabanına ekliyoruz
            statement.executeUpdate();
            System.out.println("Kullanıcı başarıyla eklendi.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // KULLANICI GÜNCELLE
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
                System.out.println("Kullanıcı başarıyla güncellendi.");
            } else {
                System.out.println("Kullanıcı bulunamadı.");
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
                System.out.println("Kullanıcı başarıyla güncellendi.");
            } else {
                System.out.println("Kullanıcı bulunamadı.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // MESAJ GÖNDER
    public static void sendMessageMain(User sender, User receiver, String message, String title) {
        Message messageTemp = new Message(sender, receiver, message, title);
        sendMessage(messageTemp);
    }

    private static void sendMessage(Message message) {
        String sql = "INSERT INTO Message (sender_id, receiver_id, message, title, send_date_time) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Veritabanına ekliyoruz
            preparedStatement.setInt(1, message.getSender().getId());
            preparedStatement.setInt(2, message.getReceiver().getId());
            preparedStatement.setString(3, message.getMessage());
            preparedStatement.setString(4, message.getTitle());

            // LocalDateTime'ı doğrudan set edebiliriz, MySQL'de DATETIME tipi ile uyumludur
            preparedStatement.setObject(5, message.getSendDateTime());

            // SQL komutunu çalıştır
            preparedStatement.executeUpdate();
            System.out.println("Mesaj başarıyla gönderildi.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // TASK GÖNDER
    public static void sendTaskMain(User sender, User receiver, String task, String title, LocalDateTime sendDateTime, boolean isTaskDone) {
        Task taskTemp = new Task(sender, receiver, task, title, sendDateTime, isTaskDone);
        sendTask(taskTemp);
    }

    public static void sendTask(Task task) {
        String insertTaskSQL = "INSERT INTO Task (sender_id, receiver_id, task, title, send_date_time, is_task_done) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(insertTaskSQL)) {

            // SQL sorgusunda ? yerlerine parametreleri yerleştiriyoruz
            preparedStatement.setInt(1, task.getSender().getId()); // Gönderenin ID'si
            preparedStatement.setInt(2, task.getReceiver().getId()); // Alıcının ID'si
            preparedStatement.setString(3, task.getTask()); // Görev açıklaması
            preparedStatement.setString(4, task.getTitle()); // Görev başlığı
            preparedStatement.setObject(5, task.getSendDateTime()); // Gönderilme tarihi
            preparedStatement.setBoolean(6, task.isTaskDone()); // Görev tamamlanma durumu

            // SQL sorgusunu çalıştır
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Görev başarıyla gönderildi.");
            } else {
                System.out.println("Görev gönderilirken bir hata oluştu.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // TASK GÜNCELLE
    public static void updateTask(Task task) {
        String updateTaskSQL = "UPDATE Task SET "
                + "task = ?, "
                + "title = ?, "
                + "is_task_done = ? "
                + "WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(updateTaskSQL)) {

            // SQL sorgusundaki ? yerlerine parametreleri yerleştiriyoruz
            preparedStatement.setString(1, task.getTask()); // Görev açıklaması
            preparedStatement.setString(2, task.getTitle()); // Görev başlığı
            preparedStatement.setBoolean(3, task.isTaskDone()); // Görev tamamlanma durumu
            preparedStatement.setInt(4, task.getId()); // Görev ID'si (güncellenmek istenen görev)

            // SQL sorgusunu çalıştır
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Görev başarıyla güncellendi.");
            } else {
                System.out.println("Görev güncellenirken bir hata oluştu veya görev bulunamadı.");
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
            System.out.println("Veritabanı sorgulama hatası: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    //Stock table'ına yeni bir product ekler.*p

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
                        System.out.println("Ürün başarıyla eklendi.");
                    } else {
                        System.out.println("Ürün ekleme başarısız oldu.");
                    }
                }
            } else {
                System.out.println("Veritabanı bağlantısı başarısız.");
            }
        } catch (SQLException e) {
            System.out.println("Ürün ekleme hatası: " + e.getMessage());
            e.printStackTrace();
        }}

    // Seçilen nesnenin count'ını increaseAmount kadar arttırır.
    public static void addStock(Product product, int increaseAmount) {
        // SQL UPDATE sorgusu
        String updateStockSQL = "UPDATE Stock SET count = count + ? WHERE name = ? AND month_and_year = ?";

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                // PreparedStatement ile sorguyu hazırlayın
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateStockSQL)) {
                    // Sorgu parametrelerini ayarlayın
                    preparedStatement.setInt(1, increaseAmount); // count + increaseAmount
                    preparedStatement.setString(2, product.getName()); // name
                    preparedStatement.setString(3, product.getMonthAndYear()); // month_and_year

                    // Sorguyu çalıştırın
                    int affectedRows = preparedStatement.executeUpdate();

                    if (affectedRows > 0) {
                        System.out.println("Stok başarıyla güncellendi.");
                    } else {
                        System.out.println("Ürün bulunamadı. Stok güncellenemedi.");
                    }
                }
            } else {
                System.out.println("Veritabanı bağlantısı başarısız.");
            }
        } catch (SQLException e) {
            System.out.println("Stok güncelleme hatası: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Seçilen nesnenin count'ını decreaseAmount kadar eksiltir.
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
                                        System.out.println("Stok başarıyla güncellendi.");
                                    } else {
                                        System.out.println("Ürün bulunamadı. Stok güncellenemedi.");
                                    }
                                }
                            } else {
                                System.out.println("Yetersiz stok! Mevcut miktar: " + currentCount);
                            }
                        } else {
                            System.out.println("Ürün bulunamadı.");
                        }
                    }
                }
            } else {
                System.out.println("Veritabanı bağlantısı başarısız.");
            }
        } catch (SQLException e) {
            System.out.println("Stok güncelleme hatası: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static User getUserById(int userId) {
        User user = null;
        String query = "SELECT id, name, surname, phoneNumber, salary, isAdmin, birth, department, email, "
                     + "remainingLeaveDays, isActive, password FROM User WHERE id = ?";

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
        String query = "SELECT id, sender_id, receiver_id, task, title, send_date_time, is_task_done FROM Task";

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
                LocalDateTime sendDateTime = LocalDateTime.parse(resultSet.getString("send_date_time"),formatter);
                boolean isTaskDone = resultSet.getBoolean("is_task_done");

                Task taskObj = new Task(getUserById(senderId),getUserById(receiverId),task, title,sendDateTime,isTaskDone);
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
            System.out.println("Veritabanı sorgulama hatası: " + e.getMessage());
            e.printStackTrace();
        }
        return employees;
    }

    public static List<Task> getUserTasks(int userID) {
        List<Task> taskList = new ArrayList<>();
        String query = "SELECT id, sender_id, receiver_id, task, title, send_date_time, is_task_done FROM Task WHERE receiver_id = " + userID ;

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
                LocalDateTime sendDateTime = LocalDateTime.parse(resultSet.getString("send_date_time"),formatter);
                boolean isTaskDone = resultSet.getBoolean("is_task_done");

                Task taskObj = new Task(getUserById(senderId),getUserById(receiverId),task, title,sendDateTime,isTaskDone);
                taskList.add(taskObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return taskList;
    }


}
