package it.polimi.progettoIngSoft.TrackingPlatform.service;

import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.UpdatePermissionsDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.post.Activity;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.ActivityDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.ProjectActivitiesRequest;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.RequestActivityDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.post.ActivityPost;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.post.ActivityProject;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.post.Project;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.user.User;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.ActivityPostRepository;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.ActivityProjectRepository;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.ActivityRepository;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.ProjectRepository;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.TokenRepository;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.UserRepository;
import it.polimi.progettoIngSoft.TrackingPlatform.util.ActivityUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Preconditions;


@Service
@Transactional
public class ActivityService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ActivityProjectRepository activityProjectRepository;

    @Autowired
    private ActivityPostRepository activityPostRepository;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private UserRepository userRepository;
    
    
    private final String USER_DOES_NOT_HAVE_PERMISSIONS_TO_ = "user does not have permission to ";

    private final String ACTIVITY_NAME_CANNOT_BE_EMPTY = "activity name cannot be empty";
    private final String USER_NOT_FOUND = "user not found";
    private final String CREATE_AN_ACTIVITY_IN_THIS_PROJECT= "create an activity in this project";
    private final String THE_NEW_ACTIVITY_IS_IN_CONFLICTS_WITH_ANOTHER_ACTIVITY = "the new activity is in conflict with another activity";
    private final String SELECTED_VEHICLES_ARE_NOT_AVAILABLE = "selected vehicles are not available";
    private final String UPDATE_THIS_ACTIVITY = "update this activity";
    private final String THE_ACTIVITY_IS_NOT_PART_OF_THE_PROJECT = "the activity is not part of the project";
    private final String NEW_ACTIVITY_DATES_ARE_IN_CONFLICT_WITH_OTHER_ACTIVITIES = "new activity dates are in conflict with other activities";
    private final String THE_PROJECT_ID_CANNOT_BE_NULL = "the project id cannot be null";
    private final String THIS_PROJECT_HAS_NO_ACTIVITIES = "this project has no activities";
    private final String THE_ACTIVITY_ID_CANNOT_BE_NULL = "the activity id cannot be null";
    private final String DELETE_THIS_ACTIVITY = "delete this activity";
    private final String THE_LIST_OF_USERNAMES_IS_EMPTY = "the list of usernames is empty";
    private final String ADD_CREATORS_TO_THIS_ACTIVITY = "add creators to this activity";
    private final String NOT_EVERY_SPECIFIED_USERNAME_EXISTS = "not every specified username exists";
    private final String REMOVE_CREATORS_FROM_THIS_ACTIVITY = "remove creators from this activity";
    private final String ADD_ADMINS_TO_THIS_ACTIVITY = "add admins to this activity";
    private final String REMOVE_ADMINS_FROM_THIS_ACTIVITY = "remove admins from this activity";
    private final String ADD_PARTECIPANTS_TO_THIS_ACTIVITY = "add partecipants to this activity";
    private final String REMOVE_PARTECIPANTS_FROM_THIS_ACTIVITY = "remove partecipants from this activity";
    
    
    public ActivityDto createActivity(RequestActivityDto requestActivityDto) {
        try {
            //check the minimum required field to create an activity
            Preconditions.checkArgument(StringUtils.isNotEmpty(requestActivityDto.getName()), ACTIVITY_NAME_CANNOT_BE_EMPTY);
            //
            Preconditions.checkArgument(
                    requestActivityDto.getVehiclesIds() == null || vehicleService.checkVehiclesExistence(requestActivityDto.getVehiclesIds()),
                    SELECTED_VEHICLES_ARE_NOT_AVAILABLE);
            User user = tokenRepository.getUserByToken(requestActivityDto.getToken());
            Preconditions.checkNotNull(user, USER_NOT_FOUND);
            //different cases based on the type of request (if it's an activityProject or an activityPost creation)

            //activityProject case
            if (requestActivityDto.getProjectId() != null && requestActivityDto.getProjectId() > 0) {
                Project project = projectRepository.findById(requestActivityDto.getProjectId()).get();
                //check if the user is an admin of the activity's project
                Preconditions.checkArgument(project.getAdmins().contains(user), USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + CREATE_AN_ACTIVITY_IN_THIS_PROJECT);
                List<ActivityProject> existingActivities = projectRepository.getProjectActivitiesById(requestActivityDto.getProjectId());
                Preconditions.checkArgument(ActivityUtil.isNotInConflict(requestActivityDto, existingActivities), THE_NEW_ACTIVITY_IS_IN_CONFLICTS_WITH_ANOTHER_ACTIVITY);

                ActivityProject activity = activityProjectRepository.save(new ActivityProject(requestActivityDto.getName(), requestActivityDto.getDescription(), requestActivityDto.getBeginDate(), requestActivityDto.getEndDate(), vehicleService.getAllByIds(requestActivityDto.getVehiclesIds()), user));
                project.getActivities().add(activity);
                projectRepository.save(project);
                return new ActivityDto(activity);

            }
            //activityPost case
            else if (requestActivityDto.getProjectId() == null) {
                ActivityPost activity = activityPostRepository.save(new ActivityPost(requestActivityDto.getName(), requestActivityDto.getDescription(), requestActivityDto.getBeginDate(), requestActivityDto.getEndDate(), vehicleService.getAllByIds(requestActivityDto.getVehiclesIds()), user));
                return new ActivityDto(activity);
            } else {
                return null;
            }

        } catch (Exception e) {
            switch (e.getMessage()) {
                case ACTIVITY_NAME_CANNOT_BE_EMPTY: {
                    return new ActivityDto(ACTIVITY_NAME_CANNOT_BE_EMPTY);
                }
                case SELECTED_VEHICLES_ARE_NOT_AVAILABLE: {
                    return new ActivityDto(SELECTED_VEHICLES_ARE_NOT_AVAILABLE);
                }
                case USER_NOT_FOUND: {
                    return new ActivityDto(USER_NOT_FOUND);
                }
                case THE_NEW_ACTIVITY_IS_IN_CONFLICTS_WITH_ANOTHER_ACTIVITY: {
                    return new ActivityDto(THE_NEW_ACTIVITY_IS_IN_CONFLICTS_WITH_ANOTHER_ACTIVITY);
                }
                case USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + CREATE_AN_ACTIVITY_IN_THIS_PROJECT: {
                    return new ActivityDto((USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + CREATE_AN_ACTIVITY_IN_THIS_PROJECT));
                }
                default:
                    return null;
            }
        }
    }

    public ActivityDto updateActivity(RequestActivityDto updatedActivityDto) {
        try {
            Preconditions.checkArgument(StringUtils.isNotEmpty(updatedActivityDto.getName()), ACTIVITY_NAME_CANNOT_BE_EMPTY);
            Preconditions.checkArgument(updatedActivityDto.getVehiclesIds() == null || vehicleService.checkVehiclesExistence(updatedActivityDto.getVehiclesIds()),
                    SELECTED_VEHICLES_ARE_NOT_AVAILABLE);
            User user = tokenRepository.getUserByToken(updatedActivityDto.getToken());
            Preconditions.checkNotNull(user, USER_NOT_FOUND);
            Activity activity = activityRepository.findById(updatedActivityDto.getId()).get();
            //check if the user is an admin of the activity
            Preconditions.checkArgument(activity.getAdmins().contains(user), USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + UPDATE_THIS_ACTIVITY);

            //different cases based on the type of request (if it's an activityProject or an activityPost creation)

            //activityProject case
            if (updatedActivityDto.getProjectId() != null) {
                Project project = projectRepository.findById(updatedActivityDto.getProjectId()).get();
                //check if the project contains that activity
                boolean isContained = false;
                Iterator<ActivityProject> activityIterator = project.getActivities().iterator();
                while (activityIterator.hasNext() && !isContained) {
                    if (activityIterator.next().getId().equals(activity.getId())) {
                        isContained = true;
                    }
                }
                //check that the activity matches with its project
                Preconditions.checkArgument(isContained, THE_ACTIVITY_IS_NOT_PART_OF_THE_PROJECT);
                //check if dates have been updated or not
                if (!activity.getBeginDate().equals(updatedActivityDto.getBeginDate()) || !activity.getEndDate().equals(updatedActivityDto.getEndDate())) {
                    //if the dates have been updated, i check the conflict with other project's activities
                    List<ActivityProject> existingActivities = projectRepository.getProjectActivitiesById(activity.getId());
                    Preconditions.checkArgument(ActivityUtil.isNotInConflict(updatedActivityDto, existingActivities),
                            NEW_ACTIVITY_DATES_ARE_IN_CONFLICT_WITH_OTHER_ACTIVITIES);
                }
            }

            activity.setName(updatedActivityDto.getName());
            if (StringUtils.isNotEmpty(updatedActivityDto.getDescription())) {
                activity.setDescription(updatedActivityDto.getDescription());
            }
            activity.setBeginDate(updatedActivityDto.getBeginDate());
            activity.setEndDate(updatedActivityDto.getEndDate());
            activityRepository.save(activity);
            return new ActivityDto(activity);

        } catch (Exception e) {
            switch (e.getMessage()) {
                case ACTIVITY_NAME_CANNOT_BE_EMPTY: {
                    return new ActivityDto(ACTIVITY_NAME_CANNOT_BE_EMPTY);
                }
                case SELECTED_VEHICLES_ARE_NOT_AVAILABLE: {
                    return new ActivityDto(SELECTED_VEHICLES_ARE_NOT_AVAILABLE);
                }
                case USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + UPDATE_THIS_ACTIVITY: {
                    return new ActivityDto(USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + UPDATE_THIS_ACTIVITY);
                }
                case THE_ACTIVITY_IS_NOT_PART_OF_THE_PROJECT: {
                    return new ActivityDto(THE_ACTIVITY_IS_NOT_PART_OF_THE_PROJECT);
                }
                case NEW_ACTIVITY_DATES_ARE_IN_CONFLICT_WITH_OTHER_ACTIVITIES: {
                    return new ActivityDto(NEW_ACTIVITY_DATES_ARE_IN_CONFLICT_WITH_OTHER_ACTIVITIES);
                }
                case USER_NOT_FOUND: {
                    return new ActivityDto(USER_NOT_FOUND);
                }
                default:
                    return null;
            }
        }
    }

    public List<ActivityDto> getActivitiesFromProject(ProjectActivitiesRequest projectActivitiesRequest) {
        try {
            User user = tokenRepository.getUserByToken(projectActivitiesRequest.getToken());
            Preconditions.checkNotNull(user, USER_NOT_FOUND);
            Preconditions.checkNotNull(projectActivitiesRequest.getProjectId(), THE_PROJECT_ID_CANNOT_BE_NULL);
            List<ActivityProject> projectActivities = projectRepository.getProjectActivitiesById(projectActivitiesRequest.getProjectId());
            Preconditions.checkArgument(!projectActivities.isEmpty(), THIS_PROJECT_HAS_NO_ACTIVITIES);
            Iterator<ActivityProject> activityCounter = projectActivities.listIterator();
            List<ActivityDto> returnList = List.of();
            while (activityCounter.hasNext()) {
                returnList.add(new ActivityDto(activityCounter.next()));
            }
            return returnList;
        } catch (Exception e) {
            switch (e.getMessage()) {
                case USER_NOT_FOUND: {
                    return List.of(new ActivityDto(USER_NOT_FOUND));
                }
                case THE_PROJECT_ID_CANNOT_BE_NULL: {
                    return List.of(new ActivityDto(THE_PROJECT_ID_CANNOT_BE_NULL));
                }
                case THIS_PROJECT_HAS_NO_ACTIVITIES: {
                    return List.of();
                }
                default:
                    return null;
            }
        }
    }

    public String deleteActivity(ProjectActivitiesRequest deleteDto) {
        try {
            Preconditions.checkNotNull(deleteDto.getProjectId(), THE_ACTIVITY_ID_CANNOT_BE_NULL);
            User user = tokenRepository.getUserByToken(deleteDto.getToken());
            Preconditions.checkNotNull(user, USER_NOT_FOUND);
            Activity activity = activityRepository.findById(deleteDto.getProjectId()).get();
            Preconditions.checkArgument(activity.getCreators().contains(user), USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + DELETE_THIS_ACTIVITY);
            activity.setVisible(false);
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
                case USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + DELETE_THIS_ACTIVITY: {
                    return USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + DELETE_THIS_ACTIVITY;
                }
                default:
                    return null;
            }
        }
    }

    public String addActivityCreatorPermissions(UpdatePermissionsDto request) {
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

    public String removeActivityCreatorPermissions(UpdatePermissionsDto request) {
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

    public String addActivityAdminPermissions(UpdatePermissionsDto request) {
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

    public String removeActivityAdminPermissions(UpdatePermissionsDto request) {
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

    public String addActivityPartecipants(UpdatePermissionsDto request) {
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

    public String removeActivityPartecipants(UpdatePermissionsDto request) {
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