package it.polimi.progettoIngSoft.TrackingPlatform.repository;

import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User getByEmailAndPassword(String email, String password);

    public User findByEmail(String oldEmail);

    @Query("select u from User u where u.username in :usernames")
    public List<User> getAllByUsernames(List<String> usernames);
}
