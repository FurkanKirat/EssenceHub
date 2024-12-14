package com.essencehub.project.Controllers.Finance;

import com.essencehub.project.Controllers.Menu.AdminMenuController;
import com.essencehub.project.Finance.Income;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class IncomeController {
    // will be deleted, for debug purposes
    public ObservableList<Income> getIncomeList() {
        return FXCollections.observableArrayList(
                new Income(LocalDate.of(2024, 1, 15), "January Salary", "5000"),
                new Income(LocalDate.of(2024, 2, 10), "Freelance Project", "1500"),
                new Income(LocalDate.of(2024, 3, 20), "Bonus", "2000"),
                new Income(LocalDate.of(2024, 4, 18), "Investment Return", "300"),
                new Income(LocalDate.of(2024, 5, 25), "May Salary", "5000"),
                new Income(LocalDate.of(2024, 6, 10), "Freelance Project", "1200"),
                new Income(LocalDate.of(2024, 7, 12), "July Dividend", "800"),
                new Income(LocalDate.of(2024, 8, 15), "August Salary", "5000"),
                new Income(LocalDate.of(2024, 9, 5), "Consulting Fee", "2500"),
                new Income(LocalDate.of(2024, 10, 20), "October Bonus", "1500"),
                new Income(LocalDate.of(2024, 11, 10), "Freelance Work", "1700"),
                new Income(LocalDate.of(2024, 12, 25), "December Gift", "500")
        );
    }
    public ObservableList<Income> incomes;
    public ObservableList<Income> incomesFromMost = FXCollections.observableArrayList();
    private String[] timePeriods = {"Last Month", "Last 6 Months", "Last 1 Year", "All Incomes"};
    private double totalIncome = 0;
    private LocalDate ourDay;
    private LocalDate startProcessWhere;
    private int delay;
    private boolean listFromMost = false;


    @FXML
    private VBox expensesIconVBox;

    @FXML
    private VBox incomeIconVBox;

    @FXML
    private TableColumn<Income, String> amountColumn;

    @FXML
    private TableColumn<Income, LocalDate> dateColumn;

    @FXML
    private TableColumn<Income, String> titleColumn;

    @FXML
    private ComboBox<String> incomeComboBox;
    @FXML
    private VBox func;
    @FXML
    private TextField incomeField;

    @FXML
    private TableView<Income> incomeTableView;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private RadioButton theMostButton = new RadioButton();

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
    void isTheMostClicked(ActionEvent event) {
        listFromMost = theMostButton.isSelected();
        fillTable();
    }

    @FXML
    void incomeComboBoxIsSelected(ActionEvent event) {
        switch (incomeComboBox.getValue()){
            case "Last Month":
                delay = 1;
                startProcessWhere = ourDay.minusMonths(delay);
                fillTable();
                break;
            case "Last 6 Months":
                delay = 6;
                startProcessWhere = ourDay.minusMonths(delay);
                fillTable();
                break;
            case "Last 1 Year":
                delay = 12;
                startProcessWhere = ourDay.minusMonths(delay);
                fillTable();
                break;
            case "All Incomes":
                startProcessWhere = ourDay;
                fillTable();
                break;
        }
    }

    @FXML
    private void initialize() {
        incomeComboBox.getItems().clear();
        incomeComboBox.getItems().addAll(timePeriods);

        incomeField.setText("" + totalIncome);
        ourDay = LocalDate.now();

        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        incomes = getIncomeList();
        sortIncomesByAmount();// sorting alg

        findTotalIncome();
    }

    private void findTotalIncome(){
        incomeField.setText(totalIncome + "");

    }

    private void fillTable(){
        incomeTableView.getItems().clear();
        totalIncome = 0;
        ObservableList<Income> tableItems = incomeTableView.getItems();

        if(ourDay.equals(startProcessWhere)){
            if(listFromMost){

                tableItems.addAll(incomesFromMost);
                for (Income income : incomesFromMost) {
                    totalIncome += Double.parseDouble(income.getAmount());
                }
                findTotalIncome();
            }else{
                tableItems.addAll(incomes);
                for (Income income : incomes) {
                    totalIncome += Double.parseDouble(income.getAmount());
                }
                findTotalIncome();
            }

        }else{
            totalIncome = 0;// don't give permission to repeat
            if(listFromMost){
                for (Income income : incomesFromMost) {
                    if(income.getDate().isAfter(startProcessWhere) ||income.getDate().equals(startProcessWhere)){
                        tableItems.add(income);
                        totalIncome += Double.parseDouble(income.getAmount());
                    }
                }
            }else{

                for (Income income : incomes) {
                    if(income.getDate().isAfter(startProcessWhere) ||income.getDate().equals(startProcessWhere)){
                        tableItems.add(income);
                        totalIncome += Double.parseDouble(income.getAmount());
                    }
                }
            }

            findTotalIncome();
        }
    }

    public void sortIncomesByAmount() {
        incomesFromMost.clear();
        incomesFromMost.addAll(incomes);
        incomesFromMost.sort((i1, i2) -> {
            double amount1 = Double.parseDouble(i1.getAmount());
            double amount2 = Double.parseDouble(i2.getAmount());
            return Double.compare(amount2, amount1);
        });
    }
}