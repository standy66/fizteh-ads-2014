package me.standy.tests.modifiablesingletemplatematcher;

import me.standy.matchers.ModifiableSingleTemplateMatcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author andrew
 *         Created by andrew on 18.12.14.
 */
@RunWith(Parameterized.class)
public class AdditionalInterfaceTest {
    private Class<? extends ModifiableSingleTemplateMatcher> matcherClass;
    private ModifiableSingleTemplateMatcher matcher;

    public AdditionalInterfaceTest(Class<? extends ModifiableSingleTemplateMatcher> matcherClass) {
        this.matcherClass = matcherClass;
    }

    @Before
    public void setUp() throws Exception {
        matcher = matcherClass.newInstance();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {ModifiableSingleTemplateMatcher.class},
        });
    }

    @Test(expected = IllegalStateException.class)
    public void testPrependBeforeAddTemplateShouldFail() throws Exception {
        matcher.prependCharToTemplate('a');
    }

    @Test(expected = IllegalStateException.class)
    public void testAppendBeforeAddTemplateShouldFail() throws Exception {
        matcher.appendCharToTemplate('a');
    }
}
