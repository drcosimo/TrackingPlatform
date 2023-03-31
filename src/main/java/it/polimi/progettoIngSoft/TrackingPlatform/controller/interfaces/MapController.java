package it.polimi.progettoIngSoft.TrackingPlatform.controller.interfaces;


import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.AddMapDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.GetMapDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.TrackDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/map", produces="application/json" , consumes="application/json")
public interface MapController {

    @PostMapping(path="/addMap")
    public ResponseEntity<TrackDto> addMapToPost(@RequestBody AddMapDto addMap);

    @PostMapping(path = "/getMap")
    public ResponseEntity<TrackDto> getMap(@RequestBody GetMapDto getMap);

}
