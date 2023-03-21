package it.polimi.progettoIngSoft.TrackingPlatform.model.DTO;

public class GetCommentsDto {

    private String token;
    private Long postId;

    public GetCommentsDto() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
