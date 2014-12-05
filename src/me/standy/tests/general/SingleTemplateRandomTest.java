package me.standy.tests.general;

import me.standy.matchers.*;
import me.standy.streams.CharStream;
import me.standy.streams.RandomCharStream;
import me.standy.utility.Utility;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;
import java.util.List;

/**
 * Created by astepanov on 21.10.14.
 */
@RunWith(Parameterized.class)
public class SingleTemplateRandomTest {
    private final Class<? extends MetaTemplateMatcher> matcherClass;
    private final int streamSize;
    private final int templateSize;
    private final char[] alphabet;
    private MetaTemplateMatcher matcher;
    private String template;
    private CharStream stream;
    private int templateId;


    public SingleTemplateRandomTest(Class<? extends MetaTemplateMatcher> matcherClass, int streamSize, int templateSize, char[] alphabet) {
        this.matcherClass = matcherClass;
        this.streamSize = streamSize;
        this.templateSize = templateSize;
        this.alphabet = alphabet;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] classes = new Object[][]{
                {SingleTemplateMatcher.class},
                {NaiveTemplateMatcher.class},
                {StaticTemplateMatcher.class}
        };
        return Utility.cartesianProduct(classes, getParametersProjection());
    }

    public static Object[][] getParametersProjection() {
        return new Object[][]{
                {10000, 3, new char[]{'a', 'b'}},
                {1000, 2, new char[]{'a', 'b'}},
                {30000, 3, new char[]{'a', 'b', 'c'}},
        };
    }

    @Before
    public void setUp() throws Exception {
        matcher = matcherClass.newInstance();
        stream = new RandomCharStream(streamSize, alphabet);
        template = new RandomCharStream(templateSize, alphabet).toString();
        templateId = matcher.addTemplate(template);

    }

    @Test
    public void test() throws Exception {
        List<Occurrence> rightAnswer = Utility.getListOfOccurrences(new String[] {template}, new int[] {templateId}, stream.toString());
        List<Occurrence> result = matcher.matchStream(stream);

        Assert.assertTrue(matcherClass.toString(), Utility.isListsIsomorphic(rightAnswer, result));
    }

    public void benchmark() throws Exception {
        matcher.matchStream(stream);
    }
}
