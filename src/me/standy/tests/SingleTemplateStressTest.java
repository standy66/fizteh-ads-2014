package me.standy.tests;

import me.standy.matchers.SingleTemplateMatcher;
import me.standy.matchers.utility.Occurrence;
import me.standy.streams.RandomCharStream;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Created by astepanov on 21.10.14.
 */
@RunWith(Parameterized.class)
public class SingleTemplateStressTest {
    private int streamSize;
    private int templateSize;
    private char[] alphabet;

    public SingleTemplateStressTest(int streamSize, int templateSize, char[] alphabet) {
        this.streamSize = streamSize;
        this.templateSize = templateSize;
        this.alphabet = alphabet;
    }


    @Parameterized.Parameters
    public static Collection<Object[]> getParameters() {
        return Arrays.asList(new Object[][] {
                { 10000, 3, new char[] {'a', 'b'} },
                { 1000, 2, new char[] {'a', 'b'} },
                { 30000, 3, new char[] {'a', 'b', 'c'} },
        });
    }

    @Test
    public void test() {
        RandomCharStream stream = new RandomCharStream(streamSize, alphabet);
        String template = new RandomCharStream(templateSize, alphabet).toString();
        SingleTemplateMatcher matcher = new SingleTemplateMatcher();
        int templateId = matcher.addTemplate(template);
        List<Occurrence> rightAnswer = Utility.getListOfOccurrences(new String[]{template}, new int[]{templateId}, stream.toString());
        List<Occurrence> result = matcher.matchStream(stream);
        Assert.assertTrue(Utility.isListsIsomorphic(rightAnswer, result));
    }
}
