package sample;

import java.io.*;
import java.net.*;

import static javafx.application.Application.launch;

/**
 * Created by michael on 27/03/17.
 */
public class BullitenHandler implements Runnable {
    private Socket socket;
    private DataOutputStream out;
    public String message;

    public BullitenHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            InputStream is = socket.getInputStream();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(is));
            message = in.readLine();
            if (message != null){
                System.out.println(message);

            }
            OutputStream os = socket.getOutputStream();
            out = new DataOutputStream(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
