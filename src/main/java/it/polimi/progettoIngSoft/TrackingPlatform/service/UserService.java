package it.polimi.progettoIngSoft.TrackingPlatform.service;

import com.google.common.base.Preconditions;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.ChangeEmailDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.LoginDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.ResetPasswordDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.UserDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.Token;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.user.ActivationElement;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.user.User;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.user.Guest;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.ActivationElementRepository;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.TokenRepository;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.UserRepository;
import it.polimi.progettoIngSoft.TrackingPlatform.util.TokenGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    private ActivationElementRepository activationRepository;

    @Autowired
    private TokenGenerator tokenGenerator;

    @Autowired
    private  TokenService tokenService;

    public UserDto register(UserDto userDto) {
        //check guest register fields not null or invalid
        if (StringUtils.isNoneEmpty(userDto.getEmail(), userDto.getPassword(), userDto.getName(), userDto.getSurname(), userDto.getUsername(), userDto.getSex())
                && userDto.getBirthDate().toLocalDate().isBefore(LocalDate.now().minus(14, ChronoUnit.YEARS)) &&
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
                e.printStackTrace();
                return null;
            }
        }
        else {
            String error = "error creating new guest : \n";
            if (StringUtils.isAnyEmpty(userDto.getEmail(), userDto.getPassword(), userDto.getName(), userDto.getSurname(), userDto.getUsername(), userDto.getSex())) {
                error += "something between email, pass, name, surname, username, sex is missing \n";
            }
            else{
                if (userDto.getBirthDate().toLocalDate().isAfter(LocalDate.now().minus(14, ChronoUnit.YEARS))) {
                    error += "you are not old enough to access the website \n";
                }
                if (!Pattern.compile(regexPattern).matcher(userDto.getEmail()).matches()) {
                    error += "email not valid \n";
                }
                if (userDto.getName().length() < 2) {
                    error += "name length not valid \n";
                }
                if (userDto.getSurname().length() < 2) {
                    error += "surname length not valid \n";
                }
                if (userDto.getPassword().length() < 5) {
                    error += "password length not valid \n";
                }
                if (userDto.getUsername().length() < 2) {
                    error += "username length not valid \n";
                }
                if (userDto.getSex().length() < 3) {
                    error += "sex length not valid \n";
                }
            }
            UserDto userError = new UserDto();
            userError.setError(error);
            return userError;
        }
    }

    public UserDto login(LoginDto credentials) {
        User user = userRepository.getByEmailAndPassword(credentials.getEmail(), credentials.getPassword());
        Token token = tokenRepository.findByUser(user);
        if (user == null || (token != null && !tokenService.isUserEnabled(token.getToken()))) {
            return null;
        }
        else {
            return new UserDto(user.getId(), user.getName(), user.getSurname(), user.getUsername(), user.getEmail(), user.getBirthDate(), user.getSex(), tokenGenerator.getUserToken(user).getToken(), user.isAdmin());
        }
    }


    public UserDto updateUserDetails(UserDto userUpdate) {
        try {
            Preconditions.checkArgument(tokenService.isUserEnabled(userUpdate.getToken()) && StringUtils.isNoneEmpty(userUpdate.getUsername(), userUpdate.getName(), userUpdate.getSex(), userUpdate.getSurname()) && userUpdate.getBirthDate().toLocalDate().isBefore(LocalDate.now().minus(14, ChronoUnit.YEARS)));
            User dbUser = tokenRepository.findByToken(userUpdate.getToken()).getUser();
            dbUser.setBirthDate(userUpdate.getBirthDate());
            dbUser.setUsername(userUpdate.getUsername());
            dbUser.setName(userUpdate.getName());
            dbUser.setSex(userUpdate.getSex());
            dbUser.setSurname(userUpdate.getSurname());
            dbUser = userRepository.save(dbUser);
            return new UserDto(dbUser);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public UserDto resetPassword(ResetPasswordDto resetPasswordDto) {
        try {
            Preconditions.checkArgument(tokenService.isUserEnabled(resetPasswordDto.getToken()) && StringUtils.isNoneEmpty(resetPasswordDto.getNewPassword(), resetPasswordDto.getOldPassword(), resetPasswordDto.getToken()));
            User user = tokenRepository.findByToken(resetPasswordDto.getToken()).getUser();
            //check if the old password is valid
            Preconditions.checkArgument(user != null && resetPasswordDto.getOldPassword().equals(user.getPassword()) && resetPasswordDto.getNewPassword().length() > 4);
            user.setPassword(resetPasswordDto.getNewPassword());
            userRepository.save(user);
            return new UserDto(user);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public synchronized UserDto changeEmail(ChangeEmailDto changeEmailDto) {
        try {
            Preconditions.checkArgument(tokenService.isUserEnabled(changeEmailDto.getToken()) && StringUtils.isNoneEmpty(changeEmailDto.getNewEmail(), changeEmailDto.getOldEmail(), changeEmailDto.getToken()));
            User user = tokenRepository.findByToken(changeEmailDto.getToken()).getUser();
            //check that the new email is not used in another account
            Preconditions.checkArgument(user != null && checkEmailUnique(changeEmailDto.getNewEmail()) == null && user.getEmail().equals(changeEmailDto.getOldEmail()) && Pattern.compile(regexPattern).matcher(changeEmailDto.getNewEmail()).matches());
            user.setEmail(changeEmailDto.getNewEmail());
            userRepository.save(user);
            return new UserDto(user);

        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public synchronized Boolean unsubscribe(String userToken) {
        try {
            Preconditions.checkArgument(tokenService.isUserEnabled(userToken));
            User user = tokenRepository.getUserByToken(userToken);
            user.setActive(false);
            userRepository.save(user);
            return Boolean.TRUE;
        }
        catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    public Boolean checkUsernameUnique(String newUsername) {
        try {
            User u = userRepository.findFirstByUsername(newUsername);
            return u == null || StringUtils.isEmpty(u.getUsername());
        }
        catch (Exception e){
            e.printStackTrace();
            return Boolean.TRUE;
        }
    }

    public Boolean checkEmailUnique(String newEmail) {
        try {
            User u = userRepository.findFirstByEmail(newEmail);
            return u == null || StringUtils.isEmpty(u.getEmail());
        }
        catch (Exception e){
            e.printStackTrace();
            return Boolean.TRUE;
        }
    }

    public synchronized String activateAccount(String activationToken) {
        try {
            Preconditions.checkArgument(StringUtils.isNotEmpty(activationToken));
            ActivationElement actElem = activationRepository.findFirstByActivationTokenAndAlreadyUsed(activationToken, Boolean.FALSE);
            Preconditions.checkArgument(actElem != null && actElem.getActivationToken().equals(activationToken));
            actElem.getUser().setActive(true);
            actElem.setAlreadyUsed(true);
            userRepository.save(actElem.getUser());
            activationRepository.save(actElem);
            return "account activated with success";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public synchronized String confirmChangeEmail(String changeToken) {
        try {
            Preconditions.checkArgument(StringUtils.isNotEmpty(changeToken));
            ActivationElement actElem = activationRepository.findFirstByActivationTokenAndAlreadyUsed(changeToken, Boolean.FALSE);
            Preconditions.checkArgument(actElem != null && actElem.getActivationToken().equals(changeToken));
            //check that the new email doesn't belong to another account
            User alreadyWithNewEmail = userRepository.findFirstByEmail(actElem.getNewEmail());
            Preconditions.checkArgument(alreadyWithNewEmail == null || alreadyWithNewEmail.getEmail().isEmpty(), "this email already belongs to another account");
            actElem.getUser().setEmail(actElem.getNewEmail());
            actElem.setAlreadyUsed(true);
            userRepository.save(actElem.getUser());
            activationRepository.save(actElem);
            return "email changed with success";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
