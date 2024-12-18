package com.essencehub.project.Controllers.Finance;

import com.essencehub.project.Controllers.Menu.AdminMenuController;
import com.essencehub.project.Controllers.Settings.ThemeController;
import com.essencehub.project.DatabaseOperations.DatabaseConnection;
import com.essencehub.project.Finance.Outgoings;
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

public class OutgoingsController {

    @FXML
    private VBox expensesIconVBox;

    @FXML
    private VBox incomeIconVBox;

    @FXML
    private PieChart pieChart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @FXML
    private RadioButton pieChartButton;

    @FXML
    private RadioButton lineChartButton;

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
    private LineChart lineChart;

    @FXML
    private ComboBox<String> outcomeComboBox;

    @FXML
    private TableView<Outgoings> outcomeTableView;


    public ObservableList<Outgoings> outcomes;
    public ObservableList<Outgoings> outcomesForLineChart;
    public ObservableList<Outgoings> outcomesForPieChart;
    public ObservableList<Outgoings> outcomesFromMost = FXCollections.observableArrayList();
    private String[] timePeriods = {"Last Month", "Last 6 Months", "Last 1 Year", "All Outgoings"};
    private double totalOutcome = 0;
    private LocalDate ourDay;
    private LocalDate startProcessWhere;
    private int delay;
    private boolean listFromMost = false;


    @FXML
    void isAddOutcomeButtonClicked(ActionEvent event) {
        createNewScene("/com/essencehub/project/fxml/Finance/AddOutcome.fxml","Add Expenses",event);
    }

    @FXML
    void isUpdateOutcomeButtonClicked(ActionEvent event) {
        createNewScene("/com/essencehub/project/fxml/Finance/UpdateOutcome.fxml","Update Expenses",event);
    }

    @FXML
    void isLineChartButtonSelected(ActionEvent event) {


        if(outcomeComboBox.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty");
            alert.setHeaderText("Operation Failed");
            alert.setContentText("Choose a category first");
            alert.showAndWait();
            lineChartButton.setSelected(false);
            return;
        }
        outcomesForLineChart = getRidOfRepeatDate(outcomesForLineChart);
        lineChart.setVisible(lineChart.isVisible());

        boolean isVisible = lineChart.isVisible();
        lineChart.setVisible(!isVisible);

        if(lineChart.isVisible()){
            XYChart.Series series = new XYChart.Series();
            for (Outgoings outcome : outcomesForLineChart) {
                series.getData().add(new XYChart.Data(outcome.getDate().toString(), Double.parseDouble(outcome.getCost())));
            }

            lineChart.getData().addAll(series);
        }
    }

    public ObservableList<Outgoings> getRidOfRepeatDate(ObservableList<Outgoings> outcomes) {
        Map<LocalDate, Double> dateToAmount = new HashMap<>();

        // Aggregate amounts by date only
        for (Outgoings out : outcomes) {
            LocalDate date = out.getDate();
            double cost = Double.parseDouble(out.getCost());
            dateToAmount.put(date, dateToAmount.getOrDefault(date, 0.0) + cost);
        }

        // Create the final list, one entry per date
        ObservableList<Outgoings> aggregated = FXCollections.observableArrayList();
        for (Map.Entry<LocalDate, Double> entry : dateToAmount.entrySet()) {
            LocalDate date = entry.getKey();
            String costStr = String.valueOf(entry.getValue());
            // Choose a generic title or modify as needed
            aggregated.add(new Outgoings(date, "Aggregated", costStr));
        }

        return aggregated;
    }

