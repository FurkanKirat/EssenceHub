package com.essencehub.project.Controllers.StockTracking;

import com.essencehub.project.DatabaseOperations.DatabaseConnection;
import com.essencehub.project.Stock.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class StockController {

    @FXML
    private TableColumn<Product, String> productNameColumn;

    @FXML
    private TableColumn<Product, Integer> stockColumn;

    @FXML
    private TableColumn<Product, LocalDate> dateColumn;

    @FXML
    private TableView<Product> stockView;

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private NumberAxis numberAxis;

    @FXML
    private CategoryAxis productDateAxis;

    @FXML
    private ComboBox<String> productCombobox;

    @FXML
    private ComboBox<String> timeRangeCombobox; // New ComboBox for time range

    @FXML
    private VBox func;

    private ObservableList<Product> productList = FXCollections.observableArrayList();

    public void initialize() {

        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("buyingDate"));

        loadStockData();

        initializeProductComboBox();
        initializeTimeRangeComboBox();

        productCombobox.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateLineChart(newValue, timeRangeCombobox.getValue());
            updateTableView(newValue, timeRangeCombobox.getValue());
        });

        timeRangeCombobox.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateLineChart(productCombobox.getValue(), newValue);
            updateTableView(productCombobox.getValue(), newValue);
        });
    }

    private void loadStockData() {
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM stock")) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int count = resultSet.getInt("count");
                LocalDate buyingDate = resultSet.getDate("buyingDate").toLocalDate();

                productList.add(new Product(name, count, buyingDate, null, 0, 0)); // Placeholder for sellingDate, prices
            }

            stockView.setItems(productList);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading stock data: " + e.getMessage());
        }
    }

    private void initializeProductComboBox() {
        ObservableList<String> uniqueProducts = FXCollections.observableArrayList();

        uniqueProducts.add("All");

        int index = 0;

        while (index < productList.size()) {
            Product product = productList.get(index);
            String productName = product.getName();

            if (!uniqueProducts.contains(productName)) {
                uniqueProducts.add(productName);
            }

            index++;
        }

        productCombobox.setItems(uniqueProducts);
        productCombobox.setValue("All");
    }

    private void initializeTimeRangeComboBox() {
        ObservableList<String> timeRanges = FXCollections.observableArrayList(
                "Last 6 Months", "Last 1 Year", "Last 5 Years", "All"
        );

        timeRangeCombobox.setItems(timeRanges);
        timeRangeCombobox.setValue("All");
    }

    private void updateLineChart(String selectedProduct, String selectedTimeRange) {
        lineChart.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(selectedProduct.equals("All") ? "All Products" : "Stock for " + selectedProduct);

        int index = 0;
        LocalDate currentDate = LocalDate.now();

        while (index < productList.size()) {
            Product product = productList.get(index);

            if (selectedProduct.equals("All") || product.getName().equals(selectedProduct)) {

                boolean withinTimeRange = false;
                switch (selectedTimeRange) {
                    case "Last 6 Months":
                        withinTimeRange = product.getBuyingDate().isAfter(currentDate.minusMonths(6));
                        break;
                    case "Last 1 Year":
                        withinTimeRange = product.getBuyingDate().isAfter(currentDate.minusYears(1));
                        break;
                    case "Last 5 Years":
                        withinTimeRange = product.getBuyingDate().isAfter(currentDate.minusYears(5));
                        break;
                    case "All":
                        withinTimeRange = true;
                        break;
                }

                if (withinTimeRange) {
                    String productName = product.getName();
                    int stockCount = product.getCount();
                    series.getData().add(new XYChart.Data<>(productName, stockCount));
                }
            }

            index++;
        }

        lineChart.getData().add(series);
    }

    private void updateTableView(String selectedProduct, String selectedTimeRange) {
        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

        int index = 0;
        LocalDate currentDate = LocalDate.now();

        while (index < productList.size()) {
            Product product = productList.get(index);

            if (selectedProduct.equals("All") || product.getName().equals(selectedProduct)) {

                boolean withinTimeRange = false;
                switch (selectedTimeRange) {
                    case "Last 6 Months":
                        withinTimeRange = product.getBuyingDate().isAfter(currentDate.minusMonths(6));
                        break;
                    case "Last 1 Year":
                        withinTimeRange = product.getBuyingDate().isAfter(currentDate.minusYears(1));
                        break;
                    case "Last 5 Years":
                        withinTimeRange = product.getBuyingDate().isAfter(currentDate.minusYears(5));
                        break;
                    case "All":
                        withinTimeRange = true;
                        break;
                }

                if (withinTimeRange) {
                    filteredProducts.add(product);
                }
            }

            index++;
        }

        stockView.setItems(filteredProducts);
    }
}
