package com.essencehub.project.Controllers.StockTracking;

import com.essencehub.project.DatabaseOperations.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class BarChartController {

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private ComboBox<String> timeRangeComboBox;

    public void initialize() {
        initializeTimeRangeComboBox();

        timeRangeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> updateBarChart(newValue));

        updateBarChart("All");
    }

    private void initializeTimeRangeComboBox() {
        timeRangeComboBox.getItems().addAll("Last 6 Months", "Last 1 Year", "Last 5 Years", "All");
        timeRangeComboBox.setValue("All");
    }

    private void updateBarChart(String selectedTimeRange) {
        barChart.getData().clear();

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

            query += " GROUP BY name";

            ResultSet resultSet = statement.executeQuery(query);

            XYChart.Series<String, Number> series = new XYChart.Series<>();

            while (resultSet.next()) {
                String productName = resultSet.getString("name");
                int totalStockCount = resultSet.getInt("totalCount");

                series.getData().add(new XYChart.Data<>(productName, totalStockCount));
            }

            barChart.getData().add(series);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching data: " + e.getMessage());
        }
    }
}