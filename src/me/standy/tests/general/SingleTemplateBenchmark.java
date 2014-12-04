package me.standy.tests.general;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;
import com.carrotsearch.junitbenchmarks.annotation.AxisRange;
import com.carrotsearch.junitbenchmarks.annotation.BenchmarkMethodChart;
import me.standy.matchers.MetaTemplateMatcher;
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
@AxisRange(min = 0, max = 1)
@BenchmarkMethodChart(filePrefix = "benchmark-lists")
@RunWith(Parameterized.class)
public class SingleTemplateBenchmark extends SingleTemplateRandomTest {
    @Rule
    public TestRule benchmarkRun = new BenchmarkRule();

    public SingleTemplateBenchmark(Class<? extends MetaTemplateMatcher> matcherClass, int streamSize, int templateSize, char[] alphabet) {
        super(matcherClass, streamSize, templateSize, alphabet);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {
                        SingleTemplateMatcher.class, 1000000, 1000, new char[] {'a', 'b', 'c'}
                }
        });
    }

    @Override
    @BenchmarkOptions(benchmarkRounds = 0)
    public void test() throws Exception {
        super.test();
    }

    @Test
    @Override
    @BenchmarkOptions(benchmarkRounds = 5, warmupRounds = 2, callgc = false)
    public void benchmark() throws Exception {
        super.benchmark();
    }
}
