package it.polimi.progettoIngSoft.TrackingPlatform.model.entities;


import org.geolatte.geom.GeometryType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;

@Entity
@TypeDef(name = "point", typeClass = GeometryType.class, defaultForType = Point.class)

public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_place")
    private Long id;

    @Type(type = "point")
    @Column(nullable = false)
    private  Point point;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name="id_track")
    private Track track;


    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
