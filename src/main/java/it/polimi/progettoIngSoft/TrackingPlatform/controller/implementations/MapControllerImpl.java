package it.polimi.progettoIngSoft.TrackingPlatform.controller.implementations;

import com.google.common.base.Preconditions;
import it.polimi.progettoIngSoft.TrackingPlatform.controller.interfaces.MapController;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.AddMapDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.GetMapDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.TrackDto;
import it.polimi.progettoIngSoft.TrackingPlatform.service.MapService;
import it.polimi.progettoIngSoft.TrackingPlatform.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins="*")
public class MapControllerImpl implements MapController {
    @Autowired
    TokenService tokenService;

    @Autowired
    MapService mapService;

    private final String PRECONDITIONS_FAILED = "preconditions failed";
    private final String RESPONSE_NULL = "response null";

    @Override
    public ResponseEntity<TrackDto> addMapToPost(@RequestBody AddMapDto addMap) {
        try {
            // check user authenticated and body not null
            Preconditions.checkArgument(addMap != null && tokenService.isUserEnabled(addMap.getToken()), PRECONDITIONS_FAILED);

            // execute method service and get response
            TrackDto track = mapService.addTrack(addMap);

            Preconditions.checkArgument(track != null, RESPONSE_NULL);
            return new ResponseEntity<>(track,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return exceptionReturn(e.getMessage());
        }

    }

    @Override
    public ResponseEntity<TrackDto> getMap(@RequestBody GetMapDto getMap){
        try{
            // check user authenticated and body not null
            Preconditions.checkArgument(getMap != null && tokenService.isUserEnabled(getMap.getToken()), PRECONDITIONS_FAILED);

            // execute method service and get response
            TrackDto track = mapService.getTrack(getMap);

            Preconditions.checkArgument(track != null, RESPONSE_NULL);
            return new ResponseEntity<>(track,HttpStatus.OK);

        }catch(Exception e){
            e.printStackTrace();
            return exceptionReturn(e.getMessage());
        }
    }
    private ResponseEntity exceptionReturn(String errorMessage) {
        switch (errorMessage) {
            case PRECONDITIONS_FAILED : {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
            case RESPONSE_NULL : {
                return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
            }
            default: return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}

