package com.volvo.project.tests.ui;

import com.volvo.project.base.WebTestBase;
import com.volvo.project.components.Utils;
import com.volvo.project.components.datatdriventesting.ExcelDataProvider;
import com.volvo.project.components.datatdriventesting.ExcelLibrary;
import com.volvo.project.components.datatdriventesting.TestDataProvider;
import com.volvo.project.pages.*;
import io.qameta.allure.Epic;
import io.qameta.allure.Stories;
import io.qameta.allure.Story;
import org.junit.Assert;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("epic 1234")
@Stories({@Story("user story 12345")})
public class AdminTest extends WebTestBase {
    @Test(groups = {"smoke", "regression", "Admin"})
    public void verifyMetaDataFields() throws Exception {
        InternetLoginPage lp = new InternetLoginPage(getDriver());
        lp.open();
        lp.login("Supplier");
        InternetHomePage homePage = new InternetHomePage(getDriver());
        VolvoSupplierDashBoardPage supp = new VolvoSupplierDashBoardPage(getDriver());
        supp.createNewPart();
        homePage.logout();
        lp.login("Admin");
        homePage.openProductStaging();
        SearchPage sp = new SearchPage(getDriver());
        sp.searchForRecord();
        Thread.sleep(10000);
        sp.openRecord();
        Thread.sleep(20000);
        ProductPage prPage = new ProductPage(getDriver());
        prPage.changeViewToNoPreference();
        prPage.openBasicInfo();

        String supplierName = prPage.supplierName.getText();
        String manuName = prPage.manufacturerName.getText();
        String partDescription = prPage.supplierPartDescription.getText();
        prPage.scrollElementIntoView(prPage.digitalDropdown);
        prPage.digitalDropdown.click();
        Thread.sleep(1000);
        String brand = prPage.brand.getText();
        String metaTitle = prPage.metadataTitle.getText();
        String metaDescription = prPage.metadataDescription.getText();
        String metaKeywords = prPage.metadataKeywords.getText();
        Assert.assertTrue(metaTitle.equals(supplierName + " Truck Parts, " + manuName + " Truck Parts, " + partDescription));
        Assert.assertTrue(metaDescription.equals(partDescription));
        Assert.assertTrue(metaKeywords.equals(brand + ", "+ supplierName + ", " + manuName + ", " + partDescription));
        prPage.clickUsingJS(prPage.closePage);
        Thread.sleep(10000);
        sp.searchForRecord();
        sp.deleteRecord();
        Thread.sleep(3000);
    }

    @Test(groups = {"smoke", "regression", "Admin"})
    public void verifyVMRSFields() throws Exception {
        InternetLoginPage lp = new InternetLoginPage(getDriver());
        lp.open();
        lp.login("Supplier");
        InternetHomePage homePage = new InternetHomePage(getDriver());
        VolvoSupplierDashBoardPage supp = new VolvoSupplierDashBoardPage(getDriver());
        supp.createNewPart();
        homePage.logout();
        lp.login("Admin");
        homePage.openProductStaging();
        SearchPage sp = new SearchPage(getDriver());
        sp.searchForRecord();
        Thread.sleep(10000);
        sp.openRecord();
        Thread.sleep(20000);
        ProductPage prPage = new ProductPage(getDriver());
        prPage.changeViewToNoPreference();
        prPage.scrollElementIntoView(prPage.categorizationDropdown);
        prPage.openCategorization();
        Assert.assertTrue(prPage.VMRSCode.getText().equals("123-456-789"));
        Assert.assertTrue(prPage.VMRSCK31.getText().equals("123"));
        Assert.assertTrue(prPage.VMRSCK32.getText().equals("456"));
        Assert.assertTrue(prPage.VMRSCK33.getText().equals("789"));
        prPage.clickUsingJS(prPage.closePage);
        Thread.sleep(10000);
        sp.searchForRecord();
        sp.deleteRecord();
        Thread.sleep(3000);

    }

    @Test(groups = {"smoke", "regression", "Admin"})
    public void verifySpecialCharactersRemoved() throws Exception {
        InternetLoginPage lp = new InternetLoginPage(getDriver());
        lp.open();
        lp.login("Supplier");
        InternetHomePage homePage = new InternetHomePage(getDriver());
        VolvoSupplierDashBoardPage supp = new VolvoSupplierDashBoardPage(getDriver());
        supp.createNewPart();
        lp.login("Admin");
        homePage.openProductStaging();
        SearchPage sp = new SearchPage(getDriver());
        sp.searchForRecord();
        Thread.sleep(10000);
        sp.openRecord();
        Thread.sleep(20000);
        ProductPage prPage = new ProductPage(getDriver());
        prPage.changeViewToNoPreference();
        prPage.openBasicInfo();
        prPage.partDescriptionChange("Part; Description: ®   ©  ™  *  °  ¡ ª ½ É Þ Ö ");
        prPage.saveButton.click();
        Thread.sleep(5000);
        prPage.verifyPart("Supplier Part Description", "Part, Description.");
        prPage.clickUsingJS(prPage.closePage);
        Thread.sleep(10000);
        sp.searchForRecord();
        sp.deleteRecord();
        Thread.sleep(3000);

    }

