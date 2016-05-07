package ru.voronoy.webscraper;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Scrapes the web page
 */
class Scraper {

    private final URL url;

    public Scraper(URL url) {
        Routines.checkArgument(url);
        this.url = url;
    }

    public String getPageContent() throws IOException {
        StringBuilder builder = new StringBuilder();
        try (InputStreamReader reader = new InputStreamReader(url.openStream())) {
            char[] buffer = new char[1024];
            while (true) {
                int read = reader.read(buffer, 0, buffer.length);
                if (read < 0)
                    break;
                builder.append(buffer, 0, read);
            }
        }
        return builder.toString();
    }
}
