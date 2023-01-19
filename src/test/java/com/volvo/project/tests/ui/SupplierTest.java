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
import org.testng.annotations.Test;
import com.volvo.project.components.fileoperations.VerifyZipFolderAndExtractFiles;

import java.io.IOException;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("epic 1234")
@Stories({@Story("user story 12345")})
public class SupplierTest extends WebTestBase {


    @Test(groups = {"smoke", "regression", "supplier"})
    public void loginTo_SPIDR_Application() throws InterruptedException {
        InternetLoginPage lp = new InternetLoginPage(getDriver());
        lp.open();
        lp.login("Supplier");
        InternetHomePage homePage = new InternetHomePage(getDriver());
        step("Check if user is clicked Profile Menu");
        homePage.profileMenuApp();
        step("Check if user is clicked LogOut Button");
        homePage.logout();
    }

    @Test(groups = {"smoke", "regression", "supplier"})
    public void verifySupplierCanCreateNewProduct() throws InterruptedException, IOException {
        InternetLoginPage lp = new InternetLoginPage(getDriver());
        lp.open();
        lp.login("Supplier");
        InternetHomePage homePage = new InternetHomePage(getDriver());
        VolvoSupplierDashBoardPage supp = new VolvoSupplierDashBoardPage(getDriver());
        supp.createNewPart();
        homePage.logout();
        lp.login("Admin");
        VolvoCSDashBoardPage csDash = new VolvoCSDashBoardPage(getDriver());
        homePage.openProductStaging();
        SearchPage sp = new SearchPage(getDriver());
        sp.searchForRecord();
        Thread.sleep(10000);
        sp.deleteRecord();
    }

    @ExcelDataProvider(fileName = "SupplierLoginValues.xlsx",tab = "testCase1")
    @Test(priority =1,groups = {"smoke", "regression"}, dataProvider = "getExcelDataFromFile", dataProviderClass = TestDataProvider.class)
    public void verifySupplierIsAbleToImportProductFileIntoSPIDRApplication_ValidFiles(String name, String password,String col3) throws Exception {
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
        String filePath=VerifyZipFolderAndExtractFiles.unZipFolder();
        //Excel File Existing row update operation
        ExcelLibrary objExcelFile = new ExcelLibrary();
        String partName = Utils.getRandomString(8);
        writeToMultiStepExcel(partName);
        objExcelFile.writeToExcel(filePath,2,4,partName);
        objExcelFile.writeToExcel(filePath,2,28, "Uncategorized");
        objExcelFile.writeToExcel(filePath,2,11,"123-456-789");
        supplierPage.clickChooseFileImportProductData();
        //supplierPage.refreshDownloadUploadProgress();
        supplierPage.getTotalProcessedRecordsUnderDownloadUploadProgress();
        //supplierPage.getValidRowsRecordsUnderDownloadUploadProgress();
        homePage.profileMenuApp();
        homePage.logout();

    }

    @ExcelDataProvider(fileName = "SupplierLoginValues.xlsx",tab = "testCase1")
    @Test(priority =0,groups = {"smoke", "regression"}, dataProvider = "getExcelDataFromFile", dataProviderClass = TestDataProvider.class)
    public void verifySupplierIsAbleToImportCostFileIntoSPIDRApplication_ValidFiles(String name, String password,String col3) throws Exception {
        dataProviderTestParameters.set(name + "," + password + ", + col3 + ");
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
        supplierPage.exportCostData_RadioButton();
        supplierPage.downloadUploadProgressOperation();
        //VerifyZipFolderAndExtractFiles objZip = new VerifyZipFolderAndExtractFiles();
        String filePath = VerifyZipFolderAndExtractFiles.unZipFolder();
        //Excel File Existing row update operation
        ExcelLibrary objExcelFile = new ExcelLibrary();
        String partName = Utils.getRandomString(8);
        writeToMultiStepExcel(partName);
        objExcelFile.writeToExcel(filePath, 2, 8, partName);
        supplierPage.clickChooseFileImportCostData();
        //supplierPage.refreshDownloadUploadProgress();
        supplierPage.getTotalProcessedRecordsUnderDownloadUploadProgress();
        //supplierPage.getValidRowsRecordsUnderDownloadUploadProgress();
        homePage.profileMenuApp();
        homePage.logout();

    }
}
