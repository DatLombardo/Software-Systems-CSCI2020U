package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;
import java.util.*;

/**
 * @author: Michael Lombardo
 * @date: 03/03/2017
 * @project: CSCI 2020U Assignment 1
 * @file: Testing.java
 */
public class Testing {
    public ObservableList<TestFile> testData = FXCollections.observableArrayList();
    public Testing(File[] listOfSpam, File[] listOfHam, HashMap<String, Double> wordMap) throws IOException{
        CalculateProb(listOfSpam, wordMap);
        CalculateProb(listOfHam, wordMap);
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
                    if (wordMap.containsKey(word)) {
                        double wordSpamProbability = wordMap.get(word);
                        if (wordSpamProbability > 0.0f && wordSpamProbability < 1.0f) {
                            total += Math.log(1 - wordSpamProbability)
                                    - Math.log(wordSpamProbability);
                        }
                    }
            }
            fileReader.close();
            String guessClass = actualClass;
            double spamProbability = 1 / (1 + Math.pow(Math.E, total));
            if (spamProbability >= 0.5){
                guessClass = "Spam";
            }else if(spamProbability < 0.5) {
                guessClass = "Ham";
            }
            TestFile testFile = new TestFile(path, spamProbability, guessClass, actualClass);
            testData.add(testFile);
        }
    }
}
