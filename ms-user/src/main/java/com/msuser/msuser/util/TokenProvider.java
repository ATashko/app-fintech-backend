package com.msuser.msuser.util;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Base64;

@Service
public class TokenProvider {

    public String requestToken(String username, String password) {
        try {
            return getToken("http://localhost:9090/realms/triwal-realm-dev/protocol/openid-connect/token",
                    "triwal-app", "2AW8lVZlq25DVBwI8UTLRYaK95bQsg9p", username, password);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static String getToken(String tokenUrl, String clientId, String clientSecret, String username, String password) throws IOException, URISyntaxException {
        String credentials = clientId + ":" + clientSecret;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        URL url = new URL(tokenUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Authorization", "Basic " + encodedCredentials);
        connection.setDoOutput(true);

        String data = "grant_type=password&username=" + username + "&password=" + password;

        connection.getOutputStream().write(data.getBytes());

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        return response.toString();
    }


}
