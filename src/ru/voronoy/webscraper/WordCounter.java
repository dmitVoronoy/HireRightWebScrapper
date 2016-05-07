package ru.voronoy.webscraper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Counts words for the web document
 */
public class WordCounter {
    private final Document document;
    private final Map<String, List<String>> sentencesForWords = new HashMap<>();

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
            int sentenceLength = s.length();
            int wordCount = (sentenceLength - s.replaceAll(expression, "").length()) / wordLength;
            if (wordCount > 0) {
                String sLowerCase = s.toLowerCase();
                String wordLowerCase = word.toLowerCase();
                int index = sLowerCase.indexOf(wordLowerCase);
                while (index > -1) {
                    boolean wordIsOk = false;
                    if (index != 0) {
                        char symbolLeft = s.charAt(index - 1);
                        wordIsOk = Character.isSpaceChar(symbolLeft);
                    }
                    int wordEnd = index + word.length();
                    if (wordEnd < sentenceLength) {
                        char symbolRight = s.charAt(wordEnd);
                        wordIsOk = Character.isSpaceChar(symbolRight);
                    }
                    if (wordIsOk)
                        count[0]++;
                    index = sLowerCase.indexOf(wordLowerCase, wordEnd);
                }
                List<String> sentences = sentencesForWords.get(word);
                if (sentences == null) {
                    sentences = new ArrayList<>();
                    sentencesForWords.put(word, sentences);
                }
                sentences.add(s);
            }
        });
        return count[0];
    }

    public List<String> getSentencesWithWord(String word) {
        Routines.checkArgument(word);
        return sentencesForWords.get(word);
    }

}
