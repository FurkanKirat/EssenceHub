package com.essencehub.project.Controllers;

import com.essencehub.project.Employees.Admin;
import com.essencehub.project.Employees.Employee;
import com.essencehub.project.Employees.Staff;
import javafx.event.ActionEvent;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
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
    ArrayList<Staff> staffs = new ArrayList<>();

    // Will be the method to add staffs from the SQL to arraylist
    private void addStaffs(){

        // These are for tests
        staffs.add(new Employee("12345","abcde"));
        staffs.add(new Admin("54321","abcde"));
    }

    @FXML
    void loginClicked(ActionEvent event) throws IOException {

        Staff currentStaff = new Employee("1","1");
        addStaffs();

        // Get the staff that has the same ID and password
        for(Staff staff: staffs){
            boolean isPasswordTrue = passwordField.isVisible() ? staff.getPassword().equals(passwordField.getText()) : staff.getPassword().equals(passwordText.getText());
            if(staff.getID().equals(idField.getText())&&isPasswordTrue){
                currentStaff = staff;
            }
        }

        if(currentStaff.getPassword().equals("1")&&currentStaff.getID().equals("1")){

        }
        else if(currentStaff.isAdmin()){

            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/essencehub/project/essence.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);

                Stage stage = new Stage();
                stage.setTitle("Essence Hub");
                stage.setScene(scene);
                stage.getIcons().add(new Image( getClass().getResourceAsStream( "/com/essencehub/project/images/logo.jpg" )));
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
            stage.getIcons().add(new Image( getClass().getResourceAsStream( "/com/essencehub/project/images/logo.jpg" )));
            stage.show();

            Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        }

    }


}
