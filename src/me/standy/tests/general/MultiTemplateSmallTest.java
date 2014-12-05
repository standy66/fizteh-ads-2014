package me.standy.tests.general;

import me.standy.matchers.MetaTemplateMatcher;
import me.standy.matchers.NaiveTemplateMatcher;
import me.standy.matchers.Occurrence;
import me.standy.streams.CharStream;
import me.standy.streams.StringStream;
import me.standy.utility.Utility;
import org.junit.Assert;
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
public class MultiTemplateSmallTest {
    private final Class<? extends MetaTemplateMatcher> matcherClass;
    private final String[] templates;
    private final CharStream textStream;
    private final String text;

    public MultiTemplateSmallTest(Class<? extends MetaTemplateMatcher> matcherClass, String text, String... templates) {
        this.matcherClass = matcherClass;
        this.text = text;
        this.textStream = new StringStream(text);
        this.templates = templates;

    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] classes = new Object[][]{
                {NaiveTemplateMatcher.class}
        };
        return Utility.cartesianProduct(classes, getParametersProjection());
    }

    public static Object[][] getParametersProjection() {
        return new Object[][]{
                {"abacaba", new String[]{"aba", "c", "a"}},
                {"aaaaaaaaabaaaabbbaaaaba", new String[]{"a", "b", "ab", "aba", "bb"}},
                {"Much text. So letters.", new String[]{"", "", "", ""}}
        };
    }

    @Test
    public void test() throws Exception {
        MetaTemplateMatcher matcher = matcherClass.newInstance();
        int[] templateIds = new int[templates.length];
        for (int i = 0; i < templates.length; i++) {
            templateIds[i] = matcher.addTemplate(templates[i]);
        }
        List<Occurrence> matcherAnswer = matcher.matchStream(textStream);
        List<Occurrence> rightAnswer = Utility.getListOfOccurrences(templates, templateIds, text);
        Assert.assertTrue(String.format("Templates: %s, text: %s", templates, text), Utility.isListsIsomorphic(matcherAnswer, rightAnswer));
    }
}
