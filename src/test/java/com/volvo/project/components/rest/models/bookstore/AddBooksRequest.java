package com.volvo.project.components.rest.models.bookstore;

import java.util.List;

public class AddBooksRequest {
    public String userId;
    public List<ISBN> collectionOfIsbns;

    public AddBooksRequest(String userId, ISBN isbn) {
        this.userId = userId;
        collectionOfIsbns = List.of(isbn);
    }

}
