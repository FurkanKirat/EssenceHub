package com.essencehub.project.Controllers.Menu;


import com.essencehub.project.Controllers.Settings.ThemeController;
import com.essencehub.project.DatabaseOperations.DatabaseConnection;
import com.essencehub.project.Stock.Product;
import com.essencehub.project.User.NotificationSender;
import com.essencehub.project.User.Performance;
import com.essencehub.project.User.Task;
import com.essencehub.project.User.User;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class LoginPageController {

    @FXML
    private ImageView ID;

    @FXML
    private AnchorPane anchor1;

    @FXML
    private AnchorPane anchor2;

    @FXML
    private Label appLabel;

    @FXML
    private HBox hbpx;

    @FXML
    private ImageView hide;

    @FXML
    private TextField idField;

    @FXML
    private Label idLabel;

    @FXML
    private Button loginbtn;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField passwordText;

    @FXML
    private ImageView passwordicon;

    @FXML
    private ImageView show;

    @FXML
    private Label warning;

    public static User loggedUser;

    @FXML
    void hideClicked(MouseEvent event) {

        if(passwordField.isVisible()){
            passwordText.setVisible(true);
            passwordField.setVisible(false);

            passwordText.setText(passwordField.getText());

        }
        else{
            passwordField.setVisible(true);
            passwordText.setVisible(false);

            passwordField.setText(passwordText.getText());


        }

        show.setVisible(!show.isVisible());
        hide.setVisible(!hide.isVisible());
    }


    @FXML
    void loginClicked(ActionEvent event) throws IOException {
        login(event);

    }
    @FXML
    void loginEnter(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            login(event);
        }
    }
    public void login(Event event) {

        try {
            warning.setVisible(false);
            int id = Integer.parseInt(idField.getText());
            String password = passwordField.isVisible() ? passwordField.getText() : passwordText.getText();

            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM User WHERE id = ? AND password = ?"
            );
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, password);
            ResultSet resultset = preparedStatement.executeQuery();
            
            if (!resultset.next()) {
                warning.setText("ID or password is wrong");
                warning.setVisible(true);
                return;

            }
            loggedUser = new User();
            logUser(resultset);

            boolean isPasswordTrue = id == resultset.getInt("id") && password.equals(resultset.getString("password"));
            if(!isPasswordTrue){
                warning.setText("ID or password is wrong");
                warning.setVisible(true);
                return;
            }

            if (resultset.getInt("isActive") == 0) {
                warning.setVisible(true);
                warning.setText("This user is not active");
                return;

            }
            warning.setVisible(false);
            loadAppropriateMenu(event);
            sendNotifications(loggedUser.isAdmin());


        } catch (NullPointerException e) {
            warning.setText("A required component is missing!");
            e.printStackTrace();
            warning.setVisible(true);
        } catch (IllegalArgumentException e) {
            warning.setText("Invalid argument provided!");
            e.printStackTrace();
            warning.setVisible(true);
        } catch (SQLTimeoutException e) {
            warning.setText("Database operation timed out!");
            e.printStackTrace();
            warning.setVisible(true);
        } catch (ClassCastException e) {
            warning.setText("Data type mismatch error!");
            e.printStackTrace();
            warning.setVisible(true);
        } catch (IOException e) {
            warning.setText("FXML Loading error!");
            e.printStackTrace();
            warning.setVisible(true);
        }
        catch (Exception e) {
            warning.setText("Something went wrong!");
            e.printStackTrace();
            warning.setVisible(true);
        }
    }

    public static User getUser(){
        return loggedUser;
    }
    public void logUser(ResultSet resultset) {
        try {
            loggedUser.setId(resultset.getInt("id"));
            loggedUser.setName(resultset.getString("name"));
            loggedUser.setSurname(resultset.getString("surname"));
            loggedUser.setPhoneNumber(resultset.getString("phoneNumber"));
            loggedUser.setSalary(resultset.getDouble("salary"));
            loggedUser.setAdmin(resultset.getBoolean("isAdmin"));

            loggedUser.setBirth(getStringOrNull(resultset, "birth"));

            loggedUser.setDepartment(resultset.getString("department"));
            loggedUser.setEmail(resultset.getString("email"));
            loggedUser.setRemainingLeaveDays(resultset.getInt("remainingLeaveDays"));

            loggedUser.setMonthlyPerformance(getPerformance(resultset,"monthlyPerformance"));

            loggedUser.setBonusSalary(resultset.getDouble("bonusSalary"));
            loggedUser.setActive(resultset.getBoolean("isActive"));
            loggedUser.setPassword(resultset.getString("password"));

            String imageLocation = resultset.getString("imageLocation");
            loggedUser.setImage(new Image(imageLocation));
            loggedUser.setImageLocation(imageLocation);
            loggedUser.setWorkingHour(resultset.getString("workingHour"));
        } catch (SQLException e) {
            throw new RuntimeException("Failed to login",e);
        }
    }
    private void loadAppropriateMenu(Event event) throws IOException {

        String fxmlFile;

        if (loggedUser.isAdmin()) {
            fxmlFile = "/com/essencehub/project/fxml/Menu/AdminMenu.fxml"; // Admin menu
        } else {
            fxmlFile = "/com/essencehub/project/fxml/Menu/EmployeeMenu.fxml"; // User menu
        }

        // Load the FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();

        // Set the new scene
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        ThemeController.changeTheme(scene);
        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();

    }

    private String getStringOrNull(ResultSet resultSet, String columnLabel) throws SQLException {
        return resultSet.getString(columnLabel);
    }

    private Performance getPerformance(ResultSet resultSet, String columnLabel) throws SQLException {
        String value = resultSet.getString(columnLabel);
        return (value != null) ? Performance.valueOf(value.toUpperCase()) : null;
    }
    private void sendNotifications(boolean isAdmin){

        if(isAdmin){
            List<Task> tasks = Task.getAllTasks();
            for(Task task: tasks){
                if(!task.isTaskDone()&& LocalDateTime.now().isAfter(task.getFinishTime())){
                    NotificationSender.send("Task not finished","Someone did not finished their task before the deadline!");
                    break;
                }
            }
            List<Product> products = Product.getAllProducts();
            for(Product product: products){
                if(product.getCount()>=100){
                    NotificationSender.send("Overstock","There is an overstock situation on a product!");
                    break;
                }
            }
            for(Product product: products){
                if(product.getCount()<=5){
                    NotificationSender.send("Understock","There is an understock situation on a product");
                    break;
                }
            }
        }
        else{

            boolean isSent = false;
            List<Task> tasks = Task.getUserTasks(LoginPageController.getUser().getId());
            Task soonestTask = null;

            for (Task task : tasks) {
                if (!task.isTaskDone() && LocalDateTime.now().isAfter(task.getFinishTime())) {
                    NotificationSender.send("Task Not Completed", "You did not finished one of your tasks before the deadline!");
                    isSent = true;
                    break;
                }
            }

            if (!isSent) {
                for (Task task : tasks) {
                    if (!task.isTaskDone()) {
                        if (soonestTask == null || task.getFinishTime().isBefore(soonestTask.getFinishTime())) {
                            soonestTask = task;
                        }
                    }
                }

                if (soonestTask != null) {
                    Duration remainingTime = Duration.between(LocalDateTime.now(), soonestTask.getFinishTime());
                    long days = remainingTime.toDays();
                    long hours = remainingTime.toHoursPart();
                    long minutes = remainingTime.toMinutesPart();

                    NotificationSender.send(
                            "Upcoming Task Deadline",
                            "Task \"" + soonestTask.getTitle() + "\" has the closest deadline: "
                                    + days + " days, " + hours + " hours, and " + minutes + " minutes left!"
                    );
                }
            }


        }
    }


}
