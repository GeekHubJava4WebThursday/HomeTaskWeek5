package source;

import java.io.*;
import java.nio.file.Files;

/**
 * Implementation for loading content from local file system.
 * This implementation supports absolute paths to local file system without specifying file:// protocol.
 * Examples c:/1.txt or d:/pathToFile/file.txt
 */
public class FileSourceProvider implements SourceProvider {

    @Override
    public boolean isAllowed(String pathToSource) {
        //TODO: implement me
        File file = new File(pathToSource);
        if (file.exists()&& file.canRead()){
            return true;
        }

        return false;
    }

    @Override
    public String load(String pathToSource) throws IOException {
        //TODO: implement me
        BufferedReader br = null;

        String currentLine;

        br = new BufferedReader(new FileReader(pathToSource));
        StringBuilder result = new StringBuilder();
        while ((currentLine = br.readLine()) != null) {
            result.append(currentLine);

        }
        return result.toString();
    }
}
