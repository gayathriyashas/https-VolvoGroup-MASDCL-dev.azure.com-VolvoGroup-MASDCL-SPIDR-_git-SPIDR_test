package com.volvo.project.pages;

import com.volvo.project.components.PageObject;
import com.volvo.project.components.Utils;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SupplierPage extends PageObject {
    //private static final String SEARCH_PMR_FORM_NAME = "Welcome to the Secure Area. When you are done click logout below.";


    public SupplierPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//span[contains(text(), 'Supplier Name')]/../../../p/span")
    public WebElement supplierName;

    @FindBy(xpath = "//span[contains(text(), 'Supplier Name')]/../../../..//textarea")
    WebElement supplierNameTextBox;

    @FindBy(xpath = "//a[normalize-space()='Save']")
    public WebElement saveButton;

    @FindBy(xpath = "//tabset//li[3]//span[3]")
    public WebElement closePage;

    public void supplierNameChange(String newPartDescription) throws InterruptedException {
        scrollElementIntoView(supplierName);
        System.out.println("Scrolled to element");
        Thread.sleep(10000);
        supplierName.click();
        Thread.sleep(1000);
        supplierNameTextBox.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        supplierNameTextBox.sendKeys(Keys.DELETE);
        supplierNameTextBox.sendKeys(newPartDescription);
    }
}
