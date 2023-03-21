package it.polimi.progettoIngSoft.TrackingPlatform.model.DTO;

public class AddCommentDto {

    private String token;

    private Long id_post;

    private String content;

    public AddCommentDto() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getId_post() {
        return id_post;
    }

    public void setId_post(Long id_post) {
        this.id_post = id_post;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
