package it.polimi.progettoIngSoft.TrackingPlatform.model.DTO;

import java.time.Instant;

public class CommentReplyDto {

    private String content;

    private Instant timestamp;

    private String usernameCreator;

    public CommentReplyDto() {
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

    public String getUsernameCreator() {
        return usernameCreator;
    }

    public void setUsernameCreator(String usernameCreator) {
        this.usernameCreator = usernameCreator;
    }
}
