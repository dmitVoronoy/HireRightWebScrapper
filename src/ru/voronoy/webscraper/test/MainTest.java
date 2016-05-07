package ru.voronoy.webscraper.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.voronoy.webscraper.Main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class MainTest {
    private final ByteArrayOutputStream testOut = new ByteArrayOutputStream();

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    @Before
    public void setUp() {
        System.setOut(new PrintStream(testOut));
    }

    @Test
    public void noArgumentsTest() {
        Main.main(new String[]{});
        assertEquals(Main.PROGRAM_USAGE_MESSAGE + LINE_SEPARATOR, testOut.toString());
    }

    @Test
    public void oneArgumentTest() {
        Main.main(new String[]{"site"});
        assertEquals(Main.PROGRAM_USAGE_MESSAGE + LINE_SEPARATOR, testOut.toString());
    }

    @Test
    public void incorrectUrlTest() {
        Main.main(new String[]{"incorrectUrl", "word"});
        assertEquals(Main.INCORRECT_URL_OR_NONEXISTENT_FILE_PATH_MESSAGE + LINE_SEPARATOR, testOut.toString());
    }

    @Test
    public void incorrectUrlInFileTest() {
        Main.main(new String[]{"assets/testUrlList", "word"});
        assertEquals(String.format(Main.IS_NOT_CORRECT_URL_MESSAGE, "incorrect")
                + LINE_SEPARATOR, testOut.toString());
    }

    @Test
    public void argumentWithoutDashTest() {
        Main.main(new String[]{"https://yandex.com", "yandex", "w"});
        assertEquals(String.format(Main.ARGUMENT_IS_UNDEFINED, "w") + LINE_SEPARATOR
                + Main.PROGRAM_USAGE_MESSAGE + LINE_SEPARATOR, testOut.toString());
    }

    @Test
    public void oneWordOccurrenceTest() {
        Main.main(new String[]{"https://yandex.com", "yandex", "-w"});
        assertEquals("The word yandex occurs on page https://yandex.com 4 times" + LINE_SEPARATOR
        + "The portal https://yandex.com has the 4 words occurrences in total" + LINE_SEPARATOR, testOut.toString());
    }

    @After
    public void tearDown() {
        System.setOut(null);
        Main.clearCommandValues();
    }
}
