package blog.utils;

import io.restassured.RestAssured;

public class TestBase {

    protected static final String ROOT_ELEMENT = ".";
    protected static final String EMAIL_REGEX = "^[\\w-+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";

    public TestBase() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }
}
