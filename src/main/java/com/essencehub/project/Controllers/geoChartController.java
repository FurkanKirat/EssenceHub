package com.essencehub.project.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.util.ArrayList;

public class geoChartController {

    @FXML
    private HBox assignTaskPanel;

    @FXML
    private HBox dashboardPanel;

    @FXML
    private HBox employeePanel;

    @FXML
    private HBox financePanel;

    @FXML
    private ImageView msgIcon;

    @FXML
    private Label namePanel;

    @FXML
    private ImageView profilPicturePanel;

    @FXML
    private ImageView settingIcon;

    @FXML
    private Label statusPanel;

    @FXML
    private HBox stockTrackingPanel;

    @FXML
    private WebView webView;

    @FXML
    void assignTaskPanelClicked(MouseEvent event) {

    }

    @FXML
    void dashboradClicked(MouseEvent event) {

    }

    @FXML
    void employeePanelClicked(MouseEvent event) {

    }

    @FXML
    void financeClicked(MouseEvent event) {

    }

    @FXML
    void msgIconClicked(MouseEvent event) {

    }

    @FXML
    void profilePicturePanelClicked(MouseEvent event) {

    }

    @FXML
    void settingIconClicked(MouseEvent event) {

    }

    @FXML
    void stockTrackingCLicked(MouseEvent event) {





    }
    @FXML
    void initialize() {
        WebEngine webEngine = webView.getEngine();

        String highchartsHTML = """
        <!DOCTYPE html>
        <html>
        <head>
            <script src="https://code.highcharts.com/maps/highmaps.js"></script>
            <script src="https://code.highcharts.com/maps/modules/drilldown.js"></script>
            <script src="https://code.highcharts.com/mapdata/index.js"></script>
            <script src="https://code.highcharts.com/modules/accessibility.js"></script>
            <link href="https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">
            <style>
                #demo-wrapper {
                    display: flex;
                    flex-direction: column;
                    align-items: center;
                    margin: 20px;
                }
                #selector {
                    display: flex;
                    justify-content: center;
                    margin-bottom: 10px;
                }
                #map-box {
                    display: flex;
                    flex-direction: row;
                }
                #container {
                    width: 600px;
                    height: 400px;
                    margin-right: 20px;
                }
                #side-box {
                    width: 200px;
                }
                .links {
                    list-style: none;
                }
                .links a {
                    text-decoration: none;
                    color: blue;
                }
            </style>
        </head>
        <body>
            <div id="demo-wrapper">
              <div id="selector">
                <button id="prev-map-btn" class="prev-next" aria-label="Previous map"><i class="fa fa-angle-left"></i></button>

                <input list="maps" id="map-datalist-input" name="map-choice" aria-label="Select a map to display" />
                <datalist id="maps"></datalist>

                <button id="next-map-btn" class="prev-next" aria-label="Next map"><i class="fa fa-angle-right"></i></button>
              </div>
              <div id="map-box">
                <div id="container"></div>

                <div id="side-box">
                    <div id="checkbox-wrapper">
                        <input type="checkbox" id="datalabels-checkbox" />
                      <label for="datalabels-checkbox">Data labels</label>
                    </div>

                  <div id="info-box">
                    <h4 id="map-name-header">World Map</h4>

                    <div id="download">
                      <a id="clean-demo-btn" target="_blank">View clean demo</a>
                      <div>
                        <p>... or view as</p>
                        <ul>
                          <li class='links'><a target="_blank" href="" id="svg-link">SVG</a></li>
                          <li class='links'><a target="_blank" href="" id="geojson-link">GeoJSON</a></li>
                          <li class='links'><a target="_blank" href="" id="topojson-link">TopoJSON</a></li>
                          <li class='links'><a target="_blank" href="" id="javascript-link">JavaScript</a></li>
                        </ul>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <script>
                Highcharts.mapChart('container', {
                    chart: {
                        map: 'custom/world'
                    },
                    title: {
                        text: 'Highcharts Maps Example'
                    },
                    subtitle: {
                        text: 'World map with data labels'
                    },
                    legend: {
                        enabled: true
                    },
                    series: [{
                        name: 'Random data',
                        data: [
                            ['us', 10], ['fr', 5], ['de', 3], ['in', 8],
                            ['cn', 7], ['ru', 2], ['br', 6], ['za', 4]
                        ],
                        mapData: Highcharts.maps['custom/world'],
                        joinBy: 'hc-key',
                        states: {
                            hover: {
                                color: '#BADA55'
                            }
                        },
                        dataLabels: {
                            enabled: true,
                            format: '{point.name}'
                        }
                    }]
                });
            </script>
        </body>
        </html>
        """;

        // Load the Highcharts Maps HTML content
        webEngine.loadContent(highchartsHTML);
    }

}

