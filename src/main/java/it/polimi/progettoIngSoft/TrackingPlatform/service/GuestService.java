package it.polimi.progettoIngSoft.TrackingPlatform.service;

import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.GeneralGuestDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.User;
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
            User user = guestRepository.save(newGuest);
            return new GeneralGuestDto(user.getId(), user.getName(), user.getSurname(), user.getUsername(), user.getEmail(), user.getBirthDate(), user.getSex(), false);
        }
        else return null;
    }

    public GeneralGuestDto login(String email, String password) {
        User user = guestRepository.getByEmailAndPassword(email, password);
        return new GeneralGuestDto(user.getId(), user.getName(), user.getSurname(), user.getUsername(), user.getEmail(), user.getBirthDate(), user.getSex(), user.isAdmin());
    }
}
