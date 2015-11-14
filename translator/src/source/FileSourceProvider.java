package source;

import java.io.*;

/**
 * Implementation for loading content from local file system.
 * This implementation supports absolute paths to local file system without specifying file:// protocol.
 * Examples c:/1.txt or d:/pathToFile/file.txt
 */
public class FileSourceProvider implements SourceProvider {

    @Override
    public boolean isAllowed(String pathToSource) {
        File file = new File(pathToSource);
        return file.isFile() && file.canRead();
    }

    @Override
    public String load(String pathToSource) throws IOException {
        String inputLine;
        StringBuilder stringBuffer = new StringBuilder();

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(pathToSource))) {
            while ((inputLine = bufferedReader.readLine()) != null) {
                stringBuffer.append(inputLine);
            }
        }

        return stringBuffer.toString();
    }
}
