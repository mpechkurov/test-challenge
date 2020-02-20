package blog.rest.api;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import blog.models.Post;
import blog.utils.UserUtils;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;

public class PostsEndpointTest extends TestBase {

    private UserUtils userUtils;

    @Before
    public void setUp() {
        userUtils = new UserUtils();
    }

    @Test
    public void getAllPostsValidation() {
        given()
            .when()
            .get("/posts")
            .then()
            .assertThat().statusCode(SC_OK).contentType(ContentType.JSON)
            .extract().body().jsonPath().getList(".", Post.class);
    }

    @Test
    public void getAllPostsWrittenByUser() {
        String userId = userUtils.getUserIdByName("Samantha");
        List<Post> postList = given()
                                  .param("userId", userId)
                                  .when()
                                  .get("/posts")
                                  .then()
                                  .assertThat().statusCode(SC_OK).contentType(ContentType.JSON)
                                  .extract().body().jsonPath().getList(".", Post.class);
        postList.forEach(x -> assertEquals(3, (int) x.getUserId()));
    }
}
