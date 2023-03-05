package it.polimi.progettoIngSoft.TrackingPlatform.model.DTO;

public class GetCommentRepliesDto {

    private String token;

    private String id_comment;

    public GetCommentRepliesDto() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId_comment() {
        return id_comment;
    }

    public void setId_comment(String id_comment) {
        this.id_comment = id_comment;
    }
}
