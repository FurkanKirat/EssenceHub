package com.essencehub.project.Controllers.StockTracking;

import com.essencehub.project.DatabaseOperations.DatabaseConnection;
import com.essencehub.project.Stock.Product;
import com.essencehub.project.User.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StockController {

    @FXML
    private TableColumn<?, ?> dateColumn;

    @FXML
    private VBox func;

    @FXML
    private LineChart<Product, String> lineChart;

    @FXML
    private NumberAxis numberAxis;

    @FXML
    private CategoryAxis productDateAxis;

    @FXML
    private TableColumn<Product, String> productNameColumn;

    @FXML
    private TableColumn<Product, String > stockColumn;

    @FXML
    private TableView<Product> stockView;

    @FXML
    private ComboBox<?> yearCombobox;
    ResultSet resultset;

    public void initialize(){
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("monthAndYear"));
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

                stockView.getItems().add(new Product(resultset.getString("name"),resultset.getInt("count"),resultset.getString("month_and_year") ));

            }
            //areachart.getData().add(series1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
