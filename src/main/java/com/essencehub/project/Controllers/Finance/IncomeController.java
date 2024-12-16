package com.essencehub.project.Controllers.Finance;

import com.essencehub.project.Controllers.Menu.AdminMenuController;
import com.essencehub.project.DatabaseOperations.DatabaseConnection;
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class IncomeController {
    // will be deleted, for debug purposes
    public ObservableList<Income> getIncomeList() {
        ObservableList<Income> incomes = FXCollections.observableArrayList();
        String sql = "SELECT date, title, amount FROM Income";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                LocalDate date = resultSet.getDate("date").toLocalDate();
                String title = resultSet.getString("title");
                String amount = resultSet.getString("amount");

                Income income = new Income(date, title, amount);

                incomes.add(income); // Add the user to the ObservableList
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return incomes;
    }
    @FXML
    private Button addIncomeButton;

    @FXML
    private Button addOutcomeButton;
    @FXML
    void isAddButtonClicked(ActionEvent event) {
        AdminMenuController adminMenuController = AdminMenuController.getInstance();
        adminMenuController.loadFXMLContent("/com/essencehub/project/fxml/Finance/AddIncome.fxml");
    }

    @FXML
    void isAddOutcomeButtonClicked(ActionEvent event) {
        AdminMenuController adminMenuController = AdminMenuController.getInstance();
        adminMenuController.loadFXMLContent("/com/essencehub/project/fxml/Finance/AddOutcome.fxml");
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
        sortIncomesByDate();
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
    public void sortIncomesByDate() {
        incomes.sort((i1, i2) -> {
            LocalDate date1 = i1.getDate();
            LocalDate date2 = i2.getDate();
            return date2.compareTo(date1);
        });
    }

}