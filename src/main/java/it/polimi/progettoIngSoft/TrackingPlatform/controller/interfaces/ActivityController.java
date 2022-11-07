package it.polimi.progettoIngSoft.TrackingPlatform.controller.interfaces;

import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.ActivityDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.RequestActivityDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/activity", produces="application/json" , consumes="application/json")
public interface ActivityController {

    @PostMapping("/create")
    public ResponseEntity<ActivityDto> createActivity(@RequestBody RequestActivityDto newActivityDto);

    @PostMapping("/update")
    public ResponseEntity<ActivityDto> updateActivityDetails(@RequestBody RequestActivityDto updatedActivity);
}
