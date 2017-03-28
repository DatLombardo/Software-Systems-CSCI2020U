package csci2020u.samples.sockets;

import java.io.*;
import java.net.*;

public final class HttpServer {
   private ServerSocket serverSocket = null;

   public HttpServer(int port) throws IOException {
      serverSocket = new ServerSocket(port);
   }

   public void handleRequests() throws IOException {
      System.out.println("Simple Http Server v1.0: Ready to handle incoming requests.");

      // repeatedly handle incoming requests
      while(true) {
         Socket socket = serverSocket.accept();
         Thread handlerThread = new Thread(new HttpRequestHandler(socket));
         handlerThread.start();
      }
   }

   public static void main(String[] args) {
      int port = 8080;
      if (args.length > 0)
         port = Integer.parseInt(args[0]);
      try {
         HttpServer server = new HttpServer(port);
         server.handleRequests();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
