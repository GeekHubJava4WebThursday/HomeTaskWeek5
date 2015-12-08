package com.company.src;

import java.net.MalformedURLException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

/**
 * Created by InnaBakum on 01.12.2015.
 */
public class URLSourceProvider implements SourceProvider {

    private static final int HTTP_STATUS_OK = 200;

    @Override
    public boolean isAllowed(String pathToSource) {
        try {
            URL url = new URL(pathToSource);
            url.openConnection();
            return true;
        } catch (MalformedURLException e) {
            return false;
        }catch (IOException ex){
            return false;
        }
    }

    @Override
    public String load(String pathToSource) throws IOException {
        URL url = new URL(pathToSource);
        StringBuilder stringBuilder = new StringBuilder();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Accept-Encoding", "identity");
        if (HTTP_STATUS_OK != connection.getResponseCode()) {
            throw new IOException();
        }

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()
                ));

        return reader.lines().map(line -> {return line;}
        ).collect(Collectors.joining("\n"));
    }
}