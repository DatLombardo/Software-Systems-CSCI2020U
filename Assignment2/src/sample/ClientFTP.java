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
    public static int port = 3333;


    public final String clientPath = "/home/michael/Desktop/Java/Assignment2/client/";

    public static void main(String[] args) throws IOException {
        try {
            sock = new Socket("localhost", port);
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
                    giveUpload(fileName);
                    //Upload Func
                    break;
                case "DOWNLOAD":
                    output.println("DOWNLOAD");
                    output.flush();
                    System.out.println("Enter file to download");
                    fileName = input.readLine();
                    output.println(fileName);
                    output.flush();
                    getDownload();
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

    public static void giveUpload(String fileName){
        System.out.println("Giving File");
        try {
            //Initialize File and get it's length, store into byte array
            final String clientPath = "/home/michael/Desktop/Java/Assignment2/client/";
            File file = new File(clientPath + fileName);
            byte[] mybytearray = new byte[(int) file.length()];

            //Initialize streams
            FileInputStream fileInput = new FileInputStream(file);
            DataInputStream dataInput = new DataInputStream(new BufferedInputStream(fileInput));
            dataInput.readFully(mybytearray, 0, mybytearray.length);
            OutputStream outStream = sock.getOutputStream();

            //Sending file name and file size to the server
            DataOutputStream dataOutput = new DataOutputStream(outStream);
            dataOutput.writeUTF(file.getName());
            dataOutput.writeLong(mybytearray.length);
            dataOutput.write(mybytearray, 0, mybytearray.length);
            dataOutput.flush();
            System.out.println("File " + fileName + " has been sent to server");

            //Close socket and stream after transfer
            dataOutput.close();
            sock.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void getDownload(){
        System.out.println("Getting File");
        try {
            int bytesRead;
            final String clientPath = "/home/michael/Desktop/Java/Assignment2/client/";
            DataInputStream clientGet = new DataInputStream(sock.getInputStream());
            String fileName = clientGet.readUTF();
            long size = clientGet.readLong();
            byte[] buffer = new byte[1024];
            File file = new File(clientPath + fileName);

            //If file does not exist, create file.
            file.createNewFile();
            OutputStream outStream = new FileOutputStream(file, false);

            //Send the file
            while (size > 0 && (bytesRead = clientGet.read(buffer, 0, buffer.length - 0)) > -1) {
                outStream.write(buffer, 0, bytesRead);
                size -= bytesRead;
            }
            System.out.println("File " + fileName + " has been received");

            //Close socket and streams after transfer
            outStream.close();
            clientGet.close();
            sock.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
