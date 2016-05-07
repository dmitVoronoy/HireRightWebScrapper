package ru.voronoy.webscraper;

import java.net.URL;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultsCollector {

    private Map<String, Integer> infoMap = new HashMap<>();
    private Map<String, Long> charactersCountMap = new HashMap<>();
    private Map<String, Period> pageScraping = new HashMap<>();
    private Map<String, Period> pageProcessing = new HashMap<>();

    private static String occurrenceTemplate = "The word %s occurs on page %s %d times";
    private static String sentencesTemplate = "The word %s occurs in sentences %s on page %s";
    private static String charactersCountTemplate = "The page %s contains %d characters";

    private static String totalWordsCountTemplate = "The page %s has the %d words occurrences in total";
    private static String totalCharactersCountTemplate = "The overall amount of received characters is %d";

    private static String timeSpentOnPageScraping = "Time spent on %s page scraping = %s msec";
    private static String timeSpentOnPageProcessing = "Time spent on %s page processing = %s msec";
    private static String overallTimeSpentOnScraping = "Overall time spent on scraping = %s msec";
    private static String overallTimeSpentOnProcessing = "Overall time spent on processing = %s msec";

    public void collectWordCount(URL u, String word, int count) {
        String strUrl = u.toString();
        print(String.format(occurrenceTemplate, word, strUrl, count));
        Integer c = infoMap.get(strUrl);
        if (c == null)
            c = count;
        else
            c += count;
        infoMap.put(strUrl, c);
    }

    public void collectSentences(URL u, String kw, List<String> sentences) {
        String strUrl = u.toString();
        String strSentences = sentences.stream()
                .map(s -> "\"" + s + "\"")
                .reduce((s1, s2) -> s1 + ", " + s2)
                .get();
        print(String.format(sentencesTemplate, kw, strSentences, strUrl));
    }

    public void collectCharactersCount(URL u, long charactersCount) {
        String strUrl = u.toString();
        print(String.format(charactersCountTemplate, strUrl, charactersCount));
        charactersCountMap.put(strUrl, charactersCount);
    }

    public void print(String s) {
        System.out.println(s);
    }

    public void printTotalResult() {
        infoMap.forEach((k,v) -> {
            print(String.format(totalWordsCountTemplate, k, v));
        });
        charactersCountMap.values()
                .stream()
                .reduce((l1, l2) -> l1 + l2)
                .ifPresent(l -> {
                    print(String.format(totalCharactersCountTemplate, l));
                });
        long[] overallScraping = new long[] {0L};
        pageScraping.forEach((k, v) -> {
            long between = ChronoUnit.MILLIS.between(v.start, v.end);
            overallScraping[0] += between;
            print(String.format(timeSpentOnPageScraping, k, Long.toString(between)));
        });
        long[] overallProcessing = new long[] {0L};
        pageProcessing.forEach((k,v) -> {
            long between = ChronoUnit.MILLIS.between(v.start, v.end);
            overallProcessing[0] += between;
            print(String.format(timeSpentOnPageProcessing, k, Long.toString(between)));
        });
        if (overallScraping[0] != 0L)
            print(String.format(overallTimeSpentOnScraping, overallScraping[0]));
        if (overallProcessing[0] != 0L)
            print(String.format(overallTimeSpentOnProcessing, overallProcessing[0]));
    }

    public void collectScrappingStart(String page, Instant time) {
        pageScraping.put(page, new Period(time));
    }

    public void collectScrappingEnd(String page, Instant time) {
        Period period = pageScraping.get(page);
        period.end = time;
    }

    public void collectProcessingStart(String page, Instant time) {
        pageProcessing.put(page, new Period(time));
    }

    public void collectProcessingEnd(String page, Instant time) {
        Period period = pageProcessing.get(page);
        period.end = time;
    }

    private static class Period {
        private Instant start;
        private Instant end;

        public Period(Instant start) {
            this.start = start;
        }
    }
}
