package httpSamples;

import java.io.*;
import java.net.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;

public class DownloadBikeData {
   public static void main(String[] args) {
      try {
         URL url = new URL("http://www.bikesharetoronto.com/data/stations/bikeStations.xml");
         URLConnection conn = url.openConnection();
         conn.setDoOutput(false);
         conn.setDoInput(true);
         InputStream is = conn.getInputStream();

         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
         Document document = docBuilder.parse(is);
         document.getDocumentElement().normalize();

         NodeList itemNodes = document.getElementsByTagName("stations");

         // pull out the data that we want
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
