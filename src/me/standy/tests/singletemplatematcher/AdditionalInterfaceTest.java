package me.standy.tests.singletemplatematcher;

import me.standy.matchers.SingleTemplateMatcher;
import me.standy.streams.RandomCharStream;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by astepanov on 21.10.14.
 */
public class AdditionalInterfaceTest {
    private SingleTemplateMatcher matcher;

    @Before
    public void setUp() throws Exception {
        matcher = new SingleTemplateMatcher();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testMultipleTemplates() throws Exception {
        matcher.addTemplate("aba");
        matcher.addTemplate("cde");
    }
}
