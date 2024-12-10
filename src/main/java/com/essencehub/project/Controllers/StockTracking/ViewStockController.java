package com.essencehub.project.Controllers.StockTracking;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ViewStockController {

    @FXML
    private Button addProductButton;

    @FXML
    private RadioButton barChartRadio;

    @FXML
    private ImageView changeMenuIcon;

    @FXML
    private VBox chartVBox;

    @FXML
    private TableColumn<?, ?> dateColumn;

    @FXML
    private VBox func;

    @FXML
    private LineChart<?, ?> lineChart;

    @FXML
    private NumberAxis numberAxis;

    @FXML
    private RadioButton pieChartRadio;

    @FXML
    private CategoryAxis categoryAxis;;

    @FXML
    private TableColumn<?, ?> productNameColumn;

    @FXML
    private TableColumn<?, ?> stockColumn;

    @FXML
    private TableView<?> stockView;

    @FXML
    private Button updateProductButton;

    @FXML
    private ComboBox<?> yearCombobox;


    @FXML
    void addProductButtonClicked(MouseEvent event) {

    }

    @FXML
    void barChartRadioClicked(MouseEvent event) {
        loadFXMLContent("/com/essencehub/project/fxml/StockTracking/viewStock.fxml",func);
    }

    @FXML
    void changeMenuIconClicked(MouseEvent event) {
        loadFXMLContent("/com/essencehub/project/fxml/StockTracking/ViewStockTime.fxml",func);
    }

    @FXML
    void pieChartRadioClicked(MouseEvent event) {
        loadFXMLContent("/com/essencehub/project/fxml/StockTracking/viewPieChart.fxml",chartVBox);
    }

    @FXML
    void updateProductButtonClicked(MouseEvent event) {

    }
    public void initialize(){
        ToggleGroup group = new ToggleGroup();
        pieChartRadio.setToggleGroup(group);
        barChartRadio.setToggleGroup(group);
        barChartRadio.setSelected(true);
    }
    public void loadFXMLContent(String fxmlFile,VBox func) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Node newContent = loader.load();

            func.getChildren().clear();
            func.getChildren().add(newContent);
            VBox.setVgrow(newContent, Priority.ALWAYS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
