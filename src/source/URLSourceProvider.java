package source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
            URLConnection connection = new URL(pathToSource).openConnection();
            connection.connect();
        } catch (MalformedURLException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public String load(String pathToSource) throws IOException {
        URLConnection connection = new URL(pathToSource).openConnection();
        BufferedReader bReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String text = "";

        for (String line = bReader.readLine(); line != null; line = bReader.readLine()) {
            text += line;
        }

        return text;
    }
}
