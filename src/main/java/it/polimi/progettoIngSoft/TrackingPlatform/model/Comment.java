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
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    //use to order comments in a post in a chronological way
    @Column(nullable = false)
    private Instant creationTime;

    @ManyToOne
    @JoinColumn(name = "commentCreator")
    private User commentCreator;

    @ManyToOne
    @JoinColumn(name = "post")
    private Snapshot post;

    @OneToMany(mappedBy = "mainComment")
    private List<ResponseComment> responses;

    public Comment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Instant getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Instant creationTime) {
        this.creationTime = creationTime;
    }

    public User getCommentCreator() {
        return commentCreator;
    }

    public void setCommentCreator(User commentCreator) {
        this.commentCreator = commentCreator;
    }

    public Snapshot getPost() {
        return post;
    }

    public void setPost(Snapshot post) {
        this.post = post;
    }

    public List<ResponseComment> getResponses() {
        return responses;
    }

    public void setResponses(List<ResponseComment> responses) {
        this.responses = responses;
    }
}
