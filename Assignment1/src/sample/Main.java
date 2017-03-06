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
import java.util.Iterator;
import java.util.Map;
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
        File folderT = new File(mainDirectoryS.getPath());
        File[] listOfFilesT = folderS.listFiles();

        Testing test = new Testing(listOfFilesT, spamGivWord);
        System.out.println(test.testData.get(1).getFilename() + "Spew" + test.testData.get(1).getSpamProbRounded());





/////////////////////////////////////////// /////Outputs
        System.out.println(mainDirectoryS.getPath());
        /*for (HashMap.Entry<String, Double> entry : spamGivWord.entrySet()) {
            System.out.println(entry.getKey()+" : "+entry.getValue());
        }*/
        System.out.println(spamFreq.globalCount.size());
        System.out.println(hamFreq.globalCount.size());


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


