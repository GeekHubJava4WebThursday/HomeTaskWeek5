import source.SourceLoader;
import source.URLSourceProvider;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.Scanner;

public class TranslatorController {

    public static void main(String[] args) throws IOException {
        //initialization
        SourceLoader sourceLoader = new SourceLoader();
        Translator translator = new Translator(new URLSourceProvider());

        Scanner scanner = new Scanner(System.in);
        String command = scanner.next();
        while (!"exit".equals(command)) {
            String source = "";
            try {
                source = sourceLoader.loadSource(command);
            } catch (IOException | InvalidPathException e) {
                System.out.println("Something wrong with source, try another one");
            }

//            String translation = translator.translate(source);

            System.out.println("Original:" + "\n" + source);
//            System.out.println("Translation: " + translation);

            command = scanner.next();
        }
    }
}
