package it.polimi.progettoIngSoft.TrackingPlatform.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.sql.Date;
import java.util.List;

@Entity
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private List<Project> createdProjects = null;

    @ManyToMany(mappedBy = "admins")
    private List<Project> managedProjects = null;

    @ManyToMany(mappedBy = "partecipants")
    private List<Project> partecipatedProjects = null;

    @ManyToMany(mappedBy = "creators")
    private List<Activity> createdActivities = null;

    @ManyToMany(mappedBy = "admins")
    private List<Activity> managedActivities = null;

    @ManyToMany(mappedBy = "partecipants")
    private List<Activity> partecipatedActivities = null;

    @ManyToMany(mappedBy = "users")
    private List<Reaction> reactions;

    @OneToOne(mappedBy = "user")
    private Token token;

    @OneToMany(mappedBy = "commentCreator")
    private List<Comment> comments;

    /*
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
     */
}
