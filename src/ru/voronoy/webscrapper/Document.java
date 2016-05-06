package ru.voronoy.webscrapper;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Document {

    private List<String> sentences = new ArrayList<>();
    private long charactersCount;

    public Document() { }

    public String getFullText() {
        Optional<String> optional = sentences.stream().reduce((s1, s2) -> s1 + " " + s2);
        return optional.get();
    }

    public List<String> getTextEntries() {
        return sentences;
    }

    public void addSentences(List<String> sentences) {
        sentences.stream().forEach(s -> {
            addSentence(s);
        });
    }

    public void addSentence(String sentence) {
            sentences.add(sentence);
            long count = sentence.chars().filter((c) -> !Character.isSpaceChar(c)).count();
            charactersCount += count;
    }

    public long getCharactersCount() {
        return charactersCount;
    }
}
