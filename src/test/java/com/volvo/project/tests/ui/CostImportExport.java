package com.volvo.project.tests.ui;

import com.volvo.project.base.WebTestBase;
import com.volvo.project.components.datatdriventesting.ExcelDataProvider;
import com.volvo.project.components.datatdriventesting.ExcelLibrary;
import com.volvo.project.components.datatdriventesting.TestDataProvider;
import com.volvo.project.components.fileoperations.VerifyZipFolderAndExtractFiles;
import com.volvo.project.pages.InternetHomePage;
import com.volvo.project.pages.InternetLoginPage;
import com.volvo.project.pages.VolvoSupplierDashBoardPage;
import org.testng.annotations.Test;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

public class CostImportExport extends WebTestBase
{

    @ExcelDataProvider(fileName = "InternetLoginValues.xlsx",tab = "testCase1")
    @Test(groups = {"smoke", "regression"}, dataProvider = "getExcelDataFromFile", dataProviderClass = TestDataProvider.class)
    public void verifySupplierIsAbleToImportProductFileIntoSPIDRApplication_ValidFiles(String name, String password,String col3) throws Exception
    {
        dataProviderTestParameters.set(name + "," + password + ", + col3 + ");
        step("Launch Browser and logged into SPIDR Application");
        InternetHomePage homePage = new InternetLoginPage(getDriver())
                .open()
                .login(name, password);
        step(
                "Check if user is logged in",
                () -> assertThat(homePage.isLoaded(col3)).isTrue()
        );

        step("Cost file export - On Supplier dashboard, from 'Export All' widget, select 'Cost' Export type and click on Submit button");
        VolvoSupplierDashBoardPage supplierPage = new VolvoSupplierDashBoardPage(getDriver());
        supplierPage.verifySupplierDashboard();
        //supplierPage.exportCostData_RadioButton();
        supplierPage.downloadUploadProgressOperation();
        //VerifyZipFolderAndExtractFiles objZip = new VerifyZipFolderAndExtractFiles();
        String filePath = VerifyZipFolderAndExtractFiles.unZipFolder();
        //Excel File Existing row update operation
        ExcelLibrary objExcelFile = new ExcelLibrary();
        objExcelFile.writeToExcel(filePath, 2, 4, "Cost 1234");
        supplierPage.clickChooseFileImportProductData();
        supplierPage.getTotalProcessedRecordsUnderDownloadUploadProgress();
        homePage.profileMenuApp();
        homePage.logout();

        step("Launch Browser and logged into SPIDR Application");
        InternetHomePage homePage1 = new InternetLoginPage(getDriver())
                .open()
                .login("system", "system");
        homePage.hamburgeIcon();
        homePage.stagingMenu();
        homePage.volvo_Cost_Staging_SubMenu();
        homePage.searchRecord("Cost 1234");
        homePage.verifyRecord("Cost 1234");
        homePage.profileMenuApp();
        homePage.logout();
    }
}
