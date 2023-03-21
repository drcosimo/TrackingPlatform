package it.polimi.progettoIngSoft.TrackingPlatform.model.DTO;

import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.user.User;

public class VisualizeUser {
    private String username;

    public VisualizeUser(String username) {
        this.username = username;
    }

    public VisualizeUser(User u){
        this.username = u.getUsername();
    }

    public VisualizeUser() {
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
