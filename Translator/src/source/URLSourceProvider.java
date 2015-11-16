package source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Implementation for loading content from specified URL.<br/>
 * Valid paths to load are http://someurl.com, https://secureurl.com, ftp://frpurl.com etc.
 */
public class URLSourceProvider implements SourceProvider {

    private static final String LINE_SEPARATOR = System.lineSeparator();

    @Override
    public boolean isAllowed(String pathToSource) {
        try {
            URL url = new URL(pathToSource);
            url.openConnection().connect();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public String load(String pathToSource) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        URL url = new URL(pathToSource);

        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            stringBuilder.append(currentLine).append(LINE_SEPARATOR);
        }
        reader.close();

        return stringBuilder.toString();
    }
}
