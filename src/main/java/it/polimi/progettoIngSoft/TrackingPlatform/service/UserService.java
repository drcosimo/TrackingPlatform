package it.polimi.progettoIngSoft.TrackingPlatform.service;

import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.LoginDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.UserDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.User;
import it.polimi.progettoIngSoft.TrackingPlatform.model.Guest;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.TokenRepository;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.UserRepository;
import it.polimi.progettoIngSoft.TrackingPlatform.util.TokenGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.regex.Pattern;

@Service
@Transactional
public class UserService {

    private final String regexPattern = "^(.+)@(\\S+)$";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private TokenGenerator tokenGenerator;

    public UserDto register(UserDto userDto) {
        //check guest register fields not null or invalid
        if(StringUtils.isNoneEmpty(userDto.getEmail(), userDto.getPassword(), userDto.getName(), userDto.getSurname(), userDto.getUsername(), userDto.getSex())
                && userDto.getBirthDate().isBefore(Instant.now().minus(14, ChronoUnit.YEARS)) &&
                Pattern.compile(regexPattern).matcher(userDto.getEmail()).matches() && userDto.getName().length() > 1 && userDto.getSurname().length() > 1
                && userDto.getPassword().length() > 4 && userDto.getUsername().length() > 1 && userDto.getSex().length() > 2) {
            try {
                User user = userRepository.save(
                        new Guest(userDto.getName(), userDto.getSurname(), userDto.getUsername(), userDto.getEmail(), userDto.getPassword(), userDto.getBirthDate(), userDto.getSex())
                );
                LoginDto loginDto = new LoginDto();
                loginDto.setEmail(user.getEmail());
                loginDto.setPassword(user.getPassword());
                return login(loginDto);
            }
            catch (Exception e){
                System.out.println(e);
                return null;
            }
        }
        else return null;
    }

    public UserDto login(LoginDto credentials) {
        User user = userRepository.getByEmailAndPassword(credentials.getEmail(), credentials.getPassword());
        if(user == null) return null;
        else{
            return new UserDto(user.getId(), user.getName(), user.getSurname(), user.getUsername(), user.getEmail(), user.getBirthDate(), user.getSex(), tokenGenerator.getUserToken(user).getToken(), user.isAdmin());
        }
    }


    public UserDto updateUserDetails(UserDto userUpdate) {
        if(StringUtils.isNoneEmpty(userUpdate.getUsername(), userUpdate.getName(), userUpdate.getSex(), userUpdate.getSurname()) && userUpdate.getBirthDate().isAfter(Instant.now().minus(14, ChronoUnit.YEARS)) && userUpdate.getId() != null){
            try {
                User dbUser = userRepository.findById(userUpdate.getId()).get();
                dbUser.setBirthDate(userUpdate.getBirthDate());
                dbUser.setUsername(userUpdate.getUsername());
                dbUser.setName(userUpdate.getName());
                dbUser.setSex(userUpdate.getSex());
                dbUser.setSurname(userUpdate.getSurname());
                dbUser = userRepository.save(dbUser);
                return new UserDto(dbUser);
            }
            catch (Exception e) {
                System.out.println(e);
                return null;
            }
        }
        else return null;
    }
}
