package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.*;

/**
 * Created by michael on 05/03/17.
 */
public class Testing {
    public ObservableList<TestFile> testData = FXCollections.observableArrayList();
    public Testing(File[] listOfFiles, HashMap<String, Double> wordMap) throws IOException{
        CalculateProb(listOfFiles, wordMap);
    }
    private void CalculateProb(File[] listOfFiles, HashMap<String, Double> wordMap) throws IOException {
        for (File file : listOfFiles) {
            String path = file.getPath();
            String actualClass = "Ham";
            if (path.contains("spam")) {
                actualClass = "Spam";
            }

            double total = 0;

            FileReader fileReader = new FileReader(file);
            Scanner scanner = new Scanner(fileReader);
            while (scanner.hasNext()) {
                String word = scanner.next().toLowerCase();
                //if (word.matches(WORD_REGEX)) {
                    if (wordMap.containsKey(word)) {
                        double wordSpamProbability = wordMap.get(word);
                        if (wordSpamProbability > 0.0f && wordSpamProbability < 1.0f) {
                            total += Math.log(1 - wordSpamProbability)
                                    - Math.log(wordSpamProbability);
                        }
                    }
                //}
            }
            fileReader.close();
            String guessClass;
            double spamProbability = 1 / (1 + Math.pow(Math.E, total));
            if (spamProbability > 0.5){
                guessClass = "Spam";
            }else{
                guessClass = "Ham";
            }
            TestFile testFile = new TestFile(path, spamProbability, guessClass, actualClass);
            testData.add(testFile);
        }
    }
}
