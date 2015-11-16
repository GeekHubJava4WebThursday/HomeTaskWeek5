package source;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Implementation for loading content from local file system.
 * This implementation supports absolute paths to local file system without specifying file:// protocol.
 * Examples c:/1.txt or d:/pathToFile/file.txt
 */
public class FileSourceProvider implements SourceProvider {

    @Override
    public boolean isAllowed(String pathToSource) {
        Path path;
        try {
            path = Paths.get(pathToSource);
        } catch (InvalidPathException e) {
            return false;
        }
        return Files.exists(path) && pathToSource.endsWith(".txt");
    }

    @Override
    public String load(String pathToSource) throws IOException {
        String interLine, finalLine = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(pathToSource))) {
            while ((interLine = reader.readLine()) != null) {
                finalLine = finalLine + " " + interLine;
            }
        }

        return finalLine;
    }
}
