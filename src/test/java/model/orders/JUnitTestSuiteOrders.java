package model.orders;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * This class is to run all of the Order Test cases
 */
@RunWith(Suite.class)
@SuiteClasses({ AdvanceTest.class, AirliftTest.class, BlockadeTest.class, BombTest.class, DeployTest.class,
        NegotiateTest.class })
public class JUnitTestSuiteOrders {

}
