package it.polimi.progettoIngSoft.TrackingPlatform.model.entities.user;

import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.Message;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.post.Post;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.reaction.Reaction;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.Token;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.sql.Date;
import java.util.List;

@Entity
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Date birthDate;

    @Column(nullable = false)
    private String sex;

    @Column(nullable = false)
    private Boolean isActive = true;

    @ManyToMany(mappedBy = "creators")
    private List<Post> createdPosts = null;

    @ManyToMany(mappedBy = "admins")
    private List<Post> managedPosts = null;

    @ManyToMany(mappedBy = "partecipants")
    private List<Post> partecipatedPosts = null;

    @ManyToMany(mappedBy = "users")
    private List<Reaction> reactions;

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    private Token token;

    @OneToMany
    @JoinColumn(name = "id_sender")
    private List<Message> sentMessages;

    @OneToMany
    @JoinColumn(name = "id_receiver")
    private List<Message> receivedMessages;

    public abstract boolean isAdmin();

    @Override
    public boolean equals (Object obj){
        try {
            User toCompare = (User) obj;
            if((this == toCompare) || (this.token == toCompare.getToken()) ||
            (this.email.equals(toCompare.getEmail())) && this.password.equals(toCompare.getPassword())) {
                return true;
            }
            else return false;
        }
        catch (Exception e){
            return false;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public List<Message> getSentMessages() {
        return sentMessages;
    }

    public void setSentMessages(List<Message> sentMessages) {
        this.sentMessages = sentMessages;
    }

    public List<Message> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(List<Message> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    public List<Post> getCreatedPosts() {
        return createdPosts;
    }

    public void setCreatedPosts(List<Post> createdPosts) {
        this.createdPosts = createdPosts;
    }

    public List<Post> getManagedPosts() {
        return managedPosts;
    }

    public void setManagedPosts(List<Post> managedPosts) {
        this.managedPosts = managedPosts;
    }

    public List<Post> getPartecipatedPosts() {
        return partecipatedPosts;
    }

    public void setPartecipatedPosts(List<Post> partecipatedPosts) {
        this.partecipatedPosts = partecipatedPosts;
    }

    public List<Reaction> getReactions() {
        return reactions;
    }

    public void setReactions(List<Reaction> reactions) {
        this.reactions = reactions;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
