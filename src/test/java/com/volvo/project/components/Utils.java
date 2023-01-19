package com.volvo.project.components;

import com.volvo.project.components.datatdriventesting.ExcelLibrary;
import com.volvo.project.parameters.DockerHubParams;
import com.volvo.project.parameters.ExecutionParameters;
import com.volvo.project.parameters.ExecutionParametersLoader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 * Utility class for test automation scripts.
 *
 * @author a049473
 */
public class Utils {

    /**
     * @return Timestamp in "yyyy-MM-dd'T'HH:mm:ss.SSS" format
     */
    public String currentTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(new Date());
    }


    /**
     * @return today date in yyyy-MM-dd format as a String
     */
    public static String getTodayDate() {
        String dateFormat = "M/dd/yyyy";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(cal.getTime());
    }

    /**
     * @return current year as a String
     */
    public static String getCurrentYear() {
        return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    }

    public static String getRandomString(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    /**
     * @param maxValue
     * @return random integer value from following range <1, @param maxValue>
     */
    public static int getRandomInt(int maxValue) {
        Random generator = new Random();
        return generator.nextInt(maxValue) + 1;
    }

    public static void beforeConfiguration() {
        clearTestTargetFilesFromPreviousExecution();
    }

    private static void clearTestTargetFilesFromPreviousExecution() {
        String pathDir = new File("").getAbsolutePath();

        File file = new File(pathDir + File.separator + "target" + File.separator + "test-screen");
        if (!file.exists()) {
            if (file.mkdir()) {
                try {
                    FileUtils.cleanDirectory(new File(pathDir + File.separator + "target" + File.separator + "test-screen" + File.separator));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("test-screen directory is created");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
        new File(pathDir + File.separator + "target" + File.separator + "TestLogReport.html").delete();
    }

    public void writeToMultiStepExcel(String partName) {
        ExcelLibrary objExcelFile = new ExcelLibrary();
        String multiMethodfilePath = "C:/testOpsJavaFramework/src/test/resources/testdata/MultipleMethodTestData.xlsx";
        objExcelFile.writeToExcel(multiMethodfilePath, 0,0,partName);
    }

    public String readFromMultiStepExcel() {
        ExcelLibrary objExcelFile = new ExcelLibrary();
        String multiMethodfilePath = "src/test/resources/testdata/MultipleMethodTestData.xlsx";
        return objExcelFile.readFromExcel(multiMethodfilePath, 0,0);
    }
}
