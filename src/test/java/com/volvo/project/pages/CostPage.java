package com.volvo.project.pages;

import com.volvo.project.components.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CostPage extends PageObject
{
    public CostPage(WebDriver driver)
    {

        super(driver);
    }

    @FindBy(id = "Basic Info")
    public WebElement BasicInfoTab;

    @FindBy(id = "F_1005331")
    public WebElement SupplierName;

    @FindBy(id = "F_1022008")
    public WebElement SupplierNote;

    @FindBy(id = "F_1005765")
    public WebElement CostOriginalPartNumber;

    @FindBy(id = "F_1005045")
    public WebElement CostSupplierID;

    @FindBy(id = "F_1005765")
    public WebElement SupplierCompPartNumber;

    @FindBy(id = "F_1005044")
    public WebElement ProgramName;

    @FindBy(id = "F_1005041")
    public WebElement CostMarketName;

    @FindBy(id = "F_1006112")
    public WebElement CostBlanketNumber;

    @FindBy(id = "F_1006134")
    public WebElement MoveCode;

    @FindBy(id = "F_1006289")
    public WebElement CommentPackageType;

    @FindBy(id = "F_1006116")
    public WebElement CountryOfOrigin;

    @FindBy(id = "F_1005143")
    public WebElement CostImpactAmountLevel1;

    @FindBy(id = "F_1005402")
    public WebElement CostImpactPercentageLevel1;

    @FindBy(id = "F_1022009")
    public WebElement DnetImpactAmountLevel1;

    @FindBy(id = "F_1022010")
    public WebElement DnetImpactPercentageLevel1;

    @FindBy(id = "F_1006131")
    public WebElement InternalPartName;

    @FindBy(id = "F_1005033")
    public WebElement CostLevel1;

    @FindBy(id = "F_1005398")
    public WebElement PACostApproved;

    /* Elements required for Price class verification */

    @FindBy(id = "F_1005100")
    public WebElement PriceClass;

    @FindBy(id = "F_1006081")
    public WebElement StockClass;

    @FindBy(id = "F_1005494")
    public WebElement MackayCode;

    @FindBy(id = "F_1005219")
    public WebElement MackPrefix;

    @FindBy(id = "F_1005053")
    public WebElement MackPartNumber;

    @FindBy(id = "F_1005220")
    public WebElement VolvoPrefix;

    @FindBy(id = "F_1005138")
    public WebElement VolvoPartNumber;

    @FindBy(id = "F_1006082")
    public WebElement FinancialProductID;

    @FindBy(id = "F_1006084")
    public WebElement ProductIDClassification;

}