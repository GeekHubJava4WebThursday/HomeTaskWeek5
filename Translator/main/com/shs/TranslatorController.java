package com.shs;

import com.shs.source.SourceLoader;
import com.shs.source.URLSourceProvider;

import java.io.IOException;
import java.util.Scanner;

public class TranslatorController {

    public static void main(String[] args) throws IOException {

        SourceLoader sourceLoader = new SourceLoader();
        Translator translator = new Translator(new URLSourceProvider());

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter url or path to file ");
        String command = scanner.next();

        while (!"exit".equals(command)) {
            String source = sourceLoader.loadSource(command);

            if (source != null) {
                System.out.println("Original: " + source);
                String translation = translator.translate(source);
                System.out.println("Translation: " + translation);
            } else System.out.println("WRONG source path. Please enter url or file path");

            System.out.println("Enter url or file path");
            command = scanner.next();
        }
    }
}
