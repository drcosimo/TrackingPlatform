package it.polimi.progettoIngSoft.TrackingPlatform.model.DTO;

public class RemoveCommentDto {

    private String token;
    private Long idComment;


    public RemoveCommentDto() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getIdComment() {
        return idComment;
    }

    public void setIdComment(Long idComment) {
        this.idComment = idComment;
    }
}
