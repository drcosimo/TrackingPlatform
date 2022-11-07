package it.polimi.progettoIngSoft.TrackingPlatform.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.Instant;

@Entity
public class ResponseComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    //use to order comments in a post in a chronological way
    @Column(nullable = false)
    private Instant creationTime;

    @ManyToOne
    @JoinColumn(name = "responseCreator")
    private User responseCreator;

    @ManyToOne
    @JoinColumn(name = "mainComment")
    private Comment mainComment;

    public ResponseComment() {
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

    public User getResponseCreator() {
        return responseCreator;
    }

    public void setResponseCreator(User responseCreator) {
        this.responseCreator = responseCreator;
    }

    public Comment getMainComment() {
        return mainComment;
    }

    public void setMainComment(Comment mainComment) {
        this.mainComment = mainComment;
    }
}
