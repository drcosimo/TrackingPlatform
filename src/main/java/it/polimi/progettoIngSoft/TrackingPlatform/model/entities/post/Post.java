package it.polimi.progettoIngSoft.TrackingPlatform.model.entities.post;

import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.Track;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.comment.Comment;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.image.PostImage;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.reaction.Reaction;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.user.User;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.time.Instant;
import java.util.List;

@Entity
public abstract class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_post")
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
    private boolean visible = true;


    @ManyToMany
    @JoinTable(
            name = "posts_creators",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> creators;

    @ManyToMany
    @JoinTable(
            name = "posts_admins",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> admins;

    @ManyToMany
    @JoinTable(
            name = "posts_partecipants",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> partecipants;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_post")
    private List<PostImage> postImages;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_post")
    private List<Track> tracks;

    @ManyToMany
    @JoinTable(
            name = "posts_reactions",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "reaction_id")
    )
    private List<Reaction> reactions;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    public Post() {
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
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

    public List<User> getPartecipants() {
        return partecipants;
    }

    public void setPartecipants(List<User> partecipants) {
        this.partecipants = partecipants;
    }

    public List<PostImage> getPostImages() {
        return postImages;
    }

    public void setPostImages(List<PostImage> postImages) {
        this.postImages = postImages;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
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
}
