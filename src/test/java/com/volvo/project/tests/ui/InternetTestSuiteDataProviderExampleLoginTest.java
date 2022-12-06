package com.volvo.project.tests.ui;

import com.google.gson.JsonObject;
import com.volvo.project.base.WebTestBase;
import com.volvo.project.components.datatdriventesting.AzureDataProviderArguments;
import com.volvo.project.components.datatdriventesting.DataProviderArguments;
import com.volvo.project.components.datatdriventesting.ExcelDataProvider;
import com.volvo.project.components.datatdriventesting.TestDataProvider;
import com.volvo.project.pages.InternetHomePage;
import com.volvo.project.pages.InternetLoginPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Stories;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("epic 1234")
@Stories({@Story("user story 12345")})
public class InternetTestSuiteDataProviderExampleLoginTest extends WebTestBase {

    @ExcelDataProvider(fileName = "SupplierLoginValues.xlsx",tab = "testCase1")
    @Test(groups = {"smoke", "regression"}, dataProvider = "getExcelDataFromFile", dataProviderClass = TestDataProvider.class)
    public void successfulLoginExcelDataProviderTest(String name, String password,String col3) {
        dataProviderTestParameters.set(name + "," + password+", + col3 + ");

        InternetHomePage homePage = new InternetLoginPage(getDriver())
                .open()
                .login(name, password);

        step(
                "Check if user is logged in",
                () -> assertThat(homePage.isLoaded(col3)).isTrue()
        );
    }

    @ExcelDataProvider(fileName = "InternetValues.xlsx", tab = "testCase2")
    @Test(groups = {"smoke", "regression", "passUI"}, dataProvider = "getExcelDataFromFile", dataProviderClass = TestDataProvider.class)
    public void unsuccessfulLoginExcelDataProviderTabTest(String name, String password, String col3, String col4) {
        dataProviderTestParameters.set(name + ", " + password + ", " + col3 + ", " + col4);

        InternetHomePage homePage = new InternetLoginPage(getDriver())
                .open()
                .login(col3, col4);

        step(
                "Check if user is not logged in",
                () -> assertThat(homePage.isLoaded(name)).isFalse()
        );
    }

    @Story("user story 12346")
    @DataProviderArguments(file = "LoginValues.json")
    @Test(groups = {"smoke", "regression", "passUI"}, dataProvider = "getDataFromJsonFile", dataProviderClass = TestDataProvider.class)
    public void unsuccessfulLoginJsonDataProviderTest(JsonObject object) {
        dataProviderTestParameters.set(object.get("Username") + ", " + object.get("Password"));

        InternetHomePage homePage = new InternetLoginPage(getDriver())
                .open()
                .login(object.get("Username").toString(), object.get("Password").toString());

        step(
                "Check if user is not logged in",
                () -> assertThat(homePage.isLoaded(object.get("Username").toString())).isFalse()
        );
    }

    @Test(groups = {"smoke", "regression", "dockerGrid"}, dataProvider = "getSimpleData", dataProviderClass = TestDataProvider.class)
    public void unsuccessfulLoginSimpleDataTest(String name, String password) {
        dataProviderTestParameters.set(name + ", " + password);

        InternetHomePage homePage = new InternetLoginPage(getDriver())
                .open()
                .login(name, password);

        step(
                "Check if user is not logged in",
                () -> assertThat(homePage.isLoaded(name)).isFalse()
        );
    }

    @AzureDataProviderArguments(testCaseId = "116646")
    @Test(groups = {"smoke", "regression", "dockerGrid"}, dataProvider = "getDataFromAzureTestCase", dataProviderClass = TestDataProvider.class)
    public void unsuccessfulLoginAzureIdTest(String name, String password) {
        dataProviderTestParameters.set(name + ", " + password);

        InternetHomePage homePage = new InternetLoginPage(getDriver())
                .open()
                .login(name, password);

        step(
                "Check if user is not logged in",
                () -> assertThat(homePage.isLoaded(name)).isFalse()
        );
    }

    @AzureDataProviderArguments(testCaseId = "161139")
    @Test(groups = {"smoke", "regression", "dockerGrid"}, dataProvider = "getDataFromAzureTestCase", dataProviderClass = TestDataProvider.class)
    public void unsuccessfulLoginAzureDifferentIdTest(String name, String password) {
        dataProviderTestParameters.set(name + ", " + password);

        InternetHomePage homePage = new InternetLoginPage(getDriver())
                .open()
                .login(name, password);

        step(
                "Check if user is not logged in",
                () -> assertThat(homePage.isLoaded(name)).isFalse()
        );
    }

    @DataProviderArguments(file = "loginValues.xml")
    @Test(groups = {"smoke", "regression", "passUI"}, dataProvider = "getDataFromXmlFile", dataProviderClass = TestDataProvider.class)
    public void unsuccessfulLoginXmlDataProviderTest(String name, String password) {
        dataProviderTestParameters.set(name + ", " + password);

        InternetHomePage homePage = new InternetLoginPage(getDriver())
                .open()
                .login(name, password);

        step(
                "Check if user is not logged in",
                () -> assertThat(homePage.isLoaded(name)).isFalse()
        );
    }
}
