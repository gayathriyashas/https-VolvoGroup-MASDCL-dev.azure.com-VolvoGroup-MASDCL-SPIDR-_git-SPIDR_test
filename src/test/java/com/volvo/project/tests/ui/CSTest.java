package com.volvo.project.tests.ui;

import com.volvo.project.base.WebTestBase;
import com.volvo.project.components.datatdriventesting.ExcelDataProvider;
import com.volvo.project.components.datatdriventesting.TestDataProvider;
import com.volvo.project.pages.*;
import io.qameta.allure.Epic;
import io.qameta.allure.Stories;
import io.qameta.allure.Story;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("epic 1234")
@Stories({@Story("user story 12345")})
public class CSTest extends WebTestBase {

    @ExcelDataProvider(fileName = "CSLoginValues.xlsx",tab = "testCase1")
    @Test(groups = {"smoke", "regression, CS"})
    public void loginTo_SPIDR_Application() throws InterruptedException {
        InternetLoginPage lp = new InternetLoginPage(getDriver());
        lp.open();
        lp.login("CS");
        InternetHomePage homePage = new InternetHomePage(getDriver());
        step("Check if user is clicked Profile Menu");
                homePage.profileMenuApp();
        step("Check if user is clicked LogOut Button");
        homePage.logout();
    }

    @Test(groups = {"smoke", "regression, CS"})
    public void NewPartImportedCorrectly() throws Exception {
        InternetLoginPage lp = new InternetLoginPage(getDriver());
        lp.open();
        lp.login("Supplier");
        InternetHomePage homePage = new InternetHomePage(getDriver());
        VolvoSupplierDashBoardPage supp = new VolvoSupplierDashBoardPage(getDriver());
        supp.createNewPart();
        homePage.logout();
        lp.login("CS");
        VolvoCSDashBoardPage csDash = new VolvoCSDashBoardPage(getDriver());
        csDash.openAssignTaxonomy();
        System.out.println("Assign Taxonomy shortcut opened");
        SearchPage sp = new SearchPage(getDriver());
        sp.searchForRecord();
        System.out.println("Part Searched");
        Thread.sleep(10000);
        sp.openRecord();
        Thread.sleep(20000);
        ProductPage pr = new ProductPage(getDriver());
        pr.changeViewToNoPreference();
        pr.verifyStatus("New");
        pr.verifyTaxonomy("Uncategorized");
        homePage.logout();
        lp.login("Admin");
        homePage.openProductStaging();
        sp.searchForRecord();
        Thread.sleep(10000);
        sp.deleteRecord();
    }

    @Test(groups = {"smoke", "regression, CS"})
    public void EditBrand() throws Exception {
        InternetLoginPage lp = new InternetLoginPage(getDriver());
        lp.open();
        lp.login("Supplier");
        InternetHomePage homePage = new InternetHomePage(getDriver());
        VolvoSupplierDashBoardPage supp = new VolvoSupplierDashBoardPage(getDriver());
        supp.createNewPart();
        homePage.logout();
        lp.login("CS");
        VolvoCSDashBoardPage csDash = new VolvoCSDashBoardPage(getDriver());
        csDash.openAssignTaxonomy();
        System.out.println("Assign Taxonomy shortcut opened");
        SearchPage sp = new SearchPage(getDriver());
        sp.searchForRecord();
        System.out.println("Part Searched");
        Thread.sleep(10000);
        sp.openRecord();
        Thread.sleep(20000);
        ProductPage pr = new ProductPage(getDriver());
        pr.changeViewToNoPreference();
        pr.setTaxonomyNode("Electrical.Batteries (42201)");
        pr.openPartDataTab();
        pr.setCSEnrichmentComplete();
        pr.saveButton.click();
        Thread.sleep(5000);
        homePage.logout();
        lp.login("Supplier");
        String[] attributes = new String[] {"Brand"};
        supp.editPart(attributes);
        homePage.logout();
        lp.login("CS");
        csDash.openReviewUpdatedParts();
        sp.searchForRecord();
        System.out.println("Part Searched");
        Thread.sleep(10000);
        sp.openRecord();
        Thread.sleep(20000);
        pr.verifyAttributeChanged("Branded Part");
        homePage.logout();
        lp.login("Admin");
        homePage.openProductStaging();
        sp.searchForRecord();
        Thread.sleep(10000);
        sp.deleteRecord();
        Thread.sleep(3000);
    }

