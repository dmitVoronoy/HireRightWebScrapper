package ru.voronoy.webscraper.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.voronoy.webscraper.Main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        + "The page https://yandex.com has the 4 words occurrences in total" + LINE_SEPARATOR, testOut.toString());
    }

    @Test
    public void twoWordsOccurrenceTest() {
        Main.main(new String[]{"https://yandex.com", "yandex, product", "-w"});
        assertEquals("The word yandex occurs on page https://yandex.com 4 times" + LINE_SEPARATOR
                + "The word  product occurs on page https://yandex.com 0 times" + LINE_SEPARATOR
                + "The page https://yandex.com has the 4 words occurrences in total" + LINE_SEPARATOR, testOut.toString());
    }

    @Test
    public void twoWordsOccurrenceTest2() {
        Main.main(new String[]{"https://yandex.com", "yandex,products", "-w"});
        assertEquals("The word yandex occurs on page https://yandex.com 4 times" + LINE_SEPARATOR
                + "The word products occurs on page https://yandex.com 1 times" + LINE_SEPARATOR
                + "The page https://yandex.com has the 5 words occurrences in total" + LINE_SEPARATOR, testOut.toString());
    }

    @Test
    public void twoWordsAndTotalCharactersOccurrenceTest2() {
        Main.main(new String[]{"https://yandex.com", "yandex,products", "-w", "-c"});
        assertEquals("The word yandex occurs on page https://yandex.com 4 times" + LINE_SEPARATOR
                + "The word products occurs on page https://yandex.com 1 times" + LINE_SEPARATOR
                + "The page https://yandex.com contains 1533 characters" + LINE_SEPARATOR
                + "The page https://yandex.com has the 5 words occurrences in total" + LINE_SEPARATOR
                + "The overall amount of received characters is 1533" + LINE_SEPARATOR, testOut.toString());
    }

    @Test
    public void twoWordsAndTotalCharactersAndSentecesOccurrenceTest2() {
        Main.main(new String[]{"https://docs.oracle.com/javase/tutorial/collections/streams/reduction.html",
                "operation,value", "-w", "-c", "-e"});
        assertEquals("The word operation occurs on page https://docs.oracle.com/javase/tutorial/collections/streams" +
                "/reduction.html 18 times" + LINE_SEPARATOR
                + "The word value occurs on page https://docs.oracle.com/javase/tutorial/collections/streams" +
                "/reduction.html 20 times" + LINE_SEPARATOR
                + "The word operation occurs in sentences \"Reduction (The Java™ Tutorials > Collections " +
                "> Aggregate Operations)\", \"Aggregate Operations\", \"Aggregate Operations\", \"Aggregate " +
                "Operations\", \"Aggregate Operations\", \"describes the following pipeline of operations, which " +
                "calculates the average age of all male members in the collection\", \"The JDK contains many " +
                "terminal operations (such as\", \"These operations are called\", \"reduction operations\", " +
                "\"The JDK also contains reduction operations that return a collection instead of a single" +
                " value\", \"Many reduction operations perform a specific task, such as finding the average of " +
                "values or grouping elements into categories\", \"However, the JDK provides you with the" +
                " general-purpose reduction operations\", \"method is a general-purpose reduction operation\", " +
                "\"reduction operation:\", \"operation to calculate the same value:\", \"operation in this example " +
                "takes two arguments:\", \"operation always returns a new value\", \"operation involves adding " +
                "elements to a collection, then every time your accumulator function processes an element, it " +
                "creates a new collection that includes the element, which is inefficient\", \"operation in" +
                " this example takes three arguments:\", \"operation, it creates instances of the result " +
                "container\", \"operation\", \"operations with parallel streams; see the section\", \"operation " +
                "to calculate the average value of elements in a stream, you can use the\", \"operation and a " +
                "custom class if you need to calculate several values from the elements of a stream\", \"operation " +
                "is best suited for collections\", \"operation:\", \"operation takes one parameter of type\", " +
                "\"operation that requires three arguments (supplier, accumulator, and combiner functions)\", " +
                "\"class contains many useful reduction operations, such as accumulating elements into collections" +
                " and summarizing elements according to various criteria\", \"These reduction operations return " +
                "instances of the class\", \"operation\", \"operation, which accumulates the stream elements" +
                " into a new instance of\", \"As with most operations in the\", \"operation returns a map whose " +
                "keys are the values that result from applying the lambda expression specified as its parameter " +
                "(which is called a\", \"operation in this example takes two parameters, a classification " +
                "function and an instance of\", \"operation enables you to apply a\", \"operation takes three " +
                "parameters:\", \"operation, the identity element is both the initial value of the reduction " +
                "and the default result if there are no elements in the stream\", \"operation applies this " +
                "mapper function to all stream elements\", \"operation\", \": The operation function is used" +
                " to reduce the mapped values\", \"In this example, the operation function adds\", \"Aggregate" +
                " Operations\" on page https://docs.oracle.com/javase/tutorial/collections/streams/" +
                "reduction.html" + LINE_SEPARATOR
                + "The word value occurs in sentences \") that return one value by combining the contents of a" +
                " stream\", \"The JDK also contains reduction operations that return a collection instead of a " +
                "single value\", \"Many reduction operations perform a specific task, such as finding the average" +
                " of values or grouping elements into categories\", \"operation to calculate the same value:\"," +
                " \": The identity element is both the initial value of the reduction and the default result" +
                " if there are no elements in the stream\", \"; this is the initial value of the sum of ages " +
                "and the default value if no members exist in the collection\", \"values and returns an\", " +
                "\"value:\", \"operation always returns a new value\", \"However, the accumulator function " +
                "also returns a new value every time it processes an element of a stream\", \"method, which " +
                "always creates a new value when it processes an element, the\", \"method modifies, or mutates, " +
                "an existing value\", \"Consider how to find the average of values in a stream\", \"You require " +
                "two pieces of data: the total number of values and the sum of those values\", \"method returns " +
                "only one value\", \"You can create a new data type that contains member variables that keep" +
                " track of the total number of values and the sum of those values, such as the following class,\"," +
                " \"member variable the value of the stream element, which is an integer representing the " +
                "age of a male member\", \"member variable the value of the other\", \"The supplier is a " +
                "lambda expression (or a method reference) as opposed to a value like the identity element in " +
                "the\", \"The accumulator and combiner functions do not return a value\", \"operation to " +
                "calculate the average value of elements in a stream, you can use the\", \"operation and a " +
                "custom class if you need to calculate several values from the elements of a stream\", " +
                "\"operation returns a map whose keys are the values that result from applying the lambda " +
                "expression specified as its parameter (which is called a\", \"The keys' corresponding " +
                "values are instances of\", \"that contain the stream elements that, when processed by " +
                "the classification function, correspond to the key value\", \"For example, the value that " +
                "corresponds to key\", \"values created by the\", \"operation, the identity element is " +
                "both the initial value of the reduction and the default result if there are no elements " +
                "in the stream\", \"; this is the initial value of the sum of ages and the default value " +
                "if no members exist\", \": The operation function is used to reduce the mapped values\", " +
                "\"values\" on page https://docs.oracle.com/javase/tutorial/collections/streams/" +
                "reduction.html" + LINE_SEPARATOR
                + "The page https://docs.oracle.com/javase/tutorial/collections/streams/reduction.html contains" +
                " 14246 characters" + LINE_SEPARATOR
                + "The page https://docs.oracle.com/javase/tutorial/collections/streams/reduction.html has the 38" +
                " words occurrences in total" + LINE_SEPARATOR
                + "The overall amount of received characters is 14246" + LINE_SEPARATOR, testOut.toString());
    }

    @Test
    public void verbosityTest() {
        Main.main(new String[]{"https://docs.oracle.com/javase/tutorial/collections/streams/reduction.html", "operation", "-w", "-v"});
        assertTrue(testOut.toString().contains("Overall time spent"));
    }

    @After
    public void tearDown() {
        System.setOut(null);
        Main.clearCommandValues();
    }
}
