package com.essencehub.project.Controllers.Suggestions;

import com.essencehub.project.Controllers.Menu.AdminMenuController;
import com.essencehub.project.Controllers.Task.AdminViewTaskController;
import com.essencehub.project.User.Suggestion;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ViewSuggestionController {

    @FXML
    private TextArea textArea;

    @FXML
    private TextField titleTextField;

    @FXML
    void goBackClicked(MouseEvent event) {
        AdminMenuController adminMenuController = AdminMenuController.getInstance();
        adminMenuController.loadFXMLContent("/com/essencehub/project/fxml/Suggestions/viewSuggestionsTitle.fxml");
    }
    public void initialize(){
        Suggestion suggestion = AdminViewSuggestionsTitleController.getSuggestion();
        textArea.setText(suggestion.getMessage());
        titleTextField.setText(suggestion.getTitle());
    }

}
