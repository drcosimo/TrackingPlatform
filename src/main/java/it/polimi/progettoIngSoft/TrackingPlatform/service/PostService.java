package it.polimi.progettoIngSoft.TrackingPlatform.service;

import com.google.common.base.Preconditions;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.UpdatePermissionsDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.post.Activity;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.user.User;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.ActivityRepository;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.PostRepository;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.ProjectRepository;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.TokenRepository;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class PostService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TokenRepository tokenRepository;
    
    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private PostRepository postRepository;


    private final String USER_DOES_NOT_HAVE_PERMISSIONS_TO_ = "user does not have permission to ";

    private final String USER_NOT_FOUND = "user not found";
    private final String THE_ACTIVITY_ID_CANNOT_BE_NULL = "the activity id cannot be null";
    private final String THE_LIST_OF_USERNAMES_IS_EMPTY = "the list of usernames is empty";
    private final String ADD_CREATORS_TO_THIS_ACTIVITY = "add creators to this activity";
    private final String NOT_EVERY_SPECIFIED_USERNAME_EXISTS = "not every specified username exists";
    private final String REMOVE_CREATORS_FROM_THIS_ACTIVITY = "remove creators from this activity";
    private final String ADD_ADMINS_TO_THIS_ACTIVITY = "add admins to this activity";
    private final String REMOVE_ADMINS_FROM_THIS_ACTIVITY = "remove admins from this activity";
    private final String ADD_PARTECIPANTS_TO_THIS_ACTIVITY = "add partecipants to this activity";
    private final String REMOVE_PARTECIPANTS_FROM_THIS_ACTIVITY = "remove partecipants from this activity";




    //HAVE TO FORK ActivityProject AND OTHER POSTS CASES




    public String addCreator(UpdatePermissionsDto request) {
        try {
            Preconditions.checkNotNull(request.getActivityId(), THE_ACTIVITY_ID_CANNOT_BE_NULL);
            Preconditions.checkArgument(!request.getUsernames().isEmpty(), THE_LIST_OF_USERNAMES_IS_EMPTY);
            User user = tokenRepository.getUserByToken(request.getToken());
            Preconditions.checkNotNull(user, USER_NOT_FOUND);
            Activity activity = activityRepository.findById(request.getActivityId()).get();
            //check if the user has permissions to add a creator
            Preconditions.checkArgument(activity.getCreators().contains(user), USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + ADD_CREATORS_TO_THIS_ACTIVITY);
            //the system ignores if the user wants to add a creator that already exists

            //eliminates duplicate strings from the list of usernames
            List<String> usernames = request.getUsernames().stream().distinct().toList();
            //check that every username exists
            Preconditions.checkArgument(userRepository.countByUsernames(usernames).equals(Integer.valueOf(usernames.size())),
                    NOT_EVERY_SPECIFIED_USERNAME_EXISTS);

            List<User> usersToAdd = userRepository.findByUsernameIn(usernames);
            for (User u : usersToAdd) {
                //check if the user is not already a creator
                if (!activity.getCreators().contains(u)) {
                    activity.getCreators().add(u);
                }
            }
            activityRepository.save(activity);
            return "";
        } catch (Exception e) {
            switch (e.getMessage()) {
                case THE_ACTIVITY_ID_CANNOT_BE_NULL: {
                    return THE_ACTIVITY_ID_CANNOT_BE_NULL;
                }
                case USER_NOT_FOUND: {
                    return USER_NOT_FOUND;
                }
                case ADD_CREATORS_TO_THIS_ACTIVITY: {
                    return ADD_CREATORS_TO_THIS_ACTIVITY;
                }
                case THE_LIST_OF_USERNAMES_IS_EMPTY: {
                    return THE_LIST_OF_USERNAMES_IS_EMPTY;
                }
                case NOT_EVERY_SPECIFIED_USERNAME_EXISTS: {
                    return NOT_EVERY_SPECIFIED_USERNAME_EXISTS;
                }
                default:
                    return null;
            }
        }
    }

    public String removeCreator(UpdatePermissionsDto request) {
        try {
            Preconditions.checkNotNull(request.getActivityId(), THE_ACTIVITY_ID_CANNOT_BE_NULL);
            User user = tokenRepository.getUserByToken(request.getToken());
            Preconditions.checkNotNull(user, USER_NOT_FOUND);
            Activity activity = activityRepository.findById(request.getActivityId()).get();
            //check if the user has permissions to add a creator
            Preconditions.checkArgument(activity.getCreators().contains(user), USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + REMOVE_CREATORS_FROM_THIS_ACTIVITY);

            //eliminates duplicate strings from the list of usernames
            List<String> usernames = request.getUsernames().stream().distinct().toList();
            //check that every username exists
            Preconditions.checkArgument(userRepository.countByUsernames(usernames).equals(Integer.valueOf(usernames.size())),
                    NOT_EVERY_SPECIFIED_USERNAME_EXISTS);
            Iterator<User> userIterator = activity.getCreators().iterator();
            //removes all the specified users if they are contained in the creators list
            User userToAnalize;
            while (userIterator.hasNext()) {
                userToAnalize = userIterator.next();
                if (usernames.contains(userToAnalize.getUsername())) {
                    activity.getCreators().remove(userToAnalize);
                }
            }
            //checks that not every creator has been removed
            //else re-add this user as the only creator
            if (activity.getCreators().isEmpty()) {
                activity.getCreators().add(user);
            }
            activityRepository.save(activity);
            return "";
        } catch (Exception e) {
            switch (e.getMessage()) {
                case THE_ACTIVITY_ID_CANNOT_BE_NULL: {
                    return THE_ACTIVITY_ID_CANNOT_BE_NULL;
                }
                case USER_NOT_FOUND: {
                    return USER_NOT_FOUND;
                }
                case USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + REMOVE_CREATORS_FROM_THIS_ACTIVITY: {
                    return USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + REMOVE_CREATORS_FROM_THIS_ACTIVITY;
                }
                default:
                    return null;
            }
        }
    }

    public String addAdmin(UpdatePermissionsDto request) {
        try {
            Preconditions.checkNotNull(request.getActivityId(), THE_ACTIVITY_ID_CANNOT_BE_NULL);
            Preconditions.checkArgument(!request.getUsernames().isEmpty(), THE_LIST_OF_USERNAMES_IS_EMPTY);
            User user = tokenRepository.getUserByToken(request.getToken());
            Preconditions.checkNotNull(user, USER_NOT_FOUND);
            Activity activity = activityRepository.findById(request.getActivityId()).get();
            //check if the user has permissions to add an admin
            Preconditions.checkArgument(activity.getAdmins().contains(user), USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + ADD_ADMINS_TO_THIS_ACTIVITY);
            //the system ignores if the user wants to add an admin that already exists

            //eliminates duplicate strings from the list of usernames
            List<String> usernames = request.getUsernames().stream().distinct().toList();
            //check that every username exists
            Preconditions.checkArgument(userRepository.countByUsernames(usernames).equals(Integer.valueOf(usernames.size())),
                    NOT_EVERY_SPECIFIED_USERNAME_EXISTS);

            List<User> usersToAdd = userRepository.findByUsernameIn(usernames);
            for (User u : usersToAdd) {
                //check if the user is not already a creator
                if (!activity.getAdmins().contains(u)) {
                    activity.getAdmins().add(u);
                }
            }
            activityRepository.save(activity);
            return "";
        } catch (Exception e) {
            switch (e.getMessage()) {
                case THE_ACTIVITY_ID_CANNOT_BE_NULL: {
                    return THE_ACTIVITY_ID_CANNOT_BE_NULL;
                }
                case USER_NOT_FOUND: {
                    return USER_NOT_FOUND;
                }
                case USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + ADD_ADMINS_TO_THIS_ACTIVITY: {
                    return USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + ADD_ADMINS_TO_THIS_ACTIVITY;
                }
                case THE_LIST_OF_USERNAMES_IS_EMPTY: {
                    return THE_LIST_OF_USERNAMES_IS_EMPTY;
                }
                case NOT_EVERY_SPECIFIED_USERNAME_EXISTS: {
                    return NOT_EVERY_SPECIFIED_USERNAME_EXISTS;
                }
                default:
                    return null;
            }
        }
    }

    public String removeAdmin(UpdatePermissionsDto request) {
        try {
            Preconditions.checkNotNull(request.getActivityId(), THE_ACTIVITY_ID_CANNOT_BE_NULL);
            User user = tokenRepository.getUserByToken(request.getToken());
            Preconditions.checkNotNull(user, USER_NOT_FOUND);
            Activity activity = activityRepository.findById(request.getActivityId()).get();
            //check if the user has permissions to remove an admin
            Preconditions.checkArgument(activity.getAdmins().contains(user), USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + REMOVE_ADMINS_FROM_THIS_ACTIVITY);

            //eliminates duplicate strings from the list of usernames
            List<String> usernames = request.getUsernames().stream().distinct().toList();
            //check that every username exists
            Preconditions.checkArgument(userRepository.countByUsernames(usernames).equals(Integer.valueOf(usernames.size())),
                    NOT_EVERY_SPECIFIED_USERNAME_EXISTS);

            Iterator<User> userIterator = activity.getAdmins().iterator();
            //removes all the specified users if they are contained in the admins list
            User userToAnalize;
            while (userIterator.hasNext()) {
                userToAnalize = userIterator.next();
                //cannot remove an admin if he is a creator
                if (usernames.contains(userToAnalize.getUsername()) && !activity.getCreators().contains(userToAnalize)) {
                    activity.getAdmins().remove(userToAnalize);
                }
            }
            //checks that not every admin has been removed
            //else re-add this user as the only admin
            if (activity.getAdmins().isEmpty()) {
                activity.getAdmins().add(user);
            }
            activityRepository.save(activity);
            return "";
        } catch (Exception e) {
            switch (e.getMessage()) {
                case THE_ACTIVITY_ID_CANNOT_BE_NULL: {
                    return THE_ACTIVITY_ID_CANNOT_BE_NULL;
                }
                case USER_NOT_FOUND: {
                    return USER_NOT_FOUND;
                }
                case USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + REMOVE_ADMINS_FROM_THIS_ACTIVITY: {
                    return USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + REMOVE_ADMINS_FROM_THIS_ACTIVITY;
                }
                default:
                    return null;
            }
        }
    }

    public String addPartecipant(UpdatePermissionsDto request) {
        try {
            Preconditions.checkNotNull(request.getActivityId(), THE_ACTIVITY_ID_CANNOT_BE_NULL);
            Preconditions.checkArgument(!request.getUsernames().isEmpty(), THE_LIST_OF_USERNAMES_IS_EMPTY);
            User user = tokenRepository.getUserByToken(request.getToken());
            Preconditions.checkNotNull(user, USER_NOT_FOUND);
            Activity activity = activityRepository.findById(request.getActivityId()).get();
            //check if the user has permissions to add a partecipant
            Preconditions.checkArgument(activity.getAdmins().contains(user), USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + ADD_PARTECIPANTS_TO_THIS_ACTIVITY);

            //eliminates duplicate strings from the list of usernames
            List<String> usernames = request.getUsernames().stream().distinct().toList();
            //check that every username exists
            Preconditions.checkArgument(userRepository.countByUsernames(usernames).equals(Integer.valueOf(usernames.size())),
                    NOT_EVERY_SPECIFIED_USERNAME_EXISTS);

            //the system ignores if the user wants to add a partecipant that already exists
            List<User> usersToAdd = userRepository.findByUsernameIn(usernames);
            for (User u : usersToAdd) {
                //check if the user is not already a creator
                if (!activity.getPartecipants().contains(u)) {
                    activity.getPartecipants().add(u);
                }
            }
            activityRepository.save(activity);
            return "";
        } catch (Exception e) {
            switch (e.getMessage()) {
                case THE_ACTIVITY_ID_CANNOT_BE_NULL: {
                    return THE_ACTIVITY_ID_CANNOT_BE_NULL;
                }
                case USER_NOT_FOUND: {
                    return USER_NOT_FOUND;
                }
                case USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + ADD_PARTECIPANTS_TO_THIS_ACTIVITY: {
                    return USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + ADD_PARTECIPANTS_TO_THIS_ACTIVITY;
                }
                case THE_LIST_OF_USERNAMES_IS_EMPTY: {
                    return THE_LIST_OF_USERNAMES_IS_EMPTY;
                }
                case NOT_EVERY_SPECIFIED_USERNAME_EXISTS: {
                    return NOT_EVERY_SPECIFIED_USERNAME_EXISTS;
                }
                default:
                    return null;
            }
        }
    }

    public String removePartecipant(UpdatePermissionsDto request) {
        try {
            Preconditions.checkNotNull(request.getActivityId(), THE_ACTIVITY_ID_CANNOT_BE_NULL);
            User user = tokenRepository.getUserByToken(request.getToken());
            Preconditions.checkNotNull(user, USER_NOT_FOUND);
            Activity activity = activityRepository.findById(request.getActivityId()).get();
            //check if the user has permissions to remove a partecipant
            Preconditions.checkArgument(activity.getAdmins().contains(user), USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + REMOVE_PARTECIPANTS_FROM_THIS_ACTIVITY);

            //eliminates duplicate strings from the list of usernames
            List<String> usernames = request.getUsernames().stream().distinct().toList();
            //check that every username exists
            Preconditions.checkArgument(userRepository.countByUsernames(usernames).equals(Integer.valueOf(usernames.size())),
                    NOT_EVERY_SPECIFIED_USERNAME_EXISTS);

            Iterator<User> userIterator = activity.getPartecipants().iterator();
            //removes all the specified users if they are contained in the partecipants list
            User userToAnalize;
            while (userIterator.hasNext()) {
                userToAnalize = userIterator.next();
                if (usernames.contains(userToAnalize.getUsername())) {
                    activity.getPartecipants().remove(userToAnalize);
                }
            }
            activityRepository.save(activity);
            return "";
        } catch (Exception e) {
            switch (e.getMessage()) {
                case THE_ACTIVITY_ID_CANNOT_BE_NULL: {
                    return THE_ACTIVITY_ID_CANNOT_BE_NULL;
                }
                case USER_NOT_FOUND: {
                    return USER_NOT_FOUND;
                }
                case USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + REMOVE_PARTECIPANTS_FROM_THIS_ACTIVITY: {
                    return USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + REMOVE_PARTECIPANTS_FROM_THIS_ACTIVITY;
                }
                default:
                    return null;
            }
        }
    }
}
