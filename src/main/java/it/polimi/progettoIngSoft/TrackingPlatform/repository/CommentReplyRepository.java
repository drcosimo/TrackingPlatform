package it.polimi.progettoIngSoft.TrackingPlatform.repository;

import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.comment.CommentReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentReplyRepository extends JpaRepository<CommentReply, Long> {

    @Query("select r from CommentReply r where r.comment.id= :commentId and r.deleted=false and r.visible=true")
    public List<CommentReply> getRepliesFromCommentId(Long commentId);
}
