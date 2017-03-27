<<<<<<< HEAD
import java.io.*;

public class FindAverage{
  public static void main(String[] args){
    if (args.length < 2){
      System.err.println("Usage: java FindAverage <inputFile> <columnName>");
      System.exit(0);
    }

    File inputFile = new File(args[0]);
    String desiredColumnName = args[1].trig();

    if(!inputFile.exists()){
      System.err.println("Could not find File: " + inputFile);
      System.exit(0);
    }
    try{
      //Read the =header line
      FileReader reader = new FileReader(inputFile);
      BufferedReader in = new BufferedReader(reader);

      String headerLine = in.readLine();
      String[] columnNames = headerLine.split(",");
      int columnIndex = 0;
      //Determine the column index
      for (int i = 0; i < columnNames.length; i++){
        if(columnNames[i].equals(desiredColumnName)){
          columnIndex = i;
          break;
        }
      }

      //Check if column name was found
      if(columnIndex < 0){
        System.err.println("No such column name: " + desiredColumnName);
        System.exit(0);
      }

      float total = 0f;
      int count = 0;
      String line;

      while((line = in.readLine()) != null){
        if (line.trim().length() != 0){
          String[] dataFields = line.split(",");
          float nextValue = Float.parseFloat(dataFields[columnIndex]);
          total += nextValue;
          count++;
        }
      }

      System.out.printf("The average column %s is %.2f.\n", desiredColumnName, total/count);

    }catch (IOException e){
      e.printStackTrace();
    }

  }
}
=======
import java.io.*;

public class FindAverage {
  public static void main(String[] args) {
    if (args.length < 2) {
      System.err.println("Usage:  java FindAverage <inputFile> <columnName>");
      System.exit(0);
    }

    File inputFile = new File(args[0]);
    String desiredColumnName = args[1].trim();

    if (!inputFile.exists()) {
      System.err.println("Could not find file: " + inputFile);
      System.exit(0);
    }

    try {
      FileReader reader = new FileReader(inputFile);
      BufferedReader in = new BufferedReader(reader);

      // read the header line
      String headerLine = in.readLine();
      String[] columnNames = headerLine.split(",");
      int columnIndex = -1;

      // determine the column index to average
      for (int i = 0; i < columnNames.length; i++) {
        if (columnNames[i].equals(desiredColumnName)) {
          columnIndex = i;
          break;
        }
      }

      // check if the column name was found
      if (columnIndex < 0) {
        System.err.println("No such column name: " + desiredColumnName);
        System.exit(0);
      }

      // read the data lines, and calculate the average
      float total = 0f;
      int count = 0;
      String line;
      while ((line = in.readLine()) != null) {
        if (line.trim().length() != 0) {
          String[] dataFields = line.split(",");
          float nextValue = Float.parseFloat(dataFields[columnIndex]);
          total += nextValue;
          count++;
        }
      }

      System.out.printf("The average for column %s is %.2f.\n", desiredColumnName, total/count);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