    @FXML
    void isPieChartButtonSelected(ActionEvent event) {
        if(outcomeComboBox.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty");
            alert.setHeaderText("Operation Failed");
            alert.setContentText("Choose a category first");
            alert.showAndWait();
            pieChartButton.setSelected(false);
            return;
        }
        outcomesForPieChart = getRidOfRepeatTitle(outcomesForPieChart);
        pieChart.setVisible(pieChart.isVisible());

        boolean isVisible = pieChart.isVisible();
        pieChart.setVisible(!isVisible);
        if(pieChart.isVisible()){
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

            // Populate PieChart data
            for (Outgoings outgoing : outcomesForPieChart) {
                double cost = Double.parseDouble(outgoing.getCost());
                double percentage = (cost / totalOutcome) * 100;

                pieChartData.add(new PieChart.Data(outgoing.getTitle() + " (" + String.format("%.1f", percentage) + "%)", cost));
            }

            // Update the PieChart
            pieChart.setData(pieChartData);

        }
    }

    public ObservableList<Outgoings> getRidOfRepeatTitle(ObservableList<Outgoings> outcomes1) {
        Map<String, Double> titleToCost = new HashMap<>();
        Map<String, LocalDate> titleToDate = new HashMap<>();

        // Aggregate amounts by title and track the earliest date
        for (Outgoings out : outcomes1) {
            String title = out.getTitle();
            double newAmount = titleToCost.getOrDefault(title, 0.0) + Double.parseDouble(out.getCost());
            titleToCost.put(title, newAmount);

            // If it's the first time we see this title or if we found an earlier date, update the stored date
            if (!titleToDate.containsKey(title) || out.getDate().isBefore(titleToDate.get(title))) {
                titleToDate.put(title, out.getDate());
            }

        }

        // Create the final list with combined entries
        ObservableList<Outgoings> outgoingsReturn = FXCollections.observableArrayList();
        for (Map.Entry<String, Double> entry : titleToCost.entrySet()) {
            String title = entry.getKey();
            String cost = entry.getValue() + "";
            LocalDate date = titleToDate.get(title);

            outgoingsReturn.add(new Outgoings(date, title, cost));
        }
        return outgoingsReturn;
    }

    public ObservableList<Outgoings> getOutcomeList() {
        ObservableList<Outgoings> outgoings = FXCollections.observableArrayList();
        String sql = "SELECT date, title, cost FROM Outcome";

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
        if(outcomeComboBox.getValue() == null){
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

    @FXML
    void outcomeComboBoxIsSelected(ActionEvent event) {
        pieChartButton.setSelected(false);
        lineChartButton.setSelected(false);
        pieChart.setVisible(false);
        lineChart.setVisible(false);
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
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
        outcomes = getOutcomeList();
        sortOutcomesByCost();// sorting alg
        findTotalOutcome();

        lineChart.setVisible(false);
        pieChart.setVisible(false);
    }

    private void findTotalOutcome(){
        outcomeField.setText(totalOutcome + "");

    }

    private void fillTable(){
        outcomesForPieChart =  FXCollections.observableArrayList();
        outcomesForLineChart =  FXCollections.observableArrayList();

        outcomeTableView.getItems().clear();
        totalOutcome = 0;
        ObservableList<Outgoings> tableItems = outcomeTableView.getItems();

        if(ourDay.equals(startProcessWhere)){
            outcomesForPieChart.addAll(outcomes);
            outcomesForLineChart.addAll(outcomes);
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
                        outcomesForPieChart.add(outcomes);
                        outcomesForLineChart.add(outcomes);
                        totalOutcome += Double.parseDouble(outcomes.getCost());
                    }
                }
            }else{

                for (Outgoings outgoings : outcomes) {
                    if(outgoings.getDate().isAfter(startProcessWhere) || outgoings.getDate().equals(startProcessWhere)){
                        tableItems.add(outgoings);
                        outcomesForPieChart.add(outgoings);
                        outcomesForLineChart.add(outgoings);
                        totalOutcome += Double.parseDouble(outgoings.getCost());
                    }
                }
            }

            findTotalOutcome();
        }
    }

    public void sortOutcomesByCost() {
        outcomesFromMost.clear();
        outcomesFromMost.addAll(outcomes);
        outcomesFromMost.sort((i1, i2) -> {
            double cost1 = Double.parseDouble(i1.getCost());
            double cost2 = Double.parseDouble(i2.getCost());
            return Double.compare(cost2, cost1);
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
