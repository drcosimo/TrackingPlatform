package it.polimi.progettoIngSoft.TrackingPlatform.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class SinglePost extends Snapshot{

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    public SinglePost() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
