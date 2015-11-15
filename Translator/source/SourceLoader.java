package source;

import exceptions.UnknownSourceProviderException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * SourceLoader should contains all implementations of SourceProviders to be able to load different sources.
 */
public class SourceLoader {
    private List<SourceProvider> sourceProviders = new ArrayList<>();

    public SourceLoader() {
        addSourceProviders();
    }

    private void addSourceProviders() {
        sourceProviders.add(new FileSourceProvider());
        sourceProviders.add(new URLSourceProvider());
        sourceProviders.add(new DirectTextSourceProvider());
    }

    public String loadSource(String pathToSource) throws IOException, UnknownSourceProviderException {
        for (SourceProvider sourceProvider : sourceProviders) {
            if (sourceProvider.isAllowed(pathToSource)) {
                return sourceProvider.load(pathToSource);
            }
        }
        throw new UnknownSourceProviderException();
    }

}
