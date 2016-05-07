package ru.voronoy.webscraper;

import java.net.URL;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class collects and prints the results
 */
class ResultsCollector {

    private final Map<String, Integer> infoMap = new HashMap<>();
    private final Map<String, Long> charactersCountMap = new HashMap<>();
    private final Map<String, Period> pageScraping = new HashMap<>();
    private final Map<String, Period> pageProcessing = new HashMap<>();

    private static final String totalWordsCountTemplate = "The page %s has the %d words occurrences in total";
    private static final String totalCharactersCountTemplate = "The overall amount of received characters is %d";

    private static final String timeSpentOnPageScraping = "Time spent on %s page scraping = %s msec";
    private static final String timeSpentOnPageProcessing = "Time spent on %s page processing = %s msec";

    public void collectWordCount(URL u, String word, int count) {
        String strUrl = u.toString();
        print(String.format("The word %s occurs on page %s %d times", word, strUrl, count));
        Integer c = infoMap.get(strUrl);
        if (c == null)
            c = count;
        else
            c += count;
        infoMap.put(strUrl, c);
    }

    public void collectSentences(URL u, String kw, List<String> sentences) {
        if (sentences != null) {
            String strUrl = u.toString();
            String strSentences = sentences.stream()
                    .map(s -> "\"" + s + "\"")
                    .reduce((s1, s2) -> s1 + ", " + s2)
                    .orElse("");
            print(String.format("The word %s occurs in sentences %s on page %s", kw, strSentences, strUrl));
        }
    }

    public void collectCharactersCount(URL u, long charactersCount) {
        String strUrl = u.toString();
        print(String.format("The page %s contains %d characters", strUrl, charactersCount));
        charactersCountMap.put(strUrl, charactersCount);
    }

    private void print(String s) {
        System.out.println(s);
    }

    public void printTotalResult() {
        infoMap.forEach((k,v) -> print(String.format(totalWordsCountTemplate, k, v)));
        charactersCountMap.values()
                .stream()
                .reduce((l1, l2) -> l1 + l2)
                .ifPresent(l -> print(String.format(totalCharactersCountTemplate, l)));
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
            print(String.format("Overall time spent on scraping = %s msec", overallScraping[0]));
        if (overallProcessing[0] != 0L)
            print(String.format("Overall time spent on processing = %s msec", overallProcessing[0]));
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
        private final Instant start;
        private Instant end;

        public Period(Instant start) {
            this.start = start;
        }
    }
}
