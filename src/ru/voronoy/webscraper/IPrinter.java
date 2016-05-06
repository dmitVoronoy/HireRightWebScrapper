package ru.voronoy.webscraper;

import java.net.URL;
import java.util.List;

public interface IPrinter {
    void collectWordCount(URL u, String word, int count);

    void collectSentences(URL u, String kw, List<String> sentences);

    void collectCharactersCount(URL u, long charactersCount);

    void print(String s);

    void printTotalResult();
}
