package it.polimi.progettoIngSoft.TrackingPlatform.controller.implementations;

import antlr.debug.MessageAdapter;
import com.google.common.base.Preconditions;
import it.polimi.progettoIngSoft.TrackingPlatform.controller.interfaces.MessageController;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.ChatDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.GetChatDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.MessageDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.SendMessageDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.Message;
import it.polimi.progettoIngSoft.TrackingPlatform.service.MessageService;
import it.polimi.progettoIngSoft.TrackingPlatform.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins="*")
public class MessageControllerImpl implements MessageController {


    @Autowired
    private MessageService messageService;
    @Autowired
    private TokenService tokenService;

    private final String PRECONDITIONS_FAILED = "preconditions failed";
    private final String RESPONSE_NULL = "response null";



    @Override
    public ResponseEntity<List<ChatDto>> getChats(@RequestBody GetChatDto getChatDto){
        try {
            // check for user enabled and dto not null
            Preconditions.checkArgument(getChatDto != null && tokenService.isUserEnabled(getChatDto.getToken()), PRECONDITIONS_FAILED);

            // invocate service to get chats
            List<ChatDto> chats = messageService.getChats(getChatDto);

            // check service data and compose return object
            Preconditions.checkArgument(chats != null , RESPONSE_NULL);

            return new ResponseEntity<List<ChatDto>>(chats,HttpStatus.OK);

        }catch (Exception e){
            return exceptionReturn(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<MessageDto>> getMessages(@PathVariable String username,@RequestBody GetChatDto getChatDto){
        try{
            // check for user enabled and username not null
            Preconditions.checkArgument(username != null && username.length() > 1 && getChatDto != null && tokenService.isUserEnabled(getChatDto.getToken()), PRECONDITIONS_FAILED);

            // get list of messages of the specified chat
            List<MessageDto> messages = messageService.getMessages(username,getChatDto);

            return new ResponseEntity<List<MessageDto>>(messages,HttpStatus.OK);
        }catch(Exception e){
            return exceptionReturn(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<MessageDto> sendMessage(@PathVariable String username, @RequestBody SendMessageDto sendMessageDto){
        try {
            // check for user enabled and username not null
            Preconditions.checkArgument(username != null && username.length() > 1 && sendMessageDto != null && tokenService.isUserEnabled(sendMessageDto.getToken()), PRECONDITIONS_FAILED);

            // send message
            MessageDto sentMessage = messageService.sendMessage(username,sendMessageDto);

            Preconditions.checkArgument(sentMessage != null, RESPONSE_NULL);

            return new ResponseEntity<MessageDto>(sentMessage,HttpStatus.OK);
        }catch (Exception e){
            return exceptionReturn(e.getMessage());
        }
    }

    private ResponseEntity exceptionReturn(String errorMessage) {
        switch (errorMessage) {
            case PRECONDITIONS_FAILED : {
                return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
            }
            case RESPONSE_NULL : {
                return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
            }
            default: return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
