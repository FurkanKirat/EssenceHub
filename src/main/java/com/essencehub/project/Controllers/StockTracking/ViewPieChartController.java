package com.essencehub.project.Controllers.StockTracking;

import com.essencehub.project.DatabaseOperations.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class ViewPieChartController {

    @FXML
    private VBox chartVBox;

    @FXML
    private PieChart pieChart;

    @FXML
    private ComboBox<String> timeRangeCombobox;

    ResultSet resultset;

    public void initialize() {
        initializeTimeRangeComboBox();

        timeRangeCombobox.valueProperty().addListener((observable, oldValue, newValue) -> updatePieChart(newValue));

        updatePieChart("All");
    }

    private void initializeTimeRangeComboBox() {
        timeRangeCombobox.getItems().add("Last 6 Months");
        timeRangeCombobox.getItems().add("Last 1 Year");
        timeRangeCombobox.getItems().add("Last 5 Years");
        timeRangeCombobox.getItems().add("All");
        timeRangeCombobox.setValue("All");
    }

    private void updatePieChart(String selectedTimeRange) {
        pieChart.getData().clear();

        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();

            String query = "SELECT name, SUM(count) AS totalCount FROM Stock WHERE 1=1";

            LocalDate currentDate = LocalDate.now();
            if (selectedTimeRange.equals("Last 6 Months")) {
                query += " AND buyingDate > '" + currentDate.minusMonths(6) + "'";
            } else if (selectedTimeRange.equals("Last 1 Year")) {
                query += " AND buyingDate > '" + currentDate.minusYears(1) + "'";
            } else if (selectedTimeRange.equals("Last 5 Years")) {
                query += " AND buyingDate > '" + currentDate.minusYears(5) + "'";
            }

            query += " GROUP BY name"; // Example = 5 laptop + 5 laptop = 10 laptop

            resultset = statement.executeQuery(query);


            while (resultset.next()) {
                String productName = resultset.getString("name");
                int totalStockCount = resultset.getInt("totalCount");

                pieChart.getData().add(new PieChart.Data(productName, totalStockCount));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching data: " + e.getMessage());
        }
    }
}