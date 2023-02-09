package it.polimi.progettoIngSoft.TrackingPlatform.model.entities;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_message")
    private Long id;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private Date timestamp;

}
