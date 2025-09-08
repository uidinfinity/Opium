package we.devs.opium.client.integration.spotify;

import we.devs.opium.client.OpiumClient;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

/**
 * broken
 */
public class SpotifyPlayer {
    private static final String CLIENT_ID = "789dadc18e234f159884826951f0e826";
    private static final String CLIENT_SECRET = "3fd9a81d4e744f79ae2aab842af0cff3";
    private static final String REFRESH_TOKEN = "AQCILM3g3QDIz4aNHlaR03DRZ4do8zv0CK9W9kWN1uuo_Yi26AksHlPVXOoeJ3jJufVU87v1eGNa25d7fvfCbBBnETWXnda1oGg-F74FLu7ZwU-O9pRR8mFi1F9PIVQ70f0"; // Get after OAuth flow

    public static void init() {
        try {
            String accessToken = getAccessToken();
            getCurrentPlayingTrack(accessToken);
        } catch (Exception e) {
            OpiumClient.LOGGER.error("SpotifyPlayer initialization failed!");
        }
    }

    private static String getAccessToken() throws IOException {
        String auth = CLIENT_ID + ":" + CLIENT_SECRET;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        URL url = new URL("https://accounts.spotify.com/api/token");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Authorization", "Basic " + encodedAuth);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        String body = "grant_type=refresh_token&refresh_token=" + REFRESH_TOKEN;
        try (OutputStream os = conn.getOutputStream()) {
            os.write(body.getBytes());
        }

        InputStream response = conn.getInputStream();
        return parseAccessToken(response);
    }

    public static String getCurrentPlayingTrack(String accessToken) throws IOException {
        URL url = new URL("https://api.spotify.com/v1/me/player/currently-playing");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);

        InputStream response = conn.getInputStream();
        return new BufferedReader(new InputStreamReader(response))
                .lines()
                .reduce("", (acc, line) -> acc + line + "\n");
    }

    private static String parseAccessToken(InputStream response) throws IOException {
        // Parse JSON response to extract access token (use a JSON parser like GSON or Jackson)
        // For simplicity, just returning a placeholder token here
        return "your-access-token";
    }
}
