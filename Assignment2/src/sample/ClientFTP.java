package sample;


import java.io.*;
import java.net.*;

/**
 * Created by michael on 27/03/17.
 */
public class ClientFTP {
    private static Socket sock;
    protected static BufferedReader inny;
    private static PrintWriter os;

    public static void main(String[] args) throws IOException {
        String command = args[0];
        String filename = "-";
        if (args[1] != null) {
            filename = args[1];
        }

        try {
            sock = new Socket("localhost", 3333);
            System.out.println("Connected");
            os = new PrintWriter(sock.getOutputStream());
            os.write(command + " " + filename);
            os.flush();
            InputStream is = sock.getInputStream();
            inny = new BufferedReader(new InputStreamReader(is));
            while(inny.readLine() == null) {
                System.out.println("Waiting Response");
            }
            String returnMess = inny.readLine();
            System.out.println(returnMess);
        } catch (IOException e) {
            System.err.println("Cannot connect to the server, try again later.");
            sock.close();
            System.exit(0);
        }

        sock.close();
    }
}
