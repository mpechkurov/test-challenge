package blog.rest.api;

import io.restassured.RestAssured;

public class TestBase {
    public TestBase() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }
}
