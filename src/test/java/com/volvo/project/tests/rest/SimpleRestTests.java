package com.volvo.project.tests.rest;

import com.jayway.restassured.RestAssured;
import com.volvo.project.base.TestBase;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.apache.http.HttpStatus;
import org.hamcrest.CoreMatchers;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

@Epic("rest api implementation")
@Story("check basic functionalities of rest")
public class SimpleRestTests extends TestBase {

    @Test(groups = {"rest", "passAPI"})
    public void getRestTest() {
        RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1/";

        given()
                .when()
                .get("/employees")
                .then()
                .assertThat()
                .statusCode(anyOf(
                        is(200),
                        is(429) // This is public API and can be overloaded and return 429 Too many requests
                ));
    }

    @Test(groups = {"rest", "passAPI"})
    public void postRestTest() {
        RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1/";

        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "test");
        requestParams.put("salary", "123");
        requestParams.put("age", "23");

        given()
                .when()
                .body(requestParams.toString())
                .post("/create")
                .then()
                .assertThat()
                .statusCode(anyOf(
                        is(200),
                        is(429) // This is public API and can be overloaded and return 429 Too many requests
                ));
    }

    @Test(groups = "rest")
    public void putRestTest() {
        RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1/";
        int empId = 719;

        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "test1");
        requestParams.put("salary", "123");
        requestParams.put("age", "22003");

        given()
                .when()
                .body(requestParams.toString())
                .put("/update/" + empId)
                .then()
                .assertThat()
                .statusCode(anyOf(
                        is(200),
                        is(429) // This is public API and can be overloaded and return 429 Too many requests
                ));
    }

    @Test(groups = {"rest", "passAPI"})
    public void deleteRestTest() {
        RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1/";
        int empId = 719;

        given()
                .when()
                .delete("/delete/" + empId)
                .then()
                .assertThat()
                .statusCode(anyOf(
                        is(200),
                        is(429) // This is public API and can be overloaded and return 429 Too many requests
                ));
    }

    @Test(groups = {"rest", "passAPI"})
    public void queryParamsRestTest() {
        RestAssured.baseURI = "https://samples.openweathermap.org/data/2.5/";

        given()
                .when()
                .queryParam("q", "London, UK")
                .queryParam("appid", "2b1fd2d7f77ccf1b7de9b441571b39b8")
                .get("/weather")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .assertThat()
                .body("name", CoreMatchers.containsString("London"));
    }

}


