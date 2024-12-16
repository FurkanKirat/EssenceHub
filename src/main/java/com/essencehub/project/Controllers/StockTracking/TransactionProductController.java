package com.essencehub.project.Controllers.StockTracking;

import com.essencehub.project.DatabaseOperations.DatabaseConnection;
import com.essencehub.project.Stock.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionProductController {

    @FXML
    private TextField countTextField;

    @FXML
    private ComboBox<String> selectProductComboBox;

    @FXML
    private DatePicker transactionDatePicker;


    public void initialize() {
        ObservableList<String> productList = FXCollections.observableArrayList();


        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT name FROM Stock";

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String productName = resultSet.getString("name");
                productList.add(productName);
            }

            selectProductComboBox.setItems(productList);

        } catch (SQLException e) {
            System.out.println("Error fetching products: " + e.getMessage());
        }
    }


    @FXML
    void setTransactionClicked(MouseEvent event) {
        try {

            String selectedProduct = selectProductComboBox.getValue();
            String countInput = countTextField.getText();
            int count = Integer.parseInt(countInput);


            if (selectedProduct == null || selectedProduct.isEmpty()) {
                System.out.println("Please select a product.");
                return;
            }

            if (count <= 0) {
                System.out.println("Please enter a valid count.");
                return;
            }


            Product.removeProduct(selectedProduct, count);
            System.out.println("Transaction completed successfully.");


            clearForm();

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid numeric value for count.");
        } catch (Exception e) {
            System.out.println("An error occurred during the transaction: " + e.getMessage());
        }
    }

    private void clearForm() {
        countTextField.clear();
        selectProductComboBox.setValue(null);
        transactionDatePicker.setValue(null);
    }
}
