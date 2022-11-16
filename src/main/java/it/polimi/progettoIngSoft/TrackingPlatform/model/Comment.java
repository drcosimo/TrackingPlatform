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

    @OneToMany(mappedBy = "mainComment")
    private List<ResponseComment> responses;

    public Comment() {
    }

}
