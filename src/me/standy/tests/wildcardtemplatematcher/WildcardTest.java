package me.standy.tests.wildcardtemplatematcher;

import me.standy.matchers.*;
import me.standy.streams.CharStream;
import me.standy.streams.StringStream;
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
 *         Created by andrew on 18.12.14.
 */
@RunWith(Parameterized.class)
public class WildcardTest {
    private Class<? extends WildcardSingleTemplateMatcher> matcherClass;
    private WildcardSingleTemplateMatcher matcher;
    private String template;
    private String text;

    public WildcardTest(Class<? extends WildcardSingleTemplateMatcher> matcherClass, String template, String text) {
        this.matcherClass = matcherClass;
        this.template = template;
        this.text = text;
    }

    @Before
    public void setUp() throws Exception {
        matcher = matcherClass.newInstance();

    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] classes = new Object[][]{
                {WildcardSingleTemplateMatcher.class}
        };
        return Utility.cartesianProduct(classes, getParametersProjection());
    }

    public static Object[][] getParametersProjection() {
        return new Object[][]{
                {"?", "aaa"},
                {"?b?", "ababcbdblbbbbbcccc"},
                {"??????", "abc"},
                {"???b", "ccccccccccccccb"},
                {"", "abacaba"},
                {"?????", "TEXT. TEXT. TEXT. TEXT. TEXT. TEXT. TEXT. TEXT. "},
                {"???.dat", "file.dat img.dat pic.dat str.dat .dat"}
        };
    }

    @Test
    public void test() throws IllegalAccessException, InstantiationException {
        int templateId = matcher.addTemplate(template);
        CharStream textStream = new StringStream(text);
        List<Occurrence> matcherAnswer = matcher.matchStream(textStream);
        template = template.replace('?', '.');
        List<Occurrence> rightAnswer = Utility.getListOfOccurrencesRegexp(new String[]{template}, new int[]{templateId}, text);
        Assert.assertTrue(String.format("Template: %s, text: %s, matcher ans: %s, right ans: %s", template, text, matcherAnswer, rightAnswer), Utility.isListsIsomorphic(matcherAnswer, rightAnswer));
    }

}
