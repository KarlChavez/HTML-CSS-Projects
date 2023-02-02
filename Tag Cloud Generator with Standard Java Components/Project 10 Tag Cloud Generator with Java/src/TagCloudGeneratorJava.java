import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Generates a tag cloud from a given input text using Standard Java Components
 *
 * @author Karl Chavez and Hafsa Gureye
 */
public final class TagCloudGeneratorJava {

    /**
     * Default constructor--private to prevent instantiation.
     */
    private TagCloudGeneratorJava() {
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
     * Compare {@code Map.Entry<String, Integer>} in decreasing order
     */
    private static class NumberOrder
            implements Comparator<Map.Entry<String, Integer>> {
        @Override
        public int compare(Map.Entry<String, Integer> o1,
                Map.Entry<String, Integer> o2) {

            Integer i1 = o1.getValue();
            Integer i2 = o2.getValue();
            int num = i2.compareTo(i1);

            //Comparator is consistent
            if (num == 0) {
                num = o1.getKey().compareTo(o2.getKey());
            }
            return num;
        }
    }

    /**
     * Compare {@code Map.Entry<String, Integer>} in lexicographic order
     */
    private static class AlphabetOrder
            implements Comparator<Map.Entry<String, Integer>> {
        @Override
        public int compare(Map.Entry<String, Integer> o1,
                Map.Entry<String, Integer> o2) {

            String s1 = o1.getKey();
            String s2 = o2.getKey();
            int num = s1.compareToIgnoreCase(s2);

            //Comparator is consistent
            if (num == 0) {
                num = s1.compareTo(s2);
            }
            return num;
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

        //Font size initialized to 1.0 in case maxCount equals minCount
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
    public static Map<String, Integer> obtainWords(BufferedReader input) {

        //Map of words and their occurrences
        Map<String, Integer> mapWords = new HashMap<String, Integer>();

        try {
            String line = input.readLine(); //Obtaining lines from file
            while (line != null) {
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
                                    if (mapWords.containsKey(word)) {
                                        mapWords.put(word,
                                                mapWords.get(word) + 1);
                                    } else {
                                        mapWords.put(word, 1);
                                    }
                                    //Reached the end of the string & last char is a character
                                } else {
                                    word = line.substring(i, k + 1);
                                    if (mapWords.containsKey(word)) {
                                        mapWords.put(word,
                                                mapWords.get(word) + 1);
                                    } else {
                                        mapWords.put(word, 1);
                                    }
                                }
                                i = k;
                            } else { //Finding words in string
                                if (!Character.isLetter(line.charAt(k))) {
                                    word = line.substring(i, k);
                                    if (mapWords.containsKey(word)) {
                                        mapWords.put(word,
                                                mapWords.get(word) + 1);
                                    } else {
                                        mapWords.put(word, 1);
                                    }
                                    i = k;
                                    k = len; //Break

                                }
                            }
                        }
                    }
                }
                line = input.readLine();
            }
        } catch (IOException e) {
            System.err.print(("ERROR:" + e));
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
            PrintWriter out, int top) {

        //List of top occurring words
        List<Map.Entry<String, Integer>> listAlphabet = new ArrayList<Map.Entry<String, Integer>>();

        //List of number of occurrences
        List<Map.Entry<String, Integer>> listNumber = new ArrayList<Map.Entry<String, Integer>>();

        //Map that contains words
        Set<Map.Entry<String, Integer>> words = mapWordCount.entrySet();

        //Iterator for Map
        Iterator<Map.Entry<String, Integer>> next = words.iterator();

        /*
         * Adding the top word counts back to the Map while obtaining the lowest
         * and highest word count in the cloud
         */
        int max = 0;
        int min = maxNum;
        while (next.hasNext()) {
            Map.Entry<String, Integer> item = next.next();

            //Minimum number
            if (item.getValue() < min) {
                min = item.getValue();
            }
            //Maximum number
            if (item.getValue() > max) {
                max = item.getValue();
            }
            listNumber.add(item);
        }

        //Sort list in decreasing order
        Comparator<Map.Entry<String, Integer>> sortNumber = new NumberOrder();
        listNumber.sort(sortNumber);

        //Obtain top occurring words
        for (int i = 0; i < top; i++) {
            listAlphabet.add(listNumber.remove(0));
        }

        //Sorts the list in lexicographic order
        Comparator<Map.Entry<String, Integer>> sortAlphabet = new AlphabetOrder();
        listAlphabet.sort(sortAlphabet);

        //Prints and resizes top words
        for (int i = 0; i < top; i++) {
            Map.Entry<String, Integer> a = listAlphabet.remove(0);
            out.println("<span style=\"cursor:default\" class=\"f"
                    + changeFont(a.getValue(), max, min) + "\" title=\"count: "
                    + a.getValue() + "\">" + a.getKey() + "</span>");
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
    public static void printHeader(PrintWriter out, String file, int num) {
        assert out != null : "Violation of: out is not null";
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
    public static void printClosing(PrintWriter out) {
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

        BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in));

        //Input file
        String inputFile = "";
        BufferedReader inFile = null;
        System.out.print("Name of an input file: ");
        while (inFile == null) {
            try {
                inputFile = in.readLine();
                inFile = new BufferedReader(new FileReader(inputFile));
            } catch (IOException e) {
                System.err.println("ERROR: Violation of: " + e + " exists");
            }
        }

        //Output file
        String outputFile = "";
        PrintWriter outFile = null;
        System.out.print("Name of an output file: ");
        while (outFile == null) {
            try {
                outputFile = in.readLine();
                outFile = new PrintWriter(
                        new BufferedWriter(new FileWriter(outputFile)));
            } catch (IOException e) {
                System.err.println("ERROR: " + e);
            }
        }

        //Map containing all the words and occurrences
        Map<String, Integer> mapWords = obtainWords(inFile);

        //Checking for a valid number input
        boolean valid = true;
        int topNum = 0;
        while (valid) {
            try {
                //User input for number of words in the tag cloud
                System.out.print(
                        "Enter the top number of words to be included in the tag cloud: ");

                topNum = Integer.parseInt(in.readLine());
                if (topNum < 0) {
                    System.out.println("ERROR: Input is negative. Try again");
                } else if (topNum > mapWords.size()) {
                    System.out.println(
                            "ERROR: Input exceeded the number of words in the file");
                } else {
                    valid = false;
                }
            } catch (IOException e) {
                System.err.print("ERROR: Input Invalid: " + e);
            }
        }

        //Print Header of HTML file
        printHeader(outFile, inputFile, topNum);

        //Print the top words
        printWords(mapWords, outFile, topNum);

        //Print End Header of HTML file
        printClosing(outFile);

        //Close files
        outFile.close();
        try {
            in.close();
        } catch (IOException e) {
            System.err.println("ERROR: " + e);
        }
    }
}
