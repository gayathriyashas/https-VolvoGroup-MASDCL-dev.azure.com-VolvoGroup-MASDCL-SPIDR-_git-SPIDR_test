package com.volvo.project.pages;

import com.volvo.project.components.PageObject;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

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

    @FindBy(xpath = "//div[@class ='modal-content container-fluid']//button[contains(text(), 'Yes')]")
    public WebElement deleteYes;

    @FindBy(xpath = "//div[@class='nav-link active']//span[contains(text(),'Volvo Products Staging')]")
    public WebElement volvoProductsStagingTabNew;

    @FindBy(xpath = "//span[contains(text(), 'Volvo Products Staging')]/../span[3]")
    public WebElement closeVolvoProductsStaging;

    @FindBy(xpath = "//div[@class='ng-star-inserted']/span[@class='ag-paging-row-summary-panel']/span[3]")
    public WebElement numberOfRecords;

    @FindBy(xpath = "//div[@row-id='0']/div[3]//span")
    WebElement recordSupplierName;

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
    public void searchForRecord() throws InterruptedException {
        String record = readFromMultiStepExcel();
        String number;
            InternetHomePage IHPage = new InternetHomePage(driver);
            IHPage.searchRecord(record);

            wait:
            for (int i = 1; i <= 20; i++) {
                Thread.sleep(4000);
                System.out.println("Record Number: " + getRecordNumber());
                System.out.println("Supplier name: "+recordSupplierName.getText().trim());
                if (getRecordNumber() == 0 || recordSupplierName.getText().trim().length() == 0) {
                    Thread.sleep(20000);
                    Thread.sleep(20000);
                    Thread.sleep(20000);
                    Thread.sleep(20000);
                    driver.findElement(By.xpath(" //strong[contains(text(), '" + record + "')]/../button")).click();
                    Thread.sleep(1000);
                    IHPage.searchRecord(record);
                }
                else {
                    break wait;
                }
            }

    }

    public void searchForRecord(String record) throws InterruptedException {
        InternetHomePage IHPage = new InternetHomePage(driver);
        IHPage.searchRecord(record);
    }
    public int getRecordNumber() {
        List<WebElement> recordsListed= driver.findElements(By.xpath("//span[contains(text(),'No records to view')]"));
        if(recordsListed.size() != 0) {
            return 0;
        }
        else {
            return Integer.parseInt(numberOfRecords.getText());
        }
    }

    public void openRecord() {
        clickUsingJS(searchedItemCheckbox);
        System.out.println("Check box clicked");
        editButton.click();
        System.out.println("Part opened");
    }

    public void deleteRecord() throws InterruptedException {
        clickUsingJS(searchedItemCheckbox);
        deleteButton.click();
        Thread.sleep(1000);
        deleteYes.click();
        Thread.sleep(1000);
    }
}
