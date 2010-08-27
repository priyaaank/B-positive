package com.barefoot.bpositive.test;

import junit.framework.TestSuite;
import android.test.InstrumentationTestRunner;
import android.test.InstrumentationTestSuite;

public class MyBPositiveAllTestRunner extends InstrumentationTestRunner {

	@Override
	public TestSuite getAllTests() {
		InstrumentationTestSuite testSuite = new InstrumentationTestSuite(this);
		
		testSuite.addTestSuite(DashboardTest.class);
		return testSuite;
	}
	
	
	@Override
	public ClassLoader getLoader() {
		return MyBPositiveAllTestRunner.class.getClassLoader();
	}
	
}
