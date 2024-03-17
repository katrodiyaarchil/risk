import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import model.JUnitTestSuiteModel;
import model.orders.JUnitTestSuiteOrders;
import utility.state.JunitTestSuiteState;

/**
 * JUnit Test Suite Class to run all the test suite.
 */
@RunWith(Suite.class)
@SuiteClasses({JUnitTestSuiteModel.class,JUnitTestSuiteOrders.class,JunitTestSuiteState.class})
public class JUnitTestSuite {

}
