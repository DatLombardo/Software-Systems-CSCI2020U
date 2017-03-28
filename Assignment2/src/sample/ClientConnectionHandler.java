package sample;

import java.io.*;
import java.net.*;

/**
 * Created by michael on 27/03/17.
 */
public class ClientConnectionHandler implements Runnable {
    private Socket socket;
    //private DataOutputStream out;
    private PrintWriter out;

    public ClientConnectionHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            InputStream is = socket.getInputStream();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(is)
            );
            OutputStream os = socket.getOutputStream();
            //out = new DataOutputStream(os);
            out = new PrintWriter(socket.getOutputStream());

            // Command | filename
            System.out.println("reading");
            String request = in.readLine();
            System.out.println("reading2");

            String[] requestParts = request.split(" ");
            String command = requestParts[0];
            String filename = requestParts[1];
            System.out.println(command);
            if (command.equalsIgnoreCase("DIR")) {
                out.write("Dir Selected");
                out.flush();
            }
            else if (command.equalsIgnoreCase("UPLOAD")) {
                out.write("Upload selected");
                out.flush();
            }
            else if (command.equalsIgnoreCase("DOWNLOAD")) {
                out.write("Download selected");
                out.flush();
            }
            else{
                out.write("Incorrect Usage: [UPLOAD/DOWNLOAD] [filename] or [DIR]");
                out.flush();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
