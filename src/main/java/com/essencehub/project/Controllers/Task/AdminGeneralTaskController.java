package com.essencehub.project.Controllers.Task;

import com.essencehub.project.Controllers.Menu.AdminMenuController;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class AdminGeneralTaskController {

    @FXML
    public void goToAssignTask(MouseEvent e){
        AdminMenuController adminMenuController = AdminMenuController.getInstance();
        adminMenuController.loadFXMLContent("/com/essencehub/project/fxml/Task/AssignTask.fxml");

    }
    @FXML
    public void goToViewTask(MouseEvent e){
        AdminMenuController adminMenuController = AdminMenuController.getInstance();
        adminMenuController.loadFXMLContent("/com/essencehub/project/fxml/Task/AdminViewTasks.fxml");
    }


}
