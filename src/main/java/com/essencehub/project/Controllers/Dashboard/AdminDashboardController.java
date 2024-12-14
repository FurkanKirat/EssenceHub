package com.essencehub.project.Controllers.Dashboard;

import com.essencehub.project.Controllers.Menu.AdminMenuController;
import com.essencehub.project.Controllers.Task.AdminViewTaskController;
import com.essencehub.project.User.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AdminDashboardController {

    @FXML
    private TableColumn<Task, String> employeeNameColumn;

    @FXML
    private TableColumn<Task, String > employeeNameColumn1;

    @FXML
    private VBox func;

    @FXML
    private HBox functions;

    @FXML
    private TableColumn<Task, Boolean> statusColumn;

    @FXML
    private TableColumn<Task, String > taskColumn;

    @FXML
    private TableView<Task> taskTable;


    public void initialize(){
        employeeNameColumn.setCellValueFactory(new PropertyValueFactory<>("receiver"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("taskDone"));
        taskColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        employeeNameColumn1.setCellValueFactory(new PropertyValueFactory<>("sender"));

        ObservableList<Task> allTasks = FXCollections.observableArrayList(Task.getAllTasks());

        ObservableList<Task> lastFiveTasks = FXCollections.observableArrayList(
                allTasks.subList(0, Math.min(allTasks.size(), 8))
        );

        taskTable.setItems(lastFiveTasks);

        double y = System.nanoTime();
    }
    @FXML
    void taskClicked(MouseEvent event) {
        if(!taskTable.getSelectionModel().isEmpty()){
            AdminViewTaskController.setTask(taskTable.getSelectionModel().getSelectedItem());

            AdminMenuController adminMenuController = AdminMenuController.getInstance();
            adminMenuController.loadFXMLContent("/com/essencehub/project/fxml/Task/AdminOpenTask.fxml");
        }
    }
}
