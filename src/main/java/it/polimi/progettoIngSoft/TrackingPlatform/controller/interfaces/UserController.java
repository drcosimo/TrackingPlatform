package it.polimi.progettoIngSoft.TrackingPlatform.controller.interfaces;


import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.ChangeEmailDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.LoginDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.ResetPasswordDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/user")
public interface UserController {

    @PostMapping(path = "/register", produces="application/json" , consumes="application/json")
    public ResponseEntity<UserDto> register (@RequestBody UserDto userDto);

    @PostMapping(path = "/login", produces="application/json" , consumes="application/json")
    public ResponseEntity<UserDto> login (@RequestBody LoginDto credentials);

    @PostMapping(path = "/updateUserDetails", produces="application/json" , consumes="application/json")
    public ResponseEntity<UserDto> updateUserDetails (@RequestBody UserDto userUpdate);

    @PostMapping(path = "/resetPassword", produces="application/json" , consumes="application/json")
    public ResponseEntity<UserDto> resetPassword (@RequestBody ResetPasswordDto resetPasswordDto);

    @PostMapping(path = "/changeEmail", produces="application/json" , consumes="application/json")
    public ResponseEntity<UserDto> changeEmail (@RequestBody ChangeEmailDto changeEmailDto);

    @GetMapping(path = "/unsubscribe/{userToken}", produces="application/json")
    public ResponseEntity<Boolean> unsubscribe (@PathVariable("userToken") String userToken);
}
