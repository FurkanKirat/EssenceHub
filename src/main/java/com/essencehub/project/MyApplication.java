package com.essencehub.project;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static javafx.application.Application.launch;

public class MyApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        try{
            //LineChartView lineChartView = new LineChartView();
            //Scene scene = new Scene(lineChartView.createLineChart(), 800, 600);
            Parent root = FXMLLoader.load(getClass().getResource("loginpage.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Essence Hub");

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}