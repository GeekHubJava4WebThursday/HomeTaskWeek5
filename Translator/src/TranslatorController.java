import source.SourceLoader;
import source.URLSourceProvider;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.Scanner;

public class TranslatorController {

    private static final String LINE_SEPARATOR = System.lineSeparator();

    public static void main(String[] args) throws IOException {

        SourceLoader sourceLoader = new SourceLoader();
        Translator translator = new Translator(new URLSourceProvider());

        Scanner scanner = new Scanner(System.in);
        String command = scanner.next();
        while (!"exit".equals(command)) {
            String source;
            try {
                source = sourceLoader.loadSource(command);
                String translation = translator.translate(source);
                System.out.println("Original:" + LINE_SEPARATOR + source);
                System.out.println("Translation: " + LINE_SEPARATOR + translation);
            } catch (IOException | InvalidPathException e) {
                System.out.println("Something wrong with source, try another one");
            }
            command = scanner.next();
        }
    }
}
