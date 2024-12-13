package com.essencehub.project.Controllers.Finance;

import com.essencehub.project.Finance.Outgoings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;

public class OutgoingsController {

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
        return FXCollections.observableArrayList(
                new Outgoings(LocalDate.of(2024, 1, 15), "January Salay", "5000"),
                new Outgoings(LocalDate.of(2024, 2, 10), "Freelance Project", "1500"),
                new Outgoings(LocalDate.of(2024, 3, 20), "Bonus", "2000"),
                new Outgoings(LocalDate.of(2024, 4, 18), "Investment Return", "300"),
                new Outgoings(LocalDate.of(2024, 5, 25), "May Salary", "5000"),
                new Outgoings(LocalDate.of(2024, 6, 10), "Freelance Project", "1200"),
                new Outgoings(LocalDate.of(2024, 7, 12), "July Dividend", "800"),
                new Outgoings(LocalDate.of(2024, 8, 15), "August Salary", "5000"),
                new Outgoings(LocalDate.of(2024, 9, 5), "Consulting Fee", "2500"),
                new Outgoings(LocalDate.of(2024, 10, 20), "October Bonus", "1500"),
                new Outgoings(LocalDate.of(2024, 11, 10), "Freelance Work", "1700"),
                new Outgoings(LocalDate.of(2024, 12, 25), "December Gift", "500")
        );
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
                    totalOutcome += Double.parseDouble(outgoings.getAmount());
                }
                findTotalOutcome();
            }else{
                tableItems.addAll(outcomes);
                for (Outgoings outcomes : outcomes) {
                    totalOutcome += Double.parseDouble(outcomes.getAmount());
                }
                findTotalOutcome();
            }

        }else{
            totalOutcome = 0;// don't give permission to repeat
            if(listFromMost){
                for (Outgoings outcomes : outcomesFromMost) {
                    if(outcomes.getDate().isAfter(startProcessWhere) ||outcomes.getDate().equals(startProcessWhere)){
                        tableItems.add(outcomes);
                        totalOutcome += Double.parseDouble(outcomes.getAmount());
                    }
                }
            }else{

                for (Outgoings outgoings : outcomes) {
                    if(outgoings.getDate().isAfter(startProcessWhere) || outgoings.getDate().equals(startProcessWhere)){
                        tableItems.add(outgoings);
                        totalOutcome += Double.parseDouble(outgoings.getAmount());
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
