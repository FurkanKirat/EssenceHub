package com.essencehub.project.Controllers.Menu;


import com.essencehub.project.DatabaseOperations.DatabaseConnection;
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
import java.sql.*;

public class LoginPageController {
    private static ResultSet resultset;
    private static Statement statement;
    private static Connection connection;

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

    public static Statement getStatement() {
        return statement;
    }

    public static void setStatement(Statement statement) {
        LoginPageController.statement = statement;
    }

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
        try{
            warning.setVisible(false);
            int id = Integer.parseInt(idField.getText());
            String password = passwordField.isVisible() ? passwordField.getText() : passwordText.getText();

            connection = DatabaseConnection.getConnection();

            statement = connection.createStatement();

            resultset = statement.executeQuery("SELECT * FROM user WHERE id = " + id + " AND password = '" + password + "'");



            if(resultset.next()){

                boolean isPasswordTrue = id==(resultset.getInt("id"))&&password.equals(resultset.getString("password"));
                if(isPasswordTrue){
                    if(resultset.getInt("isActive")==1){
                        warning.setVisible(false);
                        if(resultset.getInt("isAdmin")==1){

                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/essencehub/project/fxml/Menu/AdminMenu.fxml"));
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

                        }
                        else{

                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/essencehub/project/fxml/Menu/EmployeeMenu.fxml"));
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
                    else{
                        warning.setVisible(true);
                        warning.setText("This user is not active");
                    }


                }else{
                    warning.setText("ID or password is wrong");

                    warning.setVisible(true);
                }

            }
            else{
                warning.setText("ID or password is wrong");
                warning.setVisible(true);
            }

        } catch (SQLException e) {
            warning.setText("SQL Exception has occurred!");
            e.printStackTrace();
            warning.setVisible(true);
        } catch (IOException e) {
            warning.setText("FXML File Could not Loaded!");
            e.printStackTrace();
            warning.setVisible(true);
        }
        catch (Exception e){
            warning.setText("Something went wrong!");
            e.printStackTrace();
            warning.setVisible(true);

        }


    }
    public static ResultSet getResultSet(){
        return resultset;
    }

    public static Connection getConnection() {
        return connection;
    }
}
