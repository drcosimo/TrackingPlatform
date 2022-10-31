package it.polimi.progettoIngSoft.TrackingPlatform.util;

import it.polimi.progettoIngSoft.TrackingPlatform.model.Token;
import it.polimi.progettoIngSoft.TrackingPlatform.model.User;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@Transactional
public class TokenGenerator {

    @Autowired
    private TokenRepository tokenRepository;

    public Token getUserToken(User user) {
        Token token = tokenRepository.findByUser(user);
        if (token != null) {
            if (token.getExpiringDate().isBefore(Instant.now())) {
                //if the token is not valid anymore
                //delete current token and create a new one
                token.setToken(generateToken(user.getEmail()));
            }
        }
        else {
            token = new Token();
            token.setUser(user);
            String newToken = generateToken(user.getEmail());
            token.setToken(newToken);
        }
        token.setExpiringDate(Instant.now().plus(2, ChronoUnit.DAYS));
        //update token expiring date
        tokenRepository.save(token);
        return token;
    }

    private String generateToken(String email) {
        String newToken = email + Instant.now().toEpochMilli();
        if(tokenRepository.findByToken(newToken) != null){
            newToken = newToken + Instant.now().toString();
        }
        return newToken;
    }
}
