package me.standy.tests.modifiablesingletemplatematcher;

import me.standy.matchers.*;
import me.standy.streams.CharStream;
import me.standy.streams.StringStream;
import me.standy.utility.Utility;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author andrew
 *         Created by andrew on 18.12.14.
 */
@RunWith(Parameterized.class)
public class AppendPrependTest {
    private abstract static class Action {
        protected char c;

        public Action(char c) {
            this.c = c;
        }

        public abstract void apply(ModifiableSingleTemplateMatcher matcher);
        public abstract String apply(String s);
    }

    private static class Append extends Action {
        public Append(char c) {
            super(c);
        }

        @Override
        public void apply(ModifiableSingleTemplateMatcher matcher) {
            matcher.appendCharToTemplate(c);
        }

        @Override
        public String apply(String s) {
            return s + c;
        }
    }

    private static class Prepend extends Action {
        public Prepend(char c) {
            super(c);
        }

        @Override
        public void apply(ModifiableSingleTemplateMatcher matcher) {
            matcher.prependCharToTemplate(c);
        }

        @Override
        public String apply(String s) {
            return c + s;
        }
    }

    private Class<? extends ModifiableSingleTemplateMatcher> matcherClass;
    private ModifiableSingleTemplateMatcher matcher;
    private String text;
    private Action[] actions;

    public AppendPrependTest(Class<? extends ModifiableSingleTemplateMatcher> matcherClass, String text, Action... actions) {
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
                {ModifiableSingleTemplateMatcher.class}
        };
        return Utility.cartesianProduct(classes, getParametersProjection());
    }
    public static Object[][] getParametersProjection() {
        return new Object[][]{
                {"abacaba", new Action[] {new Append('b'), new Append('a'), new Prepend('a')}},
                {"abacaba", new Action[] {}},
                {"aaaaaaaaaaa", new Action[] {new Append('a'), new Prepend('a'), new Append('a'), new Prepend('b')}},
                {"", new Action[] {new Append('a')}},
                {"aab", new Action[] {new Append('b'), new Prepend('a'), new Prepend('a')}}
        };
    }

    @Test
    public void test() throws Exception {
        String template = "";
        int templateId = matcher.addTemplate(template);

        for (Action action : actions) {
            action.apply(matcher);
            template = action.apply(template);
            CharStream textStream = new StringStream(text);
            List<Occurrence> matcherAnswer = matcher.matchStream(textStream);

            List<Occurrence> rightAnswer = Utility.getListOfOccurrences(new String[]{template}, new int[]{templateId}, text);
            Assert.assertTrue(String.format("Template: %s, text: %s, matcher ans: %s, right ans: %s", template, text, matcherAnswer, rightAnswer), Utility.isListsIsomorphic(matcherAnswer, rightAnswer));
        }
    }
}
