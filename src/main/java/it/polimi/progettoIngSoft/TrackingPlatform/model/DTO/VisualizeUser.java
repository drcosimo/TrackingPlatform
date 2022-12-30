package it.polimi.progettoIngSoft.TrackingPlatform.model.DTO;

import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.user.User;

public class VisualizeUser {
    private String name,surname;
    private String username;

    public VisualizeUser(String name, String surname, String username) {
        this.name = name;
        this.surname = surname;
        this.username = username;
    }

    public VisualizeUser(User u){
        this.name = u.getName();
        this.surname = u.getSurname();
        this.username = u.getUsername();
    }

    public VisualizeUser() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
