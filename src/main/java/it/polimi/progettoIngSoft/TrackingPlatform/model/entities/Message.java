package it.polimi.progettoIngSoft.TrackingPlatform.model.entities;

import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.user.User;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private Date timestamp;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_sender")
    private User sender;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_receiver")
    private User receiver;
}
