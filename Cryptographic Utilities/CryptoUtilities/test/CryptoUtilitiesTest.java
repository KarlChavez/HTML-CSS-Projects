import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * @author Karl Chavez.149
 *
 */
public class CryptoUtilitiesTest {

    /*
     * Tests of reduceToGCD
     */

    @Test
    public void testReduceToGCD_0_0() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber nExpected = new NaturalNumber2(0);
        NaturalNumber m = new NaturalNumber2(0);
        NaturalNumber mExpected = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
    }

    @Test
    public void testReduceToGCD_30_21() {
        NaturalNumber n = new NaturalNumber2(30);
        NaturalNumber nExpected = new NaturalNumber2(3);
        NaturalNumber m = new NaturalNumber2(21);
        NaturalNumber mExpected = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
    }

    /*
     * Tests of isEven
     */

    @Test
    public void testIsEven_0() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber nExpected = new NaturalNumber2(0);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(nExpected, n);
        assertEquals(true, result);
    }

    @Test
    public void testIsEven_1() {
        NaturalNumber n = new NaturalNumber2(1);
        NaturalNumber nExpected = new NaturalNumber2(1);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(nExpected, n);
        assertEquals(false, result);
    }

    /*
     * Tests of powerMod
     */

    @Test
    public void testPowerMod_0_0_2() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber nExpected = new NaturalNumber2(1);
        NaturalNumber p = new NaturalNumber2(0);
        NaturalNumber pExpected = new NaturalNumber2(0);
        NaturalNumber m = new NaturalNumber2(2);
        NaturalNumber mExpected = new NaturalNumber2(2);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(nExpected, n);
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    @Test
    public void testPowerMod_17_18_19() {
        NaturalNumber n = new NaturalNumber2(17);
        NaturalNumber nExpected = new NaturalNumber2(1);
        NaturalNumber p = new NaturalNumber2(18);
        NaturalNumber pExpected = new NaturalNumber2(18);
        NaturalNumber m = new NaturalNumber2(19);
        NaturalNumber mExpected = new NaturalNumber2(19);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(nExpected, n);
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    @Test
    public void testPowerMod_9_23_29() {
        NaturalNumber n = new NaturalNumber2(9);
        NaturalNumber nExpected = new NaturalNumber2(1);
        NaturalNumber p = new NaturalNumber2(5);
        NaturalNumber pExpected = new NaturalNumber2(5);
        NaturalNumber m = new NaturalNumber2(8);
        NaturalNumber mExpected = new NaturalNumber2(8);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(nExpected, n);
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    /*
     * Tests of testIsWitnessToCompositeness
     */

    @Test
    public void testIsWitnessToCompositeness_85_51() {
        NaturalNumber n = new NaturalNumber2(85);
        NaturalNumber nExpected = new NaturalNumber2(85);
        NaturalNumber w = new NaturalNumber2(51);
        boolean result = CryptoUtilities.isWitnessToCompositeness(w, n);
        assertEquals(nExpected, n);
        assertEquals(true, result);
    }

    @Test
    public void testIsWitnessToCompositeness_29_11() {
        NaturalNumber n = new NaturalNumber2(29);
        NaturalNumber nExpected = new NaturalNumber2(29);
        NaturalNumber w = new NaturalNumber2(11);
        boolean result = CryptoUtilities.isWitnessToCompositeness(w, n);
        assertEquals(nExpected, n);
        assertEquals(false, result);
    }

    @Test
    public void testIsWitnessToCompositeness_3000_13() {
        NaturalNumber n = new NaturalNumber2(3000);
        NaturalNumber nExpected = new NaturalNumber2(3000);
        NaturalNumber w = new NaturalNumber2(13);
        boolean result = CryptoUtilities.isWitnessToCompositeness(w, n);
        assertEquals(nExpected, n);
        assertEquals(true, result);
    }

    @Test
    public void testIsWitnessToCompositeness_103_17() {
        NaturalNumber n = new NaturalNumber2(103);
        NaturalNumber nExpected = new NaturalNumber2(103);
        NaturalNumber w = new NaturalNumber2(17);
        boolean result = CryptoUtilities.isWitnessToCompositeness(w, n);
        assertEquals(nExpected, n);
        assertEquals(false, result);
    }

    @Test
    public void testIsWitnessToCompositeness_1102_22() {
        NaturalNumber n = new NaturalNumber2(1102);
        NaturalNumber nExpected = new NaturalNumber2(1102);
        NaturalNumber w = new NaturalNumber2(22);
        boolean result = CryptoUtilities.isWitnessToCompositeness(w, n);
        assertEquals(nExpected, n);
        assertEquals(true, result);
    }

    /*
     * Tests of testIsPrime1
     */

    @Test
    public void testIsPrime1_23() {
        NaturalNumber n = new NaturalNumber2(23);
        NaturalNumber nExpected = new NaturalNumber2(23);
        boolean result = CryptoUtilities.isPrime1(n);
        assertEquals(nExpected, n);
        assertEquals(true, result);
    }

    @Test
    public void testIsPrime1_153() {
        NaturalNumber n = new NaturalNumber2(153);
        NaturalNumber nExpected = new NaturalNumber2(153);
        boolean result = CryptoUtilities.isPrime1(n);
        assertEquals(nExpected, n);
        assertEquals(false, result);
    }

    @Test
    public void testIsPrime1_174932() {
        NaturalNumber n = new NaturalNumber2(174932);
        NaturalNumber nExpected = new NaturalNumber2(174932);
        boolean result = CryptoUtilities.isPrime1(n);
        assertEquals(nExpected, n);
        assertEquals(false, result);
    }

    /*
     * Tests of testIsPrime2
     */

    @Test
    public void testIsPrime2_27() {
        NaturalNumber n = new NaturalNumber2(27);
        NaturalNumber nExpected = new NaturalNumber2(27);
        boolean result = CryptoUtilities.isPrime2(n);
        assertEquals(nExpected, n);
        assertEquals(false, result);
    }

    @Test
    public void testIsPrime2_2() {
        NaturalNumber n = new NaturalNumber2(2);
        NaturalNumber nExpected = new NaturalNumber2(2);
        boolean result = CryptoUtilities.isPrime2(n);
        assertEquals(nExpected, n);
        assertEquals(true, result);
    }

    @Test
    public void testIsPrime2_41() {
        NaturalNumber n = new NaturalNumber2(41);
        NaturalNumber nExpected = new NaturalNumber2(41);
        boolean result = CryptoUtilities.isPrime2(n);
        assertEquals(nExpected, n);
        assertEquals(true, result);
    }

    @Test
    public void testIsPrime2_119() {
        NaturalNumber n = new NaturalNumber2(119);
        NaturalNumber nExpected = new NaturalNumber2(119);
        boolean result = CryptoUtilities.isPrime2(n);
        assertEquals(nExpected, n);
        assertEquals(false, result);
    }

    @Test
    public void testIsPrime2_929() {
        NaturalNumber n = new NaturalNumber2(929);
        NaturalNumber nExpected = new NaturalNumber2(929);
        boolean result = CryptoUtilities.isPrime2(n);
        assertEquals(nExpected, n);
        assertEquals(true, result);
    }

    @Test
    public void testIsPrime2_10000() {
        NaturalNumber n = new NaturalNumber2(10000);
        NaturalNumber nExpected = new NaturalNumber2(10000);
        boolean result = CryptoUtilities.isPrime2(n);
        assertEquals(nExpected, n);
        assertEquals(false, result);
    }

    /*
     * Tests of testgenerateNextLikelyPrime
     */

    @Test
    public void testgenerateNextLikelyPrime_28() {
        NaturalNumber n = new NaturalNumber2(28);
        NaturalNumber nExpected = new NaturalNumber2(29);
        CryptoUtilities.generateNextLikelyPrime(n);
        assertEquals(nExpected, n);
    }

    @Test
    public void testgenerateNextLikelyPrime_701() {
        NaturalNumber n = new NaturalNumber2(701);
        NaturalNumber nExpected = new NaturalNumber2(701);
        CryptoUtilities.generateNextLikelyPrime(n);
        assertEquals(nExpected, n);
    }

}