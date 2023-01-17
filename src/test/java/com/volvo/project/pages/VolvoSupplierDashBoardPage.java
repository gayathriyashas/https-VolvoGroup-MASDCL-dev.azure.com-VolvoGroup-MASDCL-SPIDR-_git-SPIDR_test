package com.volvo.project.pages;

import com.volvo.project.components.PageObject;
import com.volvo.project.components.fileoperations.VerifyZipFolderAndExtractFiles;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.Set;

public class VolvoSupplierDashBoardPage extends PageObject {
    //private static final String SEARCH_PMR_FORM_NAME = "Welcome to the Secure Area. When you are done click logout below.";
    @FindBy(id = "dashboardImage")
    public WebElement supplierDashboardLabel;

    @FindBy(xpath = "//div[contains(text(),' Download / Upload Progress ')]")
    public WebElement downloadUploadHeader;

    @FindBy(xpath = "//div[contains(text(),' Download / Upload Progress ')]//parent::div//i[@class='pull-right fa fa-window-maximize showme btns ng-star-inserted']")
    public WebElement downloadUploadMaximizeButton;

    @FindBy(xpath = "(//div[@class='ag-body-viewport ag-layout-normal ag-row-animation']//span[contains(text(),'Complete')])[1]/parent::enable-codeset-grid-renderer/parent::div/following-sibling::div//input[1]")
    public WebElement goDownloadLink;

    @FindBy(xpath = "(//div[@class='ag-body-viewport ag-layout-normal ag-row-animation']//span[contains(text(),'Complete')])[1]/parent::enable-codeset-grid-renderer/parent::div/following-sibling::div//input[1]")
    public WebElement chooseFile;

    public VolvoSupplierDashBoardPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get SupplierDashboard Page loaded status")
    public void verifySupplierDashboard() throws InterruptedException {
        Thread.sleep(5000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.elementToBeClickable(supplierDashboardLabel));
        supplierDashboardLabel.click();
        logger.info("Verified Supplier Dashboard");
    }

