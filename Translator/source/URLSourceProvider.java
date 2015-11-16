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
        try{
            URL url = new URL(pathToSource);
            try{
                url.openConnection().connect();
            } catch (IOException e){
                e.printStackTrace();
            }
        } catch (MalformedURLException e){
            return false;
        }
        return true;
    }

    @Override
    public String load(String pathToSource) throws IOException {
        StringBuilder urlStr = new StringBuilder();
        try(BufferedReader bufferStr = new BufferedReader(new InputStreamReader(new URL(pathToSource).openStream(), "UTF-8"))){
            while(bufferStr.ready()){
                urlStr.append((char)bufferStr.read());
            }
        } catch(IOException e){
            e.printStackTrace();
         }
        return urlStr.toString();
    }
}
