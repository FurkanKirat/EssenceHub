package com.essencehub.project.Controllers.Finance;

import com.essencehub.project.Controllers.Menu.AdminMenuController;
import com.essencehub.project.DatabaseOperations.DatabaseConnection;
import com.essencehub.project.Finance.Income;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
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
    private RadioButton lineChartButton;

    @FXML
    private RadioButton pieChartButton;

    @FXML
    private Button updateIncomeButton;

    @FXML
    private Button updateOutcomeButton;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;


    @FXML
    void isLineChartButtonSelected(ActionEvent event) {
        lineChart.setVisible(lineChart.isVisible());

        boolean isVisible = lineChart.isVisible();
        lineChart.setVisible(!isVisible);

        if(lineChart.isVisible()){
            XYChart.Series series = new XYChart.Series();
            for (Income income : incomes) {
                series.getData().add(new XYChart.Data(income.getDate().toString(), Double.parseDouble(income.getAmount())));
            }

            lineChart.getData().addAll(series);
        }
    }

    @FXML
    void isPieChartButtonSelected(ActionEvent event) {
        pieChart.setVisible(pieChart.isVisible());

        boolean isVisible = pieChart.isVisible();
        pieChart.setVisible(!isVisible);
        if(pieChart.isVisible()){
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

            // Calculate total income
            double totalIncome = incomes.stream()
                    .mapToDouble(income -> Double.parseDouble(income.getAmount()))
                    .sum();

            // Populate PieChart data
            for (Income income : incomes) {
                double amount = Double.parseDouble(income.getAmount());
                double percentage = (amount / totalIncome) * 100;

                pieChartData.add(new PieChart.Data(income.getTitle() + " (" + String.format("%.1f", percentage) + "%)", amount));
            }

            // Update the PieChart
            pieChart.setData(pieChartData);

        }
    }

    @FXML
    void isUpdateIncomeButtonClicked(ActionEvent event) {
        AdminMenuController adminMenuController = AdminMenuController.getInstance();
        adminMenuController.loadFXMLContent("/com/essencehub/project/fxml/Finance/UpdateIncome.fxml");
    }

    @FXML
    void isUpdateOutcomeButtonClicked(ActionEvent event) {
        AdminMenuController adminMenuController = AdminMenuController.getInstance();
        adminMenuController.loadFXMLContent("/com/essencehub/project/fxml/Finance/UpdateOutcome.fxml");
    }

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
    private LineChart<?, ?> lineChart;

    @FXML
    private PieChart pieChart;


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
        initializeChartToggles();
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
        lineChart.setVisible(false);
        pieChart.setVisible(false);

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
    private void initializeChartToggles() {
        ToggleGroup group = new ToggleGroup();
        pieChartButton.setToggleGroup(group);
        lineChartButton.setToggleGroup(group);
        lineChartButton.setSelected(true);
    }

}