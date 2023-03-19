package it.polimi.progettoIngSoft.TrackingPlatform.model.entities;

import org.locationtech.jts.geom.LineString;

import javax.persistence.*;
import java.sql.Time;
import java.util.List;

@Entity
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_track")
    private Long id;

    @Column(nullable = false)
    private LineString track;

    @OneToMany(mappedBy = "track")
    private List<Place> places;


}
