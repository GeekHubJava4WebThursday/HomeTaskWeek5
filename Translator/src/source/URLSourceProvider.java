package source;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Implementation for loading content from specified URL.<br/>
 * Valid paths to load are http://someurl.com, https://secureurl.com, ftp://frpurl.com etc.
 */
public class URLSourceProvider implements SourceProvider {

    public static final int BUFFER_SIZE = 16 * 1024;

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

        InputStream is = new BufferedInputStream(url.openConnection().getInputStream(), BUFFER_SIZE);
        int currentByte;
        while ((currentByte = is.read()) > 0) {
            stringBuilder.append((char) currentByte);
        }
        is.close();

        return stringBuilder.toString();
    }
}
