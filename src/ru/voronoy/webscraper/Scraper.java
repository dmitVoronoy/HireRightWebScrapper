package ru.voronoy.webscraper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

public class Scraper {

    private final URL url;

    public Scraper(URL url) {
        Routines.checkArgument(url);
        this.url = url;
    }

    public Reader getPageContentReader() {
        try (InputStream is = url.openStream()) {
            return new InputStreamReader(is);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
