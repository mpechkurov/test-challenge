package blog.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import blog.models.Comment;

import static io.restassured.RestAssured.given;

public class CommentsUtils extends TestBase {

    public static final String POST_ID_PARAMETER = "postId";

    public List<Comment> getCommentsForPostId(String postId) {
        return given()
                   .pathParam(POST_ID_PARAMETER, postId)
                   .when()
                   .get(Endpoints.COMMENTS_FOR_POST_ID.getEndpoint())
                   .then()
                   .extract().body().jsonPath().getList(ROOT_ELEMENT, Comment.class);
    }

    public List<Comment> getCommentsForPosts(List<String> postIds) {
        List<Comment> commentList = new ArrayList<>();
        postIds.stream().map(this::getCommentsForPostId).collect(Collectors.toList()).forEach(commentList::addAll);
        return commentList;
    }
}
