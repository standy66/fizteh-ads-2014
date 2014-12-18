package me.standy.tests.singletemplatematcher;

import com.sun.org.apache.xpath.internal.operations.Mod;
import me.standy.matchers.ModifiableSingleTemplateMatcher;
import me.standy.matchers.SingleTemplateMatcher;
import me.standy.streams.RandomCharStream;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by astepanov on 21.10.14.
 */
@RunWith(Parameterized.class)
public class AdditionalInterfaceTest {
    private Class<? extends SingleTemplateMatcher> matcherClass;
    private SingleTemplateMatcher matcher;

    public AdditionalInterfaceTest(Class<? extends SingleTemplateMatcher> matcherClass) {
        this.matcherClass = matcherClass;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {SingleTemplateMatcher.class},
                {ModifiableSingleTemplateMatcher.class},
        });
    }

    @Before
    public void setUp() throws Exception {
        matcher = matcherClass.newInstance();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testMultipleTemplates() throws Exception {
        matcher.addTemplate("aba");
        matcher.addTemplate("cde");
    }
}
