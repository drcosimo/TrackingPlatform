package it.polimi.progettoIngSoft.TrackingPlatform.model.DTO;

public class GetCommentRepliesDto {

    private String token;

    private Long id_comment;

    public GetCommentRepliesDto() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getId_comment() {
        return id_comment;
    }

    public void setId_comment(Long id_comment) {
        this.id_comment = id_comment;
    }
}
