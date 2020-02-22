package blog.rest.api;

import java.util.List;

import org.junit.Test;

import blog.models.Comment;
import blog.utils.TestBase;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CommentsEndpointTest extends TestBase {

    private static final int POST_ID_NUMBER = 1;
    private static final int EXPECTED_COMMENT_AMOUNT = 5;

    @Test
    public void getAllCommentsValidation() {
        given()
            .when()
            .get("/comments")
            .then()
            .assertThat().statusCode(SC_OK).contentType(ContentType.JSON)
            .extract().body().jsonPath().getList(ROOT_ELEMENT, Comment.class);
    }

    @Test
    public void getAllCommentsForPostId() {
        List<Comment> commentList = given()
                                        .param("postId", POST_ID_NUMBER)
                                        .when()
                                        .get("/comments")
                                        .then()
                                        .assertThat().statusCode(SC_OK).contentType(ContentType.JSON)
                                        .extract().body().jsonPath().getList(ROOT_ELEMENT, Comment.class);
        assertEquals(EXPECTED_COMMENT_AMOUNT, commentList.size());
        commentList.forEach(x -> assertEquals("Wrong postId. ", POST_ID_NUMBER, (int) x.getPostId()));
    }

    @Test
    public void getAllCommentsForPostIdEndpoint() {
        List<Comment> commentList = given()
                                        .pathParam("postId", POST_ID_NUMBER)
                                        .when()
                                        .get("/posts/{postId}/comments")
                                        .then()
                                        .assertThat().statusCode(SC_OK).contentType(ContentType.JSON)
                                        .extract().body().jsonPath().getList(ROOT_ELEMENT, Comment.class);
        assertEquals(EXPECTED_COMMENT_AMOUNT, commentList.size());
        commentList.forEach(x -> assertEquals("Wrong postId. ", POST_ID_NUMBER, (int) x.getPostId()));
    }

    @Test
    public void emailFormatInCommentsValidation() {
        List<Comment> commentList = given()
                                        .when()
                                        .get("/comments")
                                        .then()
                                        .extract().body().jsonPath().getList(ROOT_ELEMENT, Comment.class);
        commentList.forEach(x -> assertTrue("Wrong email format. ", x.getEmail().matches(EMAIL_REGEX)));
    }
}
