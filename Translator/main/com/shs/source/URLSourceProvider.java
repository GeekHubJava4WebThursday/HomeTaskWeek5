package com.shs.source;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Implementation for loading content from specified URL.<br/>
 * Valid paths to load are http://someurl.com, https://secureurl.com, ftp://frpurl.com etc.
 */
public class URLSourceProvider implements SourceProvider {

    @Override
    public boolean isAllowed(String pathToSource) {
        try {
            new URL(pathToSource);
        } catch (MalformedURLException e) {
            return false;
        }
        return true;
    }

    @Override
    public String load(String pathToSource) throws IOException {

        URLConnection urlConnection = new URL(pathToSource).openConnection();
        StringBuilder strBuilder = new StringBuilder();
        String text;

        try (BufferedReader bufReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {

            while ((text = bufReader.readLine()) != null)
                strBuilder.append(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strBuilder.toString();
    }
}
