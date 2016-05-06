package ru.voronoy.webscrapper.test;

import org.junit.Test;
import ru.voronoy.webscrapper.Document;
import ru.voronoy.webscrapper.Parser;
import ru.voronoy.webscrapper.Routines;

import java.io.FileReader;
import java.io.Reader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class Tests {
    @Test
    public void getBodyTextTest() throws Exception {
        String text = "Body text";
        String htmlText = "<html><body>" + text + "</body></html>";
        Document document = Parser.parse(htmlText);
        assertEquals(text, document.getFullText());
    }

    @Test
    public void passNullToParserTest1() throws Exception {
        try {
            Parser.parse((String)null);
        } catch (IllegalArgumentException e) {
            return;
        }
        fail();
    }

    @Test
    public void passNullToParserTest2() throws Exception {
        try {
            Parser.parse((Reader)null);
        } catch (IllegalArgumentException e) {
            return;
        }
        fail();
    }

    @Test
    public void countWordTest() throws Exception {
        FileReader reader = new FileReader("assets/cnnTest.html");
        Document document = Parser.parse(reader);
        assertEquals(3, Routines.countWord(document, "testword"));
    }

    @Test
    public void passNullToCountRoutinesTest1() {
        try {
            Routines.countWord(null, "testword");
        } catch (IllegalArgumentException e) {
            return;
        }
        fail();
    }

    @Test
    public void passNullToCountRoutinesTest2() {
        try {
            Routines.countWord(new Document(), null);
        } catch (IllegalArgumentException e) {
            return;
        }
        fail();
    }

    @Test
    public void countWholeAmountOfSymbols() throws Exception {
        String text = "Body text";
        String htmlText = "<html><head>" + "Head text" + " </head><body>" + text + "</body></html>";
        Document document = Parser.parse(htmlText);
        assertEquals(16, document.getCharactersCount());
    }

    @Test
    public void prepareOneSentenceTest() {
        String text = "Some text";
        List<String> sentences = Routines.prepareSentences(text);
        assertEquals(1, sentences.size());
        assertEquals(text, sentences.get(0));
    }

    @Test
    public void prepareTwoSentencesWithDotTest() {
        String sentence1 = "Some text";
        String sentence2 = "The rest part";
        String text = sentence1 + ". " + sentence2;
        List<String> sentences = Routines.prepareSentences(text);
        assertEquals(2, sentences.size());
        assertEquals(sentence1, sentences.get(0));
        assertEquals(sentence2, sentences.get(1));
    }

    @Test
    public void prepareTwoSentencesWithExclamationTest() {
        String sentence1 = "Some text";
        String sentence2 = "The rest part";
        String text = sentence1 + "! " + sentence2;
        List<String> sentences = Routines.prepareSentences(text);
        assertEquals(2, sentences.size());
        assertEquals(sentence1, sentences.get(0));
        assertEquals(sentence2, sentences.get(1));
    }

    @Test
    public void prepareTwoSentencesWithQuestionTest() {
        String sentence1 = "Some text";
        String sentence2 = "The rest part";
        String text = sentence1 + "? " + sentence2;
        List<String> sentences = Routines.prepareSentences(text);
        assertEquals(2, sentences.size());
        assertEquals(sentence1, sentences.get(0));
        assertEquals(sentence2, sentences.get(1));
    }
}
