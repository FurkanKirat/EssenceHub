package com.essencehub.project.Controllers;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class ControllerEmployeePanel {

    @FXML
    private HBox dashboardPanel;

    @FXML
    private ImageView msgIcon;

    @FXML
    private HBox performancePanel;

    @FXML
    private ImageView profilePicturePanel;

    @FXML
    private ImageView settingIcon;

    @FXML
    private HBox tasksPanel;

    @FXML
    private WebView webview;

    @FXML
    private Label nameLabel;



    @FXML
    void dashboardPanelClicked(MouseEvent event) {

    }

    @FXML
    void msgIconCLicked(MouseEvent event) {

    }

    @FXML
    void performancePanelClicked(MouseEvent event) {

    }

    @FXML
    void profilePicturePanelClicked(MouseEvent event) {

    }

    @FXML
    void settingsIconClicked(MouseEvent event) {

    }

    @FXML
    void tasksPanelClicked(MouseEvent event) {
        WebEngine webEngine = webview.getEngine();
        String highchartsHTML = """
                <!DOCTYPE html>
                <html>
                <head>
                    <script src="https://code.highcharts.com/highcharts.js"></script>
                    <script src="https://code.highcharts.com/modules/series-label.js"></script>
                    <script src="https://code.highcharts.com/modules/exporting.js"></script>
                    <script src="https://code.highcharts.com/modules/export-data.js"></script>
                    <script src="https://code.highcharts.com/modules/accessibility.js"></script>
                </head>
                <body>
                    <figure class="highcharts-figure">
                        <div id="container" style="height: 400px;"></div>
                        <p class="highcharts-description">
                            Basic line chart showing trends in a dataset. This chart includes the
                            <code>series-label</code> module, which adds a label to each line for
                            enhanced readability.
                        </p>
                    </figure>
                    <script>
                        Highcharts.chart('container', {
                            title: {
                                text: 'Solar Employment Growth by Sector, 2010-2020'
                            },
                            subtitle: {
                                text: 'Source: thesolarfoundation.com'
                            },
                            yAxis: {
                                title: {
                                    text: 'Number of Employees'
                                }
                            },
                            xAxis: {
                                accessibility: {
                                    rangeDescription: 'Range: 2010 to 2020'
                                }
                            },
                            legend: {
                                layout: 'vertical',
                                align: 'right',
                                verticalAlign: 'middle'
                            },
                            series: [{
                                name: 'Installation',
                                data: [43934, 52503, 57177, 69658, 97031, 119931, 137133, 154175]
                            }, {
                                name: 'Manufacturing',
                                data: [24916, 24064, 29742, 29851, 32490, 30282, 38121, 40434]
                            }, {
                                name: 'Sales & Distribution',
                                data: [11744, 17722, 16005, 19771, 20185, 24377, 32147, 39387]
                            }, {
                                name: 'Project Development',
                                data: [0, 0, 7988, 12169, 15112, 22452, 34400, 34227]
                            }, {
                                name: 'Other',
                                data: [12908, 5948, 8105, 11248, 8989, 11816, 18274, 18111]
                            }],
                            responsive: {
                                rules: [{
                                    condition: {
                                        maxWidth: 500
                                    },
                                    chartOptions: {
                                        legend: {
                                            layout: 'horizontal',
                                            align: 'center',
                                            verticalAlign: 'bottom'
                                        }
                                    }
                                }]
                            }
                        });
                    </script>
                </body>
                </html>
                """;
        // Load the HTML content
        webEngine.loadContent(highchartsHTML);

        // Listen for the content to finish loading
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == javafx.concurrent.Worker.State.SUCCEEDED) {
                // The page has loaded; now inject the ArrayList data
                ArrayList<Integer> myData = new ArrayList<>();
                myData.add(43934);
                myData.add(52503);
                myData.add(57177);
                myData.add(69658);
                myData.add(97031);
                myData.add(119931);
                myData.add(4444);
                myData.add(15411);

                // Convert ArrayList to a JavaScript-compatible string
                String dataString = myData.toString();

                // Update the chart's series data using JavaScript
                webEngine.executeScript("""
                        const chart = Highcharts.charts[0];
                        chart.series[0].setData(""" + dataString + """
                         );
                        """);

            }

        });
    }

    @FXML
    void initialize() {
        try {
            String name =  LoginPageController.getResultSet().getString("name");
            String surname = LoginPageController.getResultSet().getString("surname");
            nameLabel.setText(name + " " + surname);
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }
}
