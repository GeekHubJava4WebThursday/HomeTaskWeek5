package com.geekhub.hw5.translator.source;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Implementation for loading content from local file system.
 * This implementation supports absolute paths to local file system without specifying file:// protocol.
 * Examples c:/1.txt or d:/pathToFile/file.txt
 */
public class FileSourceProvider implements SourceProvider {

    public static final int BUFFER_SIZE = 8 * 1024;

    @Override
    public boolean isAllowed(String pathToSource) {
        File file = new File(pathToSource);
        return (file.isFile() && file.canRead());
    }

    @Override
    public String load(String pathToSource) throws IOException {
        String text = "";
        File file = new File(pathToSource);
        try(BufferedReader reader = new BufferedReader(new FileReader(file), BUFFER_SIZE)) {
            String line;
            while ((line = reader.readLine()) != null) {
                text = text.concat(line).concat(" ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}
