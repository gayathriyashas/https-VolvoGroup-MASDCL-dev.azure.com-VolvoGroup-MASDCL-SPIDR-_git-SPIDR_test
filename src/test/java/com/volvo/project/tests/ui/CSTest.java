package com.volvo.project.tests.ui;

import com.volvo.project.base.WebTestBase;
import com.volvo.project.components.datatdriventesting.ExcelDataProvider;
import com.volvo.project.components.datatdriventesting.TestDataProvider;
import com.volvo.project.pages.*;
import io.qameta.allure.Epic;
import io.qameta.allure.Stories;
import io.qameta.allure.Story;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import java.util.Arrays;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("epic 1234")
@Stories({@Story("user story 12345")})
public class CSTest extends WebTestBase {

    @ExcelDataProvider(fileName = "CSLoginValues.xlsx",tab = "testCase1")
    @Test(groups = {"smoke", "regression, CS"}, dataProvider = "getExcelDataFromFile", dataProviderClass = TestDataProvider.class)
    public void loginTo_SPIDR_Application(String name, String password,String col3) throws InterruptedException {
        dataProviderTestParameters.set(name + "," + password+", + col3 + ");
        step("Launch Browser and logged into SPIDR Application");
        InternetHomePage homePage = new InternetLoginPage(getDriver())
                .open()
                .login(name, password);

        step(
                "Check if user is logged in",
                () -> assertThat(homePage.isLoaded(col3)).isTrue()
        );

        step("Check if user is clicked Profile Menu");
                homePage.profileMenuApp();
        step("Check if user is clicked LogOut Button");
        homePage.logout();
    }

    @ExcelDataProvider(fileName = "CSLoginValues.xlsx",tab = "testCase1")
    @Test(groups = {"smoke", "regression, CS"}, dataProvider = "getExcelDataFromFile", dataProviderClass = TestDataProvider.class)
    public void verifyNewPartsareSettoNewandAreUnderAssignTaxonomyShortcut(String name, String password,String col3) throws Exception {
        dataProviderTestParameters.set(name + "," + password+", + col3 + ");
        step("Launch Browser and logged into SPIDR Application");
        InternetHomePage homePage = new InternetLoginPage(getDriver())
                .open()
                .login(name, password);
        step(
                "Check if user is logged in",
                () -> assertThat(homePage.isLoaded(col3)).isTrue()
        );
        Actions action = new Actions(getDriver());
        VolvoCSDashBoardPage csDash = new VolvoCSDashBoardPage(getDriver());
        csDash.openAssignTaxonomy();
        System.out.println("Assign Taxonomy shortcut opened");
        SearchPage sp = new SearchPage(getDriver());
        sp.searchForRecord("TaxonomyTest");
        homePage.productsStagingTab.click();
        System.out.println("Part Searched");
        Thread.sleep(10000);
        sp.clickUsingJS(sp.searchedItemCheckbox);
        System.out.println("Check box clicked");
        sp.editButton.click();
        System.out.println("Part opened");
        Thread.sleep(20000);
        ProductPage prPage = new ProductPage(getDriver());
        prPage.changeViewToNoPreference();
        prPage.scrollElementIntoView(prPage.partStatusDropdown);
        System.out.println("Scroll to Part Status dropdown");
        prPage.partStatusDropdown.click();
        System.out.println("Open Part Status Dropdown");
        String status = prPage.partStatus.getText();
        assertThat(status == "New");
        prPage.scrollElementIntoView(prPage.taxonomyNode);
        String taxonomy = prPage.taxonomyNode.getText();
        assertThat(taxonomy == "Uncategorized");
        homePage.logout();
    }

    @ExcelDataProvider(fileName = "LoginValuesMap.xlsx",tab = "testCase1")
    @Test(groups = {"smoke", "regression, CS"}, dataProvider = "getDataFromExcelTabAsMap", dataProviderClass = TestDataProvider.class)
    public void verifyBrandedPartUpdate(String[] role, String[] name, String[] password,String[] col3) throws Exception {

        dataProviderTestParameters.set(role[0] + ","+name[0] + "," + password[0] + "," + col3[0]);
        step("Launch Browser and logged into SPIDR Application");
        InternetHomePage homePage = new InternetLoginPage(getDriver())
                .open()
                .login(name[0], password[0]);
        step(
                "Check if user is logged in",
                () -> assertThat(homePage.isLoaded(col3[0])).isTrue()
        );
    }
}
