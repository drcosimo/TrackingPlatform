package it.polimi.progettoIngSoft.TrackingPlatform.model.DTO;

import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.Message;

import java.time.Instant;

public class MessageDto {

    private String content;
    private Instant timestamp;
    private String senderUsername;
    private String receiverUsername;

    public MessageDto() {
    }

    public MessageDto(Message m){
        this.content = m.getMessage();
        this.receiverUsername = m.getReceiver().getUsername();
        this.senderUsername = m.getSender().getUsername();
        this.timestamp = m.getTimestamp();
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

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }
}
