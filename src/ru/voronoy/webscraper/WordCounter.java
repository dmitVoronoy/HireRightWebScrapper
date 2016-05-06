package ru.voronoy.webscraper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordCounter {
    private final Document document;
    private Map<String, List<String>> sentencesForWords = new HashMap<>();

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
            if (i > 0) {
                List<String> sentences = sentencesForWords.get(word);
                if (sentences == null) {
                    sentences = new ArrayList<>();
                    sentencesForWords.put(word, sentences);
                }
                sentences.add(s);
                count[0] += i;
            }
        });
        return count[0];
    }

    public List<String> getSentencesWithWord(String word) {
        Routines.checkArgument(word);
        return sentencesForWords.get(word);
    }

}