    @Step("Select exportProductData_RadioButton")
    public void exportProductData_RadioButton() throws InterruptedException {
        Thread.sleep(10000);
        Thread.sleep(10000);

        //ClickElementJSByXPATH("#DownloadRadioProduct", "Radio Button click");
        //ClickElementJSByXPATH("label[for='DownloadRadioProduct']", "Radio Button click");
        //Components.Utils.ScrollingFunctions.ClickElementJSByCSS(Driver.Value,"label[for='DownloadRadioProduct']", "Radio Button click");

        //JavaScriptclick("label[for='DownloadRadioProduct']");
        // WebDriverWait wait = new WebDriverWait(Driver.Value, TimeSpan.FromSeconds(360));
        //wait.Until(ExpectedConditions.TextToBePresentInElementValue(ExportDataHeader,"Export Data"));


        //Components.Utils.ScrollingFunctions.ScrollIntoView(Driver.Value, ExportDataHeader);
        //Components.Utils.ScrollingFunctions.ScrollDownUsingJs(Driver.Value);
        logger.info("ExportDataHeader done");


        //Actions action = new Actions(Driver.Value);
        //action.MoveToElement(ExportDataHeader).Click().Perform();
        logger.info("Export Data Header clicked");
        Thread.sleep(10000);

        // Store the parent window of the driver
        String parentWindowHandle = driver.getWindowHandle();
        System.out.println("Parent window's handle -> " + parentWindowHandle);
        driver.switchTo().newWindow(WindowType.TAB);

        if (InternetLoginPage.URL_INTERNET_LOGIN_PAGE.contains("wwglbn12562"))
        {
            driver.navigate().to("https://wwglbn12562.vcn.ds.volvo.net/www/client-custom/Export.html");
        }
        else
        {
            driver.navigate().to("http://wwglbn12567:81/www/client-custom/Export.html");
        }

        Thread.sleep(10000);
        Thread.sleep(10000);
        WebElement clickElement = driver.findElement(By.xpath("//table[@class='fileUpload']//input[@id='DownloadRadioProduct']"));

        // Multiple click to open multiple window
        for (var i = 0; i < 1; i++)
        {
            clickElement.click();
            System.out.println("Radio button clicked");
            ClickElementJSByXPATH("button[type='button']", "Submit Button click");
            System.out.println("Submit button clicked");
            Thread.sleep(10000);
        }
        // Store all the opened window into the list
        // Print each and every Items of the list
        Set<String> lstWindow =driver.getWindowHandles();
        System.out.println("List of Windows : "+lstWindow);
        try
        {
            for (String handle : lstWindow)
            {
                Thread.sleep(5000);
                System.out.println(handle);
                // Driver.Value.SwitchTo().Window(handle);

                //IAlert alert = Driver.Value.SwitchTo().Alert();
                //alert.Accept();
                //alert.Dismiss();
                var msg = driver.switchTo().alert().getText();
                logger.info("Alert message: " + msg);
                //accept alert
                driver.switchTo().alert().accept();

                System.out.println("Alert clicked OK");
                //driver.switchTo().window((driver.getWindowHandles().Last()));
                //driver.switchTo().window(parentWindowHandle);
                driver.close();
                //Driver.Value.SwitchTo().DefaultContent();
                //Driver.Value.SwitchTo().Window(parentWindowHandle);
            }
        }
        catch (Exception e)
        {
            System.out.println("Some Exception happened ");
            //Console.WriteLine(exp.Message);
        }

        //Switch to the parent window
        driver.switchTo().window(parentWindowHandle);

    }
    public boolean ClickElementJSByXPATH(String ed, String objectname)
    {
        //WebElement e=driver.findElement(By.cssSelector(ed));
        try
        {
            JavascriptExecutor js = (JavascriptExecutor)driver;
            String js1 = "window.document.querySelector(\"" + ed + "\").click()";
            //string js1 = "window.document.querySelector(\'"+ ed + "\').click()";
            // string js1 = "$(\"" + ed + "\").click()";
            System.out.println("Here is : " + js1);
            js.executeScript(js1);
            System.out.println("Executed");
            return true;
        }
        catch (Exception e)
        {
            System.out.println("Some Exception happened ");
            //Console.WriteLine(exp.Message);
            return false;
        }
    }
    @Step("Download/Upload Progress Operation")
    public void downloadUploadProgressOperation() throws InterruptedException {
        Thread.sleep(10000);
        Thread.sleep(10000);

        //ClickElementJSByXPATH("#DownloadRadioProduct", "Radio Button click");
        //ClickElementJSByXPATH("label[for='DownloadRadioProduct']", "Radio Button click");
        //Components.Utils.ScrollingFunctions.ClickElementJSByCSS(Driver.Value,"label[for='DownloadRadioProduct']", "Radio Button click");

        //JavaScriptclick("label[for='DownloadRadioProduct']");
        // WebDriverWait wait = new WebDriverWait(Driver.Value, TimeSpan.FromSeconds(360));
        //wait.Until(ExpectedConditions.TextToBePresentInElementValue(ExportDataHeader,"Export Data"));


        //Components.Utils.ScrollingFunctions.ScrollIntoView(Driver.Value, ExportDataHeader);
        //Components.Utils.ScrollingFunctions.ScrollDownUsingJs(Driver.Value);
        logger.info("ExportDataHeader done");
        PageObject obj = new PageObject(driver);
        obj.scrollElementIntoView(downloadUploadHeader);
        logger.info("uploadDownloadHeader clicked");
        System.out.println("uploadDownloadHeader clicked");

        //Actions action = new Actions(driver);
        //action.moveToElement(uploadDownloadHeader).click().perform();
        //logger.info("uploadDownloadHeader clicked");
        Thread.sleep(10000);

        //Actions action = new Actions(driver);
        //action.moveToElement(downloadUploadMaximizeButton).click().perform();
        //logger.info("downloadUploadMaximizeButton clicked");
        //Thread.sleep(10000);

        // Store the parent window of the driver
       // String parentWindowHandle = driver.getWindowHandle();
        //System.out.println("Parent window's handle -> " + parentWindowHandle);
       // driver.switchTo().newWindow(WindowType.TAB);
/*
        if (InternetLoginPage.URL_INTERNET_LOGIN_PAGE.contains("wwglbn12562"))
        {
            driver.navigate().to("https://wwglbn12562.vcn.ds.volvo.net/www/client-custom/Export.html");
        }
        else
        {
            driver.navigate().to("http://wwglbn12567:81/www/client-custom/Export.html");
        }
*/
        //Thread.sleep(10000);
        //WebElement clickMaximizeElement = driver.findElement(By.xpath("//div[contains(text(),' Download / Upload Progress ')]//parent::div//i[@class='pull-right fa fa-window-maximize showme btns ng-star-inserted']"));
        WebElement clickRefreshElement = driver.findElement(By.xpath("//i[@class='fa fa-refresh']"));

        //this.moveToElement(uploadDownloadHeader);
        //obj.clickUsingJS(clickMaximizeElement);
        //System.out.println("clickMaximizeElement clicked");
        this.moveToElement(downloadUploadHeader);
        obj.clickUsingJS(clickRefreshElement);
        System.out.println("clickRefreshElement clicked");
        Thread.sleep(10000);
        Thread.sleep(10000);

        // Multiple click to open multiple window
        for (int i = 0; i < 10; i++)
        {
            Thread.sleep(5000);
            Thread.sleep(5000);
            this.moveToElement(downloadUploadHeader);
            obj.clickUsingJS(clickRefreshElement);
            System.out.println("clickRefreshElement clicked");
        }
        Thread.sleep(10000);
       // Thread.sleep(1000);
        clickOnGoDownloadLinkColumn();
        System.out.println("GO button clicked");
/*
        // Store all the opened window into the list
        // Print each and every Items of the list
        Set<String> lstWindow =driver.getWindowHandles();
        System.out.println("List of Windows : "+lstWindow);
        try
        {
            for (String handle : lstWindow)
            {
                Thread.sleep(5000);
                System.out.println(handle);
                // Driver.Value.SwitchTo().Window(handle);

                //IAlert alert = Driver.Value.SwitchTo().Alert();
                //alert.Accept();
                //alert.Dismiss();
                var msg = driver.switchTo().alert().getText();
                logger.info("Alert message: " + msg);
                //accept alert
                driver.switchTo().alert().accept();

                System.out.println("Alert clicked OK");
                //driver.switchTo().window((driver.getWindowHandles().Last()));
                //driver.switchTo().window(parentWindowHandle);
                driver.close();
                //Driver.Value.SwitchTo().DefaultContent();
                //Driver.Value.SwitchTo().Window(parentWindowHandle);
            }
        }
        catch (Exception e)
        {
            System.out.println("Some Exception happened ");
            //Console.WriteLine(exp.Message);
        }
        //Switch to the parent window
        driver.switchTo().window(parentWindowHandle);
        */

    }
    @Step("ClickOn'Go'Button in DownloadLink Column")
    public void clickOnGoDownloadLinkColumn() throws InterruptedException {
        Thread.sleep(1000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='ag-body-viewport ag-layout-normal ag-row-animation']//span[contains(text(),'Complete')])[1]/parent::enable-codeset-grid-renderer/parent::div/following-sibling::div//input[1]")));
       // goDownloadLink.click();
        logger.info("Click On GO Button");
        //this.moveToElement(uploadDownloadHeader);
        this.clickUsingJS(goDownloadLink);
        Thread.sleep(10000);
       // Thread.sleep(10000);
        //JavascriptExecutor js = (JavascriptExecutor)driver;
        //System.out.println(js.executeScript("browserstack_executor: {\"action\": \"fileExists\"}"));
        System.out.println("File is downloaded successfully");
        //System.out.println(js.executeScript("browserstack_executor: {\"action\": \"getFileProperties\", \"arguments\": {\"fileName\": \"<file name>\"}}"));
    }
    @Step("Select 'ChooseFile' in ImportProductData")
    public void clickChooseFileImportProductData() throws InterruptedException {
        Thread.sleep(10000);
        logger.info("Export Data Header clicked");

        // Store the parent window of the driver
        String parentWindowHandle = driver.getWindowHandle();
        System.out.println("Parent window's handle -> " + parentWindowHandle);
        driver.switchTo().newWindow(WindowType.TAB);

        if (InternetLoginPage.URL_INTERNET_LOGIN_PAGE.contains("wwglbn12562"))
        {
            driver.navigate().to("https://wwglbn12562.vcn.ds.volvo.net/www/client-custom/ProductImport.html");
        }
        else
        {    // path double check
            driver.navigate().to("http://wwglbn12567:81/www/client-custom/ProductImport.html");
        }

        Thread.sleep(10000);
        Thread.sleep(10000);
        WebElement clickElement = driver.findElement(By.xpath("//input[@type='file']"));

        // Multiple click to open multiple window
        for (var i = 0; i < 1; i++)
        {
            //clickElement.click();
            UploadingFileToApplication();
            System.out.println("Button clicked");
            ClickElementJSByXPATH("button[type='button']", "Submit Button click");
            System.out.println("Submit button clicked");
            Thread.sleep(10000);
        }
        // Store all the opened window into the list
        // Print each and every Items of the list
        Set<String> lstWindow =driver.getWindowHandles();
        System.out.println("List of Windows : "+lstWindow);
        try
        {
            for (String handle : lstWindow)
            {
                Thread.sleep(5000);
                System.out.println(handle);
                // Driver.Value.SwitchTo().Window(handle);

                //IAlert alert = Driver.Value.SwitchTo().Alert();
                //alert.Accept();
                //alert.Dismiss();
                var msg = driver.switchTo().alert().getText();
                logger.info("Alert message: " + msg);
                //accept alert
                driver.switchTo().alert().accept();

                System.out.println("Alert clicked OK");
                driver.close();
            }
        }
        catch (Exception e)
        {
            System.out.println("Some Exception happened ");
            //Console.WriteLine(exp.Message);
        }

        //Switch to the parent window
        driver.switchTo().window(parentWindowHandle);
    }
    @Step("UploadingFileToApplication")
    public void UploadingFileToApplication()
    {
        String upLoadFilePath= VerifyZipFolderAndExtractFiles.unZipFilePathReturn;
        // FILE UPLOADING USING SENDKEYS
        WebElement browse = driver.findElement(By.xpath("//input[@type='file']"));
        //click on ‘Choose file’ to upload the desired file
        browse.sendKeys(upLoadFilePath); //Uploading the file using sendKeys
        System.out.println("upLoadFilePath is : "+upLoadFilePath);
        System.out.println("File is Uploaded Successfully");
    }
    @Step("Click Refresh Button in DownloadUploadProgress")
    public void refreshDownloadUploadProgress() throws InterruptedException {
        PageObject obj = new PageObject(driver);
        obj.scrollElementIntoView(downloadUploadHeader);

        WebElement clickRefreshElement = driver.findElement(By.xpath("//i[@class='fa fa-refresh']"));
        this.moveToElement(downloadUploadHeader);
        this.clickUsingJS(clickRefreshElement);
        System.out.println("clickRefreshElement clicked");
        Thread.sleep(10000);
        Thread.sleep(10000);
        try {
            // Multiple click to open multiple window
            for (int i = 0; i < 20; i++) {
                Thread.sleep(10000);
                Thread.sleep(5000);
                this.moveToElement(downloadUploadHeader);
                obj.clickUsingJS(clickRefreshElement);
                System.out.println("clickRefreshElement done");
            }
        }
        catch (Exception e)
        {
            System.out.println("Some Exception happened");
        }
    }

