package ru.voronoy.webscrapper;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Routines {

    public static int countWord(Document document, String word) {
        checkArgument(document);
        checkArgument(word);

        final int[] count = {0};
        int wordLength = word.length();
        String expression = "(?i)" + word;
        List<String> entries = document.getTextEntries();
        entries.stream().forEach(s -> {
            int i = (s.length() - s.replaceAll(expression, "").length()) / wordLength;
            count[0] += i;
        });
        return count[0];
    }

    public static void checkArgument(Object... args) {
        Arrays.stream(args).forEach(s -> {
            if (s == null) throw new IllegalArgumentException("Argument is null!");
        });
    }

    public static List<String> prepareSentences(String text) {
        checkArgument(text);
        return Arrays.asList(text.split("[.!?]"))
                .stream()
                .map(s -> s.trim())
                .collect(Collectors.toList());
    }

    public static void fillUrlsFromFile(List<String> urls, String path) throws FileNotFoundException {
        checkArgument(urls, path);
        File file = new File(path);
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
