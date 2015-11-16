package source;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
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
        Path path = FileSystems.getDefault().getPath(pathToSource);
        return Files.isRegularFile(path);
    }

    @Override
    public String load(String pathToSource) throws IOException {
        StringBuilder fileStr = new StringBuilder();
        try(InputStreamReader inputReader = new InputStreamReader(Files.newInputStream(Paths.get(pathToSource)))){
            while(inputReader.ready()){
                fileStr.append((char)inputReader.read());
            }
        }catch (FileNotFoundException e){
                e.printStackTrace();
        }
        return fileStr.toString();
    }
}
