package it.polimi.progettoIngSoft.TrackingPlatform.controller;

import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.LoginDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.UserDto;
import it.polimi.progettoIngSoft.TrackingPlatform.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto){
        //nullity parameter check
        if(userDto != null){
            UserDto returnDto = userService.register(userDto);
            if(returnDto != null) {
                return new ResponseEntity<>(
                        returnDto,
                        HttpStatus.OK);
            }
            else return ResponseEntity.badRequest().build();
        }
        else return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginDto credentials){
        if(StringUtils.isNoneEmpty(credentials.getEmail(), credentials.getPassword())){
            UserDto returnDto = userService.login(credentials);
            if(returnDto != null){
                return new ResponseEntity<>(
                        returnDto,
                        HttpStatus.OK
                );
            }
            else return ResponseEntity.badRequest().build();
        }
        else return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
    }

    @PostMapping("/updateUserDetails")
    public ResponseEntity<UserDto> updateUserDetails(UserDto userUpdate){
        if(userUpdate != null){
             UserDto updateduser = userService.updateUserDetails(userUpdate);
             if(updateduser != null){
                 return new ResponseEntity<>(
                         updateduser,
                         HttpStatus.OK
                 );
             }
             else return ResponseEntity.badRequest().build();
        }
        else return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
    }
}
