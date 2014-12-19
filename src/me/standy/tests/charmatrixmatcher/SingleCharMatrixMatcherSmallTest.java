package me.standy.tests.charmatrixmatcher;

import org.junit.Assert;
import me.standy.matchers.*;
import me.standy.utility.Utility;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;
import java.util.List;

/**
 * @author andrew
 *         Created by andrew on 19.12.14.
 */

@RunWith(Parameterized.class)
class SingleCharMatrixMatcherSmallTest {
    private Class<? extends MetaCharMatrixMatcher> matcherClass;
    private MetaCharMatrixMatcher matcher;
    private String[] template;
    private String[] target;
    private int templateId;
    private List<Occurrence2D> rightAnswer;

    public SingleCharMatrixMatcherSmallTest(Class<? extends MetaCharMatrixMatcher> matcherClass, String[] template, String[] target) {
        this.matcherClass = matcherClass;
        this.template = template;
        this.target = target;
    }

    @Before
    public void setUp() throws Exception {
        matcher = matcherClass.newInstance();
        templateId = matcher.addTemplate(template);
        rightAnswer = Utility.matchCharMatrices(template, target, templateId);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] classes = new Object[][]{
                {SingleCharMatrixMatcher.class}
        };
        return Utility.cartesianProduct(classes, getParametersProjection());
    }

    public static Object[][] getParametersProjection() {
        return new Object[][]{
                {
                        new String[] {"a"},
                        new String[] {"aa", "aa"}
                },
        };
    }

    @Test
    public void testName() throws Exception {
        List<Occurrence2D> matcherAnswer = matcher.matchTarget(target);
        Assert.assertTrue(String.format("matcherAns: %s rightAns: %s", matcherAnswer, rightAnswer), Utility.isListsIsomorphic(rightAnswer, matcherAnswer));
    }
}
