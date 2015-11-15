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

    private static final String LINE_BREAK = "\n";

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
        StringBuilder urlText = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL(pathToSource).openStream()))) {
            String line;
            while((line = br.readLine()) != null) {
                urlText.append(line);
                urlText.append(LINE_BREAK);
            }
        } catch(IOException e) {
            throw new IOException(e.getMessage());
        }
        return urlText.toString();
    }

}
