package it.polimi.progettoIngSoft.TrackingPlatform.service;

import it.polimi.progettoIngSoft.TrackingPlatform.model.Admin;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.GeneralGuestDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.GeneralGuest;
import it.polimi.progettoIngSoft.TrackingPlatform.model.Guest;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.GeneralGuestRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.regex.Pattern;

@Service
public class GuestService {

    private final String regexPattern = "^(.+)@(\\S+)$";

    @Autowired
    private GeneralGuestRepository guestRepository;

    public GeneralGuestDto register(Guest newGuest) {
        //check guest register fields not null or invalid
        if(StringUtils.isNoneEmpty(newGuest.getEmail(), newGuest.getPassword(), newGuest.getName(), newGuest.getSurname(), newGuest.getUsername(), newGuest.getSex())
                && newGuest.getBirthDate().isBefore(Instant.now()) &&
                Pattern.compile(regexPattern).matcher(newGuest.getEmail()).matches() && newGuest.getName().length() > 1 && newGuest.getSurname().length() > 1
                && newGuest.getPassword().length() > 4 && newGuest.getUsername().length() > 1 && newGuest.getSex().length() > 2) {
            GeneralGuest generalGuest = guestRepository.save(newGuest);
            return new GeneralGuestDto(generalGuest.getId(), generalGuest.getName(), generalGuest.getSurname(), generalGuest.getUsername(), generalGuest.getEmail(), generalGuest.getBirthDate(), generalGuest.getSex(), false);
        }
        else return null;
    }

    public GeneralGuestDto login(String email, String password) {
        GeneralGuest generalGuest = guestRepository.getByEmailAndPassword(email, password);
        return new GeneralGuestDto(generalGuest.getId(), generalGuest.getName(), generalGuest.getSurname(), generalGuest.getUsername(), generalGuest.getEmail(), generalGuest.getBirthDate(), generalGuest.getSex(), generalGuest.isAdmin());
    }
}
