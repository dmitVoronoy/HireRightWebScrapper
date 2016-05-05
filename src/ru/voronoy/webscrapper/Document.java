package ru.voronoy.webscrapper;


public class Document {

    private final String text;

    public Document(String text) {
        this.text = text;
    }


    public String getFullText() {
        return text;
    }
}
