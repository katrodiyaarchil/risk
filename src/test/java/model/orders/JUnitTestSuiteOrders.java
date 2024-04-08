package model.orders;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * JUnit Test Suite Class to run all the Test cases of orders
 */
@RunWith(Suite.class)
@SuiteClasses({ AdvanceTest.class, AirliftTest.class, BlockadeTest.class, BombTest.class, DeployTest.class,
        NegotiateTest.class })
public class JUnitTestSuiteOrders {

}
