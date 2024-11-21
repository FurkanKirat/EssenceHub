package com.essencehub.project.Controllers;


import com.essencehub.project.User.User;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

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
    void loginEnter(KeyEvent event) throws IOException {
        if(event.getCode() == KeyCode.ENTER){
            login(event);
        }
    }
    public void login(Event event) throws IOException {
        try{
            String id = idField.getText();
            String password = passwordField.isVisible() ? passwordField.getText() : passwordText.getText();
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/project_schema",
                    "root",
                    "ruhi1234"
            );

            Statement statement = connection.createStatement();

            ResultSet resultset = statement.executeQuery("SELECT * FROM users WHERE id = '" + id + "' AND password = '" + password + "'"
            );

            if(resultset.next()){

                boolean isPasswordTrue = id.equals(resultset.getString("id"))&&password.equals(resultset.getString("password"));
                if(isPasswordTrue){

                    warning.setVisible(false);
                    if(resultset.getInt("authorized_degree")==2){
                        try {

                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/essencehub/project/essence.fxml"));
                            Parent root = loader.load();

                            Scene scene = new Scene(root);

                            Stage stage = new Stage();
                            stage.setTitle("Essence Hub");
                            stage.setScene(scene);
                            stage.getIcons().add(new Image( getClass().getResourceAsStream( "/com/essencehub/project/images/logo.jpg" )));
                            //stage.setMinWidth(1315);
                            //stage.setMinHeight(890);
                            stage.show();

                            Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                            currentStage.close();
                        } catch (IOException e) {

                        }
                    }
                    else{

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/essencehub/project/employeePanel.fxml"));
                        Parent root = loader.load();

                        Scene scene = new Scene(root);

                        Stage stage = new Stage();
                        stage.setTitle("Essence Hub");
                        stage.setScene(scene);
                        stage.setMinWidth(1315);
                        stage.setMinHeight(890);
                        stage.getIcons().add(new Image( getClass().getResourceAsStream( "/com/essencehub/project/images/logo.jpg" )));
                        stage.show();

                        Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                        currentStage.close();
                    }

                }

            }
            else{
                warning.setText("ID or password is wrong");
                warning.setVisible(true);
            }
        }
        catch (Exception e){
            warning.setText("Something went wrong");
            warning.setVisible(true);

        }


    }

}
