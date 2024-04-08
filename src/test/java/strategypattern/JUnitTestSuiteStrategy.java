package strategypattern;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * JUnit Test Suite Class to run all the Test cases of Strategy
 */
@RunWith(Suite.class)
@SuiteClasses({ AggresivePlayerStrategyTest.class, BenevolentPlayerStrategyTest.class, CheaterPlayerStrategyTest.class,
        RandomPlayerStrategyTest.class })
public class JUnitTestSuiteStrategy {

}
