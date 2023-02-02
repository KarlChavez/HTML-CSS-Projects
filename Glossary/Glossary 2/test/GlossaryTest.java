import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;

public class GlossaryTest {

    /**
     *
     * Test for organize()
     *
     */

    @Test
    public void organize_test_1() {
        Map<String, String> a = new Map1L<>();
        Queue<String> b = new Queue1L();
        Set<String> c = new Set1L<>();

        //expected values
        Map<String, String> aExpected = new Map1L<>();
        Queue<String> bExpected = new Queue1L();
        Set<String> cExpected = new Set1L<>();
        aExpected.add("carrots", " an orange vegetable");
        aExpected.add("steak", " meat from an animal");
        aExpected.add("apples", " a red fruit");
        bExpected.enqueue("carrots");
        bExpected.enqueue("steak");
        bExpected.enqueue("apples");
        cExpected.add("carrots");
        cExpected.add("steak");
        cExpected.add("apples");

        SimpleReader file = new SimpleReader1L("organizeTest1.txt");
        Glossary.organize(file, a, b, c);
        assertEquals(a, aExpected);
        assertEquals(b, bExpected);
        assertEquals(c, cExpected);

    }

    /**
     *
     * Test for alphabetize()
     *
     */

    @Test
    public void alphabetize_test_1() {
        Queue<String> a = new Queue1L();
        a.enqueue("carrots");
        a.enqueue("steak");
        a.enqueue("apples");

        //expected values
        Queue<String> aExpected = new Queue1L();
        aExpected.enqueue("apples");
        aExpected.enqueue("carrots");
        aExpected.enqueue("steak");

        Glossary.alphabetize(a);
        assertEquals(a, aExpected);
    }

    @Test
    public void alphabetize_test_2() {
        Queue<String> a = new Queue1L();
        a.enqueue("zi");
        a.enqueue("bo");
        a.enqueue("be");
        a.enqueue("tri");

        //expected values
        Queue<String> aExpected = new Queue1L();
        aExpected.enqueue("be");
        aExpected.enqueue("bo");
        aExpected.enqueue("tri");
        aExpected.enqueue("zi");

        Glossary.alphabetize(a);
        assertEquals(a, aExpected);
    }

}
