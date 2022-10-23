package it.polimi.progettoIngSoft.TrackingPlatform.controller;

import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.GeneralGuestDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.Guest;
import it.polimi.progettoIngSoft.TrackingPlatform.service.GuestService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class GuestController {

    @Autowired
    private GuestService guestService;

    public ResponseEntity<GeneralGuestDto> register(Guest newGuest){
        //nullity parameter check
        if(newGuest != null){
            GeneralGuestDto returnDto = guestService.register(newGuest);
            if(returnDto != null) {
                return new ResponseEntity<>(
                        returnDto,
                        HttpStatus.OK);
            }
            else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
    }

    public ResponseEntity<GeneralGuestDto> login(String email, String password){
        if(StringUtils.isNoneEmpty(email, password)){
            GeneralGuestDto returnDto = guestService.login(email, password);
            if(returnDto != null){
                return new ResponseEntity<>(
                        returnDto,
                        HttpStatus.OK
                );
            }
            else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
    }
}
