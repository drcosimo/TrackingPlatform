package it.polimi.progettoIngSoft.TrackingPlatform.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.Instant;
import java.util.List;

@Entity
public class Activity {
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

    @ManyToOne
    @JoinColumn(name = "activityProject")
    private Project activityProject;

    @OneToMany(mappedBy = "activity")
    private List<ActivityPost> activityPosts = null;

    public Activity(String name, String description, Instant beginDate, Instant endDate) {
        this.name = name;
        this.description = description;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public Activity() {
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

    public Project getActivityProject() {
        return activityProject;
    }

    public void setActivityProject(Project activityProject) {
        this.activityProject = activityProject;
    }

    public List<ActivityPost> getActivityPosts() {
        return activityPosts;
    }

    public void setActivityPosts(List<ActivityPost> activityPosts) {
        this.activityPosts = activityPosts;
    }
}
