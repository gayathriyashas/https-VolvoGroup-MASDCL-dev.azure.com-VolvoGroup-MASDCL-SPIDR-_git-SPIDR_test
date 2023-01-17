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

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("epic 1234")
@Stories({@Story("user story 12345")})
public class SupplierTest extends WebTestBase {

    @ExcelDataProvider(fileName = "SupplierLoginValues.xlsx",tab = "testCase1")
    @Test(groups = {"smoke", "regression"}, dataProvider = "getExcelDataFromFile", dataProviderClass = TestDataProvider.class)
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
        homePage.searchRecord(partName);
        homePage.verifyRecord(partName);
       homePage.profileMenuApp();
       homePage.logout();

        /*
        //Excel File New row operation
        String[] valueToWrite = {"PDC"};
        ExcelFileOperation objExcelFile = new ExcelFileOperation();
        objExcelFile.writeExcel_ByNewRow(filePath,"Sheet_0",valueToWrite);

        //Excel File Existing row update operation
        ExcelFileWriteOperation objExcelFile1 = new ExcelFileWriteOperation(filePath);
        objExcelFile1.setCellData("Sheet_0","Region",2,"Northeast");
        objExcelFile1.setCellData("Sheet_0","Brand",2,"Volvo");
        objExcelFile1.setCellData("Sheet_0","Region",4,"Southwest");
        objExcelFile1.setCellData("Sheet_0","Brand",4,"Volvo");
        objExcelFile1.setCellData("Sheet_0","Region",6,"Northeast");
        objExcelFile1.setCellData("Sheet_0","Brand",6,"Volvo");
         */
        //homePage.profileMenuApp();
        //homePage.logout();
    }

    @ExcelDataProvider(fileName = "SupplierLoginValues.xlsx",tab = "testCase1")
    @Test(priority =1,groups = {"smoke", "regression"}, dataProvider = "getExcelDataFromFile", dataProviderClass = TestDataProvider.class)
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

        step("Launch Browser and logged into SPIDR Application");
        InternetHomePage homePage1 = new InternetLoginPage(getDriver())
                .open()
                .login("system", "system");
        homePage.hamburgeIcon();
        homePage.stagingMenu();
        homePage.volvo_Cost_Staging_SubMenu();
        homePage.searchRecord(partName);
        homePage.verifyRecord(partName);
        homePage.profileMenuApp();
        homePage.logout();
    }
    @ExcelDataProvider(fileName = "SupplierLoginValues.xlsx",tab = "testCase1")
    @Test(groups = {"smoke", "regression"}, dataProvider = "getExcelDataFromFile", dataProviderClass = TestDataProvider.class)
    public void editpartBrandedPart(String name, String password,String col3) throws Exception {
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
        supplierPage.exportProductData_RadioButton();
        supplierPage.downloadUploadProgressOperation();
        //VerifyZipFolderAndExtractFiles objZip = new VerifyZipFolderAndExtractFiles();
        String filePath=VerifyZipFolderAndExtractFiles.unZipFolder();
        //Excel File Existing row update operation
        ExcelLibrary objExcelFile = new ExcelLibrary();
        String partName = objExcelFile.readFromExcel(filePath, 2, 4);
        System.out.println("Part name: "+partName);
        String brandedPartInitial = objExcelFile.readFromExcel(filePath,2,15);
        String brandedPartFinal;
        //objExcelFile.writeToExcel(filePath,2,4, "testName");
        if(brandedPartInitial.equals("Y"))
            objExcelFile.writeToExcel(filePath, 2, 15, "N");

        else
            objExcelFile.writeToExcel(filePath, 2, 15, "Y");
        brandedPartFinal =objExcelFile.readFromExcel(filePath,2,15);
        String multiMethodfilePath = "C:/testOpsJavaFramework/src/test/resources/testdata/MultipleMethodTestData.xlsx";
        objExcelFile.writeToExcel(multiMethodfilePath, 0,0,partName);
        supplierPage.clickChooseFileImportProductData();
        //supplierPage.refreshDownloadUploadProgress();
        supplierPage.getTotalProcessedRecordsUnderDownloadUploadProgress();
        //supplierPage.getValidRowsRecordsUnderDownloadUploadProgress();

        homePage.profileMenuApp();
        homePage.logout();
    }
}