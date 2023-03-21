package it.polimi.progettoIngSoft.TrackingPlatform.repository;

import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.post.Post;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p.creators from Post p where p.id = :idPost")
    public List<User> getCreatorsById (Long idPost);

    @Query("select p.admins from Post p where p.id = :idPost")
    public List<User> getAdminsById (Long idPost);

    @Query("select p.partecipants from Post p where p.id = :idPost")
    public List<User> getPartecipantsById (Long idPost);

    @Query("select p from Post p where p.id = :idPost")
    public Post getPostById(Long idPost);

}

