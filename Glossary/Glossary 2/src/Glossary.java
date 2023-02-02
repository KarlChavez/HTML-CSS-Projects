import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Takes a file that contains words and meanings and generates a group of HTML
 * files and an index for the definition of the terms.
 *
 * @author Karl Chavez
 *
 */
public final class Glossary {

    /**
     *
     * Returns a negative integer, zero, or a positive integer as the first
     * argument is less than, equal to, or greater than the second.
     *
     */
    private static class StringLT implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    }

    /**
     *
     * Obtains words and meanings through {@code input} and stores them inside a
     * Map{@code wordAndMeaning}. The method also stores only the words inside a
     * Queue component{@code words} and Set component{@code setWords}.
     *
     *
     * @param input
     *            source of strings, one per line
     * @param wordAndMeaning
     *            Map containing the word and its meaning
     * @param words
     *            Queue that only contains the words
     * @param setWords
     *            Set that only contains the words
     * @updates wordAndMeaning, words, and setWords
     *
     * @requires wordAndMeaning, words, and setWords ARE EMPTY. Input shall
     *           consist of a single term on the first line and its definition
     *           on the next one or more lines (terminated by an empty line)
     *
     * @ensures All words and meanings from {@code input} are stored inside
     *          {@code wordAndMeaning} and only words are stored in
     *          {@code words} and {@code setWords}
     */
    public static void organize(SimpleReader input,
            Map<String, String> wordAndMeaning, Queue<String> words,
            Set<String> setWords) {

        while (!input.atEOS()) {

            String line = input.nextLine();
            String meaning = "";

            //if the string isn't a blank line
            if (line.length() > 0) {
                boolean x = true;
                //Keeps iterating until terminated by an empty line
                while (x) {
                    String y = input.nextLine();
                    if (y.length() > 0) {
                        meaning = meaning + " " + y;
                    } else {
                        x = false;
                    }
                }
            }
            //Adds word and meaning in the Map component
            wordAndMeaning.add(line, meaning);
            //Adds only word in the Queue and Set component
            words.enqueue(line);
            setWords.add(line);
        }
    }

    /**
     * Sorts the words inside {@code words} alphabetically.
     *
     * @param words
     *            Queue that contains words
     *
     * @updates words
     *
     * @requires words can ONLY contain strings
     *
     * @ensures words = [words ordered by the relation computed by string
     *          comparator]
     */
    public static void alphabetize(Queue<String> words) {

        //uses a string comparator and sorts the queue alphabetically
        Comparator<String> a = new StringLT();
        words.sort(a);
    }

    /**
     * Generates HTML files for each word and its meaning in
     * {@code wordAndMeaning}. The word is printed at the heading and the
     * definition of the word is printed at the subheading. The method checks
     * whether the meaning contains words inside {@code setWords} and if so,
     * adds a url that leads to the definition of that word. Lastly, a url is
     * created to go back to the index.
     *
     *
     * @param wordAndMeaning
     *            Map that contains the words and meanings
     * @param words
     *            Queue that contains words
     * @param setWords
     *            Set that contains words
     * @param name
     *            folder name that the user chose
     * @updates out
     *
     * @ensures <pre>
     * out.content = #out.content *
     *   [an HTML word that's red boldface italics at the heading,
     *   meaning that's normal black font at subheading , url for other
     *   terms within the meaning, and url for the index
     * </pre>
     *
     */
    public static void processPage(Map<String, String> wordAndMeaning,
            Queue<String> words, Set<String> setWords, String name) {
        Queue<String> temp = new Queue1L();
        temp.transferFrom(words);

        //Goes through each word and processes an HTML code
        while (temp.length() > 0) {
            String term = temp.dequeue();
            String fileName = term;
            SimpleWriter out = new SimpleWriter1L(fileName + ".html");

            out.println("<html>");
            out.println("<style>");
            //indendting the meaning
            out.print("div.a {text-indent: 50px;}");
            out.println("</style>");

            //The word is red, bold, and italicized
            out.print("<h2 ");
            out.print("style=\"color:Red;\">");
            out.print("<i>");
            out.print(term);
            out.print("</i>");
            out.println("</h2>");
            out.println("<div class=\"a\">");
            out.print("<p>");

            /*
             * prints out the meaning while adding url in the glossary that
             * happens to appear in the definition
             */
            findWords(wordAndMeaning.value(term), out, setWords);

            //prints out a url back to the index
            out.println("</p>");
            out.println("</div>");
            out.print("<p>");
            out.print("Return to ");
            out.print("<a href=\"");
            out.print(name + "\">index");
            out.print("</a>" + ".");
            out.println("</p>");

            out.println("</html>");
            words.enqueue(term);
            out.close();
        }
    }

    /**
     * Goes through the given definition {@code meaning} and finds terms in the
     * glossary that happens to appear in the definition by checking whether
     * it's contained at {@code wordContain}. If a word is found, a url of that
     * word will be outputted at {@code output}.
     *
     * @param meaning
     *            A string for the meaning of the word
     * @param output
     *            An incoming file that's being processed
     * @param wordContain
     *            Set of the words
     * @updates output
     *
     * @requires output.is_open
     *
     * @ensures <pre>
     * output.content = #output.content * [url of other terms and the
     *          meaning]
     * </pre>
     */
    public static void findWords(String meaning, SimpleWriter output,
            Set<String> wordContain) {

        assert output != null : "Violation of: out is not null";
        assert output.isOpen() : "Violation of: out.is_open";

        //splits all individual strings and puts them in an array
        String[] arrayWords = meaning.split(" ");
        for (int i = 0; i < arrayWords.length; i++) {
            String word = arrayWords[i];
            //checks for commas
            if (word.indexOf(",") != -1) {
                //removes the comma from the string
                String w = word.substring(0, word.indexOf(","));
                //checks if the word is one of the terms in the glossary
                if (wordContain.contains(w)) {
                    output.print("<a href=\"");
                    output.print(w + ".html\">" + w);
                    output.print("</a>" + ",");
                } else {
                    output.print(w + ",");
                }
            }

            //doesn't have comma
            else {
                if (wordContain.contains(word)) {
                    output.print("<a href=\"");
                    output.print(word + ".html\">" + word);
                    output.print("</a>");
                } else {
                    output.print(word);
                }
            }
            output.print(" ");
        }
    }

    /**
     * Generates the index which merely lists each term in the glossary
     * contained in {@code words} and prints a url for each term.
     *
     * @param words
     *            Queue of words
     *
     * @param name
     *            name of the folder
     *
     * @updates out
     *
     * @ensures <pre>
     * out.content = #out.content * [Heading named "Glossary" and a list of
     *      each term in the glossary]
     * </pre>
     */
    public static void processIndex(Queue<String> words, String name) {
        SimpleWriter out = new SimpleWriter1L(name);
        out.println("<html>");
        //the heading
        out.println("<h1><b>Glossary</b></h1>");
        out.println("<h3>Index</h3>");

        //a list of the terms and a url for its associated HTML file
        out.println("<ul>");
        Queue<String> temp = new Queue1L();
        temp.transferFrom(words);
        while (temp.length() > 0) {
            String word = temp.dequeue();
            out.print("<li>");
            out.print("<a href=\"");
            out.print(word + ".html\">" + word);
            out.println("</a></li>");
            words.enqueue(word);
        }
        out.println("</ul>");
        out.println("</html>");
        out.close();

    }

    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        //all the components used
        Map<String, String> wordsMap = new Map1L<>();
        Queue<String> wordsQue = new Queue1L();
        Set<String> wordSet = new Set1L<>();

        /*
         * User enters the file name. If the user doesn't incorporate ".txt",
         * the program will automatically add it
         */
        out.print("Enter a file name: ");
        String fileName = in.nextLine();
        if (fileName.indexOf(".txt") == -1) {
            fileName = fileName + ".txt";
        }
        SimpleReader file = new SimpleReader1L(fileName);

        /*
         * User enters folder name. If the user doesn't incorportate ".html",
         * the program will automatically add it
         */
        out.print("Enter a folder name: ");
        String folderName = in.nextLine();
        if (folderName.indexOf(".html") == -1) {
            folderName = folderName + ".html";
        }
        //all the methods being called
        organize(file, wordsMap, wordsQue, wordSet);
        alphabetize(wordsQue);
        processPage(wordsMap, wordsQue, wordSet, folderName);
        processIndex(wordsQue, folderName);

        //closing files
        file.close();
        in.close();
        out.close();
    }

}
