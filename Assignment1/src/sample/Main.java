package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

public class Main extends Application {
    private TableView<TestFile> table;
    private BorderPane layout;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Spam Buster 9999");


        System.out.println("Select Spam");
        DirectoryChooser directoryChooserS = new DirectoryChooser();
        directoryChooserS.setInitialDirectory(new File("."));
        File mainDirectoryS = directoryChooserS.showDialog(primaryStage);

        File folderS = new File(mainDirectoryS.getPath());
        File[] listOfFilesS = folderS.listFiles();
        //Buffer the spam and ham frequency maps
        Training spamFreq = new Training(listOfFilesS, true);

        System.out.println("Select Ham");
        DirectoryChooser directoryChooserH = new DirectoryChooser();
        directoryChooserH.setInitialDirectory(new File("."));
        File mainDirectoryH = directoryChooserH.showDialog(primaryStage);

        File folderHam = new File(mainDirectoryH.getPath());
        File[] listOfFilesH = folderHam.listFiles();
        Training hamFreq = new Training(listOfFilesH, false);

        //Calculate S|W
        HashMap<String,Double> spamGivWord = new HashMap<>();
        double prob = 0.0;
        Iterator it = spamFreq.wordGivProb.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if (hamFreq.globalCount.containsKey(pair.getKey())){
                // W|S / W|S + W|H
                prob = ((Double) pair.getValue()) / (((Double)pair.getValue()) + hamFreq.wordGivProb.get(pair.getKey()));
                spamGivWord.put(pair.getKey().toString(), prob);
            }else{
                // W|S / W|S + 0
                spamGivWord.put(pair.getKey().toString(), 1.0);
            }
            it.remove();
        }
////////////////////////////////////////////////// Testing
        System.out.println("Select Test File");
        DirectoryChooser directoryChooserT = new DirectoryChooser();
        directoryChooserT.setInitialDirectory(new File("."));
        File mainDirectoryT = directoryChooserT.showDialog(primaryStage);
        File folderT = new File(mainDirectoryT.getPath());
        File[] listOfFilesT = folderT.listFiles();

        Testing test = new Testing(listOfFilesT, spamGivWord);

        table = new TableView<>();
        table.setItems(test.testData);
        table.setEditable(true);
        
        TableColumn<TestFile,String> fileColumn = null;
        fileColumn = new TableColumn<>("File Name");
        fileColumn.setMinWidth(350);
        fileColumn.setCellValueFactory(new PropertyValueFactory<>("filename"));
        fileColumn.setCellFactory(TextFieldTableCell.<TestFile>forTableColumn());
        //May be able to remove below
        fileColumn.setOnEditCommit((TableColumn.CellEditEvent<TestFile, String> event) -> {
            ((TestFile)event.getTableView().getItems().get(event.getTablePosition().getRow())).setFilename(event.getNewValue());
        });

        TableColumn<TestFile,String> classColumn = null;
        classColumn = new TableColumn<>("Actual Class");
        classColumn.setMinWidth(100);
        classColumn.setCellValueFactory(new PropertyValueFactory<>("actualClass"));
        classColumn.setCellFactory(TextFieldTableCell.<TestFile>forTableColumn());
        //May be able to remove below
        classColumn.setOnEditCommit((TableColumn.CellEditEvent<TestFile, String> event) -> {
            ((TestFile)event.getTableView().getItems().get(event.getTablePosition().getRow())).setActualClass(event.getNewValue());
        });

        TableColumn<TestFile,String> probColumn = null;
        probColumn = new TableColumn<>("Spam Probability");
        probColumn.setMinWidth(100);
        probColumn.setCellValueFactory(new PropertyValueFactory<>("probRounded"));
        probColumn.setCellFactory(TextFieldTableCell.<TestFile>forTableColumn());
        //May be able to remove below
        probColumn.setOnEditCommit((TableColumn.CellEditEvent<TestFile, String> event) -> {
            ((TestFile)event.getTableView().getItems().get(event.getTablePosition().getRow())).setActualClass(event.getNewValue());
        });

        TableColumn<TestFile,String> guessColumn = null;
        guessColumn = new TableColumn<>("Guess Class");
        guessColumn.setMinWidth(100);
        guessColumn.setCellValueFactory(new PropertyValueFactory<>("guessClass"));
        guessColumn.setCellFactory(TextFieldTableCell.<TestFile>forTableColumn());
        //May be able to remove below
        guessColumn.setOnEditCommit((TableColumn.CellEditEvent<TestFile, String> event) -> {
            ((TestFile)event.getTableView().getItems().get(event.getTablePosition().getRow())).setActualClass(event.getNewValue());
        });

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

        System.out.println("Precision: + " + precision);
        System.out.println("Accuracy: + " + accuracy);
/////////////////////////////////////////// /////Outputs
        System.out.println(mainDirectoryS.getPath());
        System.out.println(mainDirectoryH.getPath());
        layout = new BorderPane();
        layout.setCenter(table);
        primaryStage.setScene(new Scene(layout, 700, 675));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}


