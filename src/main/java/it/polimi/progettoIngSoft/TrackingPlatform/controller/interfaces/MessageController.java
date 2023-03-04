package it.polimi.progettoIngSoft.TrackingPlatform.controller.interfaces;

import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.ChatDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.GetChatDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.MessageDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.SendMessageDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(path = "/chats", produces="application/json" , consumes="application/json")
public interface MessageController {

    @PostMapping(path="/")
    public ResponseEntity<List<ChatDto>> getChats(@RequestBody GetChatDto user);

    @PostMapping(path="/{username}")
    public ResponseEntity<List<MessageDto>> getMessages(@PathVariable String username, GetChatDto getChatDto);

    @PostMapping(path="/{username}/sendMessage")
    public ResponseEntity<MessageDto> sendMessage(@PathVariable String username, @RequestBody SendMessageDto message);
}
