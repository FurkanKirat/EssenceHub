package com.essencehub.project.Controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;

public class LineChartController {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private LineChart<Number, Number> lineChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    public void initialize() {
        // Configure the chart
        lineChart.setTitle("Sample Line Chart");
        xAxis.setLabel("X-Axis");
        yAxis.setLabel("Y-Axis");

        // Create a series of data
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Data Series 1");
        series.getData().add(new XYChart.Data<>(1, 2));
        series.getData().add(new XYChart.Data<>(2, 5));
        series.getData().add(new XYChart.Data<>(3, 4));
        series.getData().add(new XYChart.Data<>(4, 6));
        series.getData().add(new XYChart.Data<>(5, 3));

        // Add the series to the chart
        lineChart.getData().add(series);
    }
}
