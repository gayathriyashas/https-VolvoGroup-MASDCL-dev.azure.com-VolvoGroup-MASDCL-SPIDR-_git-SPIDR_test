package com.volvo.project.components.setup;

import com.volvo.project.parameters.DockerHubParams;
import com.volvo.project.parameters.ExecutionParameters;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class WebDriverFactory {
    private static final String CHROME_BROWSER = "chrome";
    private static final String FIREFOX_BROWSER = "firefox";
    private static final String EDGE_BROWSER = "edge";
    protected static String userID = System.getenv("RF_PROXY_USER");
    protected static String vcnPass = System.getenv("RF_PROXY_PASS");
    protected static final String PROXY = "http://" + userID + ":" + vcnPass + "@httppxgot.srv.volvo.com:8080";

    private final ExecutionParameters executionParameters;

    public WebDriverFactory(ExecutionParameters executionParameters) {
        this.executionParameters = executionParameters;
    }

    public WebDriver getWebDriver() {
        WebDriver driver = getDriver();
        driver.manage().window().setSize(new Dimension(executionParameters.getResolutionWidth(), executionParameters.getResolutionHeight()));
        return driver;
    }

    private WebDriver getDriver() {
        switch (executionParameters.getBrowser()) {
            case CHROME_BROWSER:
                return configureChromeWebDriver();
            case FIREFOX_BROWSER:
                return configureFirefoxWebDriver();
            case EDGE_BROWSER:
                return configureEdgeWebDriver();
            default:
                throw new RuntimeException(String.format("%s browser is not supported. Please use chrome/firefox/edge.", executionParameters.getBrowser()));
        }
    }

    private WebDriver configureChromeWebDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.setHeadless(executionParameters.isHeadless());
        options.addArguments("--verbose");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--log-path=/var/log/chromedriver");

        if (!executionParameters.isDocker()) {
            if (executionParameters.isProxy()) {
                WebDriverManager.chromedriver().proxy(PROXY).setup();
            } else {
               // WebDriverManager.chromedriver().setup();
               // WebDriverManager.getInstance().chromedriver().browserVersion("103.0.5060.53").setup(); //please point here version which is used on your executable machine
               // WebDriverManager.chromedriver().browserVersion("103.0.5060.53").setup();
                launchChromeDriverExe();
            }
            /*if (executionParameters.isProxy()) {
                WebDriverManager.chromedriver().proxy(PROXY).setup();
            }
            else {
                launchChromeDriverExe();
            } */
            return new ChromeDriver(options);
        } else {
            return getRemoteWebDriver(options);
        }
    }
    private void launchChromeDriverExe()  {
        try {
        Thread.sleep(10000);
        Duration d1 = Duration.ofSeconds(180);
        Duration d2 = Duration.ofMinutes(3);
        boolean flag = d1.equals(d2);
        if(flag) {
            System.out.println("Launch ChromeDriver exe");
            String exePath= System.getProperty("user.dir") + "/target/chromedriver_win32/chromedriver.exe";
            System.setProperty("webdriver.chrome.driver", exePath);
        }
        else {
            System.out.println("Not Launch ChromeDriver exe");
        }
        }
        catch(Exception e)
            {
                System.out.println("Not Launch ChromeDriver exe");
            }
    }
    private WebDriver configureFirefoxWebDriver() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setHeadless(executionParameters.isHeadless());

        if (!executionParameters.isDocker()) {
            if (executionParameters.isProxy()) {
                WebDriverManager.firefoxdriver().proxy(PROXY).setup();
            } else {
                WebDriverManager.firefoxdriver().setup();
            }
            return new FirefoxDriver(firefoxOptions);
        } else {
            return getRemoteWebDriver(firefoxOptions);
        }
    }

    private WebDriver configureEdgeWebDriver() {
        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.setHeadless(executionParameters.isHeadless());

        if (!executionParameters.isDocker()) {
            if (executionParameters.isProxy()) {
                WebDriverManager.edgedriver().proxy(PROXY).setup();
            } else {
                WebDriverManager.edgedriver().setup();
            }
            return new EdgeDriver(edgeOptions);
        } else {
            return getRemoteWebDriver(edgeOptions);
        }
    }

    private RemoteWebDriver getRemoteWebDriver(Capabilities capabilities) {
        try {
            return new RemoteWebDriver(new URL(DockerHubParams.DOCKER_GRID_NODE), capabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
