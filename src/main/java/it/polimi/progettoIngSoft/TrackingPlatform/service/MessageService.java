package it.polimi.progettoIngSoft.TrackingPlatform.service;

import com.google.common.base.Preconditions;
import com.vaadin.flow.component.html.Pre;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.ChatDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.GetChatDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.MessageDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.SendMessageDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.Message;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.user.User;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.MessageRepository;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.TokenRepository;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@Transactional
public class MessageService {

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    public List<ChatDto> getChats(GetChatDto getChatDto){
        try{
            // get user from token
            User user = tokenRepository.getUserByToken(getChatDto.getToken());
            Preconditions.checkArgument(user != null,"user doesn't exists" );

            // get chats for the specified user
            List<ChatDto> chats =  messageRepository.getChats(user.getId());

            // check nullity for chats
            Preconditions.checkArgument(chats != null, "bad request");

            // return list of chats
            return chats;
        }catch (Exception e){
            return null;
        }
    }

    public List<MessageDto> getMessages(String username, GetChatDto getChatDto){
        try{
            // get user from token
            User user = tokenRepository.getUserByToken(getChatDto.getToken());
             // get other data user
            User otherUser = userRepository.findByUsername(username);
            // check for user nullity
            Preconditions.checkArgument(user != null,"user doesn't exists");

            // get the messages for the specified chat
            List<Message> messages = messageRepository.getMessages(user.getId(), otherUser.getId());

            // check data integrity
            Preconditions.checkArgument(messages != null, "bad request");

            List<MessageDto> returnList = new ArrayList<MessageDto>();

            for (Message message :messages){
                returnList.add(new MessageDto(message));
            }
            return returnList;

        }catch(Exception e){
            return null;
        }
    }

    public MessageDto sendMessage(String username, SendMessageDto sendMessageDto){
        try{
            // get user from token
            User sendUser = tokenRepository.getUserByToken(sendMessageDto.getToken());

            // get receiver user
            User recvUser = userRepository.findByUsername(username);

            // create new message instance
            Message message = new Message();
            message.setTimestamp(Instant.now());
            message.setMessage(sendMessageDto.getContent());
            message.setSender(sendUser);
            message.setReceiver(recvUser);

            // save message
            Message returnEntity = messageRepository.save(message);

            Preconditions.checkArgument(returnEntity != null,"problem in saving message");
            return new MessageDto(message);

        }catch (Exception e){
          e.printStackTrace();
            return null;
        }
    }
}
