package it.polimi.progettoIngSoft.TrackingPlatform.repository;

import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.comment.CommentReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentReplyRepository extends JpaRepository<CommentReply, Long> {

}
