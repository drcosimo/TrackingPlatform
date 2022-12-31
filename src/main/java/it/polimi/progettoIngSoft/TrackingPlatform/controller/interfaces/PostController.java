package it.polimi.progettoIngSoft.TrackingPlatform.controller.interfaces;

import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.UpdatePermissionsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

//every method with a ResponseEntity<String> return type uses the String to describe the error
@RequestMapping(path = "/post", produces="application/json" , consumes="application/json")
public interface PostController {
    @PostMapping("/addCreator")
    public ResponseEntity<String> addCreator(@RequestBody UpdatePermissionsDto request);
/*
    @PostMapping("/removeCreator")
    public ResponseEntity<String> removeCreator(@RequestBody UpdatePermissionsDto request);

    @PostMapping("/addAdmin")
    public ResponseEntity<String> addAdmin(@RequestBody UpdatePermissionsDto request);

    @PostMapping("/removeAdmin")
    public ResponseEntity<String> removeAdmin(@RequestBody UpdatePermissionsDto request);

    @PostMapping("/addPartecipant")
    public ResponseEntity<String> addPartecipant(@RequestBody UpdatePermissionsDto request);

    @PostMapping("/removePartecipant")
    public ResponseEntity<String> removePartecipant(@RequestBody UpdatePermissionsDto request);


 */
}
