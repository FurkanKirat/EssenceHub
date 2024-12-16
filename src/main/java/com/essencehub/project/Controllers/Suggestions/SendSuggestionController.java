package com.essencehub.project.Controllers.Suggestions;

import com.essencehub.project.User.Suggestion;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class SendSuggestionController {

    @FXML
    private TextArea suggestionTextArea;

    @FXML
    private TextField titletextField;

    @FXML
    void sendSuggestionClicked(MouseEvent event) {
        if(titletextField.getText().isEmpty()||suggestionTextArea.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Title or suggestion area cannot be empty !");
            alert.setTitle("Suggestion could not be sent!");
            alert.showAndWait();
            return;
        }

        Suggestion suggestion = new Suggestion(titletextField.getText(),suggestionTextArea.getText());
        Suggestion.addSuggestion(suggestion);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"You have sent suggestion to admins successfully!");
        alert.showAndWait();
    }

}
