package sample;

import java.io.*;
import java.util.*;

/**
 * Created by michael on 03/03/17.
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
            System.out.println(i);
            processFile(listOfFiles[i]);
        }
        processProb(globalCount, isSpam);
    }
    //Count and create a map for the file, then add to global map.
    public void processFile(File file) throws IOException {
        Scanner scanner = new Scanner(file);
        fileCount = new HashMap<>();
        while (scanner.hasNext()) {
            String word = scanner.next().toLowerCase();
            countWord(word);
        }
        Iterator it = fileCount.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if(!globalCount.containsKey(pair.getKey())){
                globalCount.put(pair.getKey().toString(),1);
            }else{
                int oldCount = globalCount.get(pair.getKey());
                globalCount.put(pair.getKey().toString(), oldCount+1);
            }
            it.remove();
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

    public void processProb(HashMap<String, Integer> dataSet, boolean isSpam) throws IOException {
        Set<String> keys = dataSet.keySet();
        wordGivProb = new HashMap<>();
        Iterator<String> keyIterator = keys.iterator();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            int count = dataSet.get(key);
            if(isSpam) wordGivProb.put(key, (count / (double)spamLength));
            if(!isSpam) wordGivProb.put(key, (count / (double)hamLength));
        }
    }


}