package warzone;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * This class is to run all of the Test cases
 */
@RunWith(Suite.class)
@SuiteClasses({ GameModelTest.class, MapTest.class, OrderTest.class })
public class JUnitTestSuite {

}
