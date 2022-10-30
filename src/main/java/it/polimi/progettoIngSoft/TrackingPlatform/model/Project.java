package it.polimi.progettoIngSoft.TrackingPlatform.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.time.Instant;
import java.util.List;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    private Instant beginDate;

    @Column
    private Instant endDate;

    @Column(nullable = false)
    private boolean isPost;

    @OneToMany(mappedBy = "activityProject")
    private List<Activity> activities;

    @OneToMany(mappedBy = "project")
    private List<ProjectImage> images;

    @ManyToMany(mappedBy = "partecipatedProjects")
    private List<User> partecipants;

    @ManyToMany(mappedBy = "createdProjects")
    private List<User> creators;

    @ManyToMany(mappedBy = "managedProjects")
    private List<User> admins;

    public Project() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Instant beginDate) {
        this.beginDate = beginDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public boolean isPost() {
        return isPost;
    }

    public void setPost(boolean post) {
        isPost = post;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public List<User> getPartecipants() {
        return partecipants;
    }

    public void setPartecipants(List<User> partecipants) {
        this.partecipants = partecipants;
    }

    public List<User> getCreators() {
        return creators;
    }

    public void setCreators(List<User> creators) {
        this.creators = creators;
    }

    public List<User> getAdmins() {
        return admins;
    }

    public void setAdmins(List<User> admins) {
        this.admins = admins;
    }

    public List<ProjectImage> getImages() {
        return images;
    }

    public void setImages(List<ProjectImage> images) {
        this.images = images;
    }
}
