package blog.utils;

public enum Endpoints {
    USERS("/users"),
    POSTS("/posts"),
    POST_ID("/posts/{id}"),
    COMMENTS("/comments"),
    COMMENTS_FOR_POST_ID("/posts/{postId}/comments");

    private String endpoint;

    Endpoints(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }
}
