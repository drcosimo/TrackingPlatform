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

    //retrieving of every user in the list if everyone exists
    @Query("select u from User u " +
            "where u.username in :usernames and " +
            "((select count(u1) from User u1 where u1.username in :usernames) = :numberOfUsernames)")
    public List<User> getAllByUsernamesIfExist(List<String> usernames, Integer numberOfUsernames);
}
