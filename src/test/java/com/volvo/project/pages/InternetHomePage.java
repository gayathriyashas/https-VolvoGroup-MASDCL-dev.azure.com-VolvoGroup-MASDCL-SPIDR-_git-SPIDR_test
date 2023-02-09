package com.volvo.project.pages;

import com.ibm.msg.client.commonservices.Log.Log;
import com.volvo.project.components.PageObject;
import com.volvo.project.components.datatdriventesting.ExcelLibrary;
import io.qameta.allure.Step;
import org.apache.xml.utils.StringComparable;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Action;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

public class InternetHomePage extends PageObject {
    //private static final String SEARCH_PMR_FORM_NAME = "Welcome to the Secure Area. When you are done click logout below.";
    @FindBy(xpath = "//div[@class='user-name ng-star-inserted']")
    public WebElement LoginDetailsLabel;
    @FindBy(id = "flash")
    public WebElement Header;
    @FindBy(css = "#content > div > a > i")
    private WebElement _logoutButton;
    @FindBy(css = "div > a > i")
    private WebElement releaseVersion;

    @FindBy(xpath = "//li//mat-icon[@svgicon='profile']")
    private WebElement profileMenuButton;
    @FindBy(id = "hamburgerButton")
    private WebElement hamburgeButton;
    @FindBy(xpath = "//input[@id='inputLogin3' or @placeholder='Search']")
    public WebElement searchField;
    @FindBy(xpath = "//span[@title='Staging']")
    private WebElement stagingMenuButton;
    @FindBy(xpath = "//div[@class='ng-star-inserted']//span[contains(text(),'Volvo Products Staging')]")
    private WebElement volvoProductsStagingSubMenuButton;

    @FindBy(xpath = "//div[@class='ng-star-inserted']//span[contains(text(),'Volvo Suppliers Staging ')]")
    private WebElement volvoSupplierStagingSubMenuButton;

    @FindBy(xpath = "//div[@class='ng-star-inserted']//span[contains(text(),'Volvo Cost Staging')]")
    private WebElement volvoCostStagingSubMenuButton;

    @FindBy(id = "signoutIcon")
    WebElement signOutButton;
    @FindBy(xpath = "//div[@class='cdk-overlay-container']//span[contains(text(),' Logout ')]")
    private WebElement logoutLink;

    @FindBy(xpath = "//div[@class='MenuTop col-md-6 col-sm-5 col-xs-4']//span[contains(text(), 'Volvo Products Staging')]")
    public WebElement productsStagingTab;

    @FindBy(xpath = "//span[contains(text(), 'Volvo Products Staging')][2]")
    public WebElement volvoProductsStagingTabNew;

    @FindBy(xpath = "//button[contains(text(), 'Yes')]")
    WebElement yesPopupButton;

    public InternetHomePage(WebDriver driver) {
        super(driver);
    }

    @Step("Get Internet Home Page loaded status")
    public boolean isLoaded(String expected) {
        String actual;
        try
        {
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(60));
            wait.until(ExpectedConditions.elementToBeClickable(LoginDetailsLabel));
            actual = LoginDetailsLabel.getText();
            logger.info("Actual is: "+actual);
            // Actions action = new Actions(Driver.Value);
            // action.MoveToElement(LoginDetailsLabel).Click().Perform();

            if (LoginDetailsLabel !=null)
            {
                if (actual.contains(expected) || actual.equalsIgnoreCase(expected))
                {
                    System.out.println("Match found");
                    logger.info("Actual is : " + actual + "and Expected is : " + expected);
                    return true;
                }
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
    @Step("Select ProfileMenu App")
    public void profileMenuApp() throws InterruptedException {
        Thread.sleep(10000);
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(60));
        wait.until(ExpectedConditions.elementToBeClickable(profileMenuButton));
        profileMenuButton.click();
        //return new InternetHomePage(driver);
    }
    @Step("Logout from application")
    public void logout() {
        try {
            signOutButton.click();
            moveToElement(logoutLink);
            clickElement(logoutLink);
            yesPopupButton.click();
        } catch (Exception e) {
            logger.info("Cannot find logout button");
        }
    }
    @Step("Select HamburgeIcon")
    public void hamburgeIcon() throws InterruptedException {
        Thread.sleep(10000);
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(60));
        wait.until(ExpectedConditions.elementToBeClickable(hamburgeButton));
        hamburgeButton.click();
        //return new InternetHomePage(driver);
    }
    @Step("Select StagingMenu")
    public void stagingMenu() throws InterruptedException {
        Thread.sleep(10000);
        List<WebElement> check = driver.findElements(By.xpath("//div[@class='ng-star-inserted']//span[contains(text(),'Volvo Suppliers Staging ')]"));
        if(check.size() == 0) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
            wait.until(ExpectedConditions.elementToBeClickable(stagingMenuButton));
            // stagingMenuButton.click();
            Actions action = new Actions(driver);
            action.moveToElement(stagingMenuButton).doubleClick().perform();
            //return new InternetHomePage(driver);
        }
    }
    @Step("Select Volvo Products Staging Sub Menu")
    public void volvo_Products_Staging_SubMenu() throws InterruptedException {
        Thread.sleep(10000);
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(60));
        wait.until(ExpectedConditions.elementToBeClickable(volvoProductsStagingSubMenuButton));
        volvoProductsStagingSubMenuButton.click();
        //return new InternetHomePage(driver);
    }

    @Step("Select Volvo Suppliers Staging Sub Menu")
    public void volvo_Suppliers_Staging_SubMenu() throws InterruptedException {
        Thread.sleep(10000);
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(60));
        wait.until(ExpectedConditions.elementToBeClickable(volvoSupplierStagingSubMenuButton));
        volvoSupplierStagingSubMenuButton.click();
        //return new InternetHomePage(driver);
    }

    @Step("Select Volvo Cost Staging Sub Menu")
    public void volvo_Cost_Staging_SubMenu() throws InterruptedException {
        Thread.sleep(10000);
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(60));

            wait.until(ExpectedConditions.elementToBeClickable(volvoCostStagingSubMenuButton));
            volvoCostStagingSubMenuButton.click();
        //return new InternetHomePage(driver);
    }

    @Step("Search Record")
    public void searchRecord(String value) throws InterruptedException {
        Thread.sleep(10000);
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(60));
        wait.until(ExpectedConditions.elementToBeClickable(searchField));
        searchField.click();
        searchField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        searchField.sendKeys(Keys.DELETE);
        searchField.sendKeys(value);
        searchField.sendKeys(Keys.RETURN);
        System.out.println("Entered Record: "+value);
        //return new InternetHomePage(driver);
    }
    @Step("Verify Record")
    public void verifyRecord(String value) throws InterruptedException {
        Thread.sleep(10000);
        WebElement searchRecord = driver.findElement(By.xpath("//span[normalize-space()='"+value+"']"));
        //Thread.sleep(10000);
        System.out.println("searchRecord:"+searchRecord);
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(60));
        wait.until(ExpectedConditions.elementToBeClickable(searchRecord));
        searchRecord.click();
        System.out.println("Record found and clicked: "+searchRecord);
        //return new InternetHomePage(driver);
    }

    public void openProductStaging() throws InterruptedException {
        hamburgeIcon();
        stagingMenu();
        volvo_Products_Staging_SubMenu();
    }

    public void openSupplierStaging() throws InterruptedException {
        hamburgeIcon();
        stagingMenu();
        volvo_Suppliers_Staging_SubMenu();
    }

    public void openCostStaging() throws InterruptedException {
        hamburgeIcon();
        stagingMenu();
        volvo_Cost_Staging_SubMenu();
    }

}
