package blog.utils;

import java.util.List;

import blog.models.User;
import blog.rest.api.TestBase;

import static io.restassured.RestAssured.given;
import static java.util.Objects.requireNonNull;

public class UserUtils extends TestBase {

    public String getUserIdByName(String userName) {
        List<User> userList = given()
                                  .get("/users")
                                  .then()
                                  .extract().body().jsonPath().getList(".", User.class);

        return requireNonNull(userList
                                  .stream()
                                  .filter(x -> x.getUsername().equals(userName))
                                  .findFirst()
                                  .orElse(null))
                   .getId().toString();
    }
}
