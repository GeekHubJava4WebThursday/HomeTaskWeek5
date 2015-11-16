package com.geekhub.hw5.translator.source;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Implementation for loading content from specified URL.<br/>
 * Valid paths to load are http://someurl.com, https://secureurl.com, ftp://frpurl.com etc.
 */
public class URLSourceProvider implements SourceProvider {

    public static final int BUFFER_SIZE = 8 * 1024;

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

        URL url = new URL(pathToSource);
        try(InputStream is = new BufferedInputStream(url.openConnection().getInputStream(), BUFFER_SIZE);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            int length;
            while((length = is.read()) >= 0) {
                byteArrayOutputStream.write(length);
            }
            return byteArrayOutputStream.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
