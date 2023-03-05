package it.polimi.progettoIngSoft.TrackingPlatform.model.DTO;

import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.Message;


public class ChatDto {
    private MessageDto lastMessage;

    public ChatDto() {
    }

    public ChatDto(Message lastMessage) {
        this.lastMessage = new MessageDto(lastMessage);
    }

    public MessageDto getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(MessageDto lastMessage) {
        this.lastMessage = lastMessage;
    }
}
