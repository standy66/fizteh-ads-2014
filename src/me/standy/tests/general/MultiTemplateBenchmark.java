package me.standy.tests.general;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;
import me.standy.matchers.MetaTemplateMatcher;
import me.standy.matchers.SingleTemplateMatcher;
import me.standy.matchers.StaticTemplateMatcher;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author andrew
 *         Created by andrew on 05.12.14.
 *
 */
@BenchmarkOptions(concurrency = 1, callgc = false, warmupRounds = 2, benchmarkRounds = 5)
public class MultiTemplateBenchmark extends MultiTemplateRandomTest {
    @Rule
    public TestRule benchmarkRun = new BenchmarkRule();

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {StaticTemplateMatcher.class, 1000000, 1000, 1000, new char[]{'a', 'b'}}
        });
    }

    public MultiTemplateBenchmark(Class<? extends MetaTemplateMatcher> matcherClass, int textStreamSize, int templatesCount, int templateSize, char[] alphabet) {
        super(matcherClass, textStreamSize, templatesCount, templateSize, alphabet);
    }

    @Override
    protected boolean isBenchmark() {
        return true;
    }
}
