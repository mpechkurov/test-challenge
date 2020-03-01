package blog.utils;

import java.util.List;
import java.util.stream.Collectors;

import blog.models.Post;

import static io.restassured.RestAssured.given;

public class PostUtils extends TestBase {

    public static final String USER_ID_PARAMETER = "userId";

    public List<String> getAllPostIdsByUserId(String userId) {
        List<Post> postList = given()
                                  .param(USER_ID_PARAMETER, userId)
                                  .when()
                                  .get(Endpoints.POSTS.getEndpoint())
                                  .then()
                                  .extract().body().jsonPath().getList(ROOT_ELEMENT, Post.class);
        return postList.stream().map(post -> Integer.toString(post.getId())).collect(Collectors.toList());
    }
}
