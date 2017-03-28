package sample;

import java.io.*;
import java.net.*;

/**
 * Created by michael on 27/03/17.
 */
public class ClientConnectionHandler implements Runnable {
    private Socket socket;
    private BufferedReader requestInput = null;

    public ClientConnectionHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            requestInput = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            String command;
            String fileName;
            while ((command = requestInput.readLine()) != null) {
                switch (command.toUpperCase()) {
                    case "DIR":
                        //Function for Displaying Dir
                        break;
                    case "UPLOAD":
                        System.out.println("Upload");
                        //Get next input arguement
                        while ((fileName = requestInput.readLine()) != null) {
                            //Function to Download from client, passing filename
                            System.out.println(fileName);
                        }
                        break;
                    case "DOWNLOAD":
                        //Get next input arguement
                        while ((fileName = requestInput.readLine()) != null) {
                            //Function to upload to client, passing filename
                            System.out.println(fileName);
                        }
                        break;

                    default:
                        System.out.println("Bad Input");
                        break;
                }
                requestInput.close();
                break;
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}
