package it.polimi.progettoIngSoft.TrackingPlatform.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public abstract class Snapshot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String description;

    @Column
    private String image;

    @ManyToMany
    @JoinTable(
            name = "hashtag_snapshots",
            joinColumns = @JoinColumn(name = "snapshot_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id")
    )
    private List<Hashtag> hashtags;


    @ManyToMany
    @JoinTable(
            name = "guests_snapshots",
            joinColumns = @JoinColumn(name = "snapshot_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> taggedGuests;

    @ManyToMany
    @JoinTable(
            name = "snapshots_reactions",
            joinColumns = @JoinColumn(name = "snapshot_id"),
            inverseJoinColumns = @JoinColumn(name = "reaction_id")
    )
    private List<Reaction> reactions;

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

    public List<Hashtag> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<Hashtag> hashtags) {
        this.hashtags = hashtags;
    }

    public List<User> getTaggedGuests() {
        return taggedGuests;
    }

    public void setTaggedGuests(List<User> taggedGuests) {
        this.taggedGuests = taggedGuests;
    }

    public List<Reaction> getReactions() {
        return reactions;
    }

    public void setReactions(List<Reaction> reactions) {
        this.reactions = reactions;
    }
}
