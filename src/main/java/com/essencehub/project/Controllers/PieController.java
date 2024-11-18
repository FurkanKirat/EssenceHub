package com.essencehub.project.Controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.layout.AnchorPane;

public class PieController {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private AreaChart<String, Number> areachart;

    @FXML
    private PieChart pieChart;

    @FXML
    private CategoryAxis xaxis;

    @FXML
    private NumberAxis yaxis;
    public void initialize() {
        addPieChartData();
        addLineChartData();
    }

    private void addPieChartData() {

        PieChart.Data slice1 = new PieChart.Data("Desktop PC", 5);
        PieChart.Data slice2 = new PieChart.Data("Laptop", 4);
        PieChart.Data slice3 = new PieChart.Data("Phone", 3);

        pieChart.getData().addAll(slice1, slice2, slice3);
    }
    private void addLineChartData(){

        // Creating the first data series
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Data Series 1");
        series1.getData().add(new XYChart.Data<>("Desktop PC", 5));
        series1.getData().add(new XYChart.Data<>("Laptop", 4));
        series1.getData().add(new XYChart.Data<>("Phone", 3));

        // Creating the second data series
       /* XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("Data Series 2");
        series2.getData().add(new XYChart.Data<>("1", 10));
        series2.getData().add(new XYChart.Data<>("2", 25));
        series2.getData().add(new XYChart.Data<>("3", 15));
        series2.getData().add(new XYChart.Data<>("4", 30));*/

        // Adding both series to the LineChart
        areachart.getData().add(series1);
        //areachart.getData().add(series2);
    }
}

