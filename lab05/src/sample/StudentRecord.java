package sample;

public class StudentRecord {
    private String sid;
    private double midterm;
    private double assign;
    private double fExam;
    private double finalMark;
    private String letterGrade;

    public StudentRecord(String sid, double midterm, double assign, double fExam) {
        this.sid = sid;
        this.midterm = midterm;
        this.assign = assign;
        this.fExam = fExam;
        finalMark = (midterm*.3 + assign*.2 + fExam*.5);
        if(finalMark >= 80 && finalMark <= 100.0){
            letterGrade = "A";
        }
        else if (finalMark <= 70 && finalMark < 80){
            letterGrade = "B";
        }
        else if (finalMark <= 60 && finalMark < 70){
            letterGrade = "C";
        }
        else if (finalMark <= 50 && finalMark < 60){
            letterGrade = "D";
        }
        else{
            letterGrade = "F";
        }
    }

    public String getSid() {return sid;}
    public void setSid(String sid) {this.sid = sid;}
    public double getMidterm() {return midterm;}
    public void setMidterm(double midterm) {this.midterm = midterm;}
    public double getAssign() {return assign;}
    public void setAssign(double assign) {this.assign = assign;}
    public double getfExam() {return fExam;}
    public void setfExam(double fExam) {this.fExam = fExam;}


    public void setLetterGrade(String letterGrade) {
        this.letterGrade = letterGrade;
    }
}
