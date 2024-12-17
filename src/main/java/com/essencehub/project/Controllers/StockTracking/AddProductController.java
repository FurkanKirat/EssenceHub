package com.essencehub.project.Controllers.StockTracking;

import com.essencehub.project.Controllers.Menu.AdminMenuController;
import com.essencehub.project.Stock.Product;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;

public class AddProductController {

    @FXML
    private DatePicker buyDatePicker;

    @FXML
    private TextField costTextField;

    @FXML
    private TextField countTextField;

    @FXML
    private TextField productNameTextField;

    @FXML
    private TextField salePriceTextField;

    @FXML
    void addProductClicked(MouseEvent event) {
        try {

            String productName = productNameTextField.getText();
            int cost = Integer.parseInt(costTextField.getText());
            int salePrice = Integer.parseInt(salePriceTextField.getText());
            int count = Integer.parseInt(countTextField.getText());
            LocalDate buyDate = buyDatePicker.getValue();


            if (productName.isEmpty() || cost <= 0 || salePrice <= 0 || count <= 0 || buyDate == null) {
                System.out.println("Please fill in all fields with valid data.");
                return;
            }


            Product newProduct = new Product(productName, count, buyDate, null, cost, salePrice);


            Product.addProduct(newProduct);

            clearForm();
            AdminMenuController adminMenuController = AdminMenuController.getInstance();
            adminMenuController.loadFXMLContent("/com/essencehub/project/fxml/StockTracking/ViewStock.fxml");

        } catch (NumberFormatException e) {
            System.out.println("Please enter valid numeric values for cost, sale price, and count.");
        }
    }

    private void clearForm() {
        productNameTextField.clear();
        costTextField.clear();
        salePriceTextField.clear();
        countTextField.clear();
        buyDatePicker.setValue(null);
    }
}
