package me.standy.tests.dynamictemplatematcher;

import org.junit.Assert;
import me.standy.matchers.DynamicTemplateMatcher;
import me.standy.matchers.NaiveTemplateMatcher;
import me.standy.matchers.Occurrence;
import me.standy.matchers.StaticTemplateMatcher;
import me.standy.streams.CharStream;
import me.standy.streams.StringStream;
import me.standy.utility.Utility;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author andrew
 *         Created by andrew on 19.12.14.
 */
@RunWith(Parameterized.class)
public class DynamicTemplateMatcherTest {
    private static class Add {
        public String template;

        public Add(String template) {
            this.template = template;
        }
    }

    private static class Check { }

    private Class<? extends DynamicTemplateMatcher> matcherClass;
    private DynamicTemplateMatcher matcher;
    private String text;
    private Object[] actions;

    public DynamicTemplateMatcherTest(Class<? extends DynamicTemplateMatcher> matcherClass, String text, Object[] actions) {
        this.matcherClass = matcherClass;
        this.text = text;
        this.actions = actions;
    }

    @Before
    public void setUp() throws Exception {
        matcher = matcherClass.newInstance();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] classes = new Object[][]{
                {DynamicTemplateMatcher.class}
        };
        return Utility.cartesianProduct(classes, getParametersProjection());
    }

    public static Object[][] getParametersProjection() {
        return new Object[][]{
                {"abacaba", new Object[] {new Add("a"), new Check(), new Add(""), new Check(), new Add("aba"), new Add("c"), new Check()}},
                {"", new Object[] {new Add(""), new Check()}},
                {"aaaaaaaaaaaa", new Object[] {new Add("a"), new Check(), new Add(""), new Add("aa"), new Add("aba"), new Add("c"), new Add(""), new Check()}},
        };
    }

    @Test
    public void test() throws Exception {
        List<String> templates = new ArrayList<>();
        List<Integer> templateIds = new ArrayList<>();
        int step = 0;
        for (Object action : actions) {
            step++;
            if (action.getClass() == Check.class) {
                CharStream textStream = new StringStream(text);
                List<Occurrence> matcherAnswer = matcher.matchStream(textStream);
                int[] templateIdsPrimitive = new int[templateIds.size()];
                for (int i = 0; i < templateIdsPrimitive.length; i++) {
                    templateIdsPrimitive[i] = templateIds.get(i);
                }
                List<Occurrence> rightAnswer = Utility.getListOfOccurrences(templates.toArray(new String[0]), templateIdsPrimitive, text);
                Assert.assertTrue(String.format("Step: %d, rightAns: %s, matcherAns: %s", step, rightAnswer, matcherAnswer), Utility.isListsIsomorphic(matcherAnswer, rightAnswer));
            }
            if (action.getClass() == Add.class) {
                String template = ((Add) action).template;
                templateIds.add(matcher.addTemplate(template));
                templates.add(template);
            }
        }
    }
}
