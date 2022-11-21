package it.polimi.progettoIngSoft.TrackingPlatform.model.entities.comment;

import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.user.User;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import java.time.Instant;
import java.util.List;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comment")
    private Long id;

    @Column(nullable = false)
    private String text;

    //use to order comments in a post in a chronological way
    @Column(nullable = false)
    private Instant creationTime;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User commentCreator;

    @Column(nullable = false)
    private boolean visible = true;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_comment_reply")
    private List<CommentReply> replies = null;

    @PrePersist
    private void preSave() {
        creationTime = Instant.now();
    }

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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public List<CommentReply> getReplies() {
        return replies;
    }

    public void setReplies(List<CommentReply> replies) {
        this.replies = replies;
    }
}
