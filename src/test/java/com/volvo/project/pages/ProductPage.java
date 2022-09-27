package com.volvo.project.pages;

import com.volvo.project.components.PageObject;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductPage extends PageObject {
    //private static final String SEARCH_PMR_FORM_NAME = "Welcome to the Secure Area. When you are done click logout below.";


    public ProductPage(WebDriver driver) {
        super(driver);
    }

    //buttons
    @FindBy(xpath = "//a[contains(text(), ' Save')]")
    WebElement saveButton;

    @FindBy(xpath = "//button[contains(text(), 'Part Status')]")
    WebElement partStatusDropdown;

    @FindBy(xpath = "//button[contains(text(), 'Digital')]")
    WebElement digitalDropdown;

    @FindBy(xpath = "//button[contains(text(), 'Cross-Reference Data')]")
    WebElement crossReferenceDataDropdown;

    @FindBy(xpath = "//button[contains(text(), 'Supersession')]")
    WebElement supersessionDropdown;

    //attributes
    @FindBy(xpath = "//span[contains(text(), 'Supplier Part Description')]/../../../p/span")
    WebElement supplierPartDescription;

    @FindBy(xpath = "//span[contains(text(), 'Internal Part Name')]/../../../p/span")
    WebElement internalPartName;

    @FindBy(xpath = "//span[contains(text(), 'Branded Part')]/../../../p/span")
    WebElement brandedPart;

   @FindBy(xpath = "//span[contains(text(), 'CS Enrichment Complete')]/../../../p/span")
    WebElement CSEnrichmentComplete;

    //part Status dropdown
    @FindBy(xpath = "//span[contains(text(), 'Part Status')]/../../../p/span")
    WebElement partStatus;

    @FindBy(xpath = "//span[contains(text(), 'eCommerce Status')]/../../../p/span")
    WebElement ecommerceStatus;

    @FindBy(xpath = "//span[contains(text(), 'eCommerce Internal Status')]/../../../p/span")
    WebElement ecommerceInternalStatus;

    //Digital
    @FindBy(xpath = "//span[contains(text(), 'Metadata Title')]/../../../p/span")
    WebElement metadataTitle;

    @FindBy(xpath = "//span[contains(text(), 'Metadata Description')]/../../../p/span")
    WebElement metadataDescription;

    @FindBy(xpath = "//span[contains(text(), 'Metadata Keywords')]/../../../p/span")
    WebElement metadataKeywords;

    //Supersession
    @FindBy(xpath = "//span[contains(text(), 'Supersession From')]/../../../p/span")
    WebElement supersessionFrom;

    @FindBy(xpath = "//span[contains(text(), 'Supersession To')]/../../../p/span")
    WebElement supersessionTo;

    //Assign Interchangeable Link
    @FindBy(xpath = "//button[contains(text(), 'Assign Interchangeable Link')]/../../..//li[@id='AddLinkedLink']")
    WebElement addInterchangeableLink;

    //categorization
    @FindBy(xpath = "//span[contains(text(), 'Taxonomy Node')]/../../../p/span")
    WebElement taxonomyNode;

    @FindBy(xpath = "//span[contains(text(), 'MacKay Code')]/../../../p/span")
    WebElement mackayCode;
}
