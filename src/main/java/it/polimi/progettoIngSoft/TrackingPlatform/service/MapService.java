package it.polimi.progettoIngSoft.TrackingPlatform.service;

import com.google.common.base.Preconditions;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.AddMapDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.GetMapDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.TrackDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.Place;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.Track;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.post.Post;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.PostRepository;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.TrackRepository;
import org.locationtech.jts.geom.LineString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MapService {

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    public PostRepository postRepository;


    public TrackDto addTrack(AddMapDto addMap) {
        try {
            // get post reference
            Post post = postRepository.getPostById(addMap.getPostId());
            Preconditions.checkArgument(post != null, "riferimento post inesistente");
            // create Track object
            Track track = new Track();
            track.setPost(post);
            // conversione da stringa a oggetto
            LineString lineString = ((LineString) track.wktToGeometry(addMap.getLineStringText()));

            track.setTrack(lineString);
            track.setPlaces(new ArrayList<Place>());
            // save track
            Track returnTrack = trackRepository.save(track);

            Preconditions.checkArgument(returnTrack != null, "error inserting into db");
            return new TrackDto(returnTrack);
        } catch (Exception e) {
            return null;
        }
    }

    public TrackDto getTrack(GetMapDto getMap){
        try {
            Track track = trackRepository.getTrackById(getMap.getMapId());

            Preconditions.checkArgument(track != null, "error selecting from db");
            return new TrackDto(track);
        } catch (Exception e) {
            return null;
        }

    }
}
