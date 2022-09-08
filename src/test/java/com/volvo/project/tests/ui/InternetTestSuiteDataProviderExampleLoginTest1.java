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
public class InternetTestSuiteDataProviderExampleLoginTest1 extends WebTestBase {

    @ExcelDataProvider(fileName = "InternetLoginValues.xlsx",tab = "testCase1")
    @Test(groups = {"smoke", "regression"}, dataProvider = "getExcelDataFromFile", dataProviderClass = TestDataProvider.class)
    public void loginTo_SPIDR_Application(String name, String password,String col3) {
        dataProviderTestParameters.set(name + "," + password+", + col3 + ");

        InternetHomePage homePage = new InternetLoginPage(getDriver())
                .open()
                .login(name, password);

        step(
                "Check if user is logged in",
                () -> assertThat(homePage.isLoaded(col3)).isTrue()
        );
    }
}
