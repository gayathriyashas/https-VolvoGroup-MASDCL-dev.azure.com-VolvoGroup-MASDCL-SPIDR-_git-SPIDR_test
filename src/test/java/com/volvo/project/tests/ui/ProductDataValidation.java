package com.volvo.project.tests.ui;

import com.volvo.project.base.WebTestBase;
import com.volvo.project.components.datatdriventesting.ExcelDataProvider;
import com.volvo.project.components.datatdriventesting.ExcelLibrary;
import com.volvo.project.components.datatdriventesting.TestDataProvider;
import com.volvo.project.components.fileoperations.VerifyZipFolderAndExtractFiles;
import com.volvo.project.pages.*;
import io.qameta.allure.Epic;
import io.qameta.allure.Stories;
import io.qameta.allure.Story;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import org.openqa.selenium.Keys;

@Epic("epic 1234")
@Stories({@Story("user story 12345")})
public class ProductDataValidation extends WebTestBase
{

    @ExcelDataProvider(fileName = "EastPennSupplierLoginValues.xlsx",tab = "testCase1")
    @Test(groups = {"smoke", "regression"}, dataProvider = "getExcelDataFromFile", dataProviderClass = TestDataProvider.class)
    public void verifyVMRSAttributes(String name, String password,String col3) throws Exception
    {

        SearchPage sp = new SearchPage(getDriver());
        dataProviderTestParameters.set(name + "," + password+", + col3 + ");
        step("Launch Browser and logged into SPIDR Application");
        InternetHomePage homePage = new InternetLoginPage(getDriver())
                .open()
                .login(name, password);
        step(
                "Check if user is logged in",
                () -> assertThat(homePage.isLoaded(col3)).isTrue()
        );
        step("Cost file export - On Supplier dashboard, from 'Export All' widget, select 'Product' Export type and click on Submit button");
        VolvoSupplierDashBoardPage supplierPage = new VolvoSupplierDashBoardPage(getDriver());

        supplierPage.verifySupplierDashboard();
        supplierPage.exportProductData_RadioButton();
        supplierPage.downloadUploadProgressOperation();
        //VerifyZipFolderAndExtractFiles objZip = new VerifyZipFolderAndExtractFiles();
        String filePath= VerifyZipFolderAndExtractFiles.unZipFolder();
        //Excel File Existing row update operation
        ExcelLibrary objExcelFile = new ExcelLibrary();
        objExcelFile.writeToExcel(filePath,2,4,"VMRS validation");
        objExcelFile.writeToExcel(filePath,2,11,"123-456-789");
        supplierPage.clickChooseFileImportProductData();
        //supplierPage.refreshDownloadUploadProgress();
        supplierPage.getTotalProcessedRecordsUnderDownloadUploadProgress();
        //supplierPage.getValidRowsRecordsUnderDownloadUploadProgress();
        homePage.profileMenuApp();
        homePage.logout();

        step("Launch Browser and logged into SPIDR Application");
        InternetHomePage homePage1 = new InternetLoginPage(getDriver())
                .open()
                .login("system", "system");
        homePage.hamburgeIcon();
        homePage.stagingMenu();
        homePage.volvo_Products_Staging_SubMenu();
        sp.searchForRecord("VMRS validation");
        Thread.sleep(10000);

       homePage.productsStagingTab.click();
        Thread.sleep(10000);
        System.out.println("Part is Searched");
        Thread.sleep(10000);
        sp.clickUsingJS(sp.searchedItemCheckbox);
        System.out.println("Check box clicked");
        Thread.sleep(10000);
        sp.editButton.click();
        System.out.println("Part is opened");
        Thread.sleep(20000);
        ProductPage prPage = new ProductPage(getDriver());
        prPage.scrollElementIntoView(prPage.VMRSCode);
        System.out.println("Scrolled to VMRS code attribute");
        Thread.sleep(20000);

       // homePage.verifyRecord("VMRS validation");
       // ProductPage productPage = new ProductPage(getDriver());
        //verify VMRS code and CK1, CK2 and CK3  attributes
        step("Check if VMRS code is holding the expected value imported");
        String vmrsCodeActualValue = prPage.VMRSCode.getText();
        String vmrsCodeExpectedValue = "123-456-789";
        Assert.assertEquals(vmrsCodeActualValue,vmrsCodeExpectedValue);
        System.out.println("VMRS Code is validated");

        step("Check if VMRSCK31 code is holding the expected value imported");
        String vmrsCK31ActualValue = prPage.VMRSCK31.getText();
        String vmrsCK31ExpectedValue = "123";
        Assert.assertEquals(vmrsCK31ActualValue,vmrsCK31ExpectedValue);
        System.out.println("VMRS Ck31 is validated");

        step("Check if VMRS CK32 code is holding the expected value imported");
        String vmrsCK32ActualValue = prPage.VMRSCK32.getText();
        String vmrsCK32ExpectedValue = "456";
        Assert.assertEquals(vmrsCK32ActualValue,vmrsCK32ExpectedValue);
        System.out.println("VMRS CK32 is validated");

        step("Check if VMRSCK33 code is holding the expected value imported");
        String vmrsCK33ActualValue = prPage.VMRSCK33.getText();
        String vmrsCK33ExpectedValue = "789";
        Assert.assertEquals(vmrsCK33ActualValue,vmrsCK33ExpectedValue);
        System.out.println("VMRS CK33 is validated");
    }
}
