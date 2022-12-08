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

public class ProductPage extends PageObject {
    //private static final String SEARCH_PMR_FORM_NAME = "Welcome to the Secure Area. When you are done click logout below.";


    public ProductPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath =  "//span[@class='title-switch ng-star-inserted']")
    WebElement viewDropDown;

    @FindBy(xpath = "//div[@class='mat-menu-content']/button[1]/span")
    WebElement noPreferenceView;

    //sidebar
    @FindBy(xpath = "//span[contains(text(), 'Category Specific Attributes')]")
    WebElement categorySpecificArrtibutesTab;

    @FindBy(xpath = "//span[contains(text(), 'Admin')]")
    WebElement adminTab;

    //buttons
    @FindBy(xpath = "//a[contains(text(), ' Save')]")
    WebElement saveButton;

    @FindBy(xpath = "//button[contains(text(), 'Basic Info')]")
    WebElement basicInfoDropdown;

    @FindBy(xpath = "//button[contains(text(), 'Part Status')]")
    public WebElement partStatusDropdown;

    @FindBy(xpath = "//button[contains(text(), 'Digital')]")
    public WebElement digitalDropdown;

    @FindBy(xpath = "//button[contains(text(), 'Cross-Reference Data')]")
    WebElement crossReferenceDataDropdown;

    @FindBy(xpath = "//button[contains(text(), 'Supersession')]")
    WebElement supersessionDropdown;

    //attributes
    @FindBy(xpath = "//span[contains(text(), 'Supplier Part Description')]/../../../p/span")
    public WebElement supplierPartDescription;

    @FindBy(xpath = "//span[contains(text(), 'Internal Part Name')]/../../../p/span")
    WebElement internalPartName;

    @FindBy(xpath = "//span[contains(text(), 'Brand')]/../../../p/span")
    public WebElement brand;

    @FindBy(xpath = "//span[contains(text(), 'Branded Part')]/../../../p/span")
    WebElement brandedPart;

    @FindBy(xpath = "//span[contains(text(), 'Manufacturer Name')]/../../../p/span")
    public WebElement manufacturerName;

   @FindBy(xpath = "//span[contains(text(), 'CS Enrichment Complete')]/../../../p/span")
    WebElement CSEnrichmentComplete;

   @FindBy(xpath = "//span[contains(text(), 'Part long Description (Brand)')]/../../../p/span")
   public WebElement partLongDescBrand;

    @FindBy(xpath = "//span[contains(text(), 'Supplier Name')]/../../../p/span")
    public WebElement supplierName;

    //part Status dropdown
    @FindBy(xpath = "//span[contains(text(), 'Part Status')]/../../../p/span")
    public WebElement partStatus;

    @FindBy(xpath = "//span[contains(text(), 'eCommerce Status')]/../../../p/span")
    WebElement ecommerceStatus;

    @FindBy(xpath = "//span[contains(text(), 'eCommerce Internal Status')]/../../../p/span")
    WebElement ecommerceInternalStatus;

    //Digital
    @FindBy(xpath = "//span[contains(text(), 'Metadata Title')]/../../../p/span")
    public WebElement metadataTitle;

    @FindBy(xpath = "//span[contains(text(), 'Metadata Description')]/../../../p/span")
    public WebElement metadataDescription;

    @FindBy(xpath = "//span[contains(text(), 'Metadata Keywords')]/../../../p/span")
    public WebElement metadataKeywords;

    //Supersession
    @FindBy(xpath = "//span[contains(text(), 'Supersession From')]/../../../p/span")
    WebElement supersessionFrom;

    @FindBy(xpath = "//span[contains(text(), 'Supersession To')]/../../../p/span")
    WebElement supersessionTo;

    //Assign Interchangeable Link
    @FindBy(xpath = "//button[contains(text(), 'Assign Interchangeable Link')]/../../..//li[@id='AddLinkedLink']")
    WebElement addInterchangeableLink;

    //categorization
    @FindBy(xpath = "//span[contains(text(), 'MacKay Code')]/../../../p/span")
    WebElement mackayCode;

    @FindBy(xpath = "//span[contains(text(), 'Taxonomy Node')]/../../../p/span")
    public WebElement taxonomyNode;

    @FindBy(xpath = "//span[contains(text(), 'VMRS Code')]/../../../p/span")
    public WebElement VMRSCode;

    @FindBy(xpath = "//span[contains(text(), 'VMRS CK31')]/../../../p/span")
    public WebElement VMRSCK31;

    @FindBy(xpath = "//span[contains(text(), 'VMRS CK32')]/../../../p/span")
    public WebElement VMRSCK32;

    @FindBy(xpath = "//span[contains(text(), 'VMRS CK33')]/../../../p/span")
    public WebElement VMRSCK33;

    //Review Attributes List
    @FindBy(xpath = "//div[contains(text(), 'Branded Part')]")
    WebElement reviewAttributeList_BrandedPart;

    @FindBy(xpath = "//div[contains(text(), 'Supersession To')]")
    WebElement reviewAttributeList_SupersessionTo;

    @FindBy(xpath = "//div[contains(text(), 'Supersession From')]")
    WebElement reviewAttributeList_SupersessionFrom;

    //Admin
    @FindBy(xpath = "//span[contains(text(), 'Manager Approval')]/../../../p/span")
    WebElement managerApproval;

    @Step("Change view to No Preference")
    public void changeViewToNoPreference() throws InterruptedException {
        viewDropDown.click();
        Thread.sleep(1000);
        noPreferenceView.click();
        Thread.sleep(1000);
    }

    @Step("Open Basic Info")
    public void openBasicInfo() throws InterruptedException {
        if(driver.findElements(By.xpath("//span[contains(text(), 'Supplier Name')]/../../../p/span")).size() == 0) {
            basicInfoDropdown.click();
            Thread.sleep(1000);
        }
    }
}
