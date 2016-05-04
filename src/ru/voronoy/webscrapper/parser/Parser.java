package ru.voronoy.webscrapper.parser;

public class Parser {

    private String fullText;

    private Parser() {

    }

    public static Parser create(String doc) {
        return new Parser();
    }

    public String getFullText() {
        return fullText;
    }
}
