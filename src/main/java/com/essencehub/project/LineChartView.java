package com.essencehub.project;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class LineChartView {

    public LineChart<Number, Number> createLineChart() {
        // Defining the x-axis and y-axis
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("X Axis");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Y Axis");

        // Creating the LineChart with the defined axes
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Sample Line Chart");

        // Creating the first data series
        XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
        series1.setName("Data Series 1");
        series1.getData().add(new XYChart.Data<>(1, 5));
        series1.getData().add(new XYChart.Data<>(2, 15));
        series1.getData().add(new XYChart.Data<>(3, 10));
        series1.getData().add(new XYChart.Data<>(4, 20));

        // Creating the second data series
        XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
        series2.setName("Data Series 2");
        series2.getData().add(new XYChart.Data<>(1, 10));
        series2.getData().add(new XYChart.Data<>(2, 25));
        series2.getData().add(new XYChart.Data<>(3, 15));
        series2.getData().add(new XYChart.Data<>(4, 30));

        // Adding both series to the LineChart
        lineChart.getData().add(series1);
        lineChart.getData().add(series2);

        return lineChart;
    }
}
