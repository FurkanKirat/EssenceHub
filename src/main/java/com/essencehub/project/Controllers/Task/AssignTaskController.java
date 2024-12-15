package com.essencehub.project.Controllers.Task;

import com.essencehub.project.Controllers.Menu.AdminMenuController;
import com.essencehub.project.Controllers.Menu.LoginPageController;
import com.essencehub.project.User.Task;
import com.essencehub.project.User.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;



import java.time.LocalDateTime;
import java.util.ArrayList;

public class AssignTaskController {

    @FXML
    private ListView <HBox> receiverListView; // Employees names + checkbox

    @FXML
    private TextField titleTextField;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ImageView viewTaskImage;

    private ArrayList<User> employees;
    private CheckBox[] checkBoxes;
    private User user;


    @FXML
    void viewTaskIconClicked(MouseEvent event) {
        AdminMenuController adminMenuController = AdminMenuController.getInstance();
        adminMenuController.loadFXMLContent("/com/essencehub/project/fxml/Task/AdminViewTasks.fxml");
    }
    @FXML
    public void initialize() {

        employees = User.getEmployees();
        checkBoxes = new CheckBox[employees.size()];
        ObservableList<HBox> list = FXCollections.observableArrayList();

        for (int i = 0; i < employees.size(); i++) {
            HBox hBox = new HBox(10);
            CheckBox checkBox = new CheckBox();
            checkBoxes[i] = checkBox;
            Label label = new Label(employees.get(i).toString());
            label.getStyleClass().add("TaskLabel");

            hBox.getChildren().addAll(checkBox, label);
            list.add(hBox);
        }

        receiverListView.setItems(list);
        user = LoginPageController.getUser();
    }

    @FXML
    public void assignTask(ActionEvent e) {

        try {

            StringBuilder headerText = new StringBuilder("You have successfully sent the task to ");
            int selectedEmployee = 0;

            for(int i=0;i<checkBoxes.length;i++){
                if(checkBoxes[i].isSelected()){
                    selectedEmployee++;
                    if(selectedEmployee<=3){
                        headerText.append(employees.get(i)).append(", ");
                    }

                }
            }
            if(selectedEmployee>3){
                headerText.append("... and (").append(selectedEmployee-3).append(") more");
            }
            else {
                if (!headerText.isEmpty() && headerText.charAt(headerText.length() - 2) == ',') {
                    headerText.setLength(headerText.length() - 2);
                }
            }
            for(int i=0;i<checkBoxes.length;i++){
                if(checkBoxes[i].isSelected()){

                    Task task = new Task(user, employees.get(i), descriptionTextArea.getText(), titleTextField.getText(), LocalDateTime.now(),false,0, datePicker.getValue().atStartOfDay());
                    Task.sendTask(task);


                }
            }


            Alert logOutAlert = new Alert(Alert.AlertType.INFORMATION);
            logOutAlert.setTitle("Task Sent");

            logOutAlert.setHeaderText(headerText +" ");
            logOutAlert.showAndWait();

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void viewTaskImageClicked(javafx.scene.input.MouseEvent mouseEvent) {
    }
}
