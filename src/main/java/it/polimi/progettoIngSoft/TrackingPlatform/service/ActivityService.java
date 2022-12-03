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

    //TODO refactor the permission check by contains method
    public ActivityDto createActivity(RequestActivityDto requestActivityDto) {
        try {
            //check the minimum required field to create an activity
            Preconditions.checkArgument(StringUtils.isNotEmpty(requestActivityDto.getName()), "activity name cannot be empty");
            Preconditions.checkArgument(
                    requestActivityDto.getVehiclesIds() == null || vehicleService.checkVehiclesExistence(requestActivityDto.getVehiclesIds()),
                    "vehicles selected are wrong");
            User user = tokenRepository.getUserByToken(requestActivityDto.getToken());
            Preconditions.checkNotNull(user, "user not found");
            //different cases based on the type of request (if it's an activityProject or an activityPost creation)

            //activityProject case
            if (requestActivityDto.getProjectId() != null && requestActivityDto.getProjectId() > 0) {
                Project project = projectRepository.findById(requestActivityDto.getProjectId()).get();
                //check if the user is an admin of the activity's project
                Preconditions.checkArgument(project.getAdmins().contains(user), "you don't have permissions to create an activity in this project");
                List<ActivityProject> existingActivities = projectRepository.getProjectActivitiesById(requestActivityDto.getProjectId());
                Preconditions.checkArgument(ActivityUtil.isNotInConflict(requestActivityDto, existingActivities), "the new activity is in conflict with another activity");

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
                case "activity name cannot be empty": {
                    return new ActivityDto("activity name cannot be empty");
                }
                case "vehicles selected are wrong": {
                    return new ActivityDto("vehicles selected are wrong");
                }
                case "user not found": {
                    return new ActivityDto("user not found");
                }
                case "the new activity is in conflict with another activity": {
                    return new ActivityDto("the new activity is in conflict with another activity");
                }
                case "you don't have permissions to create an activity in this project": {
                    return new ActivityDto(("you don't have permissions to create an activity in this project"));
                }
                default:
                    return null;
            }
        }
    }

    public ActivityDto updateActivity(RequestActivityDto updatedActivityDto) {
        try {
            Preconditions.checkArgument(StringUtils.isNotEmpty(updatedActivityDto.getName()), "activity name cannot be empty");
            Preconditions.checkArgument(updatedActivityDto.getVehiclesIds() == null || vehicleService.checkVehiclesExistence(updatedActivityDto.getVehiclesIds()),
                    "selected vehicles are not available");
            User user = tokenRepository.getUserByToken(updatedActivityDto.getToken());
            Preconditions.checkNotNull(user, "user not found");
            Activity activity = activityRepository.findById(updatedActivityDto.getId()).get();
            //check if the user is an admin of the activity
            Preconditions.checkArgument(activity.getAdmins().contains(user), "you don't have permissions to update this activity");

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
                Preconditions.checkArgument(isContained, "the activity is not part of the project");
                //check if dates have been updated or not
                if (!activity.getBeginDate().equals(updatedActivityDto.getBeginDate()) || !activity.getEndDate().equals(updatedActivityDto.getEndDate())) {
                    //if the dates have been updated, i check the conflict with other project's activities
                    List<ActivityProject> existingActivities = projectRepository.getProjectActivitiesById(activity.getId());
                    Preconditions.checkArgument(ActivityUtil.isNotInConflict(updatedActivityDto, existingActivities),
                            "new activity dates are in conflict with other activities");
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
                case "activity name cannot be empty": {
                    return new ActivityDto("activity name cannot be empty");
                }
                case "selected vehicles are not available": {
                    return new ActivityDto("selected vehicles are not available");
                }
                case "you don't have permissions to update this activity": {
                    return new ActivityDto("you don't have permissions to update this activity");
                }
                case "the activity is not part of the project": {
                    return new ActivityDto("the activity is not part of the project");
                }
                case "new activity dates are in conflict with other activities": {
                    return new ActivityDto("new activity dates are in conflict with other activities");
                }
                case "user not found": {
                    return new ActivityDto("user not found");
                }
                default:
                    return null;
            }
        }
    }

    public List<ActivityDto> getActivitiesFromProject(ProjectActivitiesRequest projectActivitiesRequest) {
        try {
            User user = tokenRepository.getUserByToken(projectActivitiesRequest.getToken());
            Preconditions.checkNotNull(user, "user not found");
            Preconditions.checkNotNull(projectActivitiesRequest.getProjectId(), "the project id cannot be null");
            List<ActivityProject> projectActivities = projectRepository.getProjectActivitiesById(projectActivitiesRequest.getProjectId());
            Preconditions.checkArgument(!projectActivities.isEmpty(), "this project has no activities");
            Iterator<ActivityProject> activityCounter = projectActivities.listIterator();
            List<ActivityDto> returnList = List.of();
            while (activityCounter.hasNext()) {
                returnList.add(new ActivityDto(activityCounter.next()));
            }
            return returnList;
        } catch (Exception e) {
            switch (e.getMessage()) {
                case "user not found": {
                    return List.of(new ActivityDto("user not found"));
                }
                case "the project id cannot be null": {
                    return List.of(new ActivityDto("the project id cannot be null"));
                }
                case "this project has no activities": {
                    return List.of();
                }
                default:
                    return null;
            }
        }
    }

    public String deleteActivity(ProjectActivitiesRequest deleteDto) {
        try {
            Preconditions.checkNotNull(deleteDto.getProjectId(), "the activity id cannot be null");
            User user = tokenRepository.getUserByToken(deleteDto.getToken());
            Preconditions.checkNotNull(user, "user not found");
            Activity activity = activityRepository.findById(deleteDto.getProjectId()).get();
            Preconditions.checkArgument(activity.getCreators().contains(user), "you don't have the permission to delete this activity");
            activity.setVisible(false);
            activityRepository.save(activity);
            return "";
        } catch (Exception e) {
            switch (e.getMessage()) {
                case "the activity id cannot be null": {
                    return "the activity id cannot be null";
                }
                case "user not found": {
                    return "user not found";
                }
                case "you don't have the permission to delete this activity": {
                    return "you don't have the permission to delete this activity";
                }
                default:
                    return null;
            }
        }
    }

    public String addActivityCreatorPermissions(UpdatePermissionsDto request) {
        try {
            Preconditions.checkNotNull(request.getActivityId(), "the activity id cannot be null");
            Preconditions.checkArgument(!request.getUsernames().isEmpty(), "the list of usernames is empty");
            User user = tokenRepository.getUserByToken(request.getToken());
            Preconditions.checkNotNull(user, "user not found");
            Activity activity = activityRepository.findById(request.getActivityId()).get();
            //check if the user has permissions to add a creator
            Preconditions.checkArgument(activity.getCreators().contains(user), "you don't have the permission to add creators to this activity");
            //the system ignores if the user wants to add a creator that already exists

            //eliminates duplicate strings from the list of usernames
            List<String> usernames = request.getUsernames().stream().distinct().toList();
            //check that every username exists
            Preconditions.checkArgument(userRepository.countByUsernames(usernames).equals(Integer.valueOf(usernames.size())),
                    "not every specified username exists");

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
                case "the activity id cannot be null": {
                    return "the activity id cannot be null";
                }
                case "user not found": {
                    return "user not found";
                }
                case "you don't have the permission to add creators to this activity": {
                    return "you don't have the permission to add creators to this activity";
                }
                case "the list of usernames is empty": {
                    return "the list of usernames is empty";
                }
                case "not every specified username exists": {
                    return "not every specified username exists";
                }
                default:
                    return null;
            }
        }
    }

    public String removeActivityCreatorPermissions(UpdatePermissionsDto request) {
        try {
            Preconditions.checkNotNull(request.getActivityId(), "the activity id cannot be null");
            User user = tokenRepository.getUserByToken(request.getToken());
            Preconditions.checkNotNull(user, "user not found");
            Activity activity = activityRepository.findById(request.getActivityId()).get();
            //check if the user has permissions to add a creator
            Preconditions.checkArgument(activity.getCreators().contains(user), "you don't have the permission to remove creators from this activity");

            //eliminates duplicate strings from the list of usernames
            List<String> usernames = request.getUsernames().stream().distinct().toList();
            //check that every username exists
            Preconditions.checkArgument(userRepository.countByUsernames(usernames).equals(Integer.valueOf(usernames.size())),
                    "not every specified username exists");
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
                case "the activity id cannot be null": {
                    return "the activity id cannot be null";
                }
                case "user not found": {
                    return "user not found";
                }
                case "you don't have the permission to remove creators from this activity": {
                    return "you don't have the permission to remove creators from this activity";
                }
                default:
                    return null;
            }
        }
    }

    public String addActivityAdminPermissions(UpdatePermissionsDto request) {
        try {
            Preconditions.checkNotNull(request.getActivityId(), "the activity id cannot be null");
            Preconditions.checkArgument(!request.getUsernames().isEmpty(), "the list of usernames is empty");
            User user = tokenRepository.getUserByToken(request.getToken());
            Preconditions.checkNotNull(user, "user not found");
            Activity activity = activityRepository.findById(request.getActivityId()).get();
            //check if the user has permissions to add an admin
            Preconditions.checkArgument(activity.getAdmins().contains(user), "you don't have the permission to add admins to this activity");
            //the system ignores if the user wants to add an admin that already exists

            //eliminates duplicate strings from the list of usernames
            List<String> usernames = request.getUsernames().stream().distinct().toList();
            //check that every username exists
            Preconditions.checkArgument(userRepository.countByUsernames(usernames).equals(Integer.valueOf(usernames.size())),
                    "not every specified username exists");

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
                case "the activity id cannot be null": {
                    return "the activity id cannot be null";
                }
                case "user not found": {
                    return "user not found";
                }
                case "you don't have the permission to add admins to this activity": {
                    return "you don't have the permission to add admins to this activity";
                }
                case "the list of usernames is empty": {
                    return "the list of usernames is empty";
                }
                case "not every specified username exists": {
                    return "not every specified username exists";
                }
                default:
                    return null;
            }
        }
    }

    public String removeActivityAdminPermissions(UpdatePermissionsDto request) {
        try {
            Preconditions.checkNotNull(request.getActivityId(), "the activity id cannot be null");
            User user = tokenRepository.getUserByToken(request.getToken());
            Preconditions.checkNotNull(user, "user not found");
            Activity activity = activityRepository.findById(request.getActivityId()).get();
            //check if the user has permissions to remove an admin
            Preconditions.checkArgument(activity.getAdmins().contains(user), "you don't have the permission to remove admins from this activity");

            //eliminates duplicate strings from the list of usernames
            List<String> usernames = request.getUsernames().stream().distinct().toList();
            //check that every username exists
            Preconditions.checkArgument(userRepository.countByUsernames(usernames).equals(Integer.valueOf(usernames.size())),
                    "not every specified username exists");

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
                case "the activity id cannot be null": {
                    return "the activity id cannot be null";
                }
                case "user not found": {
                    return "user not found";
                }
                case "you don't have the permission to remove admins from this activity": {
                    return "you don't have the permission to remove admins from this activity";
                }
                default:
                    return null;
            }
        }
    }

    public String addActivityPartecipants(UpdatePermissionsDto request) {
        try {
            Preconditions.checkNotNull(request.getActivityId(), "the activity id cannot be null");
            Preconditions.checkArgument(!request.getUsernames().isEmpty(), "the list of usernames is empty");
            User user = tokenRepository.getUserByToken(request.getToken());
            Preconditions.checkNotNull(user, "user not found");
            Activity activity = activityRepository.findById(request.getActivityId()).get();
            //check if the user has permissions to add a partecipant
            Preconditions.checkArgument(activity.getAdmins().contains(user), "you don't have the permission to add partecipants to this activity");

            //eliminates duplicate strings from the list of usernames
            List<String> usernames = request.getUsernames().stream().distinct().toList();
            //check that every username exists
            Preconditions.checkArgument(userRepository.countByUsernames(usernames).equals(Integer.valueOf(usernames.size())),
                    "not every specified username exists");

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
                case "the activity id cannot be null": {
                    return "the activity id cannot be null";
                }
                case "user not found": {
                    return "user not found";
                }
                case "you don't have the permission to add partecipants to this activity": {
                    return "you don't have the permission to add partecipants to this activity";
                }
                case "the list of usernames is empty": {
                    return "the list of usernames is empty";
                }
                case "not every specified username exists": {
                    return "not every specified username exists";
                }
                default:
                    return null;
            }
        }
    }

    public String removeActivityPartecipants(UpdatePermissionsDto request) {
        try {
            Preconditions.checkNotNull(request.getActivityId(), "the activity id cannot be null");
            User user = tokenRepository.getUserByToken(request.getToken());
            Preconditions.checkNotNull(user, "user not found");
            Activity activity = activityRepository.findById(request.getActivityId()).get();
            //check if the user has permissions to remove a partecipant
            Preconditions.checkArgument(activity.getAdmins().contains(user), "you don't have the permission to remove partecipants from this activity");

            //eliminates duplicate strings from the list of usernames
            List<String> usernames = request.getUsernames().stream().distinct().toList();
            //check that every username exists
            Preconditions.checkArgument(userRepository.countByUsernames(usernames).equals(Integer.valueOf(usernames.size())),
                    "not every specified username exists");

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
                case "the activity id cannot be null": {
                    return "the activity id cannot be null";
                }
                case "user not found": {
                    return "user not found";
                }
                case "you don't have the permission to remove partecipants from this activity": {
                    return "you don't have the permission to remove partecipants from this activity";
                }
                default:
                    return null;
            }
        }
    }
}