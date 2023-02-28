package com.volvo.project.tests.ui;

import com.volvo.project.base.WebTestBase;
import com.volvo.project.pages.InternetHomePage;
import com.volvo.project.pages.InternetLoginPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Stories;
import io.qameta.allure.Story;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class  EnvLoginTest extends WebTestBase
{

    @Test(groups = {"smoke", "regression", "Admin"})
    public void devLoginCheck() throws InterruptedException
    {
    InternetLoginPage lp = new InternetLoginPage(getDriver());
    InternetHomePage hp = new InternetHomePage(getDriver());
    lp.open();
    lp.login("system", "system");
    Thread.sleep(10000);
    hp.verifyDevURL();
    Thread.sleep(10000);
}

    @Test(groups = {"smoke", "regression", "Admin"})
    public void qaLoginCheck() throws InterruptedException
    {
        InternetLoginPage lp = new InternetLoginPage(getDriver());
        InternetHomePage hp = new InternetHomePage(getDriver());
        lp.openQA();
        Thread.sleep(10000);
        hp.verifyQAURL();
        logger.info("QA environment is up and running");
    }

    @Test(groups = {"smoke", "regression", "Admin"})
    public void prodLoginCheck() throws InterruptedException
    {
        InternetLoginPage lp = new InternetLoginPage(getDriver());
        InternetHomePage hp = new InternetHomePage(getDriver());
        lp.openPROD();
        Thread.sleep(2000);
        hp.verifyProdURL();
        logger.info("PROD environment is up and running");
    }

}
