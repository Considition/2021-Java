import com.google.gson.*;
import models.*;
import responses.FetchResponse;
import responses.GameResponse;
import responses.SubmitResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.util.ArrayList;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;


public class Api {
    private static final String BasePath ="https://game.considition.com/api/games";
    private static final Gson gson;

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }

    public static GameResponse NewGame(String mapName, String apiKey) {
       try {

            URL url = new URL(BasePath + "/new?MapName=" + mapName);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("x-api-key", apiKey);
            con.setRequestMethod("GET");
            con.setDoInput(true);
            String response = readApiResponse(con);
            return gson.fromJson(response, GameResponse.class);

        } catch (Exception e) {
            System.out.println("Fatal error: Could not get map");
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }

    public static SubmitResponse SubmitGame(ArrayList<PointPackage> solution, String mapName, String apiKey){
        try {
           
            URL url = new URL(BasePath + "/submit?MapName=" + mapName);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("x-api-key", apiKey);
            con.setRequestMethod("POST");
            con.setDoInput(true);
            String solutionInJson = gson.toJson(solution);
            String response = doPost(con, solutionInJson);
            return gson.fromJson(response, SubmitResponse.class);

        } catch (Exception e) {
            System.out.println("Fatal error: Could not submit game");
            System.out.println("Error: " + e.getMessage());
            System.exit(1);
            return null;
        }
    }

    public static ArrayList<FetchResponse> FetchGame(String apiKey){
                try {
                URL url = new URL(BasePath + "/fetch");

                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestProperty("x-api-key", apiKey);
                con.setRequestMethod("GET");
                con.setDoInput(true);
                String response = readApiResponse(con);
                ArrayList<FetchResponse> outputList = gson.fromJson(response, ArrayList.class);
                return outputList;

            } catch (Exception e) {
                System.out.println("Fatal error: Could not fetch games");
                System.out.println("Error: " + e.getMessage());
                System.exit(1);
                return null;
            }
    }

    public static FetchResponse FetchGame(String apiKey, String gameId){
        try {
            
            URL url = new URL(BasePath + "/fetch?GameId=" + gameId);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("x-api-key", apiKey);
            con.setRequestMethod("GET");
            con.setDoInput(true);
            String response = readApiResponse(con);
            return gson.fromJson(response, FetchResponse.class);

        } catch (Exception e) {
            System.out.println("Fatal error: Could not fetch game");
            System.out.println("Error: " + e.getMessage());
            System.exit(1);
            return null;
        }
    }


    private static String readApiResponse(HttpURLConnection con) throws IOException {

        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String output = br.readLine();
            String response = output;
            return response;
        } else {
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            String response = br.readLine();
            throw new IOException("Http error with code " + con.getResponseCode() + " and message: " + response);
        }
    }

    private static String doPost(HttpURLConnection con, String body) throws IOException {
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("accept", "text/plain");
        con.setRequestProperty("Content-Type", "application/json");

        OutputStream os = con.getOutputStream();
        os.write(body.getBytes());
        os.flush();
        os.close();

        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String response = br.readLine();
            return response;
        } else {
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            String response = br.readLine();
            throw new IOException("Http error with code " + con.getResponseCode() + " and message: " + response);
        }
    }
}
