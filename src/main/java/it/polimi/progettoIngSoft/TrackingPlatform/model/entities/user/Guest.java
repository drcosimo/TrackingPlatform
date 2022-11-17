package it.polimi.progettoIngSoft.TrackingPlatform.model.entities.user;

import javax.persistence.Entity;
import java.sql.Date;

@Entity
public class Guest extends User {

    @Override
    public boolean isAdmin(){
        return false;
    }

    public Guest() {
    }

    public Guest(String name, String surname, String username, String email, String password, Date birthDate, String sex){
        setName(name);
        setSurname(surname);
        setUsername(username);
        setEmail(email);
        setPassword(password);
        setBirthDate(birthDate);
        setSex(sex);
        setActive(true);
    }
}
