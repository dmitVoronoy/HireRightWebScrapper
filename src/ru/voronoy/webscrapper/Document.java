package ru.voronoy.webscrapper;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Document {

    private List<String> entries = new ArrayList<>();

    public Document() { }


    public String getFullText() {
        Optional<String> optional = entries.stream().reduce((s1, s2) -> s1 + " " + s2);
        return optional.get();
    }

    public List<String> getTextEntries() {
        return entries;
    }

    public void addEntry(char[] data) {
        entries.add(new String(data));
    }
}
