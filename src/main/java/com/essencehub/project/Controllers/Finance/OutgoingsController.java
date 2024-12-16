package com.essencehub.project.Controllers.Finance;

import com.essencehub.project.Controllers.Menu.AdminMenuController;
import com.essencehub.project.DatabaseOperations.DatabaseConnection;
import com.essencehub.project.Finance.Outgoings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class OutgoingsController {

    @FXML
    private VBox expensesIconVBox;

    @FXML
    private VBox incomeIconVBox;

    @FXML
    private TextField outcomeField;

    @FXML
    private RadioButton theMostButton;

    @FXML
    private TableColumn<Outgoings, String> costColumn;

    @FXML
    private TableColumn<Outgoings, LocalDate> dateColumn;

    @FXML
    private TableColumn<Outgoings, String> titleColumn;

    @FXML
    private AnchorPane outcomeAnchorPage;

    @FXML
    private ComboBox<String> outcomeComboBox;

    @FXML
    private TableView<Outgoings> outcomeTableView;

    public ObservableList<Outgoings> getOutcomeList() {
        ObservableList<Outgoings> outgoings = FXCollections.observableArrayList();
        String sql = "SELECT date, title, cost FROM Income";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                LocalDate date = resultSet.getDate("date").toLocalDate();
                String title = resultSet.getString("title");
                String cost = resultSet.getString("cost");

                Outgoings outgoing = new Outgoings(date, title, cost);

                outgoings.add(outgoing); // Add the user to the ObservableList
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return outgoings;
    }

    public ObservableList<Outgoings> outcomes;
    public ObservableList<Outgoings> outcomesFromMost = FXCollections.observableArrayList();
    private String[] timePeriods = {"Last Month", "Last 6 Months", "Last 1 Year", "All Outgoings"};
    private double totalOutcome = 0;
    private LocalDate ourDay;
    private LocalDate startProcessWhere;
    private int delay;
    private boolean listFromMost = false;


    @FXML
    void expensesIconVBoxClicked(MouseEvent event) {
        AdminMenuController adminMenuController = AdminMenuController.getInstance();
        adminMenuController.loadFXMLContent("/com/essencehub/project/fxml/Finance/Outgoings.fxml");
    }

    @FXML
    void incomeIconVBoxClicked(MouseEvent event) {
        AdminMenuController adminMenuController = AdminMenuController.getInstance();
        adminMenuController.loadFXMLContent("/com/essencehub/project/fxml/Finance/Income.fxml");
    }

    @FXML
    void isTheMostButtonClicked(ActionEvent event) {
        listFromMost = theMostButton.isSelected();
        fillTable();
    }

    @FXML
    void outcomeComboBoxIsSelected(ActionEvent event) {
        if (outcomeComboBox.getValue().equals("Last Month")) {
            delay = 1;
            startProcessWhere = ourDay.minusMonths(delay);
            fillTable();
        } else if (outcomeComboBox.getValue().equals("Last 6 Months")) {
            delay = 6;
            startProcessWhere = ourDay.minusMonths(delay);
            fillTable();
        } else if (outcomeComboBox.getValue().equals("Last 1 Year")) {
            delay = 12;
            startProcessWhere = ourDay.minusMonths(delay);
            fillTable();
        } else if (outcomeComboBox.getValue().equals("All Outgoings")) {
            startProcessWhere = ourDay;
            fillTable();
        }
    }


    @FXML
    private void initialize() {
        outcomeComboBox.getItems().clear();
        outcomeComboBox.getItems().addAll(timePeriods);

        outcomeField.setText("" + totalOutcome);
        ourDay = LocalDate.now();

        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        outcomes = getOutcomeList();
        sortOutcomesByAmount();// sorting alg
        findTotalOutcome();
    }

    private void findTotalOutcome(){
        outcomeField.setText(totalOutcome + "");

    }

    private void fillTable(){
        outcomeTableView.getItems().clear();
        totalOutcome = 0;
        ObservableList<Outgoings> tableItems = outcomeTableView.getItems();

        if(ourDay.equals(startProcessWhere)){
            if(listFromMost){

                tableItems.addAll(outcomesFromMost);
                for (Outgoings outgoings : outcomesFromMost) {
                    totalOutcome += Double.parseDouble(outgoings.getCost());
                }
                findTotalOutcome();
            }else{
                tableItems.addAll(outcomes);
                for (Outgoings outcomes : outcomes) {
                    totalOutcome += Double.parseDouble(outcomes.getCost());
                }
                findTotalOutcome();
            }

        }else{
            totalOutcome = 0;// don't give permission to repeat
            if(listFromMost){
                for (Outgoings outcomes : outcomesFromMost) {
                    if(outcomes.getDate().isAfter(startProcessWhere) ||outcomes.getDate().equals(startProcessWhere)){
                        tableItems.add(outcomes);
                        totalOutcome += Double.parseDouble(outcomes.getCost());
                    }
                }
            }else{

                for (Outgoings outgoings : outcomes) {
                    if(outgoings.getDate().isAfter(startProcessWhere) || outgoings.getDate().equals(startProcessWhere)){
                        tableItems.add(outgoings);
                        totalOutcome += Double.parseDouble(outgoings.getCost());
                    }
                }
            }

            findTotalOutcome();
        }
    }

    public void sortOutcomesByAmount() {
        outcomesFromMost.clear();
        outcomesFromMost.addAll(outcomes);
        outcomesFromMost.sort((i1, i2) -> {
            double amount1 = Double.parseDouble(i1.getCost());
            double amount2 = Double.parseDouble(i2.getCost());
            return Double.compare(amount2, amount1);
        });
    }



}
