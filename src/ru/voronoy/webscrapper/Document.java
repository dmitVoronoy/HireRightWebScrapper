package ru.voronoy.webscrapper;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Document {

    private List<String> entries = new ArrayList<>();
    private long charactersCount;

    public Document() { }

    public String getFullText() {
        Optional<String> optional = entries.stream().reduce((s1, s2) -> s1 + " " + s2);
        return optional.get();
    }

    public List<String> getTextEntries() {
        return entries;
    }

    public void addEntry(char[] data) {
        if (data != null) {
            String s = new String(data);
            entries.add(s);
            long count = s.chars().filter((c) -> !Character.isSpaceChar(c)).count();
            charactersCount += count;
        }
    }

    public long getCharactersCount() {
        return charactersCount;
    }
}
