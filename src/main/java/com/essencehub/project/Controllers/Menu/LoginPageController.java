package com.essencehub.project.Controllers.Menu;


import com.essencehub.project.Controllers.Settings.ThemeController;
import com.essencehub.project.DatabaseOperations.DatabaseConnection;
import com.essencehub.project.User.Performance;
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
            Statement statement = connection.createStatement();

            ResultSet resultset = statement.executeQuery("SELECT * FROM user WHERE id = " + id + " AND password = '" + password + "'");

            if (resultset.next()) {
                loggedUser = new User();
                logUser(resultset); // Pass the ResultSet to logUser()

                boolean isPasswordTrue = id == resultset.getInt("id") && password.equals(resultset.getString("password"));

                if (isPasswordTrue) {
                    if (resultset.getInt("isActive") == 1) {
                        warning.setVisible(false);
                        loadAppropriateMenu(event); // Extracted menu-loading logic to a separate method.
                    } else {
                        warning.setVisible(true);
                        warning.setText("This user is not active");
                    }
                } else {
                    warning.setText("ID or password is wrong");
                    warning.setVisible(true);
                }
            } else {
                warning.setText("ID or password is wrong");
                warning.setVisible(true);
            }
        } catch (SQLException e) {
            warning.setText("SQL Exception has occurred!");
            e.printStackTrace();
            warning.setVisible(true);
        } catch (IOException e) {
            warning.setText("FXML File Could not Load!");
            e.printStackTrace();
            warning.setVisible(true);
        } catch (Exception e) {
            warning.setText("Something went wrong!");
            e.printStackTrace();
            warning.setVisible(true);
        }
    }

//    public static ResultSet getResultSet(){
//        return resultset;
//    }


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

            String dateTimeString = resultset.getString("birth");
            if (dateTimeString != null) {
                loggedUser.setBirth(dateTimeString);
            }

            loggedUser.setDepartment(resultset.getString("department"));
            loggedUser.setEmail(resultset.getString("email"));
            loggedUser.setRemainingLeaveDays(resultset.getInt("remainingLeaveDays"));

            String performanceValue = resultset.getString("monthlyPerformance");
            if (performanceValue != null) {
                Performance performance = Performance.valueOf(performanceValue.toUpperCase());
                loggedUser.setMonthlyPerformance(performance);
            }

            loggedUser.setBonusSalary(resultset.getDouble("bonusSalary"));
            loggedUser.setActive(resultset.getBoolean("isActive"));
            loggedUser.setPassword(resultset.getString("password"));
            loggedUser.setImage(new Image(resultset.getString("imageLocation")));
            loggedUser.setImageLocation(resultset.getString("imageLocation"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
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


}
