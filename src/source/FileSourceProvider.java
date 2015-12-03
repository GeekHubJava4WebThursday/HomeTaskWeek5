package source;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
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
        BufferedReader br = new BufferedReader(new FileReader(pathToSource));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line !=null) {
                sb.append(line + "\n");
                line = br.readLine();
            }
            return sb.toString();
        }
        finally {
            br.close();
        }

    }
}
