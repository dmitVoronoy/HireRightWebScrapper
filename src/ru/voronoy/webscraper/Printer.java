package ru.voronoy.webscraper;

import java.net.URL;

public class Printer {

    private static String occurenceTemplate = "The word %s occurs on page %s %d times";

    public void collectWordCount(URL u, String kw, int count) {
        print(String.format(occurenceTemplate, kw, u.toString(), count));
    }

    public void print(String s) {
        System.out.println(s);
    }
}
