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
    private List<Activity> activities = null;

    @OneToMany(mappedBy = "snapshotProject")
    private List<Snapshot> snapshots = null;

    @ManyToMany(mappedBy = "partecipatedProjects")
    private List<Guest> partecipants = null;

    @ManyToMany(mappedBy = "createdProjects")
    private List<Guest> creators = null;

    @ManyToMany(mappedBy = "managedProjects")
    private List<Guest> admins = null;

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

    public List<Snapshot> getSnapshots() {
        return snapshots;
    }

    public void setSnapshots(List<Snapshot> snapshots) {
        this.snapshots = snapshots;
    }

    public List<Guest> getPartecipants() {
        return partecipants;
    }

    public void setPartecipants(List<Guest> partecipants) {
        this.partecipants = partecipants;
    }

    public List<Guest> getCreators() {
        return creators;
    }

    public void setCreators(List<Guest> creators) {
        this.creators = creators;
    }

    public List<Guest> getAdmins() {
        return admins;
    }

    public void setAdmins(List<Guest> admins) {
        this.admins = admins;
    }

}
