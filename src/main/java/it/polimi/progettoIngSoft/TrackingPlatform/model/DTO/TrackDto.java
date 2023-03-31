package it.polimi.progettoIngSoft.TrackingPlatform.model.DTO;

import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.Track;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKTWriter;

import java.io.IOException;
import java.io.StringWriter;

public class TrackDto {

    private Long id;
    private String lineString;

    public TrackDto(){}
    public TrackDto(Track track) {
        this.id = track.getId();
        this.lineString = geometryToWkt((Geometry) track.getTrack());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLineString() {
        return lineString;
    }

    public void setLineString(String lineString) {
        this.lineString = lineString;
    }

    public String geometryToWkt(Geometry geom){
        StringWriter writer = new StringWriter();
        WKTWriter wktWriter = new WKTWriter(2);

        try {
            wktWriter.write(geom,writer);
        }catch (IOException e){
            e.printStackTrace();
        }
        String wkt = writer.toString();
        return wkt;
    }
}
