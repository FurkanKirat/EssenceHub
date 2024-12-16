package com.essencehub.project.Controllers.StockTracking;

import com.essencehub.project.DatabaseOperations.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateProductController {

    @FXML
    private TextField salePriceTextField;

    @FXML
    private ComboBox<String> selectProductComboBox;


    public void initialize() {
        ObservableList<String> productList = FXCollections.observableArrayList();

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                String query = "SELECT DISTINCT name FROM Stock";
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    String productName = resultSet.getString("name");
                    productList.add(productName);
                }

                selectProductComboBox.setItems(productList);
            } else {
                System.out.println("Database connection failed.");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching products: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @FXML
    void updateProductClicked(MouseEvent event) {
        try {

            String selectedProduct = selectProductComboBox.getValue();
            String newSalePriceInput = salePriceTextField.getText();
            int newSalePrice = Integer.parseInt(newSalePriceInput);


            if (selectedProduct == null || selectedProduct.isEmpty()) {
                System.out.println("Please select a product.");
                return;
            }

            if (newSalePrice <= 0) {
                System.out.println("Please enter a valid sale price.");
                return;
            }

            try (Connection connection = DatabaseConnection.getConnection()) {
                if (connection != null) {
                    String updateQuery = "UPDATE Stock SET sellingPrice = ? WHERE name = ?";
                    PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                    updateStatement.setInt(1, newSalePrice);
                    updateStatement.setString(2, selectedProduct);

                    int rowsAffected = updateStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Sale price updated successfully for product: " + selectedProduct);
                    } else {
                        System.out.println("No matching products found to update.");
                    }
                } else {
                    System.out.println("Database connection failed.");
                }
            } catch (SQLException e) {
                System.out.println("Error updating product: " + e.getMessage());
                e.printStackTrace();
            }


            clearForm();

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid numeric value for sale price.");
        }
    }
    
    private void clearForm() {
        salePriceTextField.clear();
        selectProductComboBox.setValue(null);
    }
}
