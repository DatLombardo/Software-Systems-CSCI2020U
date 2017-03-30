package sample;

import java.io.*;
import java.net.*;

/**
 * Created by michael on 27/03/17.
 */

public class ServerFTP {
    protected ServerSocket serverSocket;
    private static Socket clientSocket = null;
    public static int port = 3333;

    /**
     * General Constructor, launches the server.
     * @throws IOException
     */
    public ServerFTP() throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server Initialized");
    }

    /**
     * Creates a new thread for any new clients which connect
     * @throws IOException
     */
    public void handleRequests() throws IOException {
        System.out.println("Server FTP is listening on port: " + port);
        while (true) {
            clientSocket = serverSocket.accept();
            System.out.println("Accepted connection : " + clientSocket);
            ClientConnectionHandler handler = new ClientConnectionHandler(clientSocket);
            Thread handlerThread = new Thread(handler);
            handlerThread.start();
        }
    }
    public static void main(String[] args) {
        try{
            ServerFTP server = new ServerFTP();
            server.handleRequests();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
