package me.standy.tests;

import me.standy.utility.DumbSubstringFinder;
import me.standy.utility.KMPSubstringFinder;
import me.standy.utility.SubstringFinder;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Created by astepanov on 12.09.14.
 */
public class KMPSubstringFinderTest {

    private int[] filled(int size, int value) {
        int[] array = new int[size];
        Arrays.fill(array, value);
        return array;
    }

    private void testString(SubstringFinder finder, String sample, String text, int[] result) {
        finder.setSample(sample);
        for (int i = 0; i < text.length(); i++) {
            assertEquals(finder.nextChar(text.charAt(i)), result[i] != 0);
        }
    }

    private void smallTest(SubstringFinder finder) {
        testString(finder, "abacaba", "abacab", filled(6, 0));
        testString(finder, "a", "aaaaaaaa", filled(8, 1));
        testString(finder, "", "abacaba", filled(7, 1));
        testString(finder, "aba", "abacaba", new int[]{0, 0, 1, 0, 0, 0, 1});
        testString(finder, "bb", "bbbbbb", new int[]{0, 1, 1, 1, 1, 1});
    }

    private void interfaceTest(SubstringFinder finder) {
        finder.nextChar('a');
    }

    private void stressTest(SubstringFinder finder, int sampleSize, int streamSize, int alphabetSize) {
        Random r = new Random();
        StringBuilder sample = new StringBuilder();
        for (int i = 0; i < sampleSize; i++)
            sample.append((char) (r.nextInt(alphabetSize) + 1));
        SubstringFinder dumb = new DumbSubstringFinder();
        dumb.setSample(sample.toString());
        finder.setSample(sample.toString());
        int occNumber = 0;
        for (int i = 0; i < streamSize; ++i) {
            char c = (char) (r.nextInt(alphabetSize) + 1);
            boolean res1 = dumb.nextChar(c);
            boolean res2 = finder.nextChar(c);
            if (res1)
                occNumber++;
            assertEquals(res1, res2);
        }
        System.err.printf("%s stress test with parameters (sampleSize = %d, streamSize = %d, alphabetSize = %d) ended. Total number of occurrences: %d\n", finder.getClass().toString(), sampleSize, streamSize, alphabetSize, occNumber);
    }

    @Test
    public void smallTest() {
        smallTest(new KMPSubstringFinder());
        smallTest(new DumbSubstringFinder());
    }

    @Test(expected = IllegalStateException.class)
    public void interfaceTest() {
        interfaceTest(new KMPSubstringFinder());
        interfaceTest(new DumbSubstringFinder());
    }

    @Test
    public void smallStressTests() {
        SubstringFinder kmp = new KMPSubstringFinder();
        stressTest(kmp, 3, 1000, 5);
        stressTest(kmp, 4, 1000, 3);
        stressTest(kmp, 4, 1000, 3);
        stressTest(kmp, 10, 10000, 2);
    }

    @Test
    public void largeStressTest() {
        SubstringFinder kmp = new KMPSubstringFinder();
        stressTest(kmp, 20, 10000000, 2);
        stressTest(kmp, 10, 10000000, 4);
        stressTest(kmp, 7, 10000000, 8);
    }
}
