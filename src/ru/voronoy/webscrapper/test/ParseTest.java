package ru.voronoy.webscrapper.test;

import org.junit.Test;
import ru.voronoy.webscrapper.Document;
import ru.voronoy.webscrapper.Parser;
import ru.voronoy.webscrapper.Routines;

import java.io.FileReader;
import java.io.Reader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ParseTest {
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
    public void parseBigFile() throws Exception {
        FileReader reader = new FileReader("assets/cnnTest.html");
        Document document = Parser.parse(reader);
        document.getFullText();
    }

    @Test
    public void countWordTest() throws Exception {
        FileReader reader = new FileReader("assets/cnnTest2.html");
        Document document = Parser.parse(reader);
        assertEquals(3, Routines.countWord(document, "testword"));
    }
}
