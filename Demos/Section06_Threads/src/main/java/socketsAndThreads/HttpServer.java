package socketsAndThreads;

import java.io.*;
import java.net.*;

public class HttpServer {
   private ServerSocket serverSocket;
   private int port;

   public HttpServer(int port) throws IOException {
      this.port = port;
      serverSocket = new ServerSocket(port);
   }

   public void handleRequests() throws IOException {
      System.out.println("MoonServer v0.9 listening on port " + port);

      while (true) {
         Socket socket = serverSocket.accept();
         HttpRequestHandler handler =
                 new HttpRequestHandler(socket);
         Thread handlerThread = new Thread(handler);
         handlerThread.start();
      }
   }

   public static void main(String[] args) {
      int port = 8080;
      if (args.length > 0) {
         port = Integer.parseInt(args[0]);
      }

      try {
         HttpServer server = new HttpServer(port);
         server.handleRequests();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
