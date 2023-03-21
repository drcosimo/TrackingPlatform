package it.polimi.progettoIngSoft.TrackingPlatform.model.DTO;

public class AddCommentReplyDto {

    private String token;

    private Long id_user;
    private Long id_comment;
    private String content;

    public AddCommentReplyDto() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }

    public Long getId_comment() {
        return id_comment;
    }

    public void setId_comment(Long id_comment) {
        this.id_comment = id_comment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
