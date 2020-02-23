package blog.rest.api;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Before;
import org.junit.Test;

import blog.models.Post;
import blog.utils.EndPoints;
import blog.utils.TestBase;
import blog.utils.UserUtils;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PostsEndpointTest extends TestBase {

    private static final Integer EXPECTED_USER_ID = 3;
    private static final String USER_NAME = "Samantha";
    private static final String POST_ID = "id";
    private static final String POST_USER_ID = "userId";
    private static final String POST_BODY = "body";
    public static final String POST_ID_NUMBER = "1";
    private UserUtils userUtils;
    private Post newPost;

    @Before
    public void setUp() {
        userUtils = new UserUtils();
        newPost = new Post();
        newPost.setTitle("Some title");
        newPost.setUserId(1);
        newPost.setBody("Some text");
    }

    @Test
    public void getAllPostsValidation() {
        given()
            .when()
            .get(EndPoints.posts)
            .then()
            .assertThat().statusCode(SC_OK).contentType(ContentType.JSON)
            .extract().body().jsonPath().getList(ROOT_ELEMENT, Post.class);
    }

    @Test
    public void getAllPostsWrittenByUser() {
        String userId = userUtils.getUserIdByName(USER_NAME);
        List<Post> postList = given()
                                  .param(POST_USER_ID, userId)
                                  .when()
                                  .get(EndPoints.posts)
                                  .then()
                                  .assertThat().statusCode(SC_OK).contentType(ContentType.JSON)
                                  .extract().body().jsonPath().getList(ROOT_ELEMENT, Post.class);
        postList.forEach(x -> assertEquals("Wrong userId. ", EXPECTED_USER_ID, x.getUserId()));
    }

    @Test
    public void postNewPost() {
        Post actualPost = given()
                              .contentType(ContentType.JSON)
                              .body(newPost)
                              .post(EndPoints.posts)
                              .then().assertThat().statusCode(SC_CREATED).contentType(ContentType.JSON)
                              .extract().body().jsonPath().getObject(ROOT_ELEMENT, Post.class);
        assertTrue("Wrong response data. ", EqualsBuilder.reflectionEquals(newPost, actualPost, POST_ID));
    }

    @Test
    public void updatePostById() {
        newPost.setId(1);
        Post actualPost = given()
                              .pathParam(POST_ID, POST_ID_NUMBER)
                              .contentType(ContentType.JSON)
                              .body(newPost)
                              .put(EndPoints.posts_id)
                              .then().assertThat().statusCode(SC_OK).contentType(ContentType.JSON)
                              .extract().body().jsonPath().getObject(ROOT_ELEMENT, Post.class);
        assertTrue("Wrong response data. ", EqualsBuilder.reflectionEquals(newPost, actualPost));
    }

    @Test
    public void updateTitlePostById() {
        newPost.setId(1);
        String body = "{ \"title\": \"Some title\"}";
        Post actualPost = given()
                              .pathParam(POST_ID, POST_ID_NUMBER)
                              .contentType(ContentType.JSON)
                              .body(body)
                              .patch(EndPoints.posts_id)
                              .then().assertThat().statusCode(SC_OK).contentType(ContentType.JSON)
                              .extract().body().jsonPath().getObject(ROOT_ELEMENT, Post.class);
        assertTrue(EqualsBuilder.reflectionEquals(newPost, actualPost, POST_USER_ID, POST_BODY));
    }

    @Test
    public void deletePostById() {
        given()
            .pathParam(POST_ID, POST_ID_NUMBER)
            .contentType(ContentType.JSON)
            .delete(EndPoints.posts_id)
            .then().assertThat().statusCode(SC_OK).contentType(ContentType.JSON);
    }

}
