package com.shs.source;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Implementation for loading content from local file system.
 * This implementation supports absolute paths to local file system without specifying file:// protocol.
 * Examples c:/1.txt or d:/pathToFile/file.txt
 */
public class FileSourceProvider implements SourceProvider {

    @Override
    public boolean isAllowed(String pathToSource) {
        Path path = FileSystems.getDefault().getPath(pathToSource);
        return Files.isRegularFile(path);
    }

    @Override
    public String load(String pathToSource) throws IOException {
        Path path = FileSystems.getDefault().getPath(pathToSource);
        StringBuilder strBuilder = new StringBuilder();

        try (BufferedReader bufReader = new BufferedReader(new InputStreamReader(Files.newInputStream(path)))) {
            String line ;
            while ((line = bufReader.readLine()) != null)
                strBuilder.append(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strBuilder.toString();
    }
}
