package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
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
        //Initialize Primary stage and project title
        primaryStage.setTitle("Spam Buster 9999");

        System.out.println("Select Data folder");
        //Select Directory for Data, to use as a basic template for the File readers.
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File mainDirectory = directoryChooser.showDialog(primaryStage);

        //Capture the Training Spam and Ham Files, Declare as training files to initialize the word map
        System.out.println("Processing Training...");
        File folderS = new File(mainDirectory.getPath()+"/train/spam");
        File[] listOfFilesS = folderS.listFiles();
        Training spamFreq = new Training(listOfFilesS, true);

        File folderHam1 = new File(mainDirectory.getPath()+"/train/ham");
        File[] listOfFilesH1 = folderHam1.listFiles();
        Training hamFreq1 = new Training(listOfFilesH1, false);

        File folderHam2 = new File(mainDirectory.getPath()+"/train/ham2");
        File[] listOfFilesH2 = folderHam2.listFiles();
        Training hamFreq2 = new Training(listOfFilesH2, false);

        //Calculate S|W
        HashMap<String,Double> spamWordProb = new HashMap<>();
        double prob;
        //Iterate through spam frequency map to determine probabilities for all spam words.
        Iterator itr = spamFreq.wordGivProb.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry word = (Map.Entry)itr.next();
            // W|S / W|S + W|H (Key only exists in Ham1 file)
            if (hamFreq1.globalCount.containsKey(word.getKey())){
                prob = ((Double) word.getValue()) / (((Double)word.getValue()) + hamFreq1.wordGivProb.get(word.getKey()));
                spamWordProb.put(word.getKey().toString(), prob);
            }
            // W|S / W|S + W|H (Key only exists in Ham2 file)
            else if (hamFreq2.globalCount.containsKey(word.getKey())) {
                prob = ((Double) word.getValue()) / (((Double) word.getValue()) + hamFreq2.wordGivProb.get(word.getKey()));
                spamWordProb.put(word.getKey().toString(), prob);
            }
            // W|S / W|S + W|H (Key exists in both Ham files)
            else if (hamFreq1.globalCount.containsKey(word.getKey()) && hamFreq2.globalCount.containsKey(word.getKey())){
                prob = ((Double) word.getValue()) / (((Double) word.getValue())
                        + (hamFreq1.wordGivProb.get(word.getKey()) + hamFreq2.wordGivProb.get(word.getKey())));
                spamWordProb.put(word.getKey().toString(), prob);
            }
            // W|S / W|S + 0 (Key doesn't exist in either Ham files, therefore probability is 1)
            else{
                spamWordProb.put(word.getKey().toString(), 1.0);
            }
            itr.remove();
        }
        //Capture the Testing Spam and Ham Files, Declare as testing files to initialize the testing map
        File folderTestS = new File(mainDirectory.getPath()+"/test/spam");
        File[] testSpamFiles = folderTestS.listFiles();
        File folderTestH = new File(mainDirectory.getPath()+"/test/ham");
        File[] testHamFiles = folderTestH.listFiles();
        Testing testMap = new Testing(testSpamFiles, testHamFiles, spamWordProb);
        System.out.println("Processing Testing...");

        //Set up table for Interface
        table = new TableView<>();
        table.setItems(testMap.testData);
        table.setEditable(true);

        //Table Column Declaration; filename, actualClass, probRounded, guessClass
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

        //Precision and Accuracy
        double correctGuesses = 0.0;
        double truePositives = 0.0;
        double falsePositives = 0.0;
        //Iterate through test map and count the correct / incorrect information.
        for (int i = 0; i < testMap.testData.size(); i++) {
            correctGuesses = correctGuesses + testMap.testData.get(i).getCorrectGuess();
            if (testMap.testData.get(i).getActualClass() == "Spam"){
                if (testMap.testData.get(i).getCorrectGuess() == 1){
                    truePositives = truePositives + 1;
                }else{
                    falsePositives = falsePositives + 1;
                }
            }
        }
        double accuracy = correctGuesses/testMap.testData.size();
        double precision = truePositives / (falsePositives+truePositives);

        Label precAcc = new Label("Precision: " + precision + "\n\nAccuracy:  " + accuracy);

        //Set layout for stage and display
        layout = new BorderPane();
        layout.setCenter(table);
        layout.setBottom(precAcc);
        primaryStage.setScene(new Scene(layout, 625, 675));
        primaryStage.show();
    }
    // Start application
    public static void main(String[] args) {
        launch(args);
    }
}


