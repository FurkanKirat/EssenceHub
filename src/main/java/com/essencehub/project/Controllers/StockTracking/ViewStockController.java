package com.essencehub.project.Controllers.StockTracking;

import com.essencehub.project.Controllers.Settings.ThemeController;
import com.essencehub.project.Stock.Product;
import com.essencehub.project.DatabaseOperations.DatabaseConnection;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class ViewStockController {

    @FXML
    private ComboBox<String> tableViewCombo;

    @FXML
    private Button addProductButton;

    @FXML
    private RadioButton barChartRadio;

    @FXML
    private ImageView changeMenuIcon;

    @FXML
    private VBox chartVBox;

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

    @FXML
    private Button updateProductButton;

    @FXML
    private ComboBox<?> yearCombobox;

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private NumberAxis numberAxis;

    @FXML
    private RadioButton pieChartRadio;

    @FXML
    private CategoryAxis categoryAxis;

    @FXML
    private VBox func;

    public void initialize() {
        initializeTableViewCombo();
        initializeTableColumns();
        initializeChartToggles();

        tableViewCombo.valueProperty().addListener((observable, oldValue, newValue) -> updateTableView(newValue));
        updateTableView("All");
        loadFXMLContent("/com/essencehub/project/fxml/StockTracking/viewBarChart.fxml", chartVBox);
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

    private void initializeChartToggles() {
        ToggleGroup group = new ToggleGroup();
        pieChartRadio.setToggleGroup(group);
        barChartRadio.setToggleGroup(group);
        barChartRadio.setSelected(true);
    }

    private void updateTableView(String selectedTimeRange) {
        stockView.getItems().clear();

        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();

            String query = "SELECT name, SUM(count) as totalCount, MIN(buyingDate) as earliestBuyingDate, " +
                    "MAX(sellingDate) as latestSellingDate FROM Stock WHERE 1=1";

            LocalDate currentDate = LocalDate.now();
            if (selectedTimeRange.equals("Last 6 Months")) {
                query += " AND buyingDate > '" + currentDate.minusMonths(6) + "'";
            } else if (selectedTimeRange.equals("Last 1 Year")) {
                query += " AND buyingDate > '" + currentDate.minusYears(1) + "'";
            } else if (selectedTimeRange.equals("Last 5 Years")) {
                query += " AND buyingDate > '" + currentDate.minusYears(5) + "'";
            }

            query += " GROUP BY name"; // Ürün adına göre gruplama

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int totalCount = resultSet.getInt("totalCount");
                LocalDate buyingDate = resultSet.getDate("earliestBuyingDate").toLocalDate();
                LocalDate sellingDate = resultSet.getDate("latestSellingDate") != null
                        ? resultSet.getDate("latestSellingDate").toLocalDate()
                        : null;

                stockView.getItems().add(new Product(name, totalCount, buyingDate, sellingDate, 0, 0));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching data: " + e.getMessage());
        }
    }


    @FXML
    void addProductButtonClicked(MouseEvent event) {
        createNewScene("/com/essencehub/project/fxml/StockTracking/addProduct.fxml","Add Product",event);
    }

    @FXML
    void barChartRadioClicked(MouseEvent event) {
        loadFXMLContent("/com/essencehub/project/fxml/StockTracking/viewBarChart.fxml", chartVBox);
    }

    @FXML
    void changeMenuIconClicked(MouseEvent event) {
        loadFXMLContent("/com/essencehub/project/fxml/StockTracking/Stock.fxml", func);
    }

    @FXML
    void pieChartRadioClicked(MouseEvent event) {
        loadFXMLContent("/com/essencehub/project/fxml/StockTracking/ViewPieChart.fxml", chartVBox);
    }

    @FXML
    void updateProductButtonClicked(MouseEvent event) {
        createNewScene("/com/essencehub/project/fxml/StockTracking/updateProduct.fxml","Update Product", event);
    }

    private void loadFXMLContent(String fxmlFile, VBox targetVBox) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Node newContent = loader.load();

            targetVBox.getChildren().clear();
            targetVBox.getChildren().add(newContent);
            VBox.setVgrow(newContent, Priority.ALWAYS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void createNewScene(String FXMLFile,String title, Event event){
        try{
            Stage stage = new Stage();
            stage.getIcons().add(new Image( getClass().getResourceAsStream( "/com/essencehub/project/images/logo.jpg" )));
            Parent root = FXMLLoader.load(getClass().getResource(FXMLFile));

            Scene scene = new Scene(root);
            Stage parentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.initOwner(parentStage);
            stage.initModality(Modality.WINDOW_MODAL);

            ThemeController.changeTheme(scene);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    }