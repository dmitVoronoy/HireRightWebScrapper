package ru.voronoy.webscraper;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class
 */
public class Routines {

    public static void checkArgument(Object... args) {
        Arrays.stream(args).forEach(s -> {
            if (s == null) throw new IllegalArgumentException("Argument is null!");
        });
    }

    /**
     * Prepare sentences to pass it to document
     */
    public static List<String> prepareSentences(String text) {
        checkArgument(text);
        return Arrays.asList(text.split("[.!?]"))
                .stream()
                .map(s -> s.trim())
                .collect(Collectors.toList());
    }

    public static void retrieveUrlsFromFile(List<String> urls, File file) throws FileNotFoundException {
        checkArgument(urls, file);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                urls.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
