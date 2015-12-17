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
        try {
            FileReader reader = new FileReader(new File(pathToSource));
        } catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }

    @Override
    public String load(String pathToSource) throws IOException {
        FileReader fReader = new FileReader(new File(pathToSource));
        BufferedReader bReader = new BufferedReader(fReader);

        String text = "";

        for (String line = bReader.readLine(); line != null; line = bReader.readLine()) {
            text += line;
        }

        return text;
    }
}
