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
        URL url;
        try {
            url = new URL(pathToSource);
        } catch (MalformedURLException e) {
            return false;
        }
        return true;
    }

    @Override
    public String load(String pathToSource) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try(BufferedReader bf = new BufferedReader(new InputStreamReader(new URL(pathToSource).openStream(), "UTF-8"))) {
            while(bf.ready()){
                stringBuilder.append((char)bf.read());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}
