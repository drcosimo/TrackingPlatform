package it.polimi.progettoIngSoft.TrackingPlatform.model.DTO;

public class RemoveCommentReplyDto {

    private String token;

    private Long id_comment_reply;

    public RemoveCommentReplyDto() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getId_comment_reply() {
        return id_comment_reply;
    }

    public void setId_comment_reply(Long id_comment_reply) {
        this.id_comment_reply = id_comment_reply;
    }
}
