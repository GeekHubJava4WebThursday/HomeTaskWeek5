package com.company.src;

import java.io.IOException;

/**
 * Created by InnaBakum on 01.12.2015.
 */
public interface SourceProvider {

    public boolean isAllowed(String pathToSource);

    public String load(String pathToSource) throws IOException;
}