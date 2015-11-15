package source;

import java.io.*;
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
        Path path = Paths.get(pathToSource);
        if(Files.exists(path))
            if(Files.isReadable(path))
                return true;
        return false;
    }

    @Override
    public String load(String pathToSource) throws IOException {
        StringBuilder sb = new StringBuilder();
        try(
                InputStreamReader inputReader = new InputStreamReader(Files.newInputStream(Paths.get(pathToSource)), "Cp1251")){
            while(inputReader.ready()){
                sb.append((char)inputReader.read());
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return sb.toString();
    }

}
