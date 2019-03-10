import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.zip.GZIPInputStream;

public class HttpClientRequest {
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36";

    private static final String GET_URL = "https://httpbin.org/get";
    private static final String POST_URL = "https://httpbin.org/post";
    private static final String DELETE_URL = "https://httpbin.org/delete";
    private static final String IMAGE_URL = "https://httpbin.org/image";

    public static void main(String[] args) throws Exception {

        sendGET();
        sendPost();
        sendDelete();
        getImage();
    }

    private static void sendGET() throws Exception {
        URL obj = new URL(GET_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Encoding", "gzip");
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
         //   BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuffer response = new StringBuffer();

            Reader reader = null;
            if ("gzip".equals(con.getContentEncoding())) {
                reader = new InputStreamReader(new GZIPInputStream(con.getInputStream()));
            } else {
                reader = new InputStreamReader(con.getInputStream());
            }
            while (true) {
                int ch = reader.read();
                if (ch == -1) {
                    break;
                }
                System.out.print((char) ch);
            }
       }
        System.out.println("-------------------------------------------------------");
    }

    private static void sendPost() throws Exception {


        URL obj = new URL(POST_URL);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        //
        //String urlParameters = "mail=example@gmail.com&student=FAF"; here we can add some fields

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
//        wr.writeBytes(urlParameters);    writing addtitional information
//        wr.flush();
//        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nPOST request to URL : " + POST_URL);
      //  System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

        StringBuffer response = new StringBuffer();

        Reader reader = null;
        if ("gzip".equals(con.getContentEncoding())) {
            reader = new InputStreamReader(new GZIPInputStream(con.getInputStream()));
        }
        else {
            reader = new InputStreamReader(con.getInputStream());
        }
        while (true) {
            int ch = reader.read();
            if (ch==-1) {
                break;
            }
            System.out.print((char)ch);
        }

          in.close();

        //print result
        System.out.println(response.toString() + "\n");
        System.out.println("-------------------------------------------------------");

    }

    private static void sendDelete() throws IOException {
        URL deleteURL = new URL(DELETE_URL);
        HttpsURLConnection con = (HttpsURLConnection) deleteURL.openConnection();

        //add reuqest header
        con.setRequestMethod("DELETE");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        //
        //String urlParameters = "mail=example@gmail.com&student=FAF"; here we can add some fields

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
//        wr.writeBytes(urlParameters);    writing addtitional information
//        wr.flush();
//        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nDELETE request to URL : " + DELETE_URL);
        //  System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

        StringBuffer response = new StringBuffer();

        Reader reader = null;
        if ("gzip".equals(con.getContentEncoding())) {
            reader = new InputStreamReader(new GZIPInputStream(con.getInputStream()));
        }
        else {
            reader = new InputStreamReader(con.getInputStream());
        }


        while (true) {
            int ch = reader.read();
            if (ch==-1) {
                break;
            }
            System.out.print((char)ch);
        }

        in.close();

        //print result
        System.out.println(response.toString() + "\n");

    }

    private static void getImage() throws IOException {
        Image image = null;
        try {
            URL imageURL = new URL(IMAGE_URL);
            HttpsURLConnection con = (HttpsURLConnection) imageURL.openConnection();


//        //add reuqest header
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);
            image = ImageIO.read(imageURL);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame();
        frame.setSize(500, 500);
        JLabel label = new JLabel(new ImageIcon(image));
        frame.add(label);
        frame.setVisible(true);
    }

}



