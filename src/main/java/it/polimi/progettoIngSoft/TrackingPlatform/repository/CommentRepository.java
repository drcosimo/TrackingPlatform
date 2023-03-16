package it.polimi.progettoIngSoft.TrackingPlatform.repository;

import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query("select c from Comment c where c.post.id = :idPost and c.deleted=false and c.visible=true order by c.creationTime desc")
    public List<Comment> getCommentsByPostId(Long idPost);

}
