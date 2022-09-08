package com.volvo.project.base;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.filter.log.RequestLoggingFilter;
import com.jayway.restassured.filter.log.ResponseLoggingFilter;
import com.volvo.project.components.Utils;
import com.volvo.project.components.reporting.TestLog;
import com.volvo.project.parameters.ExecutionParameters;
import com.volvo.project.parameters.ExecutionParametersLoader;
import io.qameta.allure.Step;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;

public class TestBase {
    protected static final TestLog logger = new TestLog();
    protected ThreadLocal<String> dataProviderTestParameters = new ThreadLocal<>();
    protected ThreadLocal<String> startTimestamp = new ThreadLocal<>();
    protected Utils utils = new Utils();
    protected final ExecutionParameters executionParameters = new ExecutionParametersLoader().getExecutionParameters();

    @BeforeSuite(alwaysRun = true)
    public void beforeAllTests() {
        configureRestAssured();
    }

    @BeforeClass(alwaysRun = true)
    public void beforeEveryTestClass() {
        logger.logStartTestClassExecution(getClass().getSimpleName());
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeEveryTestMethod(Method testMethod) {
        startTimestamp.set(utils.currentTimeStamp());
        logger.logStartTestExecution(testMethod.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void afterEveryTestMethod(ITestResult result, Method testMethod) {
        logger.logEndTestExecution(testMethod.getName() + dataProviderParameters(), result.getStatus(), readEnv());
    }

    @AfterClass(alwaysRun = true)
    public void afterEveryTestClass() {
        logger.logEndTestClassExecution(getClass().getSimpleName());
    }


    private String dataProviderParameters() {
        return dataProviderTestParameters.get() != null ? " (" + dataProviderTestParameters.get() + ")" : "";
    }

    protected String readEnv() {
        String env = "CLOSED!";
        if (System.getProperty("testedEnv") == null || System.getProperty("testedEnv").equals("uknownEnvironment")) {
            System.setProperty("testedEnv", env);
        }
        return env;
    }

    @Step("Configure RestAssured")
    private void configureRestAssured() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.useRelaxedHTTPSValidation();
    }
}
