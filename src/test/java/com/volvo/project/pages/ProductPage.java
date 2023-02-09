package com.volvo.project.pages;

import com.volvo.project.components.PageObject;
import com.volvo.project.components.Utils;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

import java.util.List;

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

    @FindBy(xpath = "//span[contains(text(), 'Part Data')]")
    WebElement partDataTab;

    @FindBy(xpath = "//span[contains(text(), 'Admin')]")
    WebElement adminTab;

    //buttons
   // @FindBy(xpath = "//a[contains(text(), ' Save')]")
    @FindBy(xpath = "//a[normalize-space()='Save']")
    public WebElement saveButton;

    @FindBy(xpath = "//button[contains(text(), 'Basic Info')]")
    public WebElement basicInfoDropdown;

    @FindBy(xpath = "//button[contains(text(), 'Part Status')]")
    public WebElement partStatusDropdown;

    @FindBy(xpath = "//button[contains(text(), 'Categorization')]")
    public WebElement categorizationDropdown;

    @FindBy(xpath = "//button[contains(text(), 'Digital')]")
    public WebElement digitalDropdown;

    @FindBy(xpath = "//button[contains(text(), 'Cross-Reference Data')]")
    WebElement crossReferenceDataDropdown;

    @FindBy(xpath = "//button[contains(text(), 'Supersession')]")
    WebElement supersessionDropdown;

    //attributes
    @FindBy(xpath = "//span[contains(text(), 'Supplier Part Description')]/../../../p/span")
    public WebElement supplierPartDescription;

    @FindBy(xpath = "//span[contains(text(), 'Supplier Part Description')]/../../../..//textarea")
    WebElement supplierPartDescriptionTextBox;
    @FindBy(xpath = "//span[contains(text(), 'Internal Part Name')]/../../../p/span")
    public WebElement internalPartNameAtt;

    @FindBy(xpath = "//span[contains(text(), 'Internal Part Name')]/../../../..//descendant::textarea")
    public WebElement internalPartNameTextArea;

    @FindBy(xpath = "//span[contains(text(), 'Brand')]/../../../p/span")
    public WebElement brand;

    @FindBy(xpath = "//span[contains(text(), 'Branded Part')]/../../../p/span")
    WebElement brandedPart;

    @FindBy(xpath = "//span[contains(text(), 'Manufacturer Name')]/../../../p/span")
    public WebElement manufacturerName;

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

    @FindBy(xpath = "//span[contains(text(), 'Taxonomy Node')]/../../../..//descendant::input")
    public WebElement taxonomyNodeTextBox;
    @FindBy(xpath = "//span[contains(text(), 'VMRS Code')]/../../../p/span")
    public WebElement VMRSCode;

    @FindBy(xpath = "//span[contains(text(), 'VMRS CK31')]/../../../p/span")
    public WebElement VMRSCK31;

    @FindBy(xpath = "//span[contains(text(), 'VMRS CK32')]/../../../p/span")
    public WebElement VMRSCK32;

    @FindBy(xpath = "//span[contains(text(), 'VMRS CK33')]/../../../p/span")
    public WebElement VMRSCK33;

    //Part Data
    @FindBy(xpath = "//span[contains(text(), 'CS Enrichment Complete')]/../../../p/span")
    WebElement CSEnrichmentComplete;

    @FindBy(xpath = "//input[@id='mat-input-2']/..")
    WebElement CSEAfterClick;

    @FindBy(id = "cdk-overlay-29")
    WebElement CSDropDown;

    @FindBy(xpath = "//div[@role='listbox']//span[contains(text(), 'Yes')]")
    WebElement CSEYes;

    @FindBy(xpath = "//div[contains(text(), 'Branded Part')]")
    WebElement reviewAttributeList_BrandedPart;

    @FindBy(xpath = "//div[contains(text(), 'Supersession To')]")
    WebElement reviewAttributeList_SupersessionTo;

    @FindBy(xpath = "//div[contains(text(), 'Supersession From')]")
    WebElement reviewAttributeList_SupersessionFrom;

    //Admin
    @FindBy(xpath = "//span[contains(text(), 'Manager Approval')]/../../../p/span")
    WebElement managerApproval;

    @FindBy(xpath = "//tabset//li[3]//span[3]")
    public WebElement closePage;

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

    @Step("Open Categorization")
    public void openCategorization() throws InterruptedException {
        if(driver.findElements(By.xpath("//span[contains(text(), 'Taxonomy Node')]/../../../p/span")).size() == 0) {
            categorizationDropdown.click();
            Thread.sleep(1000);
        }
    }
    @Step("Open Part Data Tab")
    public void openPartDataTab() throws InterruptedException {
        partDataTab.click();
        Thread.sleep(1000);
    }

    @Step("Set CS Enrichment to Yes")
    public void setCSEnrichmentComplete() throws InterruptedException {
        CSEnrichmentComplete.click();

        Thread.sleep(1000);
        clickUsingJS(CSEAfterClick);
        Thread.sleep(1000);
        CSEYes.click();
        Thread.sleep(1000);

    }

    @Step("Set Taxonomy Node")
    public void setTaxonomyNode(String taxonomy) throws InterruptedException {
        openCategorization();
        scrollElementIntoView(taxonomyNode);
        taxonomyNode.click();
        taxonomyNodeTextBox.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        taxonomyNodeTextBox.sendKeys(Keys.DELETE);
        taxonomyNodeTextBox.sendKeys(taxonomy);
    }

    public void verifyStatus(String statusExpected) {
        scrollElementIntoView(partStatusDropdown);
        System.out.println("Scroll to Part Status dropdown");
        partStatusDropdown.click();
        System.out.println("Open Part Status Dropdown");
        String statusActual = partStatus.getText().trim();
        Assert.assertTrue(statusActual.equals("New"));

    }

    public void verifyTaxonomy(String taxonomyExpected) throws InterruptedException {
        openCategorization();
        scrollElementIntoView(taxonomyNode);
        String taxonomyActual = taxonomyNode.getText().trim();
        Assert.assertTrue(taxonomyActual.equals(taxonomyExpected));
    }

    public void verifyAttributeChanged(String attributeChanged) throws InterruptedException {
        openPartDataTab();
        List<WebElement> attributes = driver.findElements(By.xpath("//div[@id='repitableGrid']//div[contains(text(), '"+attributeChanged+"')]"));
        Assert.assertTrue(attributes.size() == 1);
    }

    public String InternalPartNameChange() throws InterruptedException {
        scrollElementIntoView(internalPartNameAtt);
        System.out.println("Scrolled to element");
        Thread.sleep(10000);
        internalPartNameAtt.click();
        String actualText = internalPartNameTextArea.getText();
        String newPartName = Utils.getRandomString(8).toUpperCase();
        System.out.println(actualText);
        internalPartNameTextArea.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        internalPartNameTextArea.sendKeys(Keys.DELETE);
        internalPartNameTextArea.sendKeys(newPartName);
        return newPartName;
    }

    public void partDescriptionChange(String newPartDescription) throws InterruptedException {
        scrollElementIntoView(supplierPartDescription);
        System.out.println("Scrolled to element");
        Thread.sleep(10000);
        supplierPartDescription.click();
        Thread.sleep(1000);
        supplierPartDescriptionTextBox.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        supplierPartDescriptionTextBox.sendKeys(Keys.DELETE);
        supplierPartDescriptionTextBox.sendKeys(newPartDescription);
    }

    public void verifyPart(String attributeChanged, String newValue) {
        waitForElementToBeVisible(supplierPartDescription);
        WebElement att = driver.findElement(By.xpath("//span[contains(text(), '"+attributeChanged+"')]/../../../p/span"));
        scrollElementIntoView(att);
        Assert.assertTrue(att.getText().equals(newValue));
    }
}
