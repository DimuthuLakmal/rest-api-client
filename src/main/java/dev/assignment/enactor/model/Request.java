package dev.assignment.enactor.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class Request {
    String date;
    char startStop;
    char endStop;
    int passengerCount;
    String url;
    String requestBody;

    public Request(String date, char startStop, char endStop, int passengerCount, String url) {
        this.date = date;
        this.startStop = startStop;
        this.endStop = endStop;
        this.passengerCount = passengerCount;
        this.url = url;
    }

    public abstract void buildRequestBody();

    public String sendRequest() throws IOException {
        URL url = new URL(this.url);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        try(OutputStream os = con.getOutputStream()) {
            byte[] input = this.requestBody.getBytes("utf-8");
            os.write(input, 0, input.length);

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return response.toString();
            }
        }
    }
}
