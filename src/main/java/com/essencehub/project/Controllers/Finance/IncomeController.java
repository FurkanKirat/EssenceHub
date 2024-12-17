package com.essencehub.project.Controllers.Finance;

import com.essencehub.project.Controllers.Menu.AdminMenuController;
import com.essencehub.project.Controllers.Settings.ThemeController;
import com.essencehub.project.DatabaseOperations.DatabaseConnection;
import com.essencehub.project.Finance.Income;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

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

    public ObservableList<Income> incomes;
    public ObservableList<Income> incomesForPieChart =  FXCollections.observableArrayList();
    public ObservableList<Income> incomesForLineChart =  FXCollections.observableArrayList();
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
    void isLineChartButtonSelected(ActionEvent event) {
        incomesForLineChart = getRidOfRepeatDate(incomesForLineChart);
        if(incomeComboBox.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty");
            alert.setHeaderText("Operation Failed");
            alert.setContentText("Choose a category first");
            alert.showAndWait();
            lineChartButton.setSelected(false);
            return;
        }
        lineChart.setVisible(lineChart.isVisible());

        boolean isVisible = lineChart.isVisible();
        lineChart.setVisible(!isVisible);

        if(lineChart.isVisible()){
            XYChart.Series series = new XYChart.Series();
            for (Income income : incomesForLineChart) {
                series.getData().add(new XYChart.Data(income.getDate().toString(), Double.parseDouble(income.getAmount())));
            }

            lineChart.getData().addAll(series);
        }
    }

    public ObservableList<Income> getRidOfRepeatDate(ObservableList<Income> incomes) {
        Map<LocalDate, Double> dateToAmount = new HashMap<>();

        // Sum all amounts by date only
        for (Income inc : incomes) {
            LocalDate date = inc.getDate();
            double amount = Double.parseDouble(inc.getAmount());
            dateToAmount.put(date, dateToAmount.getOrDefault(date, 0.0) + amount);
        }

        // Create a new list with one Income per date, combined amount
        ObservableList<Income> aggregated = FXCollections.observableArrayList();
        for (Map.Entry<LocalDate, Double> entry : dateToAmount.entrySet()) {
            LocalDate date = entry.getKey();
            String amountStr = String.valueOf(entry.getValue());
            // Use a generic title or adjust as needed
            aggregated.add(new Income(date, "Aggregated", amountStr));
        }

        return aggregated;
    }

    @FXML
    void isPieChartButtonSelected(ActionEvent event) {
        incomesForPieChart = getRidOfRepeatTitle(incomesForPieChart);
        if(incomeComboBox.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty");
            alert.setHeaderText("Operation Failed");
            alert.setContentText("Choose a category first");
            alert.showAndWait();
            pieChartButton.setSelected(false);
            return;
        }
        pieChart.setVisible(pieChart.isVisible());

        boolean isVisible = pieChart.isVisible();
        pieChart.setVisible(!isVisible);
        if(pieChart.isVisible()){
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

            // Populate PieChart data
            for (Income income : incomesForPieChart) {
                double amount = Double.parseDouble(income.getAmount());
                double percentage = (amount / totalIncome) * 100;

                pieChartData.add(new PieChart.Data(income.getTitle() + " (" + String.format("%.1f", percentage) + "%)", amount));
            }

            // Update the PieChart
            pieChart.setData(pieChartData);

        }
    }

    public ObservableList<Income> getRidOfRepeatTitle(ObservableList<Income> incomes1) {
        Map<String, Double> titleToAmount = new HashMap<>();
        Map<String, LocalDate> titleToDate = new HashMap<>();

        // Aggregate amounts by title and track the earliest date
        for (Income inc : incomes1) {
            String title = inc.getTitle();
            double newAmount = titleToAmount.getOrDefault(title, 0.0) + Double.parseDouble(inc.getAmount());
            titleToAmount.put(title, newAmount);

            // If it's the first time we see this title or if we found an earlier date, update the stored date
            if (!titleToDate.containsKey(title) || inc.getDate().isBefore(titleToDate.get(title))) {
                titleToDate.put(title, inc.getDate());
            }

        }

        // Create the final list with combined entries
        ObservableList<Income> incomesReturn = FXCollections.observableArrayList();
        for (Map.Entry<String, Double> entry : titleToAmount.entrySet()) {
            String title = entry.getKey();
            String amount = entry.getValue() + "";
            LocalDate date = titleToDate.get(title);

            incomesReturn.add(new Income(date, title, amount));
        }
        return incomesReturn;

    }

    @FXML
    void isUpdateIncomeButtonClicked(ActionEvent event) {
        createNewScene("/com/essencehub/project/fxml/Finance/UpdateIncome.fxml","Update Income",event);
    }

    @FXML
    void isAddButtonClicked(ActionEvent event) {
        createNewScene("/com/essencehub/project/fxml/Finance/AddIncome.fxml","Add Income",event);
    }

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
        if(incomeComboBox.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty");
            alert.setHeaderText("Operation Failed");
            alert.setContentText("Choose a category first");
            alert.showAndWait();
            theMostButton.setSelected(false);
            return;
        }
        listFromMost = theMostButton.isSelected();
        fillTable();
    }

    //
    @FXML
    void incomeComboBoxIsSelected(ActionEvent event) {
        pieChartButton.setSelected(false);
        lineChartButton.setSelected(false);
        pieChart.setVisible(false);
        lineChart.setVisible(false);
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
        lineChart.setVisible(false);
        pieChart.setVisible(false);

    }

    private void findTotalIncome(){
        incomeField.setText(totalIncome + "");
    }

    private void fillTable(){
        incomesForPieChart =  FXCollections.observableArrayList();
        incomesForLineChart =  FXCollections.observableArrayList();

        incomeTableView.getItems().clear();
        totalIncome = 0;
        ObservableList<Income> tableItems = incomeTableView.getItems();

        if(ourDay.equals(startProcessWhere)){
            incomesForPieChart.addAll(incomes);
            incomesForLineChart.addAll(incomes);
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
            }

        }else{
            if(listFromMost){
                for (Income income : incomesFromMost) {
                    if(income.getDate().isAfter(startProcessWhere) ||income.getDate().equals(startProcessWhere)){
                        tableItems.add(income);
                        incomesForPieChart.add(income);
                        incomesForLineChart.add(income);
                        totalIncome += Double.parseDouble(income.getAmount());
                    }
                }
            }else{

                for (Income income : incomes) {
                    if(income.getDate().isAfter(startProcessWhere) ||income.getDate().equals(startProcessWhere)){
                        tableItems.add(income);
                        incomesForPieChart.add(income);
                        incomesForLineChart.add(income);
                        totalIncome += Double.parseDouble(income.getAmount());
                    }
                }
            }
        }
        findTotalIncome();
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

    void createNewScene(String FXMLFile,String title, Event event){
        try{
            Stage stage = new Stage();
            stage.getIcons().add(new Image( getClass().getResourceAsStream( "/com/essencehub/project/images/logo.jpg" )));
            Parent root = FXMLLoader.load(getClass().getResource(FXMLFile));

            Scene scene = new Scene(root);
            Stage parentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.initOwner(parentStage);
            stage.initModality(Modality.WINDOW_MODAL);

            ThemeController.changeTheme(scene);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}