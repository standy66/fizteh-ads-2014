package me.standy.tests.general;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;
import com.carrotsearch.junitbenchmarks.annotation.AxisRange;
import com.carrotsearch.junitbenchmarks.annotation.BenchmarkMethodChart;
import me.standy.matchers.MetaTemplateMatcher;
import me.standy.matchers.NaiveTemplateMatcher;
import me.standy.matchers.SingleTemplateMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author andrew
 *         Created by andrew on 04.12.14.
 */
@BenchmarkOptions(concurrency = 1, callgc = false, warmupRounds = 2, benchmarkRounds = 5)
@RunWith(Parameterized.class)
public class SingleTemplateBenchmark extends SingleTemplateRandomTest {
    @Rule
    public TestRule benchmarkRun = new BenchmarkRule();

    public SingleTemplateBenchmark(Class<? extends MetaTemplateMatcher> matcherClass, int streamSize, int templateSize, char[] alphabet) {
        super(matcherClass, streamSize, templateSize, alphabet);
    }

    @Override
    protected boolean isBenchmark() {
        return true;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {SingleTemplateMatcher.class, 100000000, 10000, new char[]{'a', 'b', 'c'}}
        });
    }
}
