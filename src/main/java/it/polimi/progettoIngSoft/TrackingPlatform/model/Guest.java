package it.polimi.progettoIngSoft.TrackingPlatform.model;

import javax.persistence.Entity;

@Entity
public class Guest extends GeneralGuest{

    @Override
    public boolean isAdmin(){
        return false;
    }

    public Guest() {
    }
}
