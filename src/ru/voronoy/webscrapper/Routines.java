package ru.voronoy.webscrapper;

import java.util.List;

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

    public static void checkArgument(Object arg) {
        if (arg == null) {
            throw new IllegalArgumentException("Argument is null!");
        }
    }
}
