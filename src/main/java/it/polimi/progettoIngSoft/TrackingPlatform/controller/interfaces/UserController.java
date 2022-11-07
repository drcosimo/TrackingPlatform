package it.polimi.progettoIngSoft.TrackingPlatform.controller.interfaces;


import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.ChangeEmailDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.LoginDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.ResetPasswordDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/user", produces="application/json" , consumes="application/json")
public interface UserController {

    @PostMapping("/register")
    public ResponseEntity<UserDto> register (@RequestBody UserDto userDto);

    @PostMapping("/login")
    public ResponseEntity<UserDto> login (@RequestBody LoginDto credentials);

    @PostMapping("/updateUserDetails")
    public ResponseEntity<UserDto> updateUserDetails (@RequestBody UserDto userUpdate);

    @PostMapping("/resetPassword")
    public ResponseEntity<UserDto> resetPassword (@RequestBody ResetPasswordDto resetPasswordDto);

    @PostMapping("/changeEmail")
    public ResponseEntity<UserDto> changeEmail (@RequestBody ChangeEmailDto changeEmailDto);
}
