package source;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Implementation for loading content from specified URL.<br/>
 * Valid paths to load are http://someurl.com, https://secureurl.com, ftp://frpurl.com etc.
 */
public class URLSourceProvider implements SourceProvider {

    @Override
    public boolean isAllowed(String pathToSource) {
        try {
            new URL(pathToSource).openStream();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public String load(String pathToSource) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(pathToSource).openConnection();
        int responseCode = connection.getResponseCode();

        if (responseCode != 200) {
            responseCodeHandling(responseCode);
        }

        String interLine, finalLine = "";
        try (BufferedReader is = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            while ((interLine = is.readLine()) != null) {
                finalLine += interLine;
            }
        }

        return finalLine;
    }

    private void responseCodeHandling(int code) throws IOException {
        switch (code) {
            case 401: throw new IOException("Invalid API key");
            case 402: throw new IOException("API key is blocked");
            case 403: throw new IOException("You have exceeded the daily limit on the number of requests");
            case 404: throw new IOException("You have exceeded the daily limit on the amount of translated text");
            case 413: throw new IOException("Exceeded the maximum size of the text");
            case 422: throw new IOException("Text can not be translated");
            case 501: throw new IOException("Set the direction of translation is not supported");
            default:  throw new IOException("Something happened");
        }
    }
}
