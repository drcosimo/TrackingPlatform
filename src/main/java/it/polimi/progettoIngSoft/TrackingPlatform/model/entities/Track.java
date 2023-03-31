package it.polimi.progettoIngSoft.TrackingPlatform.model.entities;

import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.post.Post;
import org.geolatte.geom.GeometryType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import javax.persistence.*;
import java.sql.Time;
import java.util.List;

@Entity
@TypeDef(name = "track", typeClass = GeometryType.class, defaultForType = LineString.class)

public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_track")
    private Long id;

    @Type(type = "track")
    @Column(name = "track", length = 10000)
    private LineString track;


    @OneToMany(mappedBy="track")
    private List<Place> places;

    @ManyToOne
    @JoinColumn(name="id_post")
    private Post post;

    public Track() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LineString getTrack() {
        return track;
    }

    public void setTrack(LineString track) {
        this.track = track;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Geometry wktToGeometry(String wellKnownText){

        try {
            return new WKTReader().read(wellKnownText);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
