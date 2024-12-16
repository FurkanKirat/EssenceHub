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
    private ComboBox<String> timeRangeCombobox;

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
             ResultSet resultSet = statement.executeQuery("SELECT name, SUM(count) as total_count, MAX(buyingDate) as latest_date, MAX(sellingDate) as latest_selling_date FROM stock GROUP BY name")) {

            productList.clear(); // Clear previous data

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int totalCount = resultSet.getInt("total_count");
                LocalDate latestDate = resultSet.getDate("latest_date").toLocalDate();
                LocalDate latestSellingDate = resultSet.getDate("latest_selling_date") != null ? resultSet.getDate("latest_selling_date").toLocalDate() : null; // Latest selling date

                productList.add(new Product(name, totalCount, latestDate, latestSellingDate, 0, 0));
            }

            stockView.setItems(productList); // Update the TableView with the aggregated data

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading stock data: " + e.getMessage());
        }
    }


    private void initializeProductComboBox() {
        ObservableList<String> uniqueProducts = FXCollections.observableArrayList();

        uniqueProducts.add("All");

        // Add unique product names to ComboBox
        for (Product product : productList) {
            String productName = product.getName();
            if (!uniqueProducts.contains(productName)) {
                uniqueProducts.add(productName);
            }
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

        LocalDate currentDate = LocalDate.now();


        for (Product product : productList) {
            if (selectedProduct.equals("All") || product.getName().equals(selectedProduct)) {

                boolean withinTimeRange = false;
                switch (selectedTimeRange) {
                    case "Last 6 Months":
                        withinTimeRange = product.getBuyingDate().isAfter(currentDate.minusMonths(6)) && (product.getSellingDate() == null || product.getSellingDate().isAfter(currentDate.minusMonths(6)));
                        break;
                    case "Last 1 Year":
                        withinTimeRange = product.getBuyingDate().isAfter(currentDate.minusYears(1)) && (product.getSellingDate() == null || product.getSellingDate().isAfter(currentDate.minusYears(1)));
                        break;
                    case "Last 5 Years":
                        withinTimeRange = product.getBuyingDate().isAfter(currentDate.minusYears(5)) && (product.getSellingDate() == null || product.getSellingDate().isAfter(currentDate.minusYears(5)));
                        break;
                    case "All":
                        withinTimeRange = true;
                        break;
                }

                if (withinTimeRange) {
                    series.getData().add(new XYChart.Data<>(product.getName(), product.getCount()));
                }
            }
        }

        lineChart.getData().add(series);
    }


    private void updateTableView(String selectedProduct, String selectedTimeRange) {
        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

        LocalDate currentDate = LocalDate.now();


        for (Product product : productList) {
            if (selectedProduct.equals("All") || product.getName().equals(selectedProduct)) {

                boolean withinTimeRange = false;
                switch (selectedTimeRange) {
                    case "Last 6 Months":
                        withinTimeRange = product.getBuyingDate().isAfter(currentDate.minusMonths(6)) && (product.getSellingDate() == null || product.getSellingDate().isAfter(currentDate.minusMonths(6)));
                        break;
                    case "Last 1 Year":
                        withinTimeRange = product.getBuyingDate().isAfter(currentDate.minusYears(1)) && (product.getSellingDate() == null || product.getSellingDate().isAfter(currentDate.minusYears(1)));
                        break;
                    case "Last 5 Years":
                        withinTimeRange = product.getBuyingDate().isAfter(currentDate.minusYears(5)) && (product.getSellingDate() == null || product.getSellingDate().isAfter(currentDate.minusYears(5)));
                        break;
                    case "All":
                        withinTimeRange = true;
                        break;
                }

                if (withinTimeRange) {
                    filteredProducts.add(product);
                }
            }
        }

        stockView.setItems(filteredProducts);
    }
}