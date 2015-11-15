package source;

import java.io.IOException;

/**
 * Implementation for reading text from console <br/>
 * Text to translation ust starts with asterisk <br/>
 * Valid values are *This is test text, *Other text etc.
 */
public class DirectTextSourceProvider implements SourceProvider {

    @Override
    public boolean isAllowed(String pathToSource) {
        return pathToSource.charAt(0) == '*';
    }

    @Override
    public String load(String pathToSource) throws IOException {
        return pathToSource.substring(1);
    }

}
