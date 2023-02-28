package com.volvo.project.pages;

import com.volvo.project.components.PageObject;
import com.volvo.project.components.datatdriventesting.ExcelLibrary;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.time.Duration;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

public class InternetLoginPage extends PageObject {
    //private static final String URL_INTERNET_LOGIN_PAGE = "https://the-internet.herokuapp.com/login";

    //<!--QA URL-->
   // public static final String URL_INTERNET_LOGIN_PAGE = "https://qa-supplierpartsmaster.volvo.com/enable2020/";

    //<!--DEV URL-->
   public static final String URL_INTERNET_LOGIN_PAGE = "https://dev-supplierpartsmaster.volvo.net/enable2020/login";

    @FindBy(xpath = "//div[@class='login-form']//input[@placeholder='Username']")
    private WebElement userNameField;
    @FindBy(xpath = "//div[@class='login-form']//input[@placeholder='Password']")
    private WebElement passwordField;

    //@FindBy(css = "#login > button > i")
    @FindBy(xpath = "//div[@class='login-form']//button[contains(text(),'sign in')]")
    private WebElement loginButton;
    @FindBy(id = "msgWarn")
    private WebElement loginMessage;

    public InternetLoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open Internet Login Page")
    public InternetLoginPage open() {
        driver.get(URL_INTERNET_LOGIN_PAGE);
        driver.manage().window().maximize();
        return this;
    }

    @Step("Log into app by user:{0} and pass:{1}")
    public InternetHomePage login(String user, String password) {
        enterUserName(user);
        enterPassword(password);
        return clickSubmitButton();
    }

    @Step("Enter username")
    private void enterUserName(String user) {
        typeInto(userNameField, user);
    }

    @Step("Enter password")
    private void enterPassword(String password) {
        typeInto(passwordField, password);
    }

    @Step("Click submit button")
    public InternetHomePage clickSubmitButton() {
        clickButton(loginButton);
        return new InternetHomePage(driver);
    }

    @Step("Login")
    public void login(String role) {
        step("Launch Browser and logged into SPIDR Application");

        ExcelLibrary objExcelFile = new ExcelLibrary();
        String filePath = "src/test/resources/testdata/LoginValuesMap.xlsx";
        int partRow = -1;
        for(int i=1; i<= objExcelFile.getRowCount(filePath); i++) {
            String rowPartName = objExcelFile.readFromExcel(filePath, i, 0);
            if(rowPartName.equals(role)) {
                partRow = i;
            }
        }
        String name = objExcelFile.readFromExcel(filePath, partRow, 1);
        String password = objExcelFile.readFromExcel(filePath, partRow, 2);
        String loginLabel = objExcelFile.readFromExcel(filePath, partRow, 3);
        InternetHomePage homePage = new InternetLoginPage(driver)
                .open()
                .login(name, password);

        step(
                "Check if user is logged in",
                () -> assertThat(homePage.isLoaded(loginLabel)).isTrue()
        );
    }

    @Step("Open Internet Login Page")
    public InternetLoginPage openQA() {
        driver.get("https://qa-supplierpartsmaster.volvo.com/enable2020/");
        driver.manage().window().maximize();
        return this;
    }

    @Step("Open Internet Login Page")
    public InternetLoginPage openPROD() {
        driver.get("https://supplierpartsmaster.volvo.com/enable2020/");
        driver.manage().window().maximize();
        return this;
    }

}
