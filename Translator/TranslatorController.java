import source.SourceLoader;
import source.URLSourceProvider;

import java.io.IOException;
import java.util.Scanner;

public class TranslatorController {

    public static void main(String[] args) {
        //initialization
        SourceLoader sourceLoader = new SourceLoader();
        Translator translator = new Translator(new URLSourceProvider());

        Scanner scanner = new Scanner(System.in);
        String command = scanner.next();

        while(!"exit".equals(command)) {
            try {
                // So, the only way to stop the application is to do that manually or type "exit"
                String source = sourceLoader.loadSource(command);
                String translation = translator.translate(source);

                System.out.println("Original: " + source);
                System.out.println("Translation: " + translation);

                command = scanner.next();
            } catch(IOException e){
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
