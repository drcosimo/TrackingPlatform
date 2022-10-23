package it.polimi.progettoIngSoft.TrackingPlatform.model;

import javax.persistence.Entity;

@Entity
public class Admin extends GeneralGuest{
    @Override
    public boolean isAdmin(){
        return true;
    }

    public Admin() {
    }
}
