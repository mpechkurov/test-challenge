package blog.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import blog.models.Comment;
import blog.models.Post;
import blog.models.User;

import static io.restassured.RestAssured.given;
import static java.util.Objects.requireNonNull;

public class UserUtils extends TestBase {

    public static final String USER_ID_PARAMETER = "userId";
    public static final String POST_ID_PARAMETER = "postId";

    public String getUserIdByName(String userName) {
        List<User> userList = given()
                                  .get(EndPoints.users)
                                  .then()
                                  .extract().body().jsonPath().getList(".", User.class);

        return requireNonNull(userList
                                  .stream()
                                  .filter(x -> x.getUsername().equals(userName))
                                  .findFirst()
                                  .orElse(null))
                   .getId().toString();
    }

    public List<String> getAllPostIdsByUserId(String userId) {
        List<Post> postList = given()
                                  .param(USER_ID_PARAMETER, userId)
                                  .when()
                                  .get(EndPoints.posts)
                                  .then()
                                  .extract().body().jsonPath().getList(".", Post.class);
        return postList.stream().map(post -> Integer.toString(post.getId())).collect(Collectors.toList());
    }

    public List<Comment> getCommentsForPostId(String postId) {
        return given()
                   .pathParam(POST_ID_PARAMETER, postId)
                   .when()
                   .get(EndPoints.commentsForPostId)
                   .then()
                   .extract().body().jsonPath().getList(ROOT_ELEMENT, Comment.class);
    }

    public List<Comment> getCommentsForPosts(List<String> postIds) {
        List<Comment> commentList = new ArrayList<>();
        postIds.stream().map(this::getCommentsForPostId).collect(Collectors.toList()).forEach(commentList::addAll);
        return commentList;
    }
}
