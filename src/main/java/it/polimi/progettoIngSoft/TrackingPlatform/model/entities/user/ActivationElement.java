package it.polimi.progettoIngSoft.TrackingPlatform.model.entities.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ActivationElement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_activation")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    //token to confirm the activation af a new account
    //or to confirm the email change (is called changeToken in this case)
    private String activationToken;

    //new email if the request is to change email
    private String newEmail;

    private boolean alreadyUsed = false;

    public ActivationElement() {
    }

    public ActivationElement(User user, String activationToken, String newEmail) {
        this.user = user;
        this.activationToken = activationToken;
        this.newEmail = newEmail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getActivationToken() {
        return activationToken;
    }

    public void setActivationToken(String activationToken) {
        this.activationToken = activationToken;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public boolean isAlreadyUsed() {
        return alreadyUsed;
    }

    public void setAlreadyUsed(boolean alreadyUsed) {
        this.alreadyUsed = alreadyUsed;
    }
}
