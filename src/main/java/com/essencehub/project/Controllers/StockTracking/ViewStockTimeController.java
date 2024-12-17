package com.essencehub.project.Controllers.StockTracking;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ViewStockTimeController {

    @FXML
    private ImageView changeMenuIcon;

    @FXML
    private VBox chartVBox;

    @FXML
    private VBox func;

    @FXML
    private LineChart<?, ?> lineChart;

    @FXML
    private NumberAxis numberAxis;

    @FXML
    private CategoryAxis productDateAxis;

    @FXML
    private ComboBox<?> yearCombobox;

    @FXML
    void changeMenuIconClicked(MouseEvent event) {
        loadFXMLContent("/com/essencehub/project/fxml/StockTracking/ViewStock.fxml");
    }
    public void loadFXMLContent(String fxmlFile) {
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
