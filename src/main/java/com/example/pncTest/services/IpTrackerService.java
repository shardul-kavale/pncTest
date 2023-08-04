package com.example.pncTest.services;

import com.example.pncTest.model.IPLookupPojo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class IpTrackerService {

    private final ObjectMapper objectMapper;

    public IpTrackerService() {
        this.objectMapper = new ObjectMapper();
    }


    public IPLookupPojo lookupIP(String ipAddress) throws IOException {

        String url = "http://ip-api.com/json/" + ipAddress;
        URL apiUrl = new URL(url);

        try{
            HttpURLConnection con = (HttpURLConnection) apiUrl.openConnection();
            con.setRequestMethod("GET");

            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                    // Deserialize the JSON response into the IPLookupPojo class
                    return objectMapper.readValue(reader, IPLookupPojo.class);
                }
            } else {
                // Handle the error response here if needed
                throw new IOException("Request failed with status: " + con.getResponseCode());
            }

        }catch (IOException e){
            throw new IOException("Error connecting to the IP API service: " + e.getMessage(), e);
        }


    }


}
