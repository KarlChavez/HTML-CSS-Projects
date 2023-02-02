import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

public class StringReassemblyTest {

    /**
     *
     * Test for combination().
     *
     */

    @Test
    public void combination_taxicab_cabin_3() {
        String n = "taxicab";
        String nExpected = "taxicab";
        String m = "cabin";
        String mExpected = "cabin";
        int i = 3;
        int iExpected = 3;
        String oExpected = "taxicabin";
        String o = StringReassembly.combination(n, m, i);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
        assertEquals(iExpected, i);
        assertEquals(oExpected, o);
    }

    @Test
    public void combination_Long_drive_drive_far_away_2() {
        String n = "Long drive";
        String nExpected = "Long drive";
        String m = "drive far away";
        String mExpected = "drive far away";
        int i = 5;
        int iExpected = 5;
        String oExpected = "Long drive far away";
        String o = StringReassembly.combination(n, m, i);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
        assertEquals(iExpected, i);
        assertEquals(oExpected, o);
    }

    @Test
    public void combination_nothing_else_0() {
        String n = "nothing";
        String nExpected = "nothing";
        String m = "else";
        String mExpected = "else";
        int i = 0;
        int iExpected = 0;
        String oExpected = "nothingelse";
        String o = StringReassembly.combination(n, m, i);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
        assertEquals(iExpected, i);
        assertEquals(oExpected, o);
    }

    /**
     *
     * Test for addToSetAvoidingSubstrings().
     *
     */

    @Test
    public void addToSetAvoidingSubstrings_abcd() {

        Set<String> s = new Set1L<>();
        s.add("abc");
        s.add("xyz");
        Set<String> sExpected = new Set1L<>();
        sExpected.add("xyz");
        sExpected.add("abcd");
        String n = "abcd";
        String nExpected = "abcd";
        StringReassembly.addToSetAvoidingSubstrings(s, n);
        assertEquals(n, nExpected);
        assertEquals(s, sExpected);

    }

    @Test
    public void addToSetAvoidingSubstrings_rudiment() {

        Set<String> s = new Set1L<>();
        s.add("flower");
        s.add("grape");
        s.add("dim");
        Set<String> sExpected = new Set1L<>();
        sExpected.add("flower");
        sExpected.add("grape");
        sExpected.add("rudiment");
        String n = "rudiment";
        String nExpected = "rudiment";
        StringReassembly.addToSetAvoidingSubstrings(s, n);
        assertEquals(n, nExpected);
        assertEquals(s, sExpected);

    }

    @Test
    public void addToSetAvoidingSubstrings_flower() {

        Set<String> s = new Set1L<>();
        s.add("flower");
        s.add("grape");
        s.add("dim");
        Set<String> sExpected = new Set1L<>();
        sExpected.add("flower");
        sExpected.add("grape");
        sExpected.add("dim");
        String n = "dim";
        String nExpected = "dim";
        StringReassembly.addToSetAvoidingSubstrings(s, n);
        assertEquals(n, nExpected);
        assertEquals(s, sExpected);

    }

    @Test
    public void addToSetAvoidingSubstrings1() {

        Set<String> set1 = new Set1L<String>();
        set1.add("hi");
        set1.add("hello");
        set1.add("howAreYou");
        set1.add("goodmorning");
        Set<String> set2 = new Set1L<String>();
        set2.add("hi");
        set2.add("hello");
        set2.add("howAreYou");
        set2.add("goodmorning");
        set2.add("goodAfternoon");
        String str = "goodAfternoon";
        StringReassembly.addToSetAvoidingSubstrings(set1, str);
        assertEquals(set1, set2);

    }

    /**
     *
     * Test for printWithLineSeparators().
     *
     */

    @Test
    public void testPrintWithLineSeparatorsTest1() {

        SimpleWriter out = new SimpleWriter1L("file1.txt");
        SimpleReader in = new SimpleReader1L("file1.txt");
        String n = "I~Love~Ohio";
        String nExpected = "I\nLove\nOhio";
        StringReassembly.printWithLineSeparators(n, out);
        String a = in.nextLine();
        String b = in.nextLine();
        String c = in.nextLine();
        in.close();
        out.close();
        assertEquals(nExpected, a + "\n" + b + "\n" + c);

    }

    @Test
    public void testPrintWithLineSeparatorsTest2() {

        SimpleWriter out = new SimpleWriter1L("file1.txt");
        SimpleReader in = new SimpleReader1L("file1.txt");
        String n = "Karl~Chavez~Me";
        String nExpected = "Karl\nChavez\nMe";
        StringReassembly.printWithLineSeparators(n, out);
        String a = in.nextLine();
        String b = in.nextLine();
        String c = in.nextLine();
        in.close();
        out.close();
        assertEquals(nExpected, a + "\n" + b + "\n" + c);

    }

    /**
     *
     * Test for linesFromInput().
     *
     */

    @Test
    public void testLinesFromInput1() {
        SimpleWriter out = new SimpleWriter1L("file1.txt");
        SimpleReader in = new SimpleReader1L("file1.txt");
        out.println("Karl");
        out.println("KarlChavez");
        out.println("KarlChavez");
        Set<String> n = StringReassembly.linesFromInput(in);
        Set<String> nExpected = new Set1L<>();
        nExpected.add("KarlChavez");
        assertEquals(n, nExpected);
        in.close();
        out.close();
    }

    @Test
    public void testLinesFromInput2() {
        SimpleWriter out = new SimpleWriter1L("file1.txt");
        SimpleReader in = new SimpleReader1L("file1.txt");
        out.println("TheOhio");
        out.println("TheOhioStateUniversity");
        out.println("University");
        Set<String> n = StringReassembly.linesFromInput(in);
        Set<String> nExpected = new Set1L<>();
        nExpected.add("TheOhioStateUniversity");
        assertEquals(n, nExpected);
        in.close();
        out.close();
    }

}