    @Step("getTotalProcessedRecordsUnderDownloadUploadProgress")
    public String getTotalProcessedRecordsUnderDownloadUploadProgress() throws InterruptedException {
            WebElement clickRefreshElement = driver.findElement(By.xpath("//i[@class='fa fa-refresh']"));
            WebElement totalProcessedRecordsElement;
            System.out.println("clickRefreshElement:"+clickRefreshElement);
            String value = null;
            boolean isLoaded = false;
            int i = 5;
            while(isLoaded == false || i > 0) {
                i--;
                Thread.sleep(10000);
                Thread.sleep(10000);
                Thread.sleep(10000);
                this.moveToElement(downloadUploadHeader);
                //this.clickUsingJS(downloadUploadHeader);
                this.clickUsingJS(clickRefreshElement);
                Thread.sleep(10000);
                //Thread.sleep(10000);
                System.out.println("Thread sleep");
                this.moveToElement(downloadUploadHeader);
                System.out.println("moved to header");
                this.clickUsingJS(downloadUploadHeader);
                System.out.println("click header");
                try{
                    totalProcessedRecordsElement = driver.findElement(By.xpath("//div[@class='ag-center-cols-container']//span[contains(text(),'all records passed validation')][1]/../../../div[7]"));
                    this.moveToElement(totalProcessedRecordsElement);
                System.out.println("Move to processed records");
                System.out.println("downloadUploadHeader again Done:"+downloadUploadHeader);
                value = totalProcessedRecordsElement.getText();
                System.out.println("totalProcessedRecordsElement: "+totalProcessedRecordsElement);
                System.out.println(isLoaded);


                        Double.parseDouble(value) ;
                        isLoaded = true;
                        //value = totalProcessedRecordsElement.getAttribute("innerText");
                        //value = totalProcessedRecordsElement.getAttribute("value");
                        System.out.println("Total Processed: " + value);

                }
                catch (Exception e)
                {

                }
            }
            if(isLoaded == false) {
                System.out.println("Some Exception happened-totalProcessedRecordsElement");
            }
        return value;
    }

