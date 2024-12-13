package com.essencehub.project.Controllers.Finance;

import com.essencehub.project.Finance.Outcome;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;

public class OutcomeController {

    @FXML
    private TextField outcomeField;

    @FXML
    private RadioButton theMostButton;

    @FXML
    private TableColumn<Outcome, String> costColumn;

    @FXML
    private TableColumn<Outcome, LocalDate> dateColumn;

    @FXML
    private TableColumn<Outcome, String> titleColumn;

    @FXML
    private AnchorPane outcomeAnchorPage;

    @FXML
    private ComboBox<String> outcomeComboBox;

    @FXML
    private TableView<Outcome> outcomeTableView;

    public ObservableList<Outcome> getOutcomeList() {
        return FXCollections.observableArrayList(
                new Outcome(LocalDate.of(2024, 1, 15), "January Salay", "5000"),
                new Outcome(LocalDate.of(2024, 2, 10), "Freelance Project", "1500"),
                new Outcome(LocalDate.of(2024, 3, 20), "Bonus", "2000"),
                new Outcome(LocalDate.of(2024, 4, 18), "Investment Return", "300"),
                new Outcome(LocalDate.of(2024, 5, 25), "May Salary", "5000"),
                new Outcome(LocalDate.of(2024, 6, 10), "Freelance Project", "1200"),
                new Outcome(LocalDate.of(2024, 7, 12), "July Dividend", "800"),
                new Outcome(LocalDate.of(2024, 8, 15), "August Salary", "5000"),
                new Outcome(LocalDate.of(2024, 9, 5), "Consulting Fee", "2500"),
                new Outcome(LocalDate.of(2024, 10, 20), "October Bonus", "1500"),
                new Outcome(LocalDate.of(2024, 11, 10), "Freelance Work", "1700"),
                new Outcome(LocalDate.of(2024, 12, 25), "December Gift", "500")
        );
    }

    public ObservableList<Outcome> outcomes;
    public ObservableList<Outcome> outcomesFromMost = FXCollections.observableArrayList();
    private String[] timePeriods = {"Last Month", "Last 6 Months", "Last 1 Year", "All Incomes"};
    private double totalOutcome = 0;
    private LocalDate ourDay;
    private LocalDate startProcessWhere;
    private int delay;
    private boolean listFromMost = false;


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
        } else if (outcomeComboBox.getValue().equals("All Incomes")) {
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
        ObservableList<Outcome> tableItems = outcomeTableView.getItems();

        if(ourDay.equals(startProcessWhere)){
            if(listFromMost){

                tableItems.addAll(outcomesFromMost);
                for (Outcome outcome : outcomesFromMost) {
                    totalOutcome += Double.parseDouble(outcome.getAmount());
                }
                findTotalOutcome();
            }else{
                tableItems.addAll(outcomes);
                for (Outcome outcomes : outcomes) {
                    totalOutcome += Double.parseDouble(outcomes.getAmount());
                }
                findTotalOutcome();
            }

        }else{
            totalOutcome = 0;// don't give permission to repeat
            if(listFromMost){
                for (Outcome outcomes : outcomesFromMost) {
                    if(outcomes.getDate().isAfter(startProcessWhere) ||outcomes.getDate().equals(startProcessWhere)){
                        tableItems.add(outcomes);
                        totalOutcome += Double.parseDouble(outcomes.getAmount());
                    }
                }
            }else{

                for (Outcome outcome : outcomes) {
                    if(outcome.getDate().isAfter(startProcessWhere) || outcome.getDate().equals(startProcessWhere)){
                        tableItems.add(outcome);
                        totalOutcome += Double.parseDouble(outcome.getAmount());
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
            double amount1 = Double.parseDouble(i1.getAmount());
            double amount2 = Double.parseDouble(i2.getAmount());
            return Double.compare(amount2, amount1);
        });
    }


}
