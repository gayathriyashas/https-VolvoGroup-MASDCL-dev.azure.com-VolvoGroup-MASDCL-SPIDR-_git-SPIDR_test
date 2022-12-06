package com.volvo.project.pages;

import com.volvo.project.components.PageObject;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchPage extends PageObject {
    //private static final String SEARCH_PMR_FORM_NAME = "Welcome to the Secure Area. When you are done click logout below.";
    @FindBy(xpath = "//div[@class='user-name ng-star-inserted']")
    public WebElement LoginDetailsLabel;

    @FindBy(xpath = "//span[@class='ag-selection-checkbox']/..")
    public WebElement searchedItemCheckbox;

    @FindBy(xpath = "//a[contains(text(), ' Edit')]")
    public WebElement editButton;

    @FindBy(xpath = "//a[contains(text(), ' Delete')]")
    public WebElement deleteButton;

    @FindBy(xpath = "//span[@class='ag-selection-checkbox']/..")
    public WebElement numberOfRecords;

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    @Step("Verify that records are loaded")
    public boolean isLoaded(String expected) {
        String number;
        try
        {
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(60));
            wait.until(ExpectedConditions.visibilityOf(numberOfRecords));
            number = numberOfRecords.getText();
            logger.info("Actual is: "+number);
            // Actions action = new Actions(Driver.Value);
            // action.MoveToElement(LoginDetailsLabel).Click().Perform();

            if (numberOfRecords !=null)
            {
                    System.out.println("Records Loaded");
                    logger.info("Record Count is " + number);
                    return true;
                //return new HomeInternetPage(driver);
            }
        }
        catch (Exception e)
        {
            System.out.println("Some Exception happened");
            System.out.println("Not Match found");
            //Log.Info("Actual is : " + actual + "and Expected is : " + expected);
            return false;
        }
        return true;
    }

    @Step("Search for Record")
    public void searchForRecord(String record) throws InterruptedException {
        String number;
        try {
            InternetHomePage IHPage = new InternetHomePage(driver);
            IHPage.searchRecord(record);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
            wait.until(ExpectedConditions.visibilityOf(numberOfRecords));
            number = numberOfRecords.getText();
            if(number == "1")  {
                System.out.println("Record is found");
            }
            else {
                System.out.println("Issue found with record search");
                
            }
        }
        catch (Exception e) {
            System.out.println("Some Exception happened");
            System.out.println("Record not found");
        }

    }

}
