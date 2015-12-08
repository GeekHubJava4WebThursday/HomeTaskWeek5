package com.company;

import com.company.src.SourceLoader;
import com.company.src.URLSourceProvider;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {


        public static void main(String[] args) throws IOException {
      
            SourceLoader sourceLoader = new SourceLoader();
            Translation translation = new Translation(new URLSourceProvider());

            System.out.println("Write path to file or exit to stop app.");
            Scanner scanner = new Scanner(System.in);
            String command = scanner.next();
            while(!"exit".equals(command)) {
                try {
                    String source = sourceLoader.loadSource(command);
                    String translations = translation.translate(source);

                    System.out.println("Original: " + source);
                    System.out.println("Translation: " + translations);
                } catch (UnknownHostException e) {
                    System.out.println("unable to connect: " + command);
                } catch (FileNotFoundException e) {
                    System.out.println("file not found: " + command);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                command = scanner.next();
            }
    }
}
