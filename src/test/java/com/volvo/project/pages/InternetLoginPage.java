package com.volvo.project.pages;

import com.volvo.project.components.PageObject;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.time.Duration;

public class InternetLoginPage extends PageObject {
    //private static final String URL_INTERNET_LOGIN_PAGE = "https://the-internet.herokuapp.com/login";

    //<!--QA URL-->
    public static final String URL_INTERNET_LOGIN_PAGE = "https://wwglbn12562.vcn.ds.volvo.net/enable2020/login";

    //<!--DEV URL-->
    //public static final String URL_INTERNET_LOGIN_PAGE = "http://wwglbn12567:81/enable2020/login";

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
}
