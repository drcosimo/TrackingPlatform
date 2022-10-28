package it.polimi.progettoIngSoft.TrackingPlatform.model;

import javax.persistence.Entity;
import java.time.Instant;

@Entity
public class Guest extends User {

    @Override
    public boolean isAdmin(){
        return false;
    }

    public Guest() {
    }

    public Guest(String name, String surname, String username, String email, String password, Instant birthDate, String sex){
        Guest newGuest = new Guest();
        newGuest.setName(name);
        newGuest.setSurname(surname);
        newGuest.setUsername(username);
        newGuest.setEmail(email);
        newGuest.setPassword(password);
        newGuest.setBirthDate(birthDate);
        newGuest.setSex(sex);
    }
}
