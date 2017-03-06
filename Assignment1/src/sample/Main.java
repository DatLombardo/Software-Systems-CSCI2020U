package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.io.File;
import java.util.*;

/**
 * @author: Michael Lombardo
 * @date: 03/03/2017
 * @project: CSCI 2020U Assignment 1
 * @file: Main.java
 */

public class Main extends Application {
    private TableView<TestFile> table;
    private BorderPane layout;
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Spam Buster 9999");

        System.out.println("Select Data folder");
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File mainDirectory = directoryChooser.showDialog(primaryStage);
        System.out.println("Processing Training...");
        File folderS = new File(mainDirectory.getPath()+"/train/spam");
        File[] listOfFilesS = folderS.listFiles();
        //Buffer the spam and ham frequency maps
        Training spamFreq = new Training(listOfFilesS, true);

        File folderHam1 = new File(mainDirectory.getPath()+"/train/ham");
        File[] listOfFilesH1 = folderHam1.listFiles();

        Training hamFreq1 = new Training(listOfFilesH1, false);

        File folderHam2 = new File(mainDirectory.getPath()+"/train/ham2");
        File[] listOfFilesH2 = folderHam2.listFiles();

        Training hamFreq2 = new Training(listOfFilesH2, false);


        //Calculate S|W
        HashMap<String,Double> spamGivWord = new HashMap<>();
        double prob = 0.0;
        Iterator it = spamFreq.wordGivProb.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if (hamFreq1.globalCount.containsKey(pair.getKey())){
                // W|S / W|S + W|H
                prob = ((Double) pair.getValue()) / (((Double)pair.getValue()) + hamFreq1.wordGivProb.get(pair.getKey()));
                spamGivWord.put(pair.getKey().toString(), prob);
            }
            else if (hamFreq2.globalCount.containsKey(pair.getKey())) {
                // W|S / W|S + W|H
                prob = ((Double) pair.getValue()) / (((Double) pair.getValue()) + hamFreq2.wordGivProb.get(pair.getKey()));
                spamGivWord.put(pair.getKey().toString(), prob);
            }
            else if (hamFreq1.globalCount.containsKey(pair.getKey()) && hamFreq2.globalCount.containsKey(pair.getKey()) ) {
                // W|S / W|S + W|H
                prob = ((Double) pair.getValue()) / (((Double) pair.getValue())
                        + (hamFreq1.wordGivProb.get(pair.getKey()) + hamFreq2.wordGivProb.get(pair.getKey())));
                spamGivWord.put(pair.getKey().toString(), prob);
            }
            else{
                // W|S / W|S + 0
                spamGivWord.put(pair.getKey().toString(), 1.0);
            }
            it.remove();
        }
////////////////////////////////////////////////// Testing
        File folderTS = new File(mainDirectory.getPath()+"/test/spam");
        File[] listOfFilesTS = folderTS.listFiles();


        File folderTH = new File(mainDirectory.getPath()+"/test/ham");
        File[] listOfFilesTH = folderTH.listFiles();

        Testing test = new Testing(listOfFilesTS, listOfFilesTH, spamGivWord);
        System.out.println("Processing Testing...");

        table = new TableView<>();
        table.setItems(test.testData);
        table.setEditable(true);
        
        TableColumn<TestFile,String> fileColumn = null;
        fileColumn = new TableColumn<>("File Name");
        fileColumn.setMinWidth(300);
        fileColumn.setCellValueFactory(new PropertyValueFactory<>("filename"));
        fileColumn.setCellFactory(TextFieldTableCell.<TestFile>forTableColumn());


        TableColumn<TestFile,String> classColumn = null;
        classColumn = new TableColumn<>("Actual Class");
        classColumn.setMinWidth(100);
        classColumn.setCellValueFactory(new PropertyValueFactory<>("actualClass"));
        classColumn.setCellFactory(TextFieldTableCell.<TestFile>forTableColumn());


        TableColumn<TestFile,String> probColumn = null;
        probColumn = new TableColumn<>("Spam Probability");
        probColumn.setMinWidth(100);
        probColumn.setCellValueFactory(new PropertyValueFactory<>("probRounded"));
        probColumn.setCellFactory(TextFieldTableCell.<TestFile>forTableColumn());


        TableColumn<TestFile,String> guessColumn = null;
        guessColumn = new TableColumn<>("Guess Class");
        guessColumn.setMinWidth(100);
        guessColumn.setCellValueFactory(new PropertyValueFactory<>("guessClass"));
        guessColumn.setCellFactory(TextFieldTableCell.<TestFile>forTableColumn());


        table.getColumns().add(fileColumn);
        table.getColumns().add(classColumn);
        table.getColumns().add(probColumn);
        table.getColumns().add(guessColumn);

        //Presicion and Accuracy
        double correctGuesses = 0.0;
        double wrongGuesses = 0.0;
        for (int i = 0; i < test.testData.size(); i++) {
            correctGuesses = correctGuesses + test.testData.get(i).getCorrectGuess();
            if (test.testData.get(i).getCorrectGuess() == 0){
                wrongGuesses = wrongGuesses + 1;

            }
        }
        double precision = correctGuesses/test.testData.size();
        double accuracy = correctGuesses / (wrongGuesses+correctGuesses);


        Label prec = new Label("Precision: " + precision + "\n\nAccuracy:  " + accuracy);

/////////////////////////////////////////// /////Outputs
        layout = new BorderPane();
        layout.setCenter(table);
        layout.setBottom(prec);
        primaryStage.setScene(new Scene(layout, 700, 675));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


