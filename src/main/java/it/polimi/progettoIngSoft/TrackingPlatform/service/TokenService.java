package it.polimi.progettoIngSoft.TrackingPlatform.service;

import it.polimi.progettoIngSoft.TrackingPlatform.model.Token;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    @Autowired
    private TokenRepository tokenRepository;

    public boolean isUserEnabled(String token) {
        try {
            Token tokenObj = tokenRepository.findByToken(token);
            return tokenObj.getUser().isActive();
        }
        catch (Exception e) {
            return false;
        }
    }
}
