package com.volvo.project.components.rest.requests;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.ConnectionConfig;
import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.config.SSLConfig;
import com.jayway.restassured.specification.RequestSpecification;
import com.volvo.project.components.rest.Endpoints;
import com.volvo.project.components.rest.models.bookstore.*;
import io.qameta.allure.Step;
import org.apache.http.HttpStatus;

import java.util.List;

public class BookStoreRest {
    private static final String BaseUrl = "https://bookstore.toolsqa.com";
    private String token;

    @Step("Authorize user: {userName}")
    public void authorizeUser(String userName, String password) {
        AuthorizationRequest authorizationRequest = new AuthorizationRequest(userName, password);
        TokenResponse tokenResponse = bookstoreRequest()
                .header("Content-Type", "application/json")
                .body(authorizationRequest)
                .post(Endpoints.authorization)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(TokenResponse.class);

        token = tokenResponse.token;
    }

    @Step("Get available books")
    public List<Book> getAvailableBooks() {
        return bookstoreRequest()
                .get(Endpoints.books)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(BooksResponse.class)
                .books;
    }

    @Step("Add book to users list")
    public void addBookToUsersList(String userID, String bookIsbn) {
        ISBN isbn = new ISBN(bookIsbn);
        AddBooksRequest addBooksRequest = new AddBooksRequest(userID, isbn);
        bookstoreRequestWithAuthorization()
                .header("Content-Type", "application/json")
                .body(addBooksRequest)
                .post(Endpoints.books)
                .then()
                .statusCode(HttpStatus.SC_CREATED);
    }

    @Step("Remove book from users list")
    public void removeBookFromUsersList(String userID, String bookIsbn) {
        RemoveBookRequest removeBookRequest = new RemoveBookRequest(userID, bookIsbn);
        bookstoreRequestWithAuthorization()
                .header("Content-Type", "application/json")
                .body(removeBookRequest)
                .delete(Endpoints.book)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Step("Get users books")
    public List<Book> getUsersBooks(String userID) {
        return bookstoreRequestWithAuthorization()
                .pathParameters("userID", userID)
                .get(Endpoints.userAccount)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(UserAccount.class)
                .books;
    }

    private RequestSpecification bookstoreRequest() {
        return RestAssured.given()
                .config(
                        RestAssuredConfig.config()
                                .connectionConfig(
                                        ConnectionConfig.connectionConfig().closeIdleConnectionsAfterEachResponse()
                                )
                                .sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation())
                )
                .baseUri(BaseUrl);
    }

    private RequestSpecification bookstoreRequestWithAuthorization() {
        return bookstoreRequest()
                .header("Authorization", "Bearer " + token);
    }
}
