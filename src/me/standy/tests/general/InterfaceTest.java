package me.standy.tests.general;

import me.standy.matchers.MetaTemplateMatcher;
import me.standy.matchers.SingleTemplateMatcher;
import me.standy.streams.RandomCharStream;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author andrew
 *         Created by andrew on 04.12.14.
 */
@RunWith(Parameterized.class)
public class InterfaceTest {
    private final Class<? extends MetaTemplateMatcher> matcherClass;
    private MetaTemplateMatcher matcher;

    public InterfaceTest(Class<? extends MetaTemplateMatcher> matcherClass) {
        this.matcherClass = matcherClass;
    }

    @Before
    public void setUp() throws Exception {
        matcher = matcherClass.newInstance();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {
                        SingleTemplateMatcher.class
                }
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullTemplate() throws Exception {
        matcher.addTemplate(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullStream() throws Exception {
        matcher.addTemplate("abc");
        matcher.matchStream(null);
    }

    @Test(expected = IllegalStateException.class)
    public void testNoTemplates() throws Exception {
        matcher.matchStream(new RandomCharStream(10, new char[]{'a', 'b'}));
    }
}
