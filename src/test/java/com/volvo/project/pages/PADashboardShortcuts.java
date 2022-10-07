package com.volvo.project.pages;

import com.volvo.project.components.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PADashboardShortcuts extends PageObject
{
    public PADashboardShortcuts(WebDriver driver)
    {
        super(driver);
    }

    //shortcuts
    @FindBy(xpath = "//li[@class='list-group-item list-group-item-action ng-star-inserted']//span[contains(text(),'Pricing Analyst Rejected Costs']")
    public WebElement pricingAnalystRejectedCosts;

    @FindBy(xpath = "//li[@class='list-group-item list-group-item-action ng-star-inserted']//span[contains(text(),'Pricing Analyst Review Costs']")
    public WebElement pricingAnalystReviewCosts;

    @FindBy(xpath = "//li[@class='list-group-item list-group-item-action ng-star-inserted']//span[contains(text(),'Pricing Analyst Review Complete']")
    public WebElement pricingAnalystReviewComplete;

    @FindBy(xpath = "//li[@class='list-group-item list-group-item-action ng-star-inserted']//span[contains(text(),' Core Costs Exceeding Threshold (%) ]")
    public WebElement coreCostExceedingThreshold;

    @FindBy(xpath = "//li[@class='list-group-item list-group-item-action ng-star-inserted']//span[contains(text(),'Parts Missing Price Class']")
    public WebElement partsMissingPriceClass;

    @FindBy(xpath = "//li[@class='list-group-item list-group-item-action ng-star-inserted']//span[contains(text(),'New Parts']")
    public WebElement newParts;

    @FindBy(xpath = "//li[@class='list-group-item list-group-item-action ng-star-inserted']//span[contains(text(),' RoadChoice PDC Costs ']")
    public WebElement rcPDCCosts;

    @FindBy(xpath = "//li[@class='list-group-item list-group-item-action ng-star-inserted']//span[contains(text(),' Pricing Analyst Core Cost Changes ']")
    public WebElement paCoreCostChanges;

    @FindBy(xpath = "//li[@class='list-group-item list-group-item-action ng-star-inserted']//span[contains(text(),' Core Cost Descending Threshold (%) ']")
    public WebElement coreCostDescendingThreshold;

    //other widgtes on Pricing Analyst dashboard

    @FindBy(xpath = "//div[contains(text(), 'Shortcuts')]")
    public WebElement shortcuts;

    @FindBy(xpath = "//a[contains(text(), ' Save')]")
    WebElement saveButton;

    @FindBy(xpath = "//div[contains(text(), 'Parts Missing From full Import')]")
    public WebElement partsMissingFromFullImport;

    @FindBy(xpath = "//div[contains(text(), ' Cost Threshold Flag ')]")
    public WebElement costThresholdflag;

    @FindBy(xpath = "//div[contains(text(), ' Supplier Program Summary ')]")
    public WebElement supplierProgramSummary;

    @FindBy(xpath = "//div[contains(text(), ' Rejected Cost Records ')]")
    public WebElement rejectedCostRecords;

    @FindBy(xpath = "//div[contains(text(), ' Road Choice PDC Costs ')]")
    public WebElement roadChoicePDCCosts;

}