package it.polimi.progettoIngSoft.TrackingPlatform.controller.interfaces;

import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.ActivityDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.ProjectActivitiesRequest;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.RequestActivityDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

//every method with a ResponseEntity<String> return type uses the String to describe the error
@RequestMapping(path = "/activity", produces="application/json" , consumes="application/json")
public interface ActivityController {

    @PostMapping("/create")
    public ResponseEntity<ActivityDto> createActivity(@RequestBody RequestActivityDto newActivityDto);

    @PostMapping("/update")
    public ResponseEntity<ActivityDto> updateActivityDetails(@RequestBody RequestActivityDto updatedActivity);

    @PostMapping("/delete")
    public ResponseEntity<String> deleteActivity(@RequestBody ProjectActivitiesRequest deleteDto);

    @PostMapping("/getProjectActivities/")
    public ResponseEntity<List<ActivityDto>> getActivitiesFromProject(@RequestBody ProjectActivitiesRequest projectActivitiesRequest);

}
