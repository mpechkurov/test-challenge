package blog.rest.api;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;
import static java.util.Objects.requireNonNull;

public class SearchTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void getUserIdByName() {
        String userName = "Samantha";
        List<Map<String, String>> result = given().when().get("/users").then().extract().jsonPath().get("$");

        String userIdByName = String.valueOf(requireNonNull(result
                                                                .stream()
                                                                .filter(x -> x.get("username").equals(userName))
                                                                .findFirst()
                                                                .orElse(null))
                                                 .get("id"));
        Assert.assertEquals("3", userIdByName);
    }
}
