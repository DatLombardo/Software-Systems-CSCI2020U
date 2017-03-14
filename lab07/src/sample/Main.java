package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        HashMap<String,Integer> globalCount;
        globalCount = new HashMap<>();

        String CSVFile = "weatherwarnings-2015.csv";
        FileReader reader = new FileReader(CSVFile);
        BufferedReader in = new BufferedReader(reader);

        String line;

        while((line = in.readLine()) != null) {
            if (line.trim().length() != 0) {
                String[] dataFields = line.split(",");
                if(!globalCount.containsKey(dataFields[5])){
                    //word doesn't exist in global map, add it.
                    globalCount.put(dataFields[5],1);
                }else{
                    //Increment the count
                    int oldCount = globalCount.get(dataFields[5]);
                    globalCount.put(dataFields[5], oldCount+1);
                }
            }
        }
        System.out.println(globalCount);
        int length = globalCount.size();
        String[] weatherType = new String [length];
        int [] typeCount = new int [length];
        int counter = 0;
        for ( HashMap.Entry<String, Integer> entry : globalCount.entrySet()) {
            weatherType[counter] = entry.getKey();
            typeCount[counter] = entry.getValue();
            counter += 1;
        }
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data(weatherType[0], typeCount[0]),
                new PieChart.Data(weatherType[1], typeCount[1]),
                new PieChart.Data(weatherType[2], typeCount[2]),
                new PieChart.Data(weatherType[3], typeCount[3]));


        final PieChart pieC = new PieChart(pieChartData);
        pieC.setTitle("Weather Warnings");
        primaryStage.setTitle("Lab 07");
        primaryStage.setScene(new Scene(pieC, 500, 475));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
