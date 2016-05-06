package ru.voronoy.webscraper;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Printer implements IPrinter {

    Map<String, Integer> infoMap = new HashMap<>();
    Map<String, Long> charactersCountMap = new HashMap<>();

    private static String occurrenceTemplate = "The word %s occurs on page %s %d times";
    private static String sentencesTemplate = "The word %s occurs in sentences %s";
    private static String charactersCountTemplate = "The portal %s contains %n characters";

    private static String totalWordsCountTemplate = "The portal %s has the %d words occurrences in total";

    @Override
    public void collectWordCount(URL u, String word, int count) {
        String strUrl = u.toString();
        print(String.format(occurrenceTemplate, word, strUrl, count));
        Integer c = infoMap.get(strUrl);
        if (c == null)
            infoMap.put(strUrl, count);
        else
            c += count;
    }

    @Override
    public void collectSentences(URL u, String kw, List<String> sentences) {
        String strUrl = u.toString();
        String strSentences = sentences.stream()
                .reduce((s1, s2) -> "\"" + s1 + "\", \"" + s2 + "\"")
                .get();
        print(String.format(sentencesTemplate, strUrl, strSentences));
    }

    @Override
    public void collectCharactersCount(URL u, long charactersCount) {
        String strUrl = u.toString();
        print(String.format(charactersCountTemplate, strUrl, charactersCount));
        charactersCountMap.put(strUrl, charactersCount);
    }

    @Override
    public void print(String s) {
        System.out.println(s);
    }

    @Override
    public void printTotalResult() {
        infoMap.entrySet().forEach(e -> {
            print(String.format(totalWordsCountTemplate, e.getKey(), e.getValue()));
        });
    }
}
