package it.polimi.progettoIngSoft.TrackingPlatform.repository;


import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.ChatDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.Message;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.post.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("select distinct new it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.ChatDto(u2.username,m) from User u1 inner join Message m on (u1.id = m.sender.id or u1.id = m.receiver.id) inner join User u2 on (u2.id = m.sender.id or u2.id = m.receiver.id) where (u1.id = :usrId) order by m.timestamp desc")
    public List<ChatDto> getChats(Long usrId);

    @Query("select distinct m from User u1 inner join Message m on (u1.id = m.sender.id or u1.id= m.receiver.id) inner join User u2 on (u2.id=m.sender.id or  u2.id =  m.receiver.id) where (u1.id = :idUser or u1.id = :otherUser)and(u2.id = :idUser or u2.id = :otherUser) order by m.timestamp DESC")
    public List<Message> getMessages(Long idUser, Long otherUser);

}
