package com.volvo.project.pages;

import com.volvo.project.components.PageObject;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CostPage extends PageObject
{
    public CostPage(WebDriver driver)
    {

        super(driver);
    }

    //dropdowns
    @FindBy(id = "Cost")
    public WebElement costDropdown;

    //Attributes
    //Basic Info
    @FindBy(xpath = "//span[contains(text(), 'Cost Supplier ID')]/../../../p/span")
    public WebElement costSupplierID;

    @FindBy(xpath = "//span[contains(text(), 'Supplier Name')]/../../../p/span")
    public WebElement supplierName;

    @FindBy(xpath = "//span[contains(text(), 'Supplier Note')]/../../../p/span")
    public WebElement supplierNote;

    @FindBy(xpath = "//span[contains(text(), 'Part Status')]/../../../p/span")
    public WebElement partStatus;

    @FindBy(xpath = "//span[contains(text(), 'Original Supplier Part Number')]/../../../p/span")
    public WebElement originalSupplierPartNumber;


    @FindBy(xpath = "//span[contains(text(), 'Supplier Compressed Part Number')]/../../../p/span")
    public WebElement SupplierCompPartNumber;

    @FindBy(xpath = "//span[contains(text(), 'Program Name')]/../../../p/span")
    public WebElement ProgramName;

    @FindBy(xpath = "//span[contains(text(), 'Market Name')]/../../../p/span")
    public WebElement CostMarketName;

    @FindBy(xpath = "//span[contains(text(), 'Currency')]/../../../p/span[1]")
    public WebElement currency;

    @FindBy(xpath = "//span[contains(text(), 'Order in Multiples of Level 1')]/../../../p/span")
    public WebElement orderInMultiplesOf1;

    @FindBy(xpath = "//span[contains(text(), 'Minimum Order Qty')]/../../../p/span")
    public WebElement minimumOrderQty;

   //Cost
   @FindBy(xpath = "//span[contains(text(), 'VDSP Cost Effective Date')]/../../../p/span")
   public WebElement VDSPCostEffectiveDate;

    @FindBy(xpath = "//span[contains(text(), 'Cost Level 1')]/../../../p/span")
    public WebElement costLevel1;

    @FindBy(xpath = "//tabset//li[3]//span[3]")
    public WebElement closePage;

    public void  openCosts() throws InterruptedException {
    if(driver.findElements(By.xpath("//span[contains(text(), 'Cost Impact Amount Level 1')]/../../../p/span")).size() == 0) {
        costDropdown.click();
        Thread.sleep(1000);
    }
}

    public void closeCost() {
         moveToElement(closePage);
         clickUsingJS(closePage);
    }

    public void verifynewImportFields() throws InterruptedException {
        scrollElementIntoView(costSupplierID);
        Assert.assertTrue(costSupplierID.getText() .equals("109507"));
        scrollElementIntoView(supplierName);
        Assert.assertTrue(supplierName.getText().equals("Di-Pro DEV"));
        scrollElementIntoView(partStatus);
        Assert.assertTrue(partStatus.getText().equals("New"));
        scrollElementIntoView(originalSupplierPartNumber);
        Assert.assertTrue(originalSupplierPartNumber.getText().equals(readFromMultiStepExcel()));
        scrollElementIntoView(SupplierCompPartNumber);
        Assert.assertTrue(SupplierCompPartNumber.getText().equals(readFromMultiStepExcel()));
        scrollElementIntoView(ProgramName);
        Assert.assertTrue(ProgramName.getText().equals("Road Choice"));
        scrollElementIntoView(CostMarketName);
        Assert.assertTrue(CostMarketName.getText().equals("CA"));
        scrollElementIntoView(currency);
        Assert.assertTrue(currency.getText().equals("USD"));
        scrollElementIntoView(orderInMultiplesOf1);
        Assert.assertTrue(orderInMultiplesOf1.getText().equals("1"));
        scrollElementIntoView(minimumOrderQty);
        Assert.assertTrue(minimumOrderQty.getText().equals("1"));
        openCosts();
        scrollElementIntoView(VDSPCostEffectiveDate);
        Assert.assertTrue(VDSPCostEffectiveDate.getText().equals("01/01/2022"));
        scrollElementIntoView(costLevel1);
        Assert.assertTrue(costLevel1.getText().equals("50.00"));
    }

    public void verifySupplierNote() {
        scrollElementIntoView(supplierNote);
        Assert.assertTrue(supplierNote.getText().equals("Test Supplier Note"));
    }

}