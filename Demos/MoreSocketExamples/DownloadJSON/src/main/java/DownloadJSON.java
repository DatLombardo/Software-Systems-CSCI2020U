import java.io.*;
import java.net.*;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

public class DownloadJSON {
    public static void main(String[] args) {
        try {
            String searchUrl = "http://api.coindesk.com/v1/bpi/currentprice/CAD.json";
            URL url = new URL(searchUrl);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(false);
            conn.setDoInput(true);
            InputStream inStream = conn.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inStream));
            
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject)parser.parse(in);
            JSONObject bpi = (JSONObject)obj.get("bpi");
            JSONObject cad = (JSONObject)bpi.get("CAD");
            double rate = (Double)cad.get("rate_float");
            System.out.println("Rate: " + rate);

            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
            
}