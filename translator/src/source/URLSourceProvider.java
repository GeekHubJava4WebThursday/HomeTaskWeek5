package source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementation for loading content from specified URL.<br/>
 * Valid paths to load are http://someurl.com, https://secureurl.com, ftp://frpurl.com etc.
 */
public class URLSourceProvider implements SourceProvider {

    @Override
    public boolean isAllowed(String pathToSource) {

        String regexPath = "^(https?|ftp)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Matcher matcher = Pattern.compile(regexPath).matcher(pathToSource);

        if(matcher.matches()) {
            try {
                new URL(pathToSource).openConnection();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public String load(String pathToSource) throws IOException {

        String inputLine;
        StringBuilder stringBuilder = new StringBuilder();
        HttpURLConnection url = (HttpURLConnection) new URL(pathToSource).openConnection();

        if (url.getResponseCode() == 200) {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.getInputStream(), ENCODING))) {
                while ((inputLine = bufferedReader.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new IOException("Server returned HTTP response code: " + url.getResponseCode() +" - "+ url.getResponseMessage());
        }

        return stringBuilder.toString();
    }
}
