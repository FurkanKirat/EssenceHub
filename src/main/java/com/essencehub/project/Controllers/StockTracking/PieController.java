package com.essencehub.project.Controllers.StockTracking;

import com.essencehub.project.Controllers.Menu.LoginPageController;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.layout.HBox;

import java.sql.ResultSet;
import java.sql.SQLException;

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
    ResultSet resultset;
    public void initialize() {

        try {
            resultset = LoginPageController.getStatement().executeQuery("SELECT * FROM stock");
            XYChart.Series<String, Number> series1 = new XYChart.Series<>();

            series1.setName("Products");
            xaxis.setLabel("Products");
            yaxis.setLabel("Count");
            while(resultset.next()){
                series1.getData().add(new XYChart.Data<>(resultset.getString("name"), resultset.getInt("count")));
                pieChart.getData().add(new PieChart.Data(resultset.getString("name"), resultset.getInt("count")));

            }
            areachart.getData().add(series1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
}

