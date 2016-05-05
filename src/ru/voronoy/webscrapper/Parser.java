package ru.voronoy.webscrapper;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class Parser {

    private static HTMLEditorKit.Parser parser = new ParserDelegator();

    public static Document parse(Reader reader) throws IOException {
        Routines.checkArgument(reader);
        Callback callback = new Callback();
        parser.parse(reader, callback, true);
        return callback.getDocument();
    }

    public static Document parse(String text) throws IOException {
        Routines.checkArgument(text);
        Reader reader = new StringReader(text);
        return parse(reader);
    }

    private static class Callback extends HTMLEditorKit.ParserCallback {
        private Document document = new Document();

        @Override
        public void handleText(char[] data, int pos) {
            super.handleText(data, pos);
            document.addEntry(data);
        }

        public Document getDocument() {
            return document;
        }
    }
}
