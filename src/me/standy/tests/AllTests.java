package me.standy.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by astepanov on 21.10.14.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ SingleTemplateMatcherInterfaceTest.class,
                        SingleTemplateSmallTest.class,
                        SingleTemplateStressTest.class,
                        SingleTemplateMatcherBenchmark.class
                        } )
public class AllTests {
}
