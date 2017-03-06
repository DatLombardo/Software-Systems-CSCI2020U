package sample;

import java.io.*;
import java.util.*;

/**
 * @author: Michael Lombardo
 * @date: 03/03/2017
 * @project: CSCI 2020U Assignment 1
 * @file: Training.java
 */
public class Training {
    public HashMap<String,Integer> globalCount;
    public HashMap<String,Double> wordGivProb;
    public int spamLength;
    public int hamLength;
    private HashMap<String,Integer> fileCount;


    public Training(File[] listOfFiles, boolean isSpam) throws IOException {
        globalCount = new HashMap<>();
        if(isSpam){spamLength = listOfFiles.length;}
        else{hamLength = listOfFiles.length;}
        for (int i = 0; i < listOfFiles.length; i++) {
            processFile(listOfFiles[i]);
        }
        genereateProb(globalCount, isSpam);
    }
    //Count and create a map for the file, then add to global map.
    public void processFile(File file) throws IOException {
        Scanner scanner = new Scanner(file);
        fileCount = new HashMap<>();
        while (scanner.hasNext()) {
            String word = scanner.next().toLowerCase(); //add to lowercase
            countWord(word);
        }
        Iterator itr = fileCount.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry pair = (Map.Entry)itr.next();
            if(!globalCount.containsKey(pair.getKey())){
                globalCount.put(pair.getKey().toString(),1);
            }else{
                int oldCount = globalCount.get(pair.getKey());
                globalCount.put(pair.getKey().toString(), oldCount+1);
            }
            itr.remove();
        }
    }
    private void countWord(String word) {
        if (fileCount.containsKey(word)) {
            // increment the count
            int oldCount = fileCount.get(word);
            fileCount.put(word, oldCount+1);
        } else {
            // add the word with count of 1
            fileCount.put(word, 1);
        }
    }
    //Calculate Probabilties for W|S and W|H
    public void genereateProb(HashMap<String, Integer> dataSet, boolean isSpam) throws IOException {
        Set<String> dataKeys = dataSet.keySet();
        wordGivProb = new HashMap<>();
        Iterator<String> itr = dataKeys.iterator();
        while (itr.hasNext()) {
            String key = itr.next();
            int count = dataSet.get(key);
            if(isSpam) wordGivProb.put(key, (count / (double)spamLength));
            if(!isSpam) wordGivProb.put(key, (count / (double)hamLength));
        }
    }
}