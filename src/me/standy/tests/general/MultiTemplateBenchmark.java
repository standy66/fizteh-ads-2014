package me.standy.tests.general;

import com.carrotsearch.junitbenchmarks.BenchmarkRule;
import me.standy.matchers.MetaTemplateMatcher;
import org.junit.Rule;
import org.junit.rules.TestRule;

/**
 * @author andrew
 *         Created by andrew on 05.12.14.
 */
public class MultiTemplateBenchmark extends MultiTemplateRandomTest {
    @Rule
    public TestRule benchmarkRun = new BenchmarkRule();

    public MultiTemplateBenchmark(Class<? extends MetaTemplateMatcher> matcherClass, int textStreamSize, int templatesCount, int templateSize, char[] alphabet) {
        super(matcherClass, textStreamSize, templatesCount, templateSize, alphabet);
    }
}
