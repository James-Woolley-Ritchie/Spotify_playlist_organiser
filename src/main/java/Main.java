import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class Main {
    // Client id and secret from spotify developers dashboard.
    private static final String client_id = "17e7bafabccb435fbbb651888ea05804";
    private static final String client_secret = "567c44fad2094ba88f689da99afc9735";

    // Authorisation POST request.
    public static String post_authorisation_request() {
        try {
            // Set the url we want to send requests to.
            URL url = new URL("http://accounts.spotify.com/api/token");
            String encoded_string = Base64.getEncoder().encodeToString(String.format("%s:%s", client_id, client_secret).getBytes());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("grant_type", "client_credentials");
            connection.setRequestProperty("Authorization", "Basic " + encoded_string);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setUseCaches(false);
            connection.setDoOutput(true);

            // Send the request.
            DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
            writer.close();

            // Get the response.
            InputStream input = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
                response.append("\n");
            }
            reader.close();
            return response.toString();
        } catch (IOException exception) {
            System.out.printf("Error: %s.\n", exception.getMessage());
            exception.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    // Playlist GET request.
    private static HttpURLConnection connection = null;
    public static String get_playlist_request(String target_url, String parameters) {
        try {
            // Set the url we want to send requests to.
            URL url = new URL(target_url);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setDoOutput(true);

            // Send the request.
            DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
//            writer.writeBytes(parameters);
            writer.close();

            // Get the response.
            InputStream input = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
                response.append("\n");
            }
            reader.close();
            return response.toString();
        } catch (IOException exception) {
            System.out.printf("Error: %s.\n", exception.getMessage());
            exception.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(get_playlist_request("https://api.spotify.com/v1/playlists/28PgwZmKFI9Dsf43HgMC9E", null));
    }
}
