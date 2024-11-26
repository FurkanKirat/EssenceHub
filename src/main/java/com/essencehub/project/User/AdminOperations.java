package com.essencehub.project.User;

import com.essencehub.project.DatabaseOperations.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminOperations {

// KULLANICI İŞE AL
    public void addUser(User user) {
    String sql = "INSERT INTO User (name, surname, phoneNumber, salary, isAdmin, birth, department, email, remainingLeaveDays, monthlyPerformance, bonusSalary, isActive, password) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
        statement.setString(13, user.getPassword());  // Şifreyi ekliyoruz

        // Veritabanına ekliyoruz
        statement.executeUpdate();
        System.out.println("Kullanıcı başarıyla eklendi.");

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    //KULLANICI GÜNCELLE
    public void updateUser(User user) {
        String sql = "UPDATE User SET name = ?, surname = ?, phoneNumber = ?, salary = ?, isAdmin = ?, birth = ?, department = ?, email = ?, remainingLeaveDays = ?, monthlyPerformance = ?, bonusSalary = ?, isActive = ? WHERE id = ?";

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
            statement.setInt(13, user.getId());

            // Veritabanında güncelleme yapıyoruz
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

    //MESAJ GÖNDER
    public void sendMessageMain(User sender,User receiver,String message,String title){
        Message messageTemp = new Message(sender,receiver,message,title);
        sendMessage(messageTemp);
    }
    private void sendMessage(Message message) {
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
    public void sendTaskMain(User sender,User receiver,String task,String title){
        Task taskTemp = new Task(sender,receiver,task,title);
        sendTask(taskTemp);
    }
    private void sendTask(Task task) {
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

    //TASK GÜNCELLE
    public void updateTask(Task task) {
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

    public static void main(String[] args) {
        AdminOperations ao = new AdminOperations();
        User user1 = new User("Uğur", "Güdükbay","1234567",1000, true, "1990-05-15", "CS", "UGURGUDUKBAY@ICLOUD.COM", 40, true,"123");
        ao.addUser(user1);//Ekleme yapıldı yapmadıysanız bir kere runlayın beyler
        User user2 = new User("BILL", "GATES","9876543",1000, true, "1980-05-15", "CS", "BILLGATES@ICLOUD.COM", 40, true,"ruhi1234");
        ao.addUser(user2);//Ekleme yapıldı yapmadıysanız bir kere runlayın beyler
        User user3 = new User("Elon", "Musk","5891238",100, false, "2000-05-15", "CS", "ELONMUSK@ICLOUD.COM", 40, true,"abc");
        ao.addUser(user3);//Ekleme yapıldı yapmadıysanız bir kere runlayın beyler

        ao.sendMessageMain(user1,user2,"Come to Bilkent Bill","Hello");
        ao.sendMessageMain(user1,user2,"Okay Ugur","Hello");
        ao.sendMessageMain(user2,user3,"How are u Elon","Hello");
        ao.sendMessageMain(user3,user2,"Thanks BILL, Im fine and u ?","Hello");

        ao.sendTaskMain(user1, user3,"Tesla Telefon Çıkart", "Telefon");
    }
}
