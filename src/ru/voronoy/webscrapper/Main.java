package ru.voronoy.webscrapper;

import javax.swing.plaf.synth.SynthEditorPaneUI;
import java.io.File;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Main {

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
                //Routines.fillUrlsFromFile(urls, urlListFile);
            } else {
                System.out.println("Incorrect url or nonexistent file path was used!");
                System.exit(0);
            }
        }
    }
}
