package blog.rest.api;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import blog.models.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;

public class UsersEndpointTest {

    public static final int DEFAULT_USER_AMOUNT = 10;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void getUsersValidation() {
        List<User> userList = given()
                                  .when()
                                  .get("/users")
                                  .then()
                                  .assertThat().statusCode(SC_OK).contentType(ContentType.JSON)
                                  .extract()
                                  .body()
                                  .jsonPath()
                                  .getList(".", User.class);
        assertEquals("Endpoint return wrong user amount or user model. ", DEFAULT_USER_AMOUNT, userList.size());
    }
}
