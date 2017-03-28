package csci2020u.samples.sockets;

import java.io.*;
import java.net.*;
import org.json.*;

public class YouTubeSearch {
    public static void main(String[] args) {
        try {
            String keyword = "SpaceX";
            String searchUrl = "https://www.googleapis.com/youtube/v3/search?part=id%2Csnippet&q=" + keyword + "&key=AIzaSyBKl-NXWdXHsj6dCYrNRqgU5k5h_bH6Ua8";
            URL url = new URL(searchUrl);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(false);
            conn.setDoInput(true);
            InputStream inStream = conn.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inStream));
            String line = null;
            String jsonData = "";

            while ((line = in.readLine()) != null) {
                jsonData += line + "\n";
            }
            //System.out.println("-----");
            //System.out.println(jsonData);
            //System.out.println("-----");
            
            JSONObject obj = new JSONObject(jsonData);
            JSONArray arr = obj.getJSONArray("items");
            for (int i = 0; i < arr.length(); i++) {
                String kind = arr.getJSONObject(i).getString("kind");
                JSONObject snippet = arr.getJSONObject(i).getJSONObject("snippet");
                String title = snippet.getString("title");
                String publishedAt = snippet.getString("publishedAt");
                
                System.out.printf("%s (%s)\n", title, publishedAt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
