package it.polimi.progettoIngSoft.TrackingPlatform.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity
public class Snapshot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String description;

    @Column
    private String image;

    @Column(nullable = false)
    private boolean isPicture;

    @ManyToMany
    @JoinTable(
            name = "hashtag_snapshots",
            joinColumns = @JoinColumn(name = "snapshot_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id")
    )
    private List<Hashtag> hashtags = null;


    @ManyToMany
    @JoinTable(
            name = "guests_snapshots",
            joinColumns = @JoinColumn(name = "snapshot_id"),
            inverseJoinColumns = @JoinColumn(name = "guest_id")
    )
    private List<Guest> taggedGuests = null;

    @ManyToOne
    @JoinColumn(name = "activity")
    private Activity activity = null;

    @ManyToOne
    @JoinColumn(name = "snapshotProject")
    private Project snapshotProject = null;

    public Snapshot() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isPicture() {
        return isPicture;
    }

    public void setPicture(boolean picture) {
        isPicture = picture;
    }

    public List<Hashtag> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<Hashtag> hashtags) {
        this.hashtags = hashtags;
    }

    public List<Guest> getTaggedGuests() {
        return taggedGuests;
    }

    public void setTaggedGuests(List<Guest> taggedGuests) {
        this.taggedGuests = taggedGuests;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Project getSnapshotProject() {
        return snapshotProject;
    }

    public void setSnapshotProject(Project snapshotProject) {
        this.snapshotProject = snapshotProject;
    }
}
