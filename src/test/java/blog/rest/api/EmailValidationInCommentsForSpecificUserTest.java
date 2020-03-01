package blog.rest.api;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import blog.models.Comment;
import blog.utils.CommentsUtils;
import blog.utils.PostUtils;
import blog.utils.TestBase;
import blog.utils.UserUtils;

import static org.junit.Assert.assertTrue;

public class EmailValidationInCommentsForSpecificUserTest extends TestBase {

    public static final String USER_NAME = "Samantha";
    private UserUtils userUtils;
    private PostUtils postUtils;
    private CommentsUtils commentsUtils;

    @Before
    public void setUp() {
        userUtils = new UserUtils();
        postUtils = new PostUtils();
        commentsUtils = new CommentsUtils();
    }

    @Test
    public void validationEmailInCommentsForPostsMadeBySpecificUser() {
        String userId = userUtils.getUserIdByName(USER_NAME);
        List<String> postIdsForUser = postUtils.getAllPostIdsByUserId(userId);
        List<Comment> commentList = commentsUtils.getCommentsForPosts(postIdsForUser);
        commentList.forEach(x -> assertTrue("Wrong email format in comment.", x.getEmail().matches(EMAIL_REGEX)));
    }
}
