package me.standy.tests;

import me.standy.matchers.SingleTemplateMatcher;
import me.standy.streams.RandomCharStream;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by astepanov on 21.10.14.
 */
public class SingleTemplateMatcherInterfaceTest {
    private SingleTemplateMatcher matcher;

    @Before
    public void initialize() {
        matcher = new SingleTemplateMatcher();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testMultipleTemplates() {
        matcher.addTemplate("aba");
        matcher.addTemplate("cde");
    }

    @Test(expected = IllegalStateException.class)
    public void testNoTemplates() {
        matcher.matchStream(new RandomCharStream(10, new char[]{'a', 'b'}));
    }
}
