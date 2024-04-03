package utility.state;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import java.awt.*;


/**
 * JUnit Test Suite Class to run all the Test cases of state pattern
 */
@RunWith(Suite.class)
@SuiteClasses({ EditTest.class, ExecuteOrderTest.class, IssueOrderTest.class })
public class JunitTestSuiteState {

}
