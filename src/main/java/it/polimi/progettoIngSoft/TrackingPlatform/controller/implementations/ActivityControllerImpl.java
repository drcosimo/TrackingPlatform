package it.polimi.progettoIngSoft.TrackingPlatform.controller.implementations;

import it.polimi.progettoIngSoft.TrackingPlatform.controller.interfaces.ActivityController;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.ActivityDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.ProjectActivitiesRequest;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.RequestActivityDto;
import it.polimi.progettoIngSoft.TrackingPlatform.service.ActivityService;
import it.polimi.progettoIngSoft.TrackingPlatform.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins="*")
public class ActivityControllerImpl implements ActivityController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private TokenService tokenService;


    @Override
    public ResponseEntity<ActivityDto> createActivity (RequestActivityDto requestActivityDto) {
        if(requestActivityDto != null && tokenService.isUserEnabled(requestActivityDto.getToken())) {
                ActivityDto response = activityService.createActivity(requestActivityDto);
                if(response != null) {
                    return new ResponseEntity<>(
                            response,
                            HttpStatus.OK
                    );
                }
                else return ResponseEntity.badRequest().build();
        }
        else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<ActivityDto> updateActivityDetails(RequestActivityDto updatedActivity) {
        if(updatedActivity != null && tokenService.isUserEnabled(updatedActivity.getToken())) {
                ActivityDto response = activityService.updateActivity(updatedActivity);
                if(response != null) {
                    return new ResponseEntity<>(
                            response,
                            HttpStatus.OK
                    );
                }
                else return ResponseEntity.badRequest().build();
        }
        else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<List<ActivityDto>> getActivitiesFromProject(ProjectActivitiesRequest projectActivitiesRequest) {
        if(projectActivitiesRequest != null && tokenService.isUserEnabled(projectActivitiesRequest.getToken())) {
                List<ActivityDto> response = activityService.getActivitiesFromProject(projectActivitiesRequest);
                if(response != null && !response.isEmpty()) {
                    return new ResponseEntity<>(
                            response,
                            HttpStatus.OK
                    );
                }
                else return ResponseEntity.badRequest().build();
        }
        else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }
}
