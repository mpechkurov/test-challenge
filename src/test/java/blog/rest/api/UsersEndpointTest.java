package blog.rest.api;

import java.util.List;

import org.junit.Test;

import blog.models.User;
import blog.utils.TestBase;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;

public class UsersEndpointTest extends TestBase {

    public static final int DEFAULT_USER_AMOUNT = 10;

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
                                  .getList(ROOT_ELEMENT, User.class);
        assertEquals("Endpoint return wrong user amount or user model. ", DEFAULT_USER_AMOUNT, userList.size());
    }
}
