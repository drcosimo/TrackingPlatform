package it.polimi.progettoIngSoft.TrackingPlatform.controller;

import it.polimi.progettoIngSoft.TrackingPlatform.model.Guest;
import it.polimi.progettoIngSoft.TrackingPlatform.service.GuestService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.time.Instant;

@Controller
public class GuestController {

    @Autowired
    private GuestService guestService;

    public Guest register(Guest newGuest){
        //check guest register fields not null or invalid
        if(StringUtils.isNotEmpty(newGuest.getEmail()) && StringUtils.isNotEmpty(newGuest.getPassword()) && StringUtils.isNotEmpty(newGuest.getName()) &&
                StringUtils.isNotEmpty(newGuest.getSurname()) && StringUtils.isNotEmpty(newGuest.getUsername()) && StringUtils.isNotEmpty(newGuest.getSex()) &&
                newGuest.getBirthDate().isBefore(Instant.now())){
            return guestService.register(newGuest);
        }
        else return null;
    }
}
