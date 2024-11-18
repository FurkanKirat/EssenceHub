package com.essencehub.project.Controllers;

import com.essencehub.project.Product;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class MapController {

    @FXML
    private Circle akdeniz;

    @FXML
    private Circle dogu;

    @FXML
    private Circle ege;

    @FXML
    private Circle guneydogu;

    @FXML
    private Circle icanadolu;

    @FXML
    private Circle karadeniz;

    @FXML
    private Circle marmara;


    ArrayList<Product> products = new ArrayList<>();
    int[] regions = new int[7];

    // Will be the method to add staffs from the SQL to arraylist
    private void addProducts(){

        // These are for tests
        products.add(new Product("laptop",1));
        products.add(new Product("PC",1));

        // Adds products to the region
        for(int i=0;i<products.size();i++){
            regions[products.get(i).getRegion()]++;
        }
    }
    @FXML
    private void initialize(){
        addProducts();
    }
    @FXML
    void displayEge(MouseEvent event) {


        System.out.println(regions[0]+" 0");
    }
    @FXML
    void displayMarmara(MouseEvent event) {


        System.out.println(regions[1]+" 1");
    }
    @FXML
    void displayAkdeniz(MouseEvent event) {


        System.out.println(regions[2]+" 2");
    }
    @FXML
    void displayIcAnadolu(MouseEvent event) {


        System.out.println(regions[3]+" 3");
    }
    @FXML
    void displayKaradeniz(MouseEvent event) {


        System.out.println(regions[4]+" 4");
    }
    @FXML
    void displayDogu(MouseEvent event) {


        System.out.println(regions[5]+" 5");
    }
    @FXML
    void displayGuneyDogu(MouseEvent event) {


        System.out.println(regions[6]+" 6");
    }

}
