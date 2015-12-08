package com.company.src;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

/**
 * Created by InnaBakum on 01.12.2015.
 */
public class FileSourceProvider implements SourceProvider {

    @Override
    public boolean isAllowed(String pathToSource) {
        File readFile = new File(pathToSource);
        return readFile.isFile() && readFile.canRead() && readFile.exists();
    }

    @Override
    public String load(String pathToSource) throws IOException {
        BufferedReader reader = Files.newBufferedReader(Paths.get(pathToSource), StandardCharsets.UTF_8);
        return reader.lines().map(line -> {return line;}).collect(Collectors.joining("\n"));
    }
}
