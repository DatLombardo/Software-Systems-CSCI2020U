package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.Stream;

public class Main extends Application {
    public void listFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                System.out.println(fileEntry.getName());
            }
        }
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Spam Buster 9999");


        TestFile testData = new TestFile("Filename", 0.5, "Spam");

        System.out.println("Select Spam");
        DirectoryChooser directoryChooserS = new DirectoryChooser();
        directoryChooserS.setInitialDirectory(new File("."));
        File mainDirectoryS = directoryChooserS.showDialog(primaryStage);

        File folderS = new File(mainDirectoryS.getPath());
        File[] listOfFiles = folderS.listFiles();
        //Buffer the spam and ham frequency maps
        Training spamFreq = new Training(listOfFiles, true);

        System.out.println("Select Ham");
        DirectoryChooser directoryChooserH = new DirectoryChooser();
        directoryChooserH.setInitialDirectory(new File("."));
        File mainDirectoryH = directoryChooserH.showDialog(primaryStage);

        File folderHam = new File(mainDirectoryH.getPath());
        File[] listOfFilesHam = folderHam.listFiles();
        Training hamFreq = new Training(listOfFilesHam, false);



        //Outputs
        System.out.println(listOfFiles.length);
        System.out.println(mainDirectoryS.getPath());
        for (HashMap.Entry<String, Double> entry : spamFreq.wordGivProb.entrySet()) {
            System.out.println(entry.getKey()+" : "+entry.getValue());
        }
        System.out.println(spamFreq.globalCount.size());


        System.out.println(listOfFiles.length);
        System.out.println(mainDirectoryS.getPath());
        //final File folder = new File(mainDirectory.getPath());
        //listFilesForFolder(folder);

        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}


