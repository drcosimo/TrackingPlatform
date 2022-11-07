package it.polimi.progettoIngSoft.TrackingPlatform.controller.implementations;

import it.polimi.progettoIngSoft.TrackingPlatform.controller.interfaces.ActivityController;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.ActivityDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.RequestActivityDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.User;
import it.polimi.progettoIngSoft.TrackingPlatform.service.ActivityService;
import it.polimi.progettoIngSoft.TrackingPlatform.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins="*")
public class ActivityControllerImpl implements ActivityController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private TokenService tokenService;


    @Override
    public ResponseEntity<ActivityDto> createActivity (RequestActivityDto requestActivityDto) {
        if(tokenService.isUserEnabled(requestActivityDto.getToken())) {
            if(requestActivityDto != null) {
                ActivityDto response = activityService.createActivity(requestActivityDto);
                if(response != null) {
                    return new ResponseEntity<>(
                            response,
                            HttpStatus.OK
                    );
                }
                else return ResponseEntity.badRequest().build();
            }
            else return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        }
        else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<ActivityDto> updateActivityDetails(RequestActivityDto updatedActivity) {
        if(tokenService.isUserEnabled(updatedActivity.getToken())) {
            if(updatedActivity != null) {
                ActivityDto response = activityService.updateActivity(updatedActivity);
                if(response != null) {
                    return new ResponseEntity<>(
                            response,
                            HttpStatus.OK
                    );
                }
                else return ResponseEntity.badRequest().build();
            }
            else return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        }
        else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
