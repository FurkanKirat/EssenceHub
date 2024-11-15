package com.essencehub.project.Controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;

public class PieController {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private PieChart pieChart;
    public void initialize() {
        addPieChartData();
    }

    private void addPieChartData() {

        PieChart.Data slice1 = new PieChart.Data("Desktop PC", 5);
        PieChart.Data slice2 = new PieChart.Data("Laptop", 4);
        PieChart.Data slice3 = new PieChart.Data("Phone", 3);

        pieChart.getData().addAll(slice1, slice2, slice3);
    }

}
