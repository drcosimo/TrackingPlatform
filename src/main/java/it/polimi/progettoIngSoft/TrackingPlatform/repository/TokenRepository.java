package it.polimi.progettoIngSoft.TrackingPlatform.repository;

import it.polimi.progettoIngSoft.TrackingPlatform.model.Token;
import it.polimi.progettoIngSoft.TrackingPlatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
    public Token findByUser(User user);
    public Token findByToken(String token);
}
