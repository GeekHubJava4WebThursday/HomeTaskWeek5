package source;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * SourceLoader should contains all implementations of SourceProviders to be able to load different sources.
 */
public class SourceLoader {
    private List<SourceProvider> sourceProviders = new ArrayList<>();

    public SourceLoader() {
        sourceProviders.add(new URLSourceProvider());
        sourceProviders.add(new FileSourceProvider());
    }

    public String loadSource(String pathToSource) throws IOException {
        for (SourceProvider provider : sourceProviders) {
            if(provider.isAllowed(pathToSource)){
                return provider.load(pathToSource);
            }
        }
        return null;
    }

}