    @Test(groups = {"smoke", "regression, CS"})
    public void EditCSA() throws Exception {
        InternetLoginPage lp = new InternetLoginPage(getDriver());
        lp.open();
        lp.login("Supplier");
        InternetHomePage homePage = new InternetHomePage(getDriver());
        VolvoSupplierDashBoardPage supp = new VolvoSupplierDashBoardPage(getDriver());
        supp.createNewPart();
        homePage.logout();
        lp.login("CS");
        VolvoCSDashBoardPage csDash = new VolvoCSDashBoardPage(getDriver());
        csDash.openAssignTaxonomy();
        System.out.println("Assign Taxonomy shortcut opened");
        SearchPage sp = new SearchPage(getDriver());
        sp.searchForRecord();
        System.out.println("Part Searched");
        Thread.sleep(10000);
        sp.openRecord();
        Thread.sleep(20000);
        ProductPage pr = new ProductPage(getDriver());
        pr.changeViewToNoPreference();
        pr.setTaxonomyNode("Electrical.Batteries (42201)");
        pr.openPartDataTab();
        pr.setCSEnrichmentComplete();
        pr.saveButton.click();
        Thread.sleep(5000);
        homePage.logout();
        lp.login("Supplier");
        String[] attributes = new String[] {"HEIGHT_I_IN_D", "weight_i_lb_d"};
        supp.editPart(attributes);
        homePage.logout();
        lp.login("CS");
        csDash.openReviewUpdatedParts();
        sp.searchForRecord();
        System.out.println("Part Searched");
        Thread.sleep(10000);
        sp.openRecord();
        Thread.sleep(20000);
        pr.verifyAttributeChanged("Weight_M_kg_d");
        pr.verifyAttributeChanged("Height_M_mm_d");
        homePage.logout();
        lp.login("Admin");
        homePage.openProductStaging();
        sp.searchForRecord();
        Thread.sleep(10000);
        sp.deleteRecord();
        Thread.sleep(3000);
    }

    @Test(groups = {"smoke", "regression, CS"})
    public void EditSupersession() throws Exception {
        InternetLoginPage lp = new InternetLoginPage(getDriver());
        lp.open();
        lp.login("Supplier");
        InternetHomePage homePage = new InternetHomePage(getDriver());
        VolvoSupplierDashBoardPage supp = new VolvoSupplierDashBoardPage(getDriver());
        supp.createNewPart();
        homePage.logout();
        lp.login("CS");
        VolvoCSDashBoardPage csDash = new VolvoCSDashBoardPage(getDriver());
        csDash.openAssignTaxonomy();
        System.out.println("Assign Taxonomy shortcut opened");
        SearchPage sp = new SearchPage(getDriver());
        sp.searchForRecord();
        System.out.println("Part Searched");
        Thread.sleep(10000);
        sp.openRecord();
        Thread.sleep(20000);
        ProductPage pr = new ProductPage(getDriver());
        pr.changeViewToNoPreference();
        pr.setTaxonomyNode("Electrical.Batteries (42201)");
        pr.openPartDataTab();
        pr.setCSEnrichmentComplete();
        Thread.sleep(2000);
        pr.saveButton.click();
        Thread.sleep(5000);
        homePage.logout();
        lp.login("Supplier");
        String[] attributes = new String[] {"Supersession From", "Supersession To"};
        supp.editPart(attributes);
        homePage.logout();
        lp.login("CS");
        csDash.openReviewUpdatedParts();
        sp.searchForRecord();
        System.out.println("Part Searched");
        Thread.sleep(10000);
        sp.openRecord();
        Thread.sleep(20000);
        pr.verifyAttributeChanged("Supersession From");
        pr.verifyAttributeChanged("Supersession To");
        homePage.logout();
        lp.login("Admin");
        homePage.openProductStaging();
        sp.searchForRecord();
        Thread.sleep(10000);
        sp.deleteRecord();
        Thread.sleep(3000);
    }

    @Test(groups = {"smoke", "regression, CS"})
    public void verifyCSCanEditInternalPartName() throws Exception {
        InternetLoginPage lp = new InternetLoginPage(getDriver());
        lp.open();
        lp.login("Supplier");
        InternetHomePage homePage = new InternetHomePage(getDriver());
        VolvoSupplierDashBoardPage supp = new VolvoSupplierDashBoardPage(getDriver());
        supp.createNewPart();
        homePage.logout();
        lp.login("CS");
        VolvoCSDashBoardPage csDash = new VolvoCSDashBoardPage(getDriver());
        csDash.openAssignTaxonomy();
        System.out.println("Assign Taxonomy shortcut opened");
        SearchPage sp = new SearchPage(getDriver());
        sp.searchForRecord();
        System.out.println("Part Searched");
        Thread.sleep(10000);
        sp.openRecord();
        Thread.sleep(20000);
        ProductPage pr = new ProductPage(getDriver());
        pr.changeViewToNoPreference();
        String newInternalPartName = pr.InternalPartNameChange();
        Thread.sleep(2000);
        pr.saveButton.click();
        Thread.sleep(5000);
        pr.verifyPart("Internal Part Name", newInternalPartName);
        homePage.logout();
        lp.login("Admin");
        homePage.openProductStaging();
        sp.searchForRecord();
        Thread.sleep(10000);
        sp.deleteRecord();
        Thread.sleep(3000);
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
