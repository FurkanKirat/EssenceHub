package com.essencehub.project.Controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class PieController {


    @FXML
    private AreaChart<String, Number> areachart;

    @FXML
    private PieChart pieChart;

    @FXML
    private CategoryAxis xaxis;

    @FXML
    private NumberAxis yaxis;

    @FXML
    private HBox hbox;

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

        areachart.getData().add(series1);

    }
}

