package me.standy.tests.general;

import me.standy.matchers.*;
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
 * Created by astepanov on 21.10.14.
 */

@RunWith(Parameterized.class)
public class SingleTemplateSmallTest {
    private Class<? extends MetaTemplateMatcher> matcherClass;
    private String template;
    private String text;

    public SingleTemplateSmallTest(Class<? extends MetaTemplateMatcher> matcherClass, String template, String text) {
        this.matcherClass = matcherClass;
        this.template = template;
        this.text = text;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] classes = new Object[][]{
                {SingleTemplateMatcher.class},
                {NaiveTemplateMatcher.class},
                {StaticTemplateMatcher.class},
                {ModifiableSingleTemplateMatcher.class}
        };
        return Utility.cartesianProduct(classes, getParametersProjection());
    }

    public static Object[][] getParametersProjection() {
        return new Object[][]{
                {"", "abacaba"},
                {"aba", "abacabadabacaba"},
                {"", ""},
                {"a", "aaaaaaaaaaaaaaaaaaaa"},
                {"aa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaa"},
                {"aba", "ababababababababababab"},
                {"this couldn't be found", ""}
        };
    }

    @Test
    public void test() throws IllegalAccessException, InstantiationException {
        MetaTemplateMatcher matcher = matcherClass.newInstance();
        int templateId = matcher.addTemplate(template);
        CharStream textStream = new StringStream(text);
        List<Occurrence> matcherAnswer = matcher.matchStream(textStream);
        List<Occurrence> rightAnswer = Utility.getListOfOccurrences(new String[]{template}, new int[]{templateId}, text);
        Assert.assertTrue(String.format("Template: %s, text: %s", template, text), Utility.isListsIsomorphic(matcherAnswer, rightAnswer));
    }


}
