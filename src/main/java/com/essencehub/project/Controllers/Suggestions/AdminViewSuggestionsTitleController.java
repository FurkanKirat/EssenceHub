package com.essencehub.project.Controllers.Suggestions;

import com.essencehub.project.Controllers.Menu.AdminMenuController;
import com.essencehub.project.User.Suggestion;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class AdminViewSuggestionsTitleController {

    @FXML
    private ListView<Suggestion> suggestionsList;

    private static Suggestion suggestion;

    public void initialize(){
        suggestionsList.getItems().addAll(Suggestion.getAllSuggestions());
    }

    @FXML
    void suggestionClicked(MouseEvent event) {
        if(suggestionsList.getSelectionModel().getSelectedItem()!=null){
            suggestion = suggestionsList.getSelectionModel().getSelectedItem();
            AdminMenuController adminMenuController = AdminMenuController.getInstance();
            adminMenuController.loadFXMLContent("/com/essencehub/project/fxml/Suggestions/viewSuggestion.fxml");
        }


    }

    public static Suggestion getSuggestion() {
        return suggestion;
    }
}
