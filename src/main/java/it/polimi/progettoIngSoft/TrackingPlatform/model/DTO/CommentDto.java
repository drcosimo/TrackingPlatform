package it.polimi.progettoIngSoft.TrackingPlatform.model.DTO;

import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.comment.Comment;

import java.time.Instant;

public class CommentDto {

    private Long id;

    private String usernameCreator;

    private String content;

    private Instant timestamp;


    public CommentDto(Comment comment) {
        id = comment.getId();
        usernameCreator = comment.getCommentCreator().getUsername();
        content = comment.getText();
        timestamp = comment.getCreationTime();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsernameCreator() {
        return usernameCreator;
    }

    public void setUsernameCreator(String usernameCreator) {
        this.usernameCreator = usernameCreator;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
