package it.polimi.progettoIngSoft.TrackingPlatform.util;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKTReader;

public class MapUtils {

    public Geometry wktToGeometry(String wellKnownText){
        try {
            return new WKTReader().read(wellKnownText);
        } catch (org.locationtech.jts.io.ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
