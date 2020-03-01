package blog.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Comment {
    private Integer postId;
    private Integer id;
    private String name;
    private String email;
    private String body;

}
