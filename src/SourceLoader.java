package com.company.src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by InnaBakum on 01.12.2015.
 */
public class SourceLoader {
    private List<SourceProvider> sourceProviders = new ArrayList<SourceProvider>();

    public SourceLoader() {
        sourceProviders.add(new FileSourceProvider());
        sourceProviders.add(new URLSourceProvider());
    }

    public String loadSource(String pathToSource) throws IOException {
        for (SourceProvider provider : sourceProviders) {
            if (provider.isAllowed(pathToSource)) {
                return provider.load(pathToSource);
            }
        }

        throw new IOException("NOT FOUND " + pathToSource);
    }
}
