package ru.voronoy.webscraper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is the WebScraper console application.
 * It could get the web page content, count word occurrences, total amount of characters, etc.
 * Usage: <URL|PathToUrlList> <word[,...]> [-v] [-w] [-c] [-e]
 * * <URL|PathToUrlList> - pass url or path to plain text file with the list of urls with line break delimeter;
 * * <word[,...]> - word or list of words with comma delimeter;
 * * -v - verbosity flag - prints time spent on data scraping and processing;
 * * -w - parameter to count words occurrences;
 * * -c - parameter to count overall characters amount on web page;
 * * -e - parameter to extract sentences which contain words.
 */
public class Main {
    public static final String PROGRAM_USAGE_MESSAGE = "Program usage: java -jar scraper.jar <URL|PathToUrlList> <word[,...]> [-v] [-w] [-c] [-e]";
    public static final String INCORRECT_URL_OR_NONEXISTENT_FILE_PATH_MESSAGE = "Incorrect url or nonexistent file path was used!";
    public static final String IS_NOT_CORRECT_URL_MESSAGE = "%s is not correct URL!";
    public static final String ARGUMENT_IS_UNDEFINED = "%s argument is undefined";
    public static final String CANNOT_GET_SITE_CONTENT = "There are problems with web site %s content getting. Try again later";

    private static boolean verbosity;
    private static boolean wordsOccurence;
    private static boolean charactersCount;
    private static boolean extractSentences;

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println(PROGRAM_USAGE_MESSAGE);
            return;
        }
        List<URL> urls = new ArrayList<>();
        try {
            URL url = new URL(args[0]);
            urls.add(url);
        } catch (MalformedURLException e) {
            File urlListFile = new File(args[0]);
            if (urlListFile.exists()) {
                List<String> strUrls = new ArrayList<>();
                try {
                    Routines.retrieveUrlsFromFile(strUrls, urlListFile);
                    strUrls.stream().forEach(s -> {
                        try {
                            urls.add(new URL(s));
                        } catch (MalformedURLException e1) {
                            System.out.println(String.format(IS_NOT_CORRECT_URL_MESSAGE, s));
                        }
                    });
                } catch (FileNotFoundException e1) {
                    System.out.println(INCORRECT_URL_OR_NONEXISTENT_FILE_PATH_MESSAGE);
                    return;
                }
            } else {
                System.out.println(INCORRECT_URL_OR_NONEXISTENT_FILE_PATH_MESSAGE);
                return;
            }
        }
        String[] keywords = args[1].split(",");
        parseDashArgs(args);
        ResultsCollector resultsCollector = new ResultsCollector();
        urls.stream().forEach(u -> {
            Scraper scraper = new Scraper(u);
            try {
                startPageScraping(u, resultsCollector);
                String content = scraper.getPageContent();
                endPageScraping(u, resultsCollector);
                if (!content.isEmpty()) {
                    Parser parser = new Parser();
                    startPageProcessing(u, resultsCollector);
                    Document document = parser.parse(content);
                    WordCounter counter = new WordCounter(document);
                    if (wordsOccurence) {
                        Arrays.stream(keywords).forEach(kw -> {
                            int count = counter.countWord(kw);
                            resultsCollector.collectWordCount(u, kw, count);
                        });
                    }
                    if (extractSentences) {
                        Arrays.stream(keywords).forEach(kw -> {
                            List<String> sentences = counter.getSentencesWithWord(kw);
                            resultsCollector.collectSentences(u, kw, sentences);
                        });
                    }
                    if (charactersCount) {
                        resultsCollector.collectCharactersCount(u, document.getCharactersCount());
                    }
                    endPageProcessing(u, resultsCollector);
                } else {
                    System.out.println(String.format(CANNOT_GET_SITE_CONTENT, u));
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(String.format(CANNOT_GET_SITE_CONTENT, u));
            }
        });
        resultsCollector.printTotalResult();
    }

    private static void startPageScraping(URL u, ResultsCollector resultsCollector) {
        if (verbosity) {
            resultsCollector.collectScrappingStart(u.toString(), Instant.now());
        }
    }

    private static void endPageScraping(URL u, ResultsCollector resultsCollector) {
        if (verbosity) {
            resultsCollector.collectScrappingEnd(u.toString(), Instant.now());
        }
    }

    private static void startPageProcessing(URL u, ResultsCollector resultsCollector) {
        if (verbosity) {
            resultsCollector.collectProcessingStart(u.toString(), Instant.now());
        }
    }

    private static void endPageProcessing(URL u, ResultsCollector resultsCollector) {
        if (verbosity) {
            resultsCollector.collectProcessingEnd(u.toString(), Instant.now());
        }
    }

    private static void parseDashArgs(String[] args) {
        for (int i = 2; i < args.length; i++) {
            String arg = args[i];
            if (arg.length() > 1 && arg.charAt(0) == '-') {
                DashArgument dashArgument = DashArgument.valueOf(arg.substring(1).toUpperCase());
                switch (dashArgument) {
                    case V: verbosity = true;
                            break;
                    case W: wordsOccurence = true;
                            break;
                    case C: charactersCount = true;
                            break;
                    case E: extractSentences = true;
                            break;
                    default: System.out.println(String.format(ARGUMENT_IS_UNDEFINED, arg));
                            System.out.println(PROGRAM_USAGE_MESSAGE);
                            break;
                }
            } else {
                System.out.println(String.format(ARGUMENT_IS_UNDEFINED, arg));
                System.out.println(PROGRAM_USAGE_MESSAGE);
            }
        }
    }

    public static void clearCommandValues() {
        wordsOccurence = false;
        charactersCount = false;
        extractSentences = false;
        verbosity = false;
    }
}
