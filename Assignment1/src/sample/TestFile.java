package sample;

/**
 * Created by michael on 03/03/17.
 */

import java.text.DecimalFormat;

//Used for output
public class TestFile {
    private String filename;
    private double spamProbability;
    private String probRounded;
    private String actualClass;
    private String guessClass;
    private int correctGuess;
    public TestFile(String filename,
                    double spamProbability,
                    String guessClass,
                    String actualClass) {
        this.filename = filename.substring(filename.indexOf("0"));
        this.spamProbability = spamProbability;
        this.guessClass = guessClass;
        this.probRounded = getSpamProbRounded();
        this.actualClass = actualClass;
        if(actualClass.equalsIgnoreCase(guessClass)){
            correctGuess = 1;
        }else{
            correctGuess = 0;
        }
    }
    public String getFilename() { return this.filename; }
    public double getSpamProbability() { return this.spamProbability; }
    public String getSpamProbRounded() {
        DecimalFormat df = new DecimalFormat("0.00000");
        return df.format(this.spamProbability);
    }
    public String getActualClass() { return this.actualClass; }
    public void setFilename(String value) { this.filename = value; }
    public void setSpamProbability(double val) { this.spamProbability = val; }
    public void setActualClass(String value) { this.actualClass = value; }

    public String getProbRounded() {return probRounded;}
    public void setProbRounded(String probRounded) {this.probRounded = probRounded;}
    public String getGuessClass() {return guessClass;}
    public void setGuessClass(String guessClass) {this.guessClass = guessClass;}

    public int getCorrectGuess() {
        return correctGuess;
    }

    public void setCorrectGuess(int correctGuess) {
        this.correctGuess = correctGuess;
    }
}
