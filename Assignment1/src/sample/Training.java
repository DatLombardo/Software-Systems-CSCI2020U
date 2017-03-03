package sample;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Created by michael on 03/03/17.
 */
public class Training {
    private Map<String,Integer> wordCounts;

    public Training(File[] listOfFiles) throws FileNotFoundException, IOException {
        wordCounts = new TreeMap<>();
        for (int i = 0; i < listOfFiles.length; i++) {
            processFile(listOfFiles[i]);
        }
    }

    public void processFile(File file) throws IOException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            String word = scanner.next();
            if (isWord(word)) {
                countWord(word);
            }
        }
    }
    private void countWord(String word) {
        if (wordCounts.containsKey(word)) {
            // increment the count
            int oldCount = wordCounts.get(word);
            wordCounts.put(word, oldCount+1);
        } else {
            // add the word with count of 1
            wordCounts.put(word, 1);
        }
    }
    private boolean isWord(String token) {
        String pattern = "^[a-zA-Z]*$";
        if (token.matches(pattern)) {
            return true;
        } else {
            return false;
        }
    }
}