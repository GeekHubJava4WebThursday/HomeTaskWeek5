package source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Implementation for loading content from specified URL.<br/>
 * Valid paths to load are http://someurl.com, https://secureurl.com, ftp://frpurl.com etc.
 */
public class URLSourceProvider implements SourceProvider {

    @Override
    public boolean isAllowed(String pathToSource) {
        //TODO: implement me
        try {
            URL url = new URL(pathToSource);

            if (url.getProtocol().equalsIgnoreCase("ftp")) {
                return true;
            }

            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            if (http.getResponseCode()==200){
                return true;
            }else {
                return false;
            }
        } catch (Exception e) {
           return false;
        }
    }

    @Override
    public String load(String pathToSource) throws IOException {
        //TODO: implement me
        URL url = new URL(pathToSource);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(url.openStream()));

        String currentLine;
        StringBuilder result = new StringBuilder();
        while ((currentLine = in.readLine()) != null) {
            result.append(currentLine);
        }
        in.close();
        return result.toString();
    }
}
