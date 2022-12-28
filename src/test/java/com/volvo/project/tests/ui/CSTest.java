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
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import java.util.Arrays;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIOException;

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
        sp.searchForRecord("SC20");
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
        String status = prPage.partStatus.getText().trim();
        Assert.assertTrue(status.equals("New"));
        prPage.scrollElementIntoView(prPage.taxonomyNode);
        String taxonomy = prPage.taxonomyNode.getText().trim();
        Assert.assertTrue(taxonomy.equals("Uncategorized"));
        homePage.logout();
    }

    @ExcelDataProvider(fileName = "CSLoginValues.xlsx",tab = "testCase1")
    @Test(priority=2,groups = {"smoke", "regression, CS"}, dataProvider = "getExcelDataFromFile", dataProviderClass = TestDataProvider.class)
    public void verifyBrandedPartUpdate(String name, String password,String col3) throws Exception {
        dataProviderTestParameters.set(name + "," + password+", + col3 + ");
        String partNumber = "";
        step("Launch Browser and logged into SPIDR Application");
        InternetHomePage homePage = new InternetLoginPage(getDriver())
                .open()
                .login(name, password);
        step(
                "Check if user is logged in",
                () -> assertThat(homePage.isLoaded(col3)).isTrue()
        );
        VolvoCSDashBoardPage csDash = new VolvoCSDashBoardPage(getDriver());
        csDash.openReviewUpdatedParts();
        ExcelLibrary objExcelFile = new ExcelLibrary();
        String multiMethodfilePath = "src/test/resources/testdata/MultipleMethodTestData.xlsx";
        partNumber = objExcelFile.readFromExcel(multiMethodfilePath, 0,0);
        SearchPage sp = new SearchPage(getDriver());
        sp.searchForRecord(partNumber);
        Thread.sleep(10000);
        Assert.assertTrue(Integer.parseInt(sp.numberOfRecords.getText()) > 0);
    }


    @ExcelDataProvider(fileName = "EastPennSupplierLoginValues.xlsx",tab = "testCase1")
    @Test(priority =1,groups = {"smoke", "regression"}, dataProvider = "getExcelDataFromFile", dataProviderClass = TestDataProvider.class)
    public void verifyCSUpdateInternalPartName(String name, String password,String col3) throws Exception
    {
        SearchPage sp = new SearchPage(getDriver());
        ProductPage prPage = new ProductPage(getDriver());
        JavascriptExecutor js = (JavascriptExecutor)getDriver();
        /*dataProviderTestParameters.set(name + "," + password + ", + col3 + ");
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
        String filePath = VerifyZipFolderAndExtractFiles.unZipFolder();
        //Excel File Existing row update operation
        ExcelLibrary objExcelFile = new ExcelLibrary();
        objExcelFile.writeToExcel(filePath, 3, 4, "AutoTest123");
        objExcelFile.writeToExcel(filePath, 3, 5, "CS Update Internal Part name");
        supplierPage.clickChooseFileImportProductData();
        //supplierPage.refreshDownloadUploadProgress();
        supplierPage.getTotalProcessedRecordsUnderDownloadUploadProgress();
        //supplierPage.getValidRowsRecordsUnderDownloadUploadProgress();
        homePage.profileMenuApp();
        homePage.logout();
*/
        step("Launch Browser and logged into SPIDR Application");
        InternetHomePage homePage1 = new InternetLoginPage(getDriver())
                .open()
                .login("gyashas_CS", "test");
        homePage1.hamburgeIcon();
        homePage1.stagingMenu();
        homePage1.volvo_Products_Staging_SubMenu();
        homePage1.searchRecord("AutoTest123");
        Thread.sleep(10000);

        homePage1.productsStagingTab.click();
        Thread.sleep(10000);
        System.out.println("Part is Searched");
        Thread.sleep(10000);
        sp.clickUsingJS(sp.searchedItemCheckbox);
        System.out.println("Check box clicked");
        Thread.sleep(10000);
        sp.editButton.click();
        System.out.println("Part is opened");
        Thread.sleep(20000);
        prPage.scrollElementIntoView(prPage.internalPartNameAtt);
        System.out.println("Scrolled to element");
        Thread.sleep(10000);
        prPage.internalPartNameAtt.click();
        String actualText = prPage.internalPartNameTextArea.getText();
        System.out.println(actualText);
        prPage.internalPartNameTextArea.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        prPage.internalPartNameTextArea.sendKeys(Keys.DELETE);
        prPage.internalPartNameTextArea.sendKeys("Auto CS update internal Part name");
        prPage.internalPartNameTextArea.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        prPage.internalPartNameTextArea.sendKeys(Keys.DELETE);
        prPage.internalPartNameTextArea.sendKeys(actualText);
        Thread.sleep(20000);
        prPage.saveButton.click();
        }
    }