    @Step("getValidRowsRecordsUnderDownloadUploadProgress")
    public String getValidRowsRecordsUnderDownloadUploadProgress() throws InterruptedException {
        WebElement clickRefreshElement = driver.findElement(By.xpath("//div[contains(text(),' Download / Upload Progress ')]//parent::div//i[@class='pull-right fa fa-refresh showme btns ng-star-inserted']"));
        WebElement validRowsRecordElement = driver.findElement(By.xpath("(//div[@class='ag-body-viewport ag-layout-normal ag-row-animation']//span[contains(text(),'Part Upload Partial')])[1]/parent::common-html-render/parent::div/following-sibling::div[6]"));
        String count=null;
        try {
            for (int i = 0; i < 20; i++) {
                Thread.sleep(5000);
                //Thread.sleep(5000);
                this.moveToElement(downloadUploadHeader);
                this.clickUsingJS(clickRefreshElement);
                count = validRowsRecordElement.getText();
                System.out.println("Valid Rows: " + count);
            }
        }
        catch (Exception e)
        {
            System.out.println("Some Exception happened");
        }
        return count.toString();
    }

    public void exportCostData_RadioButton()throws InterruptedException{
        Thread.sleep(10000);
        Thread.sleep(10000);
        logger.info("ExportDataHeader done");
        String parentWindowHandle = driver.getWindowHandle();
        System.out.println("Parent window's handle -> " + parentWindowHandle);
        driver.switchTo().newWindow(WindowType.TAB);
        if (InternetLoginPage.URL_INTERNET_LOGIN_PAGE.contains("wwglbn12562"))
        {
            driver.navigate().to("https://wwglbn12562.vcn.ds.volvo.net/www/client-custom/Export.html");
        }
        else
        {
            driver.navigate().to("http://wwglbn12567:81/www/client-custom/Export.html");
        }

        Thread.sleep(10000);
        Thread.sleep(10000);
        WebElement clickElement = driver.findElement(By.xpath("//table[@class='fileUpload']//input[@id='DownloadRadioCost']"));
        // Multiple click to open multiple window
        for (var i = 0; i < 1; i++)
        {
            clickElement.click();
            System.out.println("Radio button clicked");
            ClickElementJSByXPATH("button[type='button']", "Submit Button click");
            System.out.println("Submit button clicked");
            Thread.sleep(10000);
        }
        // Store all the opened window into the list
        // Print each and every Items of the list
        Set<String> lstWindow =driver.getWindowHandles();
        System.out.println("List of Windows : "+lstWindow);
        try
        {
            for (String handle : lstWindow)
            {
                Thread.sleep(5000);
                System.out.println(handle);
                // Driver.Value.SwitchTo().Window(handle);

                //IAlert alert = Driver.Value.SwitchTo().Alert();
                //alert.Accept();
                //alert.Dismiss();
                var msg = driver.switchTo().alert().getText();
                logger.info("Alert message: " + msg);
                //accept alert
                driver.switchTo().alert().accept();

                System.out.println("Alert clicked OK");
                //driver.switchTo().window((driver.getWindowHandles().Last()));
                //driver.switchTo().window(parentWindowHandle);
                driver.close();
                //Driver.Value.SwitchTo().DefaultContent();
                //Driver.Value.SwitchTo().Window(parentWindowHandle);
            }
        }
        catch (Exception e)
        {
            System.out.println("Some Exception happened ");
            //Console.WriteLine(exp.Message);
        }
    }
}
