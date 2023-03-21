package it.polimi.progettoIngSoft.TrackingPlatform.model.entities.comment;

import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.user.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import java.time.Instant;

@Entity
public class CommentReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comment_reply")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User commentReplyCreator;

    @ManyToOne
    @JoinColumn(name ="id_comment")
    private Comment comment;


    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private Instant creationTime;

    @Column(nullable = false)
    private boolean visible = true;

    @Column(nullable = false)
    private boolean deleted = false;

    @PrePersist
    private void preSave() {
        creationTime = Instant.now();
    }

    public CommentReply() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return commentReplyCreator;
    }

    public void setUser(User commentReplyCreator) {
        this.commentReplyCreator = commentReplyCreator;
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public User getCommentReplyCreator() {
        return commentReplyCreator;
    }

    public void setCommentReplyCreator(User commentReplyCreator) {
        this.commentReplyCreator = commentReplyCreator;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
