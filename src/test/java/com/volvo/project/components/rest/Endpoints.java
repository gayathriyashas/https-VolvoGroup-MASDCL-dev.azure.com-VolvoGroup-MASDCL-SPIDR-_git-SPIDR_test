package com.volvo.project.components.rest;

public class Endpoints {
    //bookstore
    public final static String authorization = "/Account/v1/GenerateToken";
    public final static String books = "/BookStore/v1/Books";
    public final static String book = "/BookStore/v1/Book";
    public final static String userAccount = "/Account/v1/User/{userID}";
}
