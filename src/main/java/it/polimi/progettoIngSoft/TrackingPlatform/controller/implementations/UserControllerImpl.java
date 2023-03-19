package it.polimi.progettoIngSoft.TrackingPlatform.controller.implementations;

import it.polimi.progettoIngSoft.TrackingPlatform.controller.interfaces.UserController;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.ChangeEmailDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.LoginDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.ResetPasswordDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.UserDto;
import it.polimi.progettoIngSoft.TrackingPlatform.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins="*")
//this layer has token control and body nullity check on every method
public class UserControllerImpl implements UserController {

    @Autowired
    private UserService userService;


    @Override
    public ResponseEntity<UserDto> register (UserDto userDto) {
        //nullity parameter check
        if(userDto != null){
            UserDto returnDto = userService.register(userDto);
            if(returnDto != null) {
                return new ResponseEntity<>(
                        returnDto,
                        HttpStatus.OK);
            }
            else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
    }

    @Override
    public ResponseEntity<UserDto> login (LoginDto credentials){
        if(StringUtils.isNoneEmpty(credentials.getEmail(), credentials.getPassword())) {
            UserDto returnDto = userService.login(credentials);
            if(returnDto != null){
                return new ResponseEntity<>(
                        returnDto,
                        HttpStatus.OK
                );
            }
            else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
    }

    @Override
    public ResponseEntity<Boolean> checkUsernameUnique(String newUsername) {
        if(StringUtils.isNotEmpty(newUsername)) {
            return new ResponseEntity<>(
                    userService.checkUsernameUnique(newUsername),
                    HttpStatus.OK
            );
        }
        else return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
    }

    @Override
    public ResponseEntity<Boolean> checkEmailUnique(String newEmail) {
        if(StringUtils.isNotEmpty(newEmail)) {
            return new ResponseEntity<>(
                    userService.checkEmailUnique(newEmail),
                    HttpStatus.OK
            );
        }
        else return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
    }

    @Override
    public ResponseEntity<UserDto> updateUserDetails (UserDto userUpdate) {
        if(userUpdate != null){
             UserDto updateduser = userService.updateUserDetails(userUpdate);
             if(updateduser != null){
                 return new ResponseEntity<>(
                         updateduser,
                         HttpStatus.OK
                 );
             }
             else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
    }

    @Override
    public ResponseEntity<UserDto> resetPassword (ResetPasswordDto resetPasswordDto) {
        if(resetPasswordDto != null){
            UserDto userDto = userService.resetPassword(resetPasswordDto);
            if(userDto != null){
                return new ResponseEntity<>(
                        userDto,
                        HttpStatus.OK
                );
            }
            else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
    }

    @Override
    public ResponseEntity<UserDto> changeEmail (ChangeEmailDto changeEmailDto) {
        if(changeEmailDto != null){
            UserDto userDto = userService.changeEmail(changeEmailDto);
            if(userDto != null){
                return new ResponseEntity<>(
                        userDto,
                        HttpStatus.OK
                );
            }
            else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
    }

    @Override
    public ResponseEntity<Boolean> unsubscribe(String userToken) {
        if(StringUtils.isNotEmpty(userToken)) {
            Boolean hasBeenCanceled = userService.unsubscribe(userToken);
            if(hasBeenCanceled) {
                return new ResponseEntity<>(
                        Boolean.TRUE,
                        HttpStatus.OK
                );
            }
            else {
                return new ResponseEntity<>(
                        Boolean.FALSE,
                        HttpStatus.BAD_REQUEST
                );
            }
        }
        else {
            return new ResponseEntity<>(
                    Boolean.FALSE,
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @Override
    public ResponseEntity<String> activateAccount(String activationToken) {
        if(StringUtils.isNotEmpty(activationToken)) {
            String response = userService.activateAccount(activationToken);
            if(StringUtils.isNotBlank(response)){
                return new ResponseEntity<>(
                        response,
                        HttpStatus.OK
                );
            }
        }
        return new ResponseEntity<>(
                "bad request",
                HttpStatus.BAD_REQUEST
        );
    }

    @Override
    public ResponseEntity<String> confirmChangeEmail(String changeToken) {
        if(StringUtils.isNotEmpty(changeToken)) {
            String response = userService.confirmChangeEmail(changeToken);
            if(StringUtils.isNotBlank(response)){
                return new ResponseEntity<>(
                        response,
                        HttpStatus.OK
                );
            }
        }
        return new ResponseEntity<>(
                "bad request",
                HttpStatus.BAD_REQUEST
        );
    }
}
