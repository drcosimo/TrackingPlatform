package it.polimi.progettoIngSoft.TrackingPlatform.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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

    @ManyToMany
    @JoinTable(
            name = "creators_projects",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private List<Project> createdProjects = null;


    @ManyToMany
    @JoinTable(
            name = "admins_projects",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private List<Project> managedProjects = null;


    @ManyToMany
    @JoinTable(
            name = "partecipants_projects",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private List<Project> partecipatedProjects = null;

    @ManyToMany(mappedBy = "taggedGuests")
    private List<Snapshot> snapshots = null;

    @OneToMany(mappedBy = "user")
    private List<SinglePost> singlePosts;

    @ManyToMany(mappedBy = "users")
    private List<Reaction> reactions;

    @OneToOne(mappedBy = "user")
    private Token token;

    @OneToMany(mappedBy = "commentCreator")
    private List<Comment> comments;

    @OneToMany(mappedBy = "responseCreator")
    private List<ResponseComment> responses;

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

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public List<Project> getCreatedProjects() {
        return createdProjects;
    }

    public void setCreatedProjects(List<Project> createdProjects) {
        this.createdProjects = createdProjects;
    }

    public List<Project> getManagedProjects() {
        return managedProjects;
    }

    public void setManagedProjects(List<Project> managedProjects) {
        this.managedProjects = managedProjects;
    }

    public List<Project> getPartecipatedProjects() {
        return partecipatedProjects;
    }

    public void setPartecipatedProjects(List<Project> partecipatedProjects) {
        this.partecipatedProjects = partecipatedProjects;
    }

    public List<Snapshot> getSnapshots() {
        return snapshots;
    }

    public void setSnapshots(List<Snapshot> snapshots) {
        this.snapshots = snapshots;
    }

    public abstract boolean isAdmin();

    public List<SinglePost> getSinglePosts() {
        return singlePosts;
    }

    public void setSinglePosts(List<SinglePost> singlePosts) {
        this.singlePosts = singlePosts;
    }

    public List<Reaction> getReactions() {
        return reactions;
    }

    public void setReactions(List<Reaction> reactions) {
        this.reactions = reactions;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<ResponseComment> getResponses() {
        return responses;
    }

    public void setResponses(List<ResponseComment> responses) {
        this.responses = responses;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
