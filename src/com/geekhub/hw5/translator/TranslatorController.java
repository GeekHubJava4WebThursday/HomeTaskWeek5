package com.geekhub.hw5.translator;

import com.geekhub.hw5.translator.source.SourceLoader;
import com.geekhub.hw5.translator.source.URLSourceProvider;

import java.io.IOException;
import java.util.Scanner;

public class TranslatorController {

    public static void main(String[] args) throws IOException {
        //initialization
        SourceLoader sourceLoader = new SourceLoader();
        Translator translator = new Translator(new URLSourceProvider());

        Scanner scanner = new Scanner(System.in);
        String command = scanner.next();
        while(!"exit".equals(command)) {
            try {
                String source = sourceLoader.loadSource(command);
                if (source != null) {
                    try {
                        String translation = translator.translate(source);
                        System.out.println("Original: " + source);
                        System.out.println("Translation: " + translation);
                    } catch (IOException e) {
                        System.out.println("Can't load source! \nPlease, check path and try again or type \"exit\"");
                    }
                } else {
                    System.out.println("Unknown source! \nPlease, check path and try again or type \"exit\"");
                }
            } catch (IOException e) {
                System.out.println("Translation error! \nPlease, check path and try again or type \"exit\"");
            }
            command = scanner.next();
        }
    }
}