    @Test(groups = {"smoke", "regression", "Admin", "IGNORE"})
    public void verifySupplierNameChange() throws Exception {
        InternetLoginPage lp = new InternetLoginPage(getDriver());
        lp.open();
        lp.login("Admin");
        InternetHomePage homePage = new InternetHomePage(getDriver());
        homePage.openSupplierStaging();
        SearchPage sp = new SearchPage(getDriver());
        sp.searchForRecord("109507");
        Thread.sleep(3000);
        sp.openRecord();
        Thread.sleep(5000);
        SupplierPage sup = new SupplierPage(getDriver());
        String oldSupplierName = sup.supplierName.getText();
        System.out.println(oldSupplierName);
        String newSupplierName = Utils.getRandomString(8).toUpperCase();
        sup.supplierNameChange(newSupplierName);
        sup.saveButton.click();
        Thread.sleep(10000);
        sup.clickUsingJS(sup.closePage);
        homePage.openProductStaging();
        sp.searchForRecord(newSupplierName);
        Thread.sleep(5000);
        Assert.assertTrue(Integer.parseInt(sp.numberOfRecords.getText()) > 0);
        homePage.openSupplierStaging();
        sp.searchForRecord("109507");
        Thread.sleep(3000);
        sp.openRecord();
        Thread.sleep(5000);
        sup.supplierNameChange(oldSupplierName);
        sup.saveButton.click();
        Thread.sleep(5000);
    }

    @ExcelDataProvider(fileName = "AdminLoginValues.xlsx",tab = "testCase1")
    @Test(priority =100,groups = {"smoke", "regression"}, dataProvider = "getExcelDataFromFile", dataProviderClass = TestDataProvider.class)
    public void deleteProduct(String name, String password,String col3) throws Exception {
        String partNumber = "";
        dataProviderTestParameters.set(name + "," + password + ", + col3 + ");
        step("Launch Browser and logged into SPIDR Application");
        InternetHomePage homePage = new InternetLoginPage(getDriver())
                .open()
                .login(name, password);
        step(
                "Check if user is logged in",
                () -> assertThat(homePage.isLoaded(col3)).isTrue()
        );
        homePage.hamburgeIcon();
        homePage.stagingMenu();
        homePage.volvo_Products_Staging_SubMenu();
        partNumber = readFromMultiStepExcel();
        SearchPage sp = new SearchPage(getDriver());
        sp.searchForRecord(partNumber);
        Thread.sleep(10000);
        sp.searchedItemCheckbox.click();
        sp.deleteButton.click();
        Thread.sleep(1000);
        sp.deleteYes.click();
        Thread.sleep(1000);
    }

    @ExcelDataProvider(fileName = "AdminLoginValues.xlsx",tab = "testCase1")
    @Test(groups = {"smoke", "regression"}, dataProvider = "getExcelDataFromFile", dataProviderClass = TestDataProvider.class)
    public void deleteCost(String name, String password,String col3) throws Exception {
        String partNumber = "";
        dataProviderTestParameters.set(name + "," + password + ", + col3 + ");
        step("Launch Browser and logged into SPIDR Application");
        InternetHomePage homePage = new InternetLoginPage(getDriver())
                .open()
                .login(name, password);
        step(
                "Check if user is logged in",
                () -> assertThat(homePage.isLoaded(col3)).isTrue()
        );
        homePage.hamburgeIcon();
        homePage.stagingMenu();
        homePage.volvo_Cost_Staging_SubMenu();
        partNumber = readFromMultiStepExcel();
        SearchPage sp = new SearchPage(getDriver());
        sp.searchForRecord(partNumber);
        Thread.sleep(10000);
        sp.searchedItemCheckbox.click();
        sp.deleteButton.click();
        Thread.sleep(1000);
        sp.deleteYes.click();
        Thread.sleep(1000);
    }


    @ExcelDataProvider(fileName = "AdminLoginValues.xlsx",tab = "testCase1")
   @Test(groups = {"smoke", "regression"})
    public void adminImpersonateAsSupplier() throws InterruptedException
    {
       InternetLoginPage lp = new InternetLoginPage(getDriver());
       InternetHomePage hp = new InternetHomePage(getDriver());
       lp.open();
       Thread.sleep(5000);
       hp.impersonateButton.click();
       Thread.sleep(5000);
       hp.eastPennSupplier.click();
       Thread.sleep(1000);
    }
}
