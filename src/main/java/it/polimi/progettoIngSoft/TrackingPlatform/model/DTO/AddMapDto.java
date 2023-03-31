package it.polimi.progettoIngSoft.TrackingPlatform.model.DTO;

public class AddMapDto {

    private String token;

    private String lineStringText;

    private Long postId;

    public AddMapDto() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLineStringText() {
        return lineStringText;
    }

    public void setLineStringText(String lineStringText) {
        this.lineStringText = lineStringText;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
