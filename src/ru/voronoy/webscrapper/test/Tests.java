package ru.voronoy.webscrapper.test;

import org.junit.Test;
import ru.voronoy.webscrapper.Document;
import ru.voronoy.webscrapper.Parser;
import ru.voronoy.webscrapper.Routines;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
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

    @Test
    public void getWebPageTest() throws Exception {
//        InputStream is = null;
//        String line;
//        File file = new File("D:/temp/text.html");
//        if (file.exists())
//            file.delete();
//        file.createNewFile();
//        FileWriter writer = new FileWriter(file, true);
//        try {
//            URL url = new URL("https://habrahabr.ru/");
//            is = url.openStream();
//            BufferedReader br = new BufferedReader(new InputStreamReader(is));
//
//            while ((line = br.readLine()) != null) {
//                writer.append(line);
//            }
//        } finally {
//            if (is != null) is.close();
//            if (writer != null) writer.close();
//        }
    }

    @Test
    public void fillUrlsListTest() throws Exception {
        String[] urlStrings = new String[] {"url1", "url2", "url3"};
        String separator = System.getProperty("line.separator");
        File file = new File(System.getProperty("user.home"), "fillUrlTest");
        if (file.exists())
            file.delete();
        file.createNewFile();
        try (FileWriter writer = new FileWriter(file, true)){
            Arrays.stream(urlStrings).forEach(s -> {
                try {
                    writer.append(s);
                    writer.append(separator);
                } catch (IOException e) {
                    e.printStackTrace();
                    fail();
                }
            });
        }
        List<String> urls = new ArrayList<>();
        Routines.fillUrlsFromFile(urls, file.getAbsolutePath());
        file.delete();
        assertArrayEquals(urlStrings, urls.toArray());
    }
}
