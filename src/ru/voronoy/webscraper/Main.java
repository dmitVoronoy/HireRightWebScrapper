package ru.voronoy.webscraper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static boolean verbosity;
    private static boolean wordsOccurence;
    private static boolean charactersCount;
    private static boolean extractSentences;

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Program usage: java -jar scraper.jar <URL|PathToUrlList> <word[,...]> [-v] [-w] [-c] [-e]");
            System.exit(0);
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
                    Routines.fillUrlsFromFile(strUrls, urlListFile);
                    strUrls.stream().forEach(s -> {
                        try {
                            urls.add(new URL(s));
                        } catch (MalformedURLException e1) {
                            System.out.println(s + " is not correct URL!");
                        }
                    });
                } catch (FileNotFoundException e1) {
                    System.out.println("Incorrect url or nonexistent file path was used!");
                    System.exit(0);
                }
            } else {
                System.out.println("Incorrect url or nonexistent file path was used!");
                System.exit(0);
            }
        }
        String[] keywords = args[1].split(",");
        parseDashArgs(args);
        Printer printer = new Printer();
        urls.stream().forEach(u -> {
            Scraper scraper = new Scraper(u);
            Reader reader = scraper.getPageContentReader();
            if (reader != null) {
                try {
                    Parser parser = new Parser();
                    Document document = parser.parse(reader);
                    WordCounter counter = new WordCounter(document);
                    if (wordsOccurence) {
                        Arrays.stream(keywords).forEach(kw -> {
                            int count = counter.countWord(kw);
                            printer.collectWordCount(u, kw, count);
                        });
                    }
                    if (extractSentences) {
                        Arrays.stream(keywords).forEach(kw -> {
                            String sentence = counter.getSentenceWithWord(kw);
                            printer.collectSentence(u, kw, sentence);
                        });
                    }
                    if (charactersCount) {
                        printer.collectCharactersCount(u, document.getCharactersCount());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("There are problems with web site" + u + " content getting. Try again later");
                }
            } else {
                System.out.println("There are problems with web site content getting. Try again later");
                System.exit(0);
            }
        });
        printer.printTotalResult();
    }

    private static void parseDashArgs(String[] args) {
        for (int i = 2; i < args.length; i++) {
            String arg = args[i];
            DashArgument dashArgument = DashArgument.valueOf(arg.substring(1));
            switch (dashArgument) {
                case V: verbosity = true;
                    break;
                case W: wordsOccurence = true;
                    break;
                case C: charactersCount = true;
                    break;
                case E: extractSentences = true;
                    break;
                default: System.out.println(arg + " argument is undefined");
                    break;
            }
        }
    }
}
