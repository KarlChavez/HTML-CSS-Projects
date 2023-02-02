import java.util.Comparator;

import components.map.Map;
import components.map.Map.Pair;
import components.map.Map1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine1L;

/**
 * Generates a tag cloud from a given input text.
 *
 * @author Karl Chavez and Hafsa Gureye
 *
 *
 */
public final class TagCloudGenerator {

    /**
     * Default constructor--private to prevent instantiation.
     */
    private TagCloudGenerator() {
        // no code needed here
    }

    /**
     * Maximum font size.
     */
    private static final int maxSizeFont = 48;

    /**
     * Maximum number.
     */
    private static final int maxNum = 2147483647;

    /**
     * Minimum font size.
     */
    private static final int minSizeFont = 11;

    /**
     * Compare {@code Map.Pair<String, Integer>} in decreasing order.
     */
    private static class NumberOrder
            implements Comparator<Map.Pair<String, Integer>> {
        @Override
        public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
            return o2.value().compareTo(o1.value());
        }
    }

    /**
     * Compare {@code Map.Pair<String, Integer>} in lexicographic order.
     */
    private static class AlphabetOrder
            implements Comparator<Map.Pair<String, Integer>> {
        @Override
        public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
            return o1.key().toLowerCase().compareTo(o2.key().toLowerCase());
        }
    }

    /**
     * Adjusting font size given {@code count} using the most and least
     * occurrences from {@code maxCount} and {@code minCount}.
     *
     * @param count
     *            The word count of a word
     * @param maxCount
     *            The highest word count
     * @param minCount
     *            The lowest word count
     *
     * @requires <pre>
     * {@code maxCount} != {@code minCount}
     * </pre>
     *
     * @ensures Font size is between 11 and 48
     *
     * @return <pre> The appropriate size font of a word
     * </pre>
     */
    public static int changeFont(int count, int maxCount, int minCount) {

        double fontSize = 1.0;
        if (maxCount != minCount) {
            fontSize = minSizeFont + ((maxSizeFont - minSizeFont)
                    * ((count * 1.0 - minCount * 1.0)
                            / (maxCount * 1.0 - minCount * 1.0)));
        }
        return (int) (fontSize);
    }

    /**
     *
     * Obtain words and its occurrences in a given file and stores it inside a
     * Map.
     *
     * @param input
     *            strings in the file
     *
     * @return A Map of each word and its occurrences
     *
     * @requires {@code input} consist of strings
     *
     * @ensures Every word in the file is included in the Map with its
     *          occurrences
     */
    public static Map<String, Integer> obtainWords(SimpleReader input) {

        Map<String, Integer> mapWords = new Map1L<>(); //Map of Strings and Integers
        while (!input.atEOS()) {
            String line = input.nextLine(); //Obtaining lines from file
            int len = line.length(); //length of string

            String word; //Contains a word in the string

            int i; //Variable for first loop
            int k; //Variable for second loop
            for (i = 0; i < len; i++) {
                if (Character.isLetter(line.charAt(i))) { //Start of a word
                    for (k = i; k < len; k++) {
                        //Reached the end of the string & last character isn't a character
                        if (k == len - 1) {
                            if (!Character.isLetter(line.charAt(k))) {
                                word = line.substring(i, k);
                                if (mapWords.hasKey(word)) {
                                    mapWords.replaceValue(word,
                                            mapWords.value(word) + 1);
                                } else {
                                    mapWords.add(word, 1);
                                }
                                //Reached the end of the string & last char is a character
                            } else {
                                word = line.substring(i, k + 1);
                                if (mapWords.hasKey(word)) {
                                    mapWords.replaceValue(word,
                                            mapWords.value(word) + 1);
                                } else {
                                    mapWords.add(word, 1);
                                }
                            }
                            i = k;
                        } else { //Finding words in string
                            if (!Character.isLetter(line.charAt(k))) {
                                word = line.substring(i, k);
                                if (mapWords.hasKey(word)) {
                                    mapWords.replaceValue(word,
                                            mapWords.value(word) + 1);
                                } else {
                                    mapWords.add(word, 1);
                                }
                                i = k;
                                k = len; //Break

                            }
                        }
                    }
                }
            }
        }
        return mapWords;
    }

    /**
     * Generate a tag cloud by outputting the top words {@code top} on
     * {@code out} using {@code mapWordCount} that contains words with their
     * occurrences.
     *
     * @param out
     *            the output stream
     *
     * @param mapWordCount
     *            map that contains words and their count
     *
     * @param top
     *            top number of words to be displayed
     *
     * @updates {@code out} and {@code mapWordCount}
     *
     * @requires <pre>
     * {@code out} is an open file
     * </pre>
     *
     * @ensures <pre>{@code out.content = #out.content * [top]} and
     *               each key in {@code mapWordCount} is printed with the font
     *               size proportional to each key's value in {@code mapWordCount}
     * </pre>
     */
    public static void printWords(Map<String, Integer> mapWordCount,
            SimpleWriter out, int top) {

        //SortingMachine for numbers
        Comparator<Pair<String, Integer>> numberCompare = new NumberOrder();
        SortingMachine<Map.Pair<String, Integer>> numberSort = new SortingMachine1L<>(
                numberCompare);

        //Iterate until Map is empty
        while (mapWordCount.size() > 0) {
            numberSort.add(mapWordCount.removeAny());
        }

        numberSort.changeToExtractionMode();

        /*
         * Adding the top word counts back to the Map while obtaining the lowest
         * and highest word count in the cloud
         */

        int max = 0;
        int min = maxNum;
        for (int i = 0; i < top; i++) {
            Map.Pair<String, Integer> maxPair = numberSort.removeFirst();
            //Minimum number
            if (maxPair.value() < min) {
                min = maxPair.value();
            }
            //Maximum number
            if (maxPair.value() > max) {
                max = maxPair.value();
            }
            mapWordCount.add(maxPair.key(), maxPair.value());
        }

        //SortingMachine for alphabetical
        Comparator<Pair<String, Integer>> alphabetCompare = new AlphabetOrder();
        SortingMachine<Map.Pair<String, Integer>> alphabetSort = new SortingMachine1L<>(
                alphabetCompare);

        //Iterate until Map is empty
        while (mapWordCount.size() > 0) {
            alphabetSort.add(mapWordCount.removeAny());
        }
        alphabetSort.changeToExtractionMode();

        //Prints and resizes top words
        for (int i = 0; i < top; i++) {
            Map.Pair<String, Integer> a = alphabetSort.removeFirst();
            out.println("<span style=\"cursor:default\" class=\"f"
                    + changeFont(a.value(), max, min) + "\" title=\"count: "
                    + a.value() + "\">" + a.key() + "</span>");
        }

    }

    /**
     * Displays the title of the file given the {@code file} name and
     * {@code} number of words.
     *
     * @param out
     *            the output stream
     * @param file
     *            the input file name
     * @param num
     *            number of words to be included in the tag cloud
     *
     * @updates {@code out}
     *
     * @requires <pre>
     * {@code out} is an open file and {@code file} is not null
     * </pre>
     *
     * @ensures <pre>{@code out.content = #out.content * [opening tags]}
     *          </pre>
     */
    public static void printHeader(SimpleWriter out, String file, int num) {
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";
        assert file != null : "Violation of: inFile is not null";

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Top " + num + " words in " + file + "</title>");
        out.println(
                "<link href=\"http://web.cse.ohio-state.edu/software/2231/web-sw2/"
                        + "assignments/projects/tag-cloud-generator/data/tagcloud.css\" "
                        + "rel=\"stylesheet\" type=\"text/css\">");
        out.println("<link href=\"" + "tagcloud.css\"" + " rel=\""
                + "stylesheet\"" + " type=\"" + "text/css\"" + ">");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>Top " + num + " words in " + file + "<h2>");
        out.println("<hr>");
        out.println("<div class=\"cdiv\">");
        out.println("<p class=\"cbox\">");
    }

    /**
     *
     * Prints Closing Header in HTML.
     *
     * @param out
     *            the output stream
     *
     * @updates {code @out}
     *
     * @requires {code @out} is an open file
     *
     * @ensures the closing header of the HTML is included in {code @out}
     */
    public static void printClosing(SimpleWriter out) {
        out.println("</p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        //Input file
        out.println("Name of an input file: ");
        String inputFile = in.nextLine();
        SimpleReader inFile = new SimpleReader1L(inputFile);

        //Output file
        out.println("Name of an output file: ");
        String outputFile = in.nextLine();
        SimpleWriter outFile = new SimpleWriter1L(outputFile);

        //Map containing all the words and occurrences
        Map<String, Integer> mapWords = obtainWords(inFile);

        //Checking for a valid number input
        boolean valid = true;
        int topNum = 0;
        while (valid) {

            //User input for number of words in the tag cloud
            out.print(
                    "Enter the top number of words to be included in the tag cloud: ");

            topNum = in.nextInteger();
            if (topNum < 0) {
                out.println("ERROR: Input is negative. Try again");
            } else if (topNum > mapWords.size()) {
                out.println(
                        "ERROR: Input exceeded the number of words in the file");
            } else {
                valid = false;
            }
        }

        //Print Header of HTML file
        printHeader(outFile, inputFile, topNum);

        //Print the top words
        printWords(mapWords, outFile, topNum);

        //Print End Header of HTML file
        printClosing(outFile);

        in.close();
        out.close();

    }

}
