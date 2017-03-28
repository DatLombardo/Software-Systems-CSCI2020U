package csci2020u.samples.sockets;

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
            InputStream inStream = conn.getInputStream();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
            Document document = docBuilder.parse(inStream);
            document.getDocumentElement().normalize();

            NodeList itemNodes = document.getElementsByTagName("station");

            for (int i = 0; i < itemNodes.getLength(); i++) {
               Node itemNode = itemNodes.item(i);
               if (itemNode.getNodeType() == Node.ELEMENT_NODE) {
                  Element itemElement = (Element)itemNode;
                  String id = getTagValue("id", itemElement);
                  String name = getTagValue("name", itemElement);
                  String lat = getTagValue("lat", itemElement);
                  String lng = getTagValue("long", itemElement);
                  String numBikes = getTagValue("nbBikes", itemElement);
                  System.out.printf("[%s] %s (%s,%s): %s bikes\n", id, name, lat, lng, numBikes);
               }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static String getTagValue(String tagName, Element element) {
        NodeList tags = element.getElementsByTagName(tagName);
        if (tags.getLength() > 0) {
            return tags.item(0).getTextContent();
        } else {
            return null;
        }
    }
}