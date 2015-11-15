import exceptions.UnknownSourceProviderException;
import source.SourceLoader;
import source.URLSourceProvider;

import java.io.IOException;
import java.util.Scanner;

public class TranslatorController {

    private static final String FOOTER_SEPARATOR = "";

    public static void main(String[] args) {
        //initialization
        SourceLoader sourceLoader = new SourceLoader();
        Translator translator = new Translator(new URLSourceProvider());

        Scanner scanner = new Scanner(System.in);
        String source;
        String translation;

        System.out.println("Yandex translator client.");
        System.out.println("Input path to file, url or direct text starts with asterisk.");
        System.out.println("Type \"exit\" for exit program.");
        System.out.print(">");
        String command = scanner.nextLine();
        while(!"exit".equals(command.toLowerCase())) {
            try {
                source = sourceLoader.loadSource(command);
                translation = translator.translate(source);

                System.out.println("Original:");
                System.out.println(source);
                System.out.println("Translation:");
                System.out.println(translation);
                System.out.print(FOOTER_SEPARATOR);
                System.out.println("Переведено «Яндекс.Переводчиком». http://translate.yandex.ru/");
            } catch (IOException e) {
                System.out.println("An error occurred: " + e.getMessage());
            } catch (UnknownSourceProviderException e) {
                System.out.println("Unknown source for translate");
                System.out.println("If you need translate plain text, please start line with \"*\"");
            }

            System.out.print(">");
            command = scanner.nextLine();
        }
    }

}
