import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.server.ExportException;

public class Main {

    private static String pathToSaveImage = "D:\\image.jpg";
    private static final String POST_PARAMS = "user = FAF_161";
    private static final String USER_AGENT = "Chrome/70";
    private static String GET_IMAGE_URL = "https://httpbin.org/image";
    private static String REQUEST_URL = "https://httpbin.org";

    public static void main(String[] args) throws IOException {

        sendRequest("GET");
    }






    private static void sendGet() throws IOException {
        URL obj = new URL(GET_IMAGE_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success

            try(InputStream in = con.getInputStream()){
                Files.copy(in, Paths.get(pathToSaveImage));
                in.close();
            }
            catch (ExportException ex){
                ex.printStackTrace();
            }

            System.out.println("Now you can see the image in: " + pathToSaveImage);
        } else {
            System.out.println("GET request not worked");
        }

    }


    private static void sendRequest(String requestType) throws IOException {
        URL obj = new URL(REQUEST_URL + "/" + requestType.toLowerCase());
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod(requestType.toUpperCase());
        con.setRequestProperty("User-Agent", USER_AGENT);


        if(requestType.toUpperCase().equals("POST")){
            // For POST only - START
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            os.write(POST_PARAMS.getBytes());
            os.flush();
            os.close();
            // For POST only - END
        }

        if(requestType.toUpperCase().equals("GET")){
           sendGet();
           return;
        }

        int responseCode = con.getResponseCode();
        System.out.println(requestType + " Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
        } else {
            System.out.println(requestType + " request not worked");
        }
    }



}
