package com.volvo.project.pages;

import com.volvo.project.components.PageObject;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class VolvoCSDashBoardPage extends PageObject {
    //private static final String SEARCH_PMR_FORM_NAME = "Welcome to the Secure Area. When you are done click logout below.";


    public VolvoCSDashBoardPage(WebDriver driver) {
        super(driver);
    }

    //shortcuts
   @FindBy(xpath = "//span[contains(text(), ' Assign Taxonomy Node ')]")
    WebElement assignTaxonomyNodeShortcut;

    @FindBy(xpath = "//span[contains(text(), ' Rejected Content ')]")
    WebElement rejectedContentShortcut;

    @FindBy(xpath = "//span[contains(text(), ' Review Updated Parts Information ')]")
    WebElement reviewUpdatedPartsInfoShortcut;

    //Widget
    @FindBy(xpath = "//div[text(), ' Assign Taxonomy Node ']")
    WebElement assignTaxonomyWidget;



    //Steps
    @Step("Get CSDashboard Page loaded status")
    public void verifySupplierDashboard() throws InterruptedException {
        Thread.sleep(5000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.elementToBeClickable(assignTaxonomyWidget));
        assignTaxonomyWidget.click();
        logger.info("Verified Supplier Dashboard");
    }

    @Step("Open Assign Taxonomy Page")
    public void openAssignTaxonomy() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.elementToBeClickable(assignTaxonomyNodeShortcut));
        assignTaxonomyNodeShortcut.click();
    }
}
