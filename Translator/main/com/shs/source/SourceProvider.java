package com.shs.source;

import java.io.IOException;

/**
 * Base interface to access different sources.</br>
 * isAllowed method should protect and help to determine can we load resource for specified path ot not.
 */
public interface SourceProvider {
    /**
     * Determines can current implementation load com.shs.source by provided pathToSource
     * @param pathToSource  absolute path to the com.shs.source
     * @return whether current implementation load the com.shs.source for specified pathToSource
     */
    public boolean isAllowed(String pathToSource);

    /**
     * Loads text from specified path.
     * @param pathToSource absolute path to the com.shs.source
     * @return content of the com.shs.source for specified pathToSource
     */
    public String load(String pathToSource) throws IOException;
}
