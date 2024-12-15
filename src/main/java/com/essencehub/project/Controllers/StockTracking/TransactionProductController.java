package com.essencehub.project.Controllers.StockTracking;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class TransactionProductController {

    @FXML
    private ComboBox<?> buyOrSellComboBox;

    @FXML
    private TextField countTextField;

    @FXML
    private ComboBox<?> selectProductComboBox;

    @FXML
    private DatePicker transactionDatePicker;

    @FXML
    void setTransactionClicked(MouseEvent event) {

    }

}
