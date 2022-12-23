package it.polimi.progettoIngSoft.TrackingPlatform.controller.implementations;

import com.google.common.base.Preconditions;
import it.polimi.progettoIngSoft.TrackingPlatform.controller.interfaces.PostController;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.UpdatePermissionsDto;
import it.polimi.progettoIngSoft.TrackingPlatform.service.PostService;
import it.polimi.progettoIngSoft.TrackingPlatform.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins="*")
public class PostControllerImpl implements PostController{

    @Autowired
    private TokenService tokenService;

    private final String PRECONDITIONS_FAILED = "preconditions failed";
    private final String RESPONSE_NULL = "response null";

    @Autowired
    private PostService postService;

    @Override
    public ResponseEntity<String> addCreator(UpdatePermissionsDto request) {
        try {
            Preconditions.checkArgument(request != null && tokenService.isUserEnabled(request.getToken()),
                    PRECONDITIONS_FAILED);
            String response = postService.addCreator(request);
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
    public ResponseEntity<String> removeCreator(UpdatePermissionsDto request) {
        try {
            Preconditions.checkArgument(request != null && tokenService.isUserEnabled(request.getToken()),
                    PRECONDITIONS_FAILED);
            String response = postService.removeCreator(request);
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
    public ResponseEntity<String> addAdmin(UpdatePermissionsDto request) {
        try {
            Preconditions.checkArgument(request != null && tokenService.isUserEnabled(request.getToken()),
                    PRECONDITIONS_FAILED);
            String response = postService.addAdmin(request);
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
    public ResponseEntity<String> removeAdmin(UpdatePermissionsDto request) {
        try {
            Preconditions.checkArgument(request != null && tokenService.isUserEnabled(request.getToken()),
                    PRECONDITIONS_FAILED);
            String response = postService.removeAdmin(request);
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
    public ResponseEntity<String> addPartecipant(UpdatePermissionsDto request) {
        try {
            Preconditions.checkArgument(request != null && tokenService.isUserEnabled(request.getToken()),
                    PRECONDITIONS_FAILED);
            String response = postService.addPartecipant(request);
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
    public ResponseEntity<String> removePartecipant(UpdatePermissionsDto request) {
        try {
            Preconditions.checkArgument(request != null && tokenService.isUserEnabled(request.getToken()),
                    PRECONDITIONS_FAILED);
            String response = postService.removePartecipant(request);
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
