package com.essencehub.project.Controllers.StockTracking;

import com.essencehub.project.Controllers.Menu.LoginPageController;
import com.essencehub.project.DatabaseOperations.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.chart.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PieChartController {


    @FXML
    private PieChart pieChart;

    ResultSet resultset;

    @FXML
    public void initialize() {

        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            resultset = statement.executeQuery("SELECT * FROM stock");
            //XYChart.Series<String, Number> series1 = new XYChart.Series<>();

           // series1.setName("Products");
//            xaxis.setLabel("Products");
//            yaxis.setLabel("Count");
            while(resultset.next()){
                //series1.getData().add(new XYChart.Data<>(resultset.getString("name"), resultset.getInt("count")));

                pieChart.getData().add(new PieChart.Data(resultset.getString("name"), resultset.getInt("count")));

            }
            //areachart.getData().add(series1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
}

