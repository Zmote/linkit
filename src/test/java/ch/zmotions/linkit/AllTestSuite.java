package ch.zmotions.linkit;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
		UnitTestSuite.class,
		IntegrationTestSuite.class,
		UITestSuite.class
})
public class AllTestSuite {}
