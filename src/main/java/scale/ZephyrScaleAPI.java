package scale;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ZephyrScaleAPI {
    private static final String BASE_URL = "https://api.zephyrscale.smartbear.com/v2/automations";
    private static final String API_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjb250ZXh0Ijp7ImJhc2VVcmwiOiJodHRwczovL2l0LXN0dWRlbnRzLXRlYW0tbDA0bmxqOWcuYXRsYXNzaWFuLm5ldCIsInVzZXIiOnsiYWNjb3VudElkIjoiNzEyMDIwOmRlMmNmZWRhLTIwYjgtNDZjNC04MWQyLThiMjg3YTZhMzFmOSIsInRva2VuSWQiOiJjNDU0NWY1My02NjIzLTRlN2ItODczNS0yZTk5MmRjNGY1ZTkifX0sImlzcyI6ImNvbS5rYW5vYWgudGVzdC1tYW5hZ2VyIiwic3ViIjoiMDM3NGE5NzgtYzY1Ni0zZTY0LTk0NTgtMGQwNWU4YTExOTRjIiwiZXhwIjoxNzY1NjMzNzczLCJpYXQiOjE3MzQwOTc3NzN9.z1uhY3Ugtck6klQnOz54Q6eptk5N184SOFR61G1SVxE";

    public static void uploadTestResult(String jsonReportPath) throws IOException {
        // Lire le rapport JSON
        String reportContent = new String(Files.readAllBytes(Paths.get(jsonReportPath)));

        // Préparer la requête HTTP
        URL url = new URL(BASE_URL + "/executions");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + API_TOKEN);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        // Envoyer les données
        try (OutputStream os = conn.getOutputStream()) {
            os.write(reportContent.getBytes());
        }

        // Lire la réponse
        int responseCode = conn.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        try (BufferedReader reader = new BufferedReader(
         new InputStreamReader(
             conn.getResponseCode() < 400 ? conn.getInputStream() : conn.getErrorStream()))) {
    StringBuilder response = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
        response.append(line);
    }
    System.out.println("Response: " + response);
}
 catch (IOException e) {
            System.err.println("Error reading API response: " + e.getMessage());
        }
    }
}
