package it.polimi.progettoIngSoft.TrackingPlatform.controller.implementations;

import com.google.common.base.Preconditions;
import it.polimi.progettoIngSoft.TrackingPlatform.controller.interfaces.ActivityController;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.ActivityDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.ProjectActivitiesRequest;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.RequestActivityDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.UpdatePermissionsDto;
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
//this layer checks token and body nullity
public class ActivityControllerImpl implements ActivityController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private TokenService tokenService;

    private final String PRECONDITIONS_FAILED = "preconditions failed";
    private final String RESPONSE_NULL = "response null"; 
    
    @Override
    public ResponseEntity<ActivityDto> createActivity (RequestActivityDto requestActivityDto) {
        try {
            Preconditions.checkArgument(requestActivityDto != null && tokenService.isUserEnabled(requestActivityDto.getToken()),
                    PRECONDITIONS_FAILED);
            ActivityDto response = activityService.createActivity(requestActivityDto);
            Preconditions.checkNotNull(response, RESPONSE_NULL);
            return new ResponseEntity<>(
                    response,
                    HttpStatus.OK
            );
        }
        catch (Exception e) {
            return exceptionReturn(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<ActivityDto> updateActivityDetails(RequestActivityDto updatedActivity) {
        try {
            Preconditions.checkArgument(updatedActivity != null && tokenService.isUserEnabled(updatedActivity.getToken()),
                    PRECONDITIONS_FAILED);
            ActivityDto response = activityService.updateActivity(updatedActivity);
            Preconditions.checkNotNull(response, RESPONSE_NULL);
            return new ResponseEntity<>(
                    response,
                    HttpStatus.OK
            );

        }
        catch (Exception e) {
            return exceptionReturn(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> deleteActivity(ProjectActivitiesRequest deleteDto) {
        try {
            Preconditions.checkArgument(deleteDto != null && tokenService.isUserEnabled(deleteDto.getToken()));
            String response = activityService.deleteActivity(deleteDto);
            Preconditions.checkNotNull(response, RESPONSE_NULL);
            return new ResponseEntity(
                    response,
                    HttpStatus.OK
            );
        }
        catch (Exception e) {
            return exceptionReturn(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<ActivityDto>> getActivitiesFromProject(ProjectActivitiesRequest projectActivitiesRequest) {
        try {
            Preconditions.checkArgument(projectActivitiesRequest != null && tokenService.isUserEnabled(projectActivitiesRequest.getToken()),
                    PRECONDITIONS_FAILED);
            List<ActivityDto> response = activityService.getActivitiesFromProject(projectActivitiesRequest);
            Preconditions.checkNotNull(response, RESPONSE_NULL);
            if(!response.isEmpty()) {
                return new ResponseEntity<>(
                        response,
                        HttpStatus.OK
                );
            }
            else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return exceptionReturn(e.getMessage());
        }

    }

    @Override
    public ResponseEntity<String> addActivityCreatorPermissions(UpdatePermissionsDto request) {
        try {
            Preconditions.checkArgument(request != null && tokenService.isUserEnabled(request.getToken()),
                    PRECONDITIONS_FAILED);
            String response = activityService.addActivityCreatorPermissions(request);
            Preconditions.checkNotNull(response, RESPONSE_NULL);
            return new ResponseEntity(
                    response,
                    HttpStatus.OK
            );
        }
        catch (Exception e) {
            return exceptionReturn(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> removeActivityCreatorPermissions(UpdatePermissionsDto request) {
        try {
            Preconditions.checkArgument(request != null && tokenService.isUserEnabled(request.getToken()),
                    PRECONDITIONS_FAILED);
            String response = activityService.removeActivityCreatorPermissions(request);
            Preconditions.checkNotNull(response, RESPONSE_NULL);
            return new ResponseEntity(
                    response,
                    HttpStatus.OK
            );
        }
        catch (Exception e) {
            return exceptionReturn(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> addActivityAdminPermissions(UpdatePermissionsDto request) {
        try {
            Preconditions.checkArgument(request != null && tokenService.isUserEnabled(request.getToken()),
                    PRECONDITIONS_FAILED);
            String response = activityService.addActivityAdminPermissions(request);
            Preconditions.checkNotNull(response, RESPONSE_NULL);
            return new ResponseEntity(
                    response,
                    HttpStatus.OK
            );
        }
        catch (Exception e) {
            return exceptionReturn(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> removeActivityAdminPermissions(UpdatePermissionsDto request) {
        try {
            Preconditions.checkArgument(request != null && tokenService.isUserEnabled(request.getToken()),
                    PRECONDITIONS_FAILED);
            String response = activityService.removeActivityAdminPermissions(request);
            Preconditions.checkNotNull(response, RESPONSE_NULL);
            return new ResponseEntity(
                    response,
                    HttpStatus.OK
            );
        }
        catch (Exception e) {
            return exceptionReturn(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> addActivityPartecipants(UpdatePermissionsDto request) {
        try {
            Preconditions.checkArgument(request != null && tokenService.isUserEnabled(request.getToken()),
                    PRECONDITIONS_FAILED);
            String response = activityService.addActivityPartecipants(request);
            Preconditions.checkNotNull(response, RESPONSE_NULL);
            return new ResponseEntity(
                    response,
                    HttpStatus.OK
            );
        }
        catch (Exception e) {
            return exceptionReturn(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> removeActivityPartecipants(UpdatePermissionsDto request) {
        try {
            Preconditions.checkArgument(request != null && tokenService.isUserEnabled(request.getToken()),
                    PRECONDITIONS_FAILED);
            String response = activityService.removeActivityPartecipants(request);
            Preconditions.checkNotNull(response, RESPONSE_NULL);
            return new ResponseEntity(
                    response,
                    HttpStatus.OK
            );
        }
        catch (Exception e) {
            return exceptionReturn(e.getMessage());
        }
    }

    private ResponseEntity exceptionReturn(String errorMessage) {
        switch (errorMessage) {
            case PRECONDITIONS_FAILED : {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            case RESPONSE_NULL : {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            default: return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
