package sample;

import java.io.*;
import java.util.*;

/**
 * @author: Michael Lombardo
 * @date: 03/03/2017
 * @project: CSCI 2020U Assignment 1
 * @file: Training.java
 */

/**
 *  Computes the Training maps for given folder with occurances for testing use.
 */
public class Training {
    public HashMap<String,Integer> globalCount;
    public HashMap<String,Double> wordGivProb;
    public int spamLength;
    public int hamLength;
    private HashMap<String,Integer> fileCount;

    /**
     * General Constructor of Training class, creates general global map then inserts parsed
     * lowercase words into a map which it's key is frequency. Training instance is used in the
     * main class.
     *
     * @param listOfFiles Folder of files to have words be parsed and counted, then probability.
     * @param isSpam boolean pre-set value that declares a file to actually be spam or ham.
     * @throws IOException
     */
    public Training(File[] listOfFiles, boolean isSpam) throws IOException {
        globalCount = new HashMap<>();
        if(isSpam){spamLength = listOfFiles.length;}
        else{hamLength = listOfFiles.length;}
        //Iterate through the files, process a frequency map for each, meanwhile generating the global map.
        for (int i = 0; i < listOfFiles.length; i++) {
            processFile(listOfFiles[i]);
        }
        generateProb(globalCount, isSpam);
    }

    /**
     * Count and create a map for the file, then add to global map.
     * @param file file which a map is being created for, then each new case of words are added to
     *             the global map.
     * @throws IOException
     */
    public void processFile(File file) throws IOException {
        Scanner scanner = new Scanner(file);
        fileCount = new HashMap<>();
        while (scanner.hasNext()) {
            //Parse to lowercase, improves accuracy.
            String word = scanner.next().toLowerCase();
            countWord(word);
        }
        Iterator itr = fileCount.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry pair = (Map.Entry)itr.next();
            if(!globalCount.containsKey(pair.getKey())){
                //word doesn't exist in global map, add it.
                globalCount.put(pair.getKey().toString(),1);
            }else{
                //Increment the count
                int oldCount = globalCount.get(pair.getKey());
                globalCount.put(pair.getKey().toString(), oldCount+1);
            }
            itr.remove();
        }
    }

    /**
     * Counts local file map's words, keeping a running total.
     *
     * @param word String value to count local file map word count.
     */
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

    /**
     * Calculate Probabilities for W|S and W|H using their frequency maps.
     *
     * @param dataSet hashmap of the words in spam/ham frequency maps
     * @param isSpam boolean pre-set value that declares a file to actually be spam or ham.
     * @throws IOException
     */
    public void generateProb(HashMap<String, Integer> dataSet, boolean isSpam) throws IOException {
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