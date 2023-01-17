package com.volvo.project.pages;

import com.volvo.project.components.PageObject;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class VolvoSPIDRAdminDashBoardPage extends PageObject {
    //private static final String SEARCH_PMR_FORM_NAME = "Welcome to the Secure Area. When you are done click logout below.";


    public VolvoSPIDRAdminDashBoardPage(WebDriver driver) {
        super(driver);
    }

    //shortcuts
   @FindBy(xpath = "//span[contains(text(), ' SPIDR Manager Approval Required ')]")
   public WebElement SPIDRManagerApprovalRequiredShortcut;

    @Step("Open Manager Approval Dashboard")
    public void openManagerApproval() throws InterruptedException
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.elementToBeClickable(SPIDRManagerApprovalRequiredShortcut));
        SPIDRManagerApprovalRequiredShortcut.click();
    }
}
