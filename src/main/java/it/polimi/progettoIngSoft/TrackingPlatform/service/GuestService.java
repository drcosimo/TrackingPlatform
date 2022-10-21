package it.polimi.progettoIngSoft.TrackingPlatform.service;

import it.polimi.progettoIngSoft.TrackingPlatform.model.Guest;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class GuestService {

    private final String regexPattern = "^(.+)@(\\S+)$";

    @Autowired
    private GuestRepository guestRepository;

    public Guest register(Guest newGuest) {
        //other fields checks
        if(Pattern.compile(regexPattern).matcher(newGuest.getEmail()).matches() && newGuest.getName().length() > 1 && newGuest.getSurname().length() > 1
            && newGuest.getPassword().length() > 4 && newGuest.getUsername().length() > 1 && newGuest.getSex().length() > 2){
            return guestRepository.save(newGuest);
        }
        else return null;
    }
}
