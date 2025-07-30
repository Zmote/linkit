package ch.zmotions.linkit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		UnitTestSuite.class,
		IntegrationTestSuite.class,
		UITestSuite.class
})
public class AllTestSuite {}
