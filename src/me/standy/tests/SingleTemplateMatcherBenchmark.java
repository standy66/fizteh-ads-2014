package me.standy.tests;

import com.carrotsearch.junitbenchmarks.AbstractBenchmark;
import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import me.standy.matchers.SingleTemplateMatcher;
import me.standy.streams.CharStream;
import me.standy.streams.RandomCharStream;
import me.standy.streams.StringStream;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by astepanov on 21.10.14.
 */
public class SingleTemplateMatcherBenchmark extends AbstractBenchmark {
    private CharStream text;
    private String template;
    private SingleTemplateMatcher matcher;

    @Before
    public void run() {

        char[] alphabet = { 'a', 'b', 'c', 'd' };
        text = new StringStream(new RandomCharStream(1000000, alphabet).toString());
        template = new RandomCharStream(1000, alphabet).toString();
        matcher = new SingleTemplateMatcher();
        matcher.addTemplate(template);
    }


    @Test
    @BenchmarkOptions(warmupRounds = 5, benchmarkRounds = 20, callgc = false)
    public void test() {
        matcher.matchStream(text);
    }
}
