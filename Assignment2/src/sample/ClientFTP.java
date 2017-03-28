package sample;


import java.io.*;
import java.net.*;

/**
 * Created by michael on 27/03/17.
 */
public class ClientFTP {
    private static Socket sock;
    protected static BufferedReader input;
    private static PrintWriter output;
    protected static String fileName;

    public static void main(String[] args) throws IOException {
        try {
            sock = new Socket("localhost", 3333);
            System.out.println("Connected");
            input = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException e) {
            System.out.println("Connection Failed");
            sock.close();
            System.exit(0);
        }

        output = new PrintWriter(sock.getOutputStream());

        try{
            switch(getCommand()){
                case "DIR":
                    output.println("DIR");
                    output.flush();
                    //Dir Func
                    break;
                case "UPLOAD":
                    output.println("UPLOAD");
                    output.flush();
                    System.out.println("Enter file to upload");
                    fileName = input.readLine();
                    output.println(fileName);
                    output.flush();
                    //Upload Func
                    break;
                case "DOWNLOAD":
                    output.println("DOWNLOAD");
                    output.flush();
                    System.out.println("Enter file to download");
                    fileName = input.readLine();
                    output.println(fileName);
                    output.flush();
                    //Download Func
                    break;
            }
        }catch(IOException e1){
            e1.printStackTrace();

        }

        sock.close();
    }

    public static String getCommand() throws IOException {
        System.out.println("Usage: COMMAND (DIR, UPLOAD, DOWNLOAD)");
        return input.readLine();
    }
}
