package ru.voronoy.webscrapper;

import java.util.List;

public class Routines {

    public static int countWord(Document document, String word) {
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
}
