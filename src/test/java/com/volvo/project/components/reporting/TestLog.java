package com.volvo.project.components.reporting;


import java.io.File;

import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLog {

    private static final Logger log = LoggerFactory.getLogger(TestLog.class);

    @Step("Log start test class execution")
    public void logStartTestClassExecution(String testClassName){
        log.info("-------------------------------------------------------");
        log.info("Execution of tests from "+testClassName+" class started");
        log.info("-------------------------------------------------------");
    }

    @Step("Log start test execution")
    public void logStartTestExecution(String testCaseName){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>");
        log.info("Test "+testCaseName+" started");
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @Step("Log end test execution")
    public void logEndTestExecution(String testCaseName, int status, String env){
        switch (status) {
            case 1:
                log.info("Test "+testCaseName+" finished --> PASSED");
                break;
            case 2:
                String pathDir = new java.io.File("").getAbsolutePath();
                String pathFile = pathDir + File.separator + "target" + File.separator + "test-screen" + File.separator;
                log.info("Screenshot has been taken and saved in "+ pathFile);
                log.error("Test "+testCaseName+" finished --> FAILED");
                break;
            case 3:
                log.info("Test "+testCaseName+" finished --> SKIPPED");
                break;
            default:
                log.info("Test "+testCaseName+" finished --> RESULT UNKNOWN");
                break;
        }
        log.info("Test environment: "+ env);
        //log.info("Browser: "+driver.getCapabilities().getBrowserName()+" ver."+driver.getCapabilities().getVersion())
        //log.info("OS:"+driver.capabilities["platform"])
        //log.info("Url in the End of Test: "+driver.getCurrentUrl())
        log.info("<<<<<<<<<<<<<<<<<<<<<");
    }

    @Step("Log end test class execution")
    public void logEndTestClassExecution(String testClassName){
        log.info("-------------------------------------------------------");
        log.info("Execution of tests from "+testClassName+" class finished");
        log.info("-------------------------------------------------------");
    }

    public void info(String message) {
        log.info(message);
    }

    public void warn(String message) {
        log.warn(message);
    }

    public void error(String message) {
        log.error(message);
    }

    public void debug(String message) {
        log.debug(message);
    }
}
