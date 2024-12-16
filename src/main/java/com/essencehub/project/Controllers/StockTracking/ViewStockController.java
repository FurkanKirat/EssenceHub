package com.essencehub.project.Controllers.StockTracking;

import com.essencehub.project.Stock.Product;
import com.essencehub.project.DatabaseOperations.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class ViewStockController {

    @FXML
    private ComboBox<String> tableViewCombo;

    @FXML
    private TableColumn<Product, String> productNameColumn;

    @FXML
    private TableColumn<Product, Integer> stockColumn;

    @FXML
    private TableColumn<Product, LocalDate> buyingDateColumn;

    @FXML
    private TableColumn<Product, LocalDate> sellingDateColumn;

    @FXML
    private TableView<Product> stockView;

    public void initialize() {
        initializeTableViewCombo();
        initializeTableColumns();

        tableViewCombo.valueProperty().addListener((observable, oldValue, newValue) -> updateTableView(newValue));
        updateTableView("All");
    }

    private void initializeTableViewCombo() {
        tableViewCombo.getItems().addAll("Last 6 Months", "Last 1 Year", "Last 5 Years", "All");
        tableViewCombo.setValue("All");
    }

    private void initializeTableColumns() {
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        buyingDateColumn.setCellValueFactory(new PropertyValueFactory<>("buyingDate"));
        sellingDateColumn.setCellValueFactory(new PropertyValueFactory<>("sellingDate"));
    }

    private void updateTableView(String selectedTimeRange) {
        stockView.getItems().clear();

        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();

            String query = "SELECT name, count, buyingDate, sellingDate FROM Stock WHERE 1=1";

            LocalDate currentDate = LocalDate.now();
            if (selectedTimeRange.equals("Last 6 Months")) {
                query += " AND buyingDate > '" + currentDate.minusMonths(6) + "'";
            } else if (selectedTimeRange.equals("Last 1 Year")) {
                query += " AND buyingDate > '" + currentDate.minusYears(1) + "'";
            } else if (selectedTimeRange.equals("Last 5 Years")) {
                query += " AND buyingDate > '" + currentDate.minusYears(5) + "'";
            }

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int count = resultSet.getInt("count");
                LocalDate buyingDate = resultSet.getDate("buyingDate").toLocalDate();
                LocalDate sellingDate = resultSet.getDate("sellingDate") != null
                        ? resultSet.getDate("sellingDate").toLocalDate()
                        : null;

                stockView.getItems().add(new Product(name, count, buyingDate, sellingDate, 0, 0));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching data: " + e.getMessage());
        }
    }

    @FXML
    void addProductButtonClicked(MouseEvent event) {

    }

    @FXML
    void barChartRadioClicked(MouseEvent event) {

    }

    @FXML
    void changeMenuIconClicked(MouseEvent event) {

    }

    @FXML
    void pieChartRadioClicked(MouseEvent event) {

    }

    @FXML
    void updateProductButtonClicked(MouseEvent event) {

    }
}
