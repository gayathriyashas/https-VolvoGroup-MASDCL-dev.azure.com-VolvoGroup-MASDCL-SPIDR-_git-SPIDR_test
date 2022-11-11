package com.volvo.project.pages;

import com.volvo.project.components.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class VolvoSPIDRAdminDashBoardPage extends PageObject {
    //private static final String SEARCH_PMR_FORM_NAME = "Welcome to the Secure Area. When you are done click logout below.";


    public VolvoSPIDRAdminDashBoardPage(WebDriver driver) {
        super(driver);
    }

    //shortcuts
   @FindBy(xpath = "//span[contains(text(), ' SPIDR Manager Approval Required ')]")
    WebElement SPIDRManagerApprovalRequiredShortcut;

}
