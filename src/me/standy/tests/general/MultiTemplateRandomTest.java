package me.standy.tests.general;

import me.standy.matchers.MetaTemplateMatcher;
import me.standy.matchers.NaiveTemplateMatcher;
import me.standy.matchers.Occurrence;
import me.standy.matchers.StaticTemplateMatcher;
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
 * @author andrew
 *         Created by andrew on 05.12.14.
 */
@RunWith(Parameterized.class)
public class MultiTemplateRandomTest {
    private final Class<? extends MetaTemplateMatcher> matcherClass;
    private final int textStreamSize;
    private final int templatesCount;
    private final int templateSize;
    private final char[] alphabet;

    private MetaTemplateMatcher matcher;
    private CharStream stream;
    private String[] templates;
    private int[] templateIds;
    private List<Occurrence> rightAnswer;


    public MultiTemplateRandomTest(Class<? extends MetaTemplateMatcher> matcherClass,
                                   int textStreamSize, int templatesCount, int templateSize, char[] alphabet) {
        this.matcherClass = matcherClass;
        this.textStreamSize = textStreamSize;
        this.templatesCount = templatesCount;
        this.templateSize = templateSize;
        this.alphabet = alphabet;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] classes = new Object[][]{
                {NaiveTemplateMatcher.class},
                {StaticTemplateMatcher.class}
        };
        return Utility.cartesianProduct(classes, getParametersProjection());
    }

    public static Object[][] getParametersProjection() {
        return new Object[][]{
                {100000, 10, 8, new char[]{'a', 'b'}}
        };
    }

    @Before
    public void setUp() throws Exception {
        matcher = matcherClass.newInstance();
        stream = new RandomCharStream(textStreamSize, alphabet);
        templates = new String[templatesCount];
        for (int i = 0; i < templatesCount; i++) {
            templates[i] = new RandomCharStream(templateSize, alphabet).toString();
            //TODO: fix this duct tape
            Thread.sleep(0, 100);
        }
        templateIds = new int[templatesCount];
        for (int i = 0; i < templatesCount; i++) {
            templateIds[i] = matcher.addTemplate(templates[i]);
        }
        rightAnswer = Utility.getListOfOccurrences(templates, templateIds, stream.toString());
    }

    @Test
    public void test() throws Exception {
        List<Occurrence> matcherAnswer = matcher.matchStream(stream);
        Assert.assertTrue(Utility.isListsIsomorphic(rightAnswer, matcherAnswer));
    }
}
