package sample;

import java.io.*;
import java.util.*;

/**
 * Created by michael on 05/03/17.
 */
public class Testing {
    public List<TestFile> testData = new ArrayList<>();
    public Testing(File[] listOfFiles, Map<String, Double> wordMap) throws IOException{
        CalculateProb(listOfFiles, wordMap);
    }
    private void CalculateProb(File[] listOfFiles, Map<String, Double> wordMap) throws IOException {
        for (File file : listOfFiles) {
            String path = file.getPath();

            String actualClass = "Ham";
            if (path.contains("spam")) {
                actualClass = "Spam";
            }

            double sum = 0;

            FileReader fileReader = new FileReader(file);
            Scanner scanner = new Scanner(fileReader);
            while (scanner.hasNext()) {
                String word = scanner.next().toLowerCase();
                //if (word.matches(WORD_REGEX)) {
                    if (wordMap.containsKey(word)) {
                        double wordSpamProbability = wordMap.get(word);
                        if (wordSpamProbability > 0.0f && wordSpamProbability < 1.0f) {
                            sum += Math.log(1 - wordSpamProbability)
                                    - Math.log(wordSpamProbability);
                        }
                    }
                //}
            }
            fileReader.close();

            double spamProbability = 1 / (1 + Math.pow(Math.E, sum));
            TestFile testFile = new TestFile(path, spamProbability, actualClass);
            testData.add(testFile);
        }
    }
}
