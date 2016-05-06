package ru.voronoy.webscraper;

import java.util.List;

public class WordCounter {
    private final Document document;

    public WordCounter(Document document) {
        Routines.checkArgument(document);
        this.document = document;
    }

    public int countWord(String word) {
        Routines.checkArgument(word);
        final int[] count = {0};
        int wordLength = word.length();
        String expression = "(?i)" + word;
        List<String> entries = document.getSentences();
        entries.stream().forEach(s -> {
            int i = (s.length() - s.replaceAll(expression, "").length()) / wordLength;
            count[0] += i;
        });
        return count[0];
    }
}
