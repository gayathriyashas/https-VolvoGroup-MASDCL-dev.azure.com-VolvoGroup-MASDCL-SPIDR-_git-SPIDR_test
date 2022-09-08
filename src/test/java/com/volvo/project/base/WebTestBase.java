package com.volvo.project.base;

import com.google.common.collect.ImmutableMap;
import com.volvo.project.components.setup.WebDriverFactory;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

public class WebTestBase extends TestBase {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @BeforeMethod(alwaysRun = true)
    public void beforeEveryWebTestMethod(Method testMethod) {
        driver.set(ThreadGuard.protect(new WebDriverFactory(executionParameters).getWebDriver()));
    }

    @AfterMethod(alwaysRun = true)
    public void afterEveryWebTestMethod(ITestResult result, Method testMethod) {
        saveScreenshotIfTestFailed(result);
        driver.get().quit();
        driver.remove();
    }

    /**
     * This method is executed after whole test suite. Test suites are defined in TestNG xml config files.
     */
    @AfterSuite(alwaysRun = true)
    public void afterTestSuite() {
        setAllureEnvironmentInfo();
    }

    @Step("Set allure environment info")
    private void setAllureEnvironmentInfo() {
        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("Browser: ", executionParameters.getBrowser())
                        .put("Browser Resolution: ", executionParameters.getResolutionWidth() + "x" + executionParameters.getResolutionHeight())
                        .put("Headless: ", String.valueOf(executionParameters.isHeadless()))
                        .put("Test Environment: ", Optional.ofNullable(System.getProperty("testedEnv")).orElse("uknownEnv"))
                        .put("APP Version", Optional.ofNullable(System.getProperty("ActualVersion")).orElse("uknownAppVersion"))
                        .put("Java Version : ", System.getProperty("java.version"))
                        .put("Execution Machine", getMachineName())
                        .build(), System.getProperty("user.dir") + "/target/allure-results/");
    }

    private String getMachineName() {
        String hostname = "Unknown Machine";
        try {
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
        } catch (UnknownHostException ex) {
            logger.info("Hostname can not be resolved");
        }
        return hostname;
    }

    @Step("Save screenshot if test failed")
    private void saveScreenshotIfTestFailed(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            saveScreenShot(driver.get());
        }
    }

    @Step("Take screenshot")
    @Attachment
    protected byte[] saveScreenShot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Override
    protected String readEnv() {
        String env;
        try {
            if (driver.get().getCurrentUrl().contains("ipmr-qa.")) {
                env = "INTERNET QA";
            } else if (driver.get().getCurrentUrl().contains("ipmr-test")) {
                env = "INTERNET TEST";
            } else if (driver.get().getCurrentUrl().contains("ipmr-dev")) {
                env = "INTERNET DEV";
            } else {
                env = "uknownEnvironment";
            }
        } catch (NullPointerException | NoSuchSessionException e) {
            env = "CLOSED!";
        }
        if (System.getProperty("testedEnv") == null || System.getProperty("testedEnv").equals("uknownEnvironment")) {
            System.setProperty("testedEnv", env);
        }
        return env;
    }

    protected static synchronized WebDriver getDriver() {
        return driver.get();
    }
}
