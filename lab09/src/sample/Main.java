package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.*;
import java.net.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;

import java.net.URL;
import java.util.LinkedList;

public class Main extends Application {
    private Canvas canvas;
    @Override
    public void start(Stage primaryStage) throws Exception{
        LinkedList<Double> google = downloadStockPrices("http://ichart.finance.yahoo.com/table.csv?s=GOOG&a=1&b=01&c=2010&d=11&e=31&f=2015&g=m");
        LinkedList<Double> apple = downloadStockPrices("http://ichart.finance.yahoo.com/table.csv?s=AAPL&a=1&b=01&c=2010&d=11&e=31&f=2015&g=m");
        Group root = new Group();
        Canvas canvas = new Canvas(810, 700);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawLinePlot(gc);
        root.getChildren().add(canvas);
        drawLine(gc, google, Color.BLUE);
        drawLine(gc, apple, Color.RED);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void drawLinePlot(GraphicsContext gc) {
        gc.setStroke(Color.GREY);
        gc.setLineWidth(5);
        gc.strokeLine(50, 650, 760, 650);
        gc.strokeLine(50, 50, 50, 650);
    }
    private void drawLine(GraphicsContext gc, LinkedList<Double> company, Color colour){
        gc.setStroke(colour);
        gc.setLineWidth(3);
        double nextY = company.get(0);
        for (int i = 1; i < company.size();i++){
            gc.strokeLine(((i*10)+50),nextY/2,((i*10)+60), (company.get(i))/2);
            nextY = company.get(i);
        }
    }

    public LinkedList<Double> downloadStockPrices(String address) throws Exception{
        LinkedList<Double> close = new LinkedList();
        try {
            URL url = new URL(address);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(false);
            conn.setDoInput(true);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            //skip first
            line = in.readLine();
            while((line = in.readLine()) != null) {
                String[] dataFields = line.split(",");
                close.add(Double.parseDouble(dataFields[4]));
            }
            // pull out the data that we want
        } catch (IOException e) {
            e.printStackTrace();
        }
        return close;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
