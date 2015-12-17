import source.SourceLoader;
import source.URLSourceProvider;

import java.io.IOException;
import java.util.Scanner;
public class TranslatorController {

    public static void main(String[] args) throws IOException {
        //initialization source = "D:\\Java\\HomeTaskWeek5\\data\\text1.txt"
        SourceLoader sourceLoader = new SourceLoader();
        Translator translator = new Translator(new URLSourceProvider());

        System.out.print("Input path : ");
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();

        while(!"exit".equals(command)) {

            if (sourceLoader.loadSource(command) != null) {
                String source = sourceLoader.loadSource(command);
                String translation = translator.translate(source);

                System.out.println("Original : " + source);
                System.out.println("Translation : " + translation);
            } else {
                System.out.println("Path is`t correct. Try again or write \"exit\" to exit.");
            }

            System.out.print("Input path : ");
            command = scanner.nextLine();
        }
    }
}
