package ru.voronoy.webscrapper.test;

import org.junit.Test;
import ru.voronoy.webscrapper.parser.Parser;

import static org.junit.Assert.assertEquals;

public class ParseTest {

    @Test
    public void getBodyTextTest() {
        String text = "Body text";
        String doc = "<html><body>" + text + "</body></html>";
        Parser p = Parser.create(doc);
        assertEquals(text, p.getFullText());
    }
}
