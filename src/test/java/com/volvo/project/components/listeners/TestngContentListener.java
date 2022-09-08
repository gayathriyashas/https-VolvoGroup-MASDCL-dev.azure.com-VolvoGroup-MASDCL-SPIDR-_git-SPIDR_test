package com.volvo.project.components.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.util.Iterator;

/**
 * This is for Azure Test Plans integration with Solidify extension
 * This is deleting all configuration methods, which is beforeMethod, beforeTest, afterTest etc.
 * Due to the fact that we don't want to have configuration methods in our test plans.
 */
public class TestngContentListener extends TestListenerAdapter {
    private final Logger logger = LoggerFactory.getLogger(TestngContentListener.class);

    @Override
    public void onFinish(ITestContext context) {
        deleteAllMethod(context.getPassedConfigurations().getAllResults().iterator());
        deleteAllMethod(context.getSkippedConfigurations().getAllResults().iterator());
        deleteAllMethod(context.getFailedConfigurations().getAllResults().iterator());
    }

    public void deleteAllMethod(Iterator<ITestResult> listTestMethods) {
        while (listTestMethods.hasNext()) {
            ITestResult testResult = listTestMethods.next();
            logger.debug("Deleting from result: {}" + testResult.getMethod().getMethodName());
            listTestMethods.remove();
        }
    }
}
